package business;

import com.google.inject.Inject;
import domain.dto.inputs.SignInDto;
import domain.dto.outputs.UserOutputDto;
import domain.entities.User;
import exceptions.AuthenticationException;
import exceptions.UserException;
import infrastructure.MqttService;
import infrastructure.repositories.IAuthenticationRepository;
import io.reactivex.disposables.Disposable;
import org.eclipse.paho.client.mqttv3.*;
import play.libs.Json;
import utils.SecurityHelper;
import utils.TimeIntervalHelper;

import java.util.Arrays;
import java.util.Observable;

public class AuthenticationService {

    private IAuthenticationRepository _repository;
    private MqttService _mqttService;

    @Inject
    public AuthenticationService(MqttService mqttService,
                                 IAuthenticationRepository authRepository){
        this._repository = authRepository;
        this._mqttService = mqttService;

    }

    public UserOutputDto login(SignInDto dto) throws Exception {

        UserOutputDto userOutput = new UserOutputDto();

        User user = _repository.getUserByEmail(dto.getEmail());

        userOutput.setId(user.getId());
        userOutput.setName(user.getName());
        userOutput.setStatus(TimeIntervalHelper.verifyStatusWhenSignIn());

        if(SecurityHelper.checkPassword(dto.getPassword(), user.getPassword())){

            userOutput.setToken(SecurityHelper.generateSessionToken(user.getEmail()));

            _repository.login(user.getId(), userOutput.getStatus());

            this._mqttService.publish("restaurants/online/",
                    Json.stringify(Json.toJson(userOutput)).getBytes(), 1);

        }else {
            throw new AuthenticationException();
        }

        return userOutput;
    }

    public void logout() throws AuthenticationException, UserException {

            /*ADICIONAR OBSERVER PARA DESLOGAR VIA MQTT*/

            this._mqttService.observer("restaurants/offline/").subscribe(mqttMessage -> {
                System.out.println(Arrays.toString(mqttMessage.getPayload()));
            });

            /*UserOutputDto user = _repository.getUserById(id);

            this._repository.logout(user.getId(), TimeIntervalHelper.verifyStatusWhenCreateAndLogout());

            this._repository.getUserById(user.getId());

            this._mqttService.publish("restaurants/offline/",
                Json.stringify(Json.toJson(user)).getBytes(), 1);*/
    }

}
