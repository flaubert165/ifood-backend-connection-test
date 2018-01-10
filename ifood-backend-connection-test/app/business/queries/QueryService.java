package business.queries;

import domain.dto.filters.FilterDto;
import domain.entities.User;

public interface QueryService<T extends FilterDto> {
    Object filter(T dto, User user) throws Exception;
}
