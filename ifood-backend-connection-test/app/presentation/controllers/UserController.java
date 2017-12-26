package presentation.controllers;

import business.UserService;
import com.google.inject.Inject;
import domain.dto.inputs.UserDto;
import exceptions.UserException;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class UserController extends Controller {

    private UserService _service;

    @Inject
    public UserController(UserService service){this._service = service;}

    @BodyParser.Of(BodyParser.Json.class)
    public Result create() throws UserException {
        this._service.create(Json.fromJson(request().body().asJson(), UserDto.class));
        return ok();
    }

    public Result getAll() throws UserException {
/*        response().setHeader(CONNECTION, "Keep-Alive");
        response().setHeader("Keep-Alive", "timeout=2, max=10");*/
        return ok(Json.toJson(this._service.getUsers()));
    }
}
