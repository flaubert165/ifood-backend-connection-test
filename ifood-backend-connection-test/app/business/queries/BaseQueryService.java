package business.queries;

import com.baidu.unbiz.fluentvalidator.Result;
import domain.dto.filters.FilterDto;
import domain.entities.User;
import infrastructure.repositories.queries.IQueryRepository;
import sun.security.validator.ValidatorException;
import java.util.List;

import static business.validator.ValidatorHelper.throwException;

public abstract class BaseQueryService<T extends FilterDto, TResult, TQueryRepository extends IQueryRepository<T, TResult>> implements QueryService<T> {

    private TQueryRepository _repository;

    protected BaseQueryService(TQueryRepository repository) {
        this._repository = repository;
    }

    @Override
    public Object filter(T dto, User user) throws ValidatorException {
        Result result = validator(dto);
        if (result != null) {
            throwException(result);
        }

        return transform(this._repository.filter(dto, user));
    }

    protected Object transform(List<TResult> results) {
        return results;
    }

    protected Result validator(T dto) {
        return null;
    }
}
