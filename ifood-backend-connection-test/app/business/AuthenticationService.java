package business;

import com.google.inject.Inject;
import domain.dto.inputs.SignInDto;
import domain.dto.outputs.UnavailabilityScheduleOutputDto;
import domain.dto.outputs.UserOutputDto;
import domain.entities.User;
import exceptions.AuthenticationException;
import exceptions.UserException;
import presentation.MqttService;
import infrastructure.repositories.IAuthenticationRepository;
import play.libs.Json;
import utils.SecurityHelper;
import utils.TimeIntervalHelper;
import java.util.List;

public class AuthenticationService {

    private IAuthenticationRepository _repository;
    private MqttService _mqttService;

    @Inject
    public AuthenticationService(MqttService mqttService,
                                 IAuthenticationRepository repository){
        this._repository = repository;
        this._mqttService = mqttService;

        this._mqttService.observer("restaurants/logout/").subscribe(mqttMessage -> {

            if(mqttMessage != null) {
                long id = new Long(mqttMessage.toString());
                this.logout(id);
            }
        });
    }

    public UserOutputDto login(SignInDto dto) throws Exception {

        UserOutputDto userOutput = new UserOutputDto();

        User user = _repository.getUserByEmail(dto.getEmail());

        if(SecurityHelper.checkPassword(dto.getPassword(), user.getPassword())){

            List<UnavailabilityScheduleOutputDto> schedules = _repository.getUnavailabilityScheduleById(user.getId());

            userOutput.setId(user.getId());
            userOutput.setName(user.getName());
            userOutput.setStatus(TimeIntervalHelper.verifyStatusWhenSignIn());

            userOutput.setToken(SecurityHelper.generateSessionToken(user.getEmail()));

            _repository.login(user.getId(), TimeIntervalHelper.verifyStatus(schedules));

            this._mqttService.publish("restaurants/online/",
                    Json.stringify(Json.toJson(userOutput)).getBytes(), 1);

        }else {
            throw new AuthenticationException();
        }

        return userOutput;
    }

    public void logout(long id) throws AuthenticationException, UserException {

            UserOutputDto user = this._repository.getUserById(id);

            this._repository.logout(user.getId(), TimeIntervalHelper.verifyStatusWhenCreateAndLogout());

            user.setStatus(TimeIntervalHelper.verifyStatusWhenCreateAndLogout());

            this._mqttService.publish("restaurants/offline/",
                Json.stringify(Json.toJson(user)).getBytes(), 1);
    }

}
