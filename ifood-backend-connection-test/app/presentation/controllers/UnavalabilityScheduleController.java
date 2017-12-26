package presentation.controllers;

import business.UnavailabilityScheduleService;
import com.google.inject.Inject;
import domain.dto.inputs.UnavailabilityScheduleDto;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class UnavalabilityScheduleController extends Controller {

    private UnavailabilityScheduleService _service;

    @Inject
    public UnavalabilityScheduleController(UnavailabilityScheduleService service){
        this._service = service;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result create() throws Exception {
        this._service.create(Json.fromJson(request().body().asJson(), UnavailabilityScheduleDto.class));
        return ok();
    }

    public Result getByUserId(long userId) throws Exception {
        return  ok(Json.toJson(this._service.getByUserId(userId)));
    }

    public Result delete(long id) throws Exception{
        this._service.delete(id);
        return ok();
    }

}
