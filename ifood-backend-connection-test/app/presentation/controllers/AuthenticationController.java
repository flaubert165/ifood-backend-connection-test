package presentation.controllers;

import business.AuthenticationService;
import com.google.inject.Inject;
import domain.dto.inputs.SignInDto;
import exceptions.AuthenticationException;
import exceptions.UserException;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class AuthenticationController extends Controller {

    private AuthenticationService _service;

    @Inject
    public AuthenticationController(AuthenticationService service){
        this._service = service;
    }

    public Result status() throws Exception {
        return ok(Json.toJson(Http.Status.OK));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result login() throws Exception {
        return ok(Json.toJson(this._service.login(Json.fromJson(request().body().asJson(), SignInDto.class))));
    }
}
