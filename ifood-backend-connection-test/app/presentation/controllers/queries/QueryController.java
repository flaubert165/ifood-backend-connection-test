package presentation.controllers.queries;

import business.queries.QueryService;
import domain.dto.filters.FilterDto;
import domain.entities.User;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public abstract class QueryController<T extends FilterDto, TService extends QueryService<T>> extends Controller {

    private Class<T> tClass;
    private TService service;

    protected QueryController(Class<T> tClass, TService service) {
        this.tClass = tClass;
        this.service = service;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result filter() throws Exception {
        return ok(Json.toJson(this.service.filter(Json.fromJson(request().body().asJson(), tClass), (User) Http.Context.current().args.get("user"))));
    }
}