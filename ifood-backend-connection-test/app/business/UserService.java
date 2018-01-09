package business;

import com.google.inject.Inject;
import domain.dto.inputs.UserDto;
import domain.dto.outputs.UnavailabilityScheduleOutputDto;
import domain.dto.outputs.UserOutputDto;
import domain.entities.User;
import domain.enums.Status;
import exceptions.AuthenticationException;
import exceptions.UserException;
import infrastructure.MqttService;
import infrastructure.repositories.IUserRepository;
import play.libs.Json;
import utils.SecurityHelper;
import utils.TimeIntervalHelper;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;


public class UserService implements IUserRepository{

    private IUserRepository _repository;
    private UnavailabilityScheduleService _schedulesService;
    private MqttService _mqttService;

    @Inject
    public UserService(MqttService mqttService,
                       IUserRepository repository,
                       UnavailabilityScheduleService schedulesService){
        this._repository = repository;
        this._schedulesService = schedulesService;
        this._mqttService = mqttService;

        /**
         * This observer listen the lastRequest channel and trigger de lastRequestUpdate method
         */
        this._mqttService.observer("restaurants/updateLasRequest/").subscribe(mqttMessage -> {
            if(mqttMessage != null) {
                long userId = new Long(mqttMessage.toString());
                this.updateLastRequest(TimeIntervalHelper.localDateTimeToDate(), userId);
            }
        });


        /**
         * This observer verify if exists an unavailability schedule every minute
         */
        io.reactivex.Observable.interval(1, TimeUnit.MINUTES).subscribe(s -> {
            this.verifyStatusPeriodicaly();
        });
    }

    public void create(UserDto user) throws UserException {

        user.setStatus(TimeIntervalHelper.verifyStatusWhenCreateAndLogout());

        if(user != null){
            user.setPassword(SecurityHelper.generatePassword(user.getPassword()));
            this._repository.create(user);
        }else{
            throw new UserException();
        }
    }

    public List<UserOutputDto> getUsers() throws UserException {

        List<UserOutputDto> users = this._repository.getUsers();

        for(UserOutputDto user : users){

            try {

                if(user.getStatus() != Status.AvailableOnline) {

                    List<UnavailabilityScheduleOutputDto> schedules = this._schedulesService.getByUserId(user.getId());

                    user.setStatus(TimeIntervalHelper.verifyStatus(schedules));
                    this._repository.updateStatus(user.getStatus(), user.getId());
                    this.updateOfflineTime(user.getId(), user.getLastRequest());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return users;
    }

    @Override
    public UserOutputDto getUserById(long id) throws UserException {
        return this._repository.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) throws UserException {
        return this._repository.getUserByEmail(email);
    }

    public void delete(long id) throws UserException {
        this._repository.delete(id);
    }

    @Override
    public Date getLastRequest(long id) throws AuthenticationException {
        return this._repository.getLastRequest(id);
    }

    @Override
    public void updateLastRequest(java.util.Date lastRequest, long userId) throws UserException {
        this._repository.updateLastRequest(lastRequest, userId);
    }

    @Override
    public void updateMinutesOffline(long minutes, long userId) throws UserException {
        this._repository.updateMinutesOffline(minutes, userId);
    }

    @Override
    public void updateStatus(Status status, long userId) throws UserException {
        this._repository.updateStatus(status, userId);
    }

    public void updateOfflineTime(long userId, java.util.Date lastRequest) throws Exception{

        java.util.Date now = TimeIntervalHelper.localDateTimeToDate();

        if(lastRequest == null){
            this._repository.updateLastRequest(now, userId);
        }else {
            this._repository.updateLastRequest(now, userId);
            long minutes = TimeIntervalHelper.calculatesOfflineUserTime(lastRequest);
            this._repository.updateMinutesOffline(minutes, userId);
        }

    }

    public void publishStatusMqttMessage(UserOutputDto dto){

        this._mqttService.publish("restaurants/status/",
                Json.stringify(Json.toJson(dto)).getBytes(), 1);
    }

    public void verifyStatusPeriodicaly() throws Exception {

        List<UserOutputDto> users = this._repository.getUsers();

        for(UserOutputDto user : users){

            java.util.Date lastRequest = new java.util.Date(user.getLastRequest().getTime());

            List<UnavailabilityScheduleOutputDto> schedules = this._schedulesService.getByUserId(user.getId());

            if ((schedules != null && schedules.size() > 0) &&
                    TimeIntervalHelper.isBetweenAvailableTime(TimeIntervalHelper.toSqlTime(LocalTime.now()))) {
                user.setStatus(TimeIntervalHelper.verifyStatus(schedules));
                this._repository.updateStatus(user.getStatus(), user.getId());
                publishStatusMqttMessage(user);
            }

            if (TimeIntervalHelper.isBetweenAvailableTime(TimeIntervalHelper.toSqlTime(LocalTime.now())) &&
                        (user.getStatus() == Status.AvailableOnline) &&
                        (TimeIntervalHelper.calculatesOfflineUserTime(lastRequest) >= 2L)){
                user.setStatus(Status.AvailableOffline);
                this._repository.updateStatus(user.getStatus(), user.getId());
                publishStatusMqttMessage(user);
            }
        }


    }

}
