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
import java.time.LocalTime;
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

        /**
         * This observer listen the logout channel and trigger de logout method (keep-alive/idletimeout)
         */
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
            userOutput.setMinutesOffline(user.getMinutesOffline());

            this._userService.updateOfflineTime(user.getId(), user.getLastRequest());

            this.login(user.getId(), userOutput.getStatus());

            publishStatusMqttMessage(userOutput);

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
    public void logout(long id) throws Exception {

            UserOutputDto user = this._userService.getUserById(id);

            user.setStatus(verifyStatusOnLogout(user.getId()));

            logout(user.getId(), user.getStatus());

            publishStatusMqttMessage(user);
    }

    /**
     * check the suitable status
     * @param userId
     * @return status
     * @throws Exception
     */
    public Status verifyStatus(long userId) throws Exception{

        List<UnavailabilityScheduleOutputDto> schedules = _schedulesService.getByUserId(userId);

        if ((schedules == null || schedules.size() == 0) &&
                TimeIntervalHelper.isBetweenAvailableTime(TimeIntervalHelper.toSqlTime(LocalTime.now()))) {
            return Status.AvailableOnline;
        } else{
            return TimeIntervalHelper.verifyStatus(schedules);
        }
    }

    public Status verifyStatusOnLogout(long userId) throws Exception{

        List<UnavailabilityScheduleOutputDto> schedules = _schedulesService.getByUserId(userId);

        if ((schedules == null || schedules.size() == 0) &&
                TimeIntervalHelper.isBetweenAvailableTime(TimeIntervalHelper.toSqlTime(LocalTime.now()))) {
            return Status.AvailableOffline;
        } else{
            return TimeIntervalHelper.verifyStatus(schedules);
        }
    }

    public void publishStatusMqttMessage(UserOutputDto dto){

        this._mqttService.publish("restaurants/status/",
                Json.stringify(Json.toJson(dto)).getBytes(), 1);
    }
}
