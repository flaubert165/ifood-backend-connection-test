package business;

import com.google.inject.Inject;
import domain.dto.inputs.SignInDto;
import domain.dto.outputs.UnavailabilityScheduleOutputDto;
import domain.dto.outputs.UserOutputDto;
import domain.entities.User;
import domain.enums.Status;
import exceptions.AuthenticationException;
import exceptions.UserException;
import presentation.MqttService;
import infrastructure.repositories.IAuthenticationRepository;
import play.libs.Json;
import utils.SecurityHelper;
import utils.TimeIntervalHelper;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

public class AuthenticationService implements IAuthenticationRepository{

    private IAuthenticationRepository _repository;
    private UserService _userService;
    private UnavailabilityScheduleService _schedulesService;
    private MqttService _mqttService;

    @Inject
    public AuthenticationService(MqttService mqttService,
                                 IAuthenticationRepository repository,
                                 UserService userService,
                                 UnavailabilityScheduleService schedulesService){
        this._repository = repository;
        this._userService = userService;
        this._schedulesService = schedulesService;
        this._mqttService = mqttService;

        this._mqttService.observer("restaurants/logout/").subscribe(mqttMessage -> {

            if(mqttMessage != null) {
                long id = new Long(mqttMessage.toString());
                this.logout(id);
            }
        });
    }

    @Override
    public void login(long id, Status status) throws AuthenticationException {
        this._repository.login(id, status);
    }

    @Override
    public void logout(long id, Status status) throws AuthenticationException {
        this._repository.logout(id, status);
    }

    /**
     * login the user
     * @param dto
     * @return userOutput
     * @throws Exception
     */
    public UserOutputDto login(SignInDto dto) throws Exception {

        UserOutputDto userOutput = new UserOutputDto();

        User user = this._userService.getUserByEmail(dto.getEmail());

        if(SecurityHelper.checkPassword(dto.getPassword(), user.getPassword())){

            userOutput.setStatus(verifyStatus(user.getId()));
            userOutput.setId(user.getId());
            userOutput.setName(user.getName());
            userOutput.setToken(SecurityHelper.generateSessionToken(user.getEmail()));

            updateOfflineTime(user.getId(), user.getLastRequest());

            this.login(user.getId(), userOutput.getStatus());

            this._mqttService.publish("restaurants/online/",
                    Json.stringify(Json.toJson(userOutput)).getBytes(), 1);

        }else {
            throw new AuthenticationException();
        }

        return userOutput;
    }

    /**
     * logout the user
     * @param id
     * @throws AuthenticationException
     * @throws UserException
     */
    public void logout(long id) throws AuthenticationException, UserException {

            UserOutputDto user = this._userService.getUserById(id);

            if(TimeIntervalHelper.isBetweenAvailableTime(TimeIntervalHelper.toSqlTime(LocalTime.now()))) {
                this.logout(user.getId(), Status.AvailableOffline);
                user.setStatus(Status.AvailableOffline);
            }else {
                this.logout(user.getId(), Status.UnavailableOffline);
                user.setStatus(Status.UnavailableOffline);
            }

            this._mqttService.publish("restaurants/offline/",
                Json.stringify(Json.toJson(user)).getBytes(), 1);
    }

    /**
     * check the suitable status
     * @param userId
     * @return status
     * @throws Exception
     */
    public Status verifyStatus(long userId) throws Exception{

        List<UnavailabilityScheduleOutputDto> schedules = _schedulesService.getByUserId(userId);

        return schedules == null || schedules.size() == 0 ? Status.AvailableOnline : TimeIntervalHelper.verifyStatus(schedules);
    }

    /**
     * check how long user has gone offline and update this indicator
     * @param userId
     * @throws AuthenticationException
     */
    public void updateOfflineTime(long userId, java.util.Date lastRequest) throws Exception{

        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);;

        if(lastRequest == null){
            _userService.updateLastRequest(now, userId);
        }else {
            _userService.updateLastRequest(now, userId);
            long minutes = TimeIntervalHelper.calculatesOfflineUserTime(lastRequest);
            _userService.updateMinutesOffline(minutes, userId);
        }

    }
}
