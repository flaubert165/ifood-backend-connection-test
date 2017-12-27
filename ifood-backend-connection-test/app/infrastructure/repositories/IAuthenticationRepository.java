package infrastructure.repositories;

import domain.dto.outputs.UnavailabilityScheduleOutputDto;
import domain.dto.outputs.UserOutputDto;
import domain.entities.User;
import domain.enums.Status;
import exceptions.AuthenticationException;
import exceptions.UserException;

import java.sql.Date;
import java.util.List;

public interface IAuthenticationRepository {
    void login(long id, Status status) throws AuthenticationException;
    void logout(long id, Status status) throws AuthenticationException;
    UserOutputDto getUserById(long id) throws UserException;
    User getUserByEmail(String email) throws AuthenticationException;
    Date getLastRequest(long id) throws AuthenticationException;
    List<UnavailabilityScheduleOutputDto> getUnavailabilityScheduleById(long id) throws Exception;
}
