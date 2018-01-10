package infrastructure.repositories.queries;

import domain.dto.filters.UserFilterDto;
import domain.dto.outputs.UserOutputDto;

public interface IUserQueryRepository extends IQueryRepository<UserFilterDto, UserOutputDto>{
}
