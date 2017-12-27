package business;

import com.google.inject.Inject;
import domain.dto.inputs.UnavailabilityScheduleDto;
import domain.dto.outputs.UnavailabilityScheduleOutputDto;
import infrastructure.repositories.IUnavailabilityScheduleRepository;
import utils.TimeIntervalHelper;

import java.util.List;

public class UnavailabilityScheduleService implements IUnavailabilityScheduleRepository{

    private IUnavailabilityScheduleRepository _repository;

    @Inject
    public UnavailabilityScheduleService(IUnavailabilityScheduleRepository repository){
        this._repository = repository;
    }

    public void create(UnavailabilityScheduleDto schedule) throws Exception {

        if(schedule ==  null){
            throw new Exception();
        }

        this._repository.create(schedule);
    }

    @Override
    public UnavailabilityScheduleOutputDto getById(long id) throws Exception {
        return this._repository.getById(id);
    }

    public List<UnavailabilityScheduleOutputDto> getByUserId(long id) throws Exception {

       return  this._repository.getByUserId(id);
    }

    public void delete(long id) throws Exception {

        UnavailabilityScheduleOutputDto schedule = this._repository.getById(id);

        this._repository.delete(id);

    }
}
