package infrastructure.repositories.queries;

import domain.dto.filters.FilterDto;
import domain.entities.User;
import java.util.List;

public interface IQueryRepository <T extends FilterDto, TResult> {
    List<TResult> filter(T dto, User user);
}
