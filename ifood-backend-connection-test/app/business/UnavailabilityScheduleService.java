package business;

import com.google.inject.Inject;
import domain.dto.inputs.UnavailabilityScheduleDto;
import domain.dto.outputs.UnavailabilityScheduleOutputDto;
import infrastructure.repositories.IUnavailabilityScheduleRepository;
import utils.TimeIntervalHelper;

import java.util.List;

public class UnavailabilityScheduleService {

    private IUnavailabilityScheduleRepository _repository;

    @Inject
    public UnavailabilityScheduleService(IUnavailabilityScheduleRepository repository){
        this._repository = repository;
    }

    public void create(UnavailabilityScheduleDto schedule) throws Exception {

        if(schedule ==  null){
            throw new Exception();
        }

        /*if(TimeIntervalHelper.isBetweenAvailableTime(schedule.getStart(), schedule.getEnd())) {

            long minutesOffline = TimeIntervalHelper.
                    calculateUnavailabiltyDuration(schedule.getStart().toInstant(),
                            schedule.getEnd().toInstant());
*/

            this._repository.create(schedule);

        /*    this._repository.sumUserMinutesOffline(minutesOffline, schedule.getUserId());
        }*/
    }

    public List<UnavailabilityScheduleOutputDto> getByUserId(long id) throws Exception {

       return  this._repository.getByUserId(id);
    }

    public void delete(long id) throws Exception {

        UnavailabilityScheduleOutputDto schedule = this._repository.getById(id);

        /*long minutesOffline = TimeIntervalHelper.
                calculateUnavailabiltyDuration(schedule.getStart().toInstant(),
                        schedule.getEnd().toInstant());*/

        this._repository.delete(id);

        /*this._repository.subtractUserMinutesOffline(minutesOffline, schedule.getUserId());*/

    }
}
