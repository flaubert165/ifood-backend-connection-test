package infrastructure.repositories;

import domain.dto.inputs.UnavailabilityScheduleDto;
import domain.dto.outputs.UnavailabilityScheduleOutputDto;
import domain.entities.UnavailabilitySchedule;

import java.util.List;

public interface IUnavailabilityScheduleRepository {
    void create(UnavailabilityScheduleDto schedule) throws Exception;
    UnavailabilityScheduleOutputDto getById(long id) throws Exception;
    List<UnavailabilityScheduleOutputDto> getByUserId(long userId) throws Exception;
    List<UnavailabilityScheduleOutputDto> getByAllByUserId(long userId) throws Exception;
    void delete(long id) throws Exception;
}
