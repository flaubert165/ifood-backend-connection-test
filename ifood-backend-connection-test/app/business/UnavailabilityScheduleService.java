package business;

import com.google.inject.Inject;
import domain.dto.inputs.UnavailabilityScheduleDto;
import domain.dto.outputs.UnavailabilityScheduleOutputDto;
import infrastructure.repositories.IUnavailabilityScheduleRepository;
import utils.TimeIntervalHelper;

import java.util.List;

public class UnavailabilityScheduleService implements IUnavailabilityScheduleRepository {

    private IUnavailabilityScheduleRepository _repository;

    @Inject
    public UnavailabilityScheduleService(IUnavailabilityScheduleRepository repository){
        this._repository = repository;
    }

    @Override
    public void create(UnavailabilityScheduleDto schedule) throws Exception {

        if(schedule ==  null){
            throw new Exception();
        }

        if(TimeIntervalHelper.isBetweenAvailableTime(schedule.getStart(), schedule.getEnd())) {

            long minutesOffline = TimeIntervalHelper.
                    calculateUnavailabiltyDuration(schedule.getStart().toInstant(),
                            schedule.getEnd().toInstant());


            this._repository.create(schedule);

            this._repository.sumUserMinutesOffline(minutesOffline, schedule.getUserId());
        }
    }



    @Override
    public List<UnavailabilityScheduleOutputDto> getByUserId(long id) throws Exception {

       return  this._repository.getByUserId(id);
    }

    @Override
    public void delete(long id) throws Exception {

        UnavailabilityScheduleOutputDto schedule = this._repository.getById(id);

        long minutesOffline = TimeIntervalHelper.
                calculateUnavailabiltyDuration(schedule.getStart().toInstant(),
                        schedule.getEnd().toInstant());

        this._repository.delete(id);

        this._repository.subtractUserMinutesOffline(minutesOffline, schedule.getUserId());

    }

    @Override
    public void sumUserMinutesOffline(long minutes, long userId) throws Exception {

        this._repository.sumUserMinutesOffline(minutes, userId);
    }

    @Override
    public void subtractUserMinutesOffline(long minutes, long userId) throws Exception {

        this._repository.subtractUserMinutesOffline(minutes, userId);
    }

    @Override
    public UnavailabilityScheduleOutputDto getById(long id) throws Exception {

        return this._repository.getById(id);
    }
}
