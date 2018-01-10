package business.queries;

import com.google.inject.Inject;
import domain.dto.filters.UserFilterDto;
import domain.dto.outputs.UserOutputDto;
import infrastructure.repositories.queries.IUserQueryRepository;

public class UserQueryService extends BaseQueryService<UserFilterDto, UserOutputDto, IUserQueryRepository>{

    @Inject
    protected UserQueryService(IUserQueryRepository repository) {
        super(repository);
    }
}
