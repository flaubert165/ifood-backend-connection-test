package presentation.controllers.queries;

import business.queries.UserQueryService;
import com.google.inject.Inject;
import domain.dto.filters.UserFilterDto;

public class UserQueryController extends QueryController<UserFilterDto, UserQueryService>{
    @Inject
    public UserQueryController(UserQueryService service){
        super(UserFilterDto.class, service);
    }
}
