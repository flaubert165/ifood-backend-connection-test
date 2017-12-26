package infrastructure.repositories;

import domain.dto.outputs.UserOutputDto;
import domain.entities.User;
import domain.enums.Status;
import exceptions.AuthenticationException;
import exceptions.UserException;

public interface IAuthenticationRepository {
    void login(long id, Status status) throws AuthenticationException;
    void logout(long id, Status status) throws AuthenticationException;
    UserOutputDto getUserById(long id) throws UserException;
    User getUserByEmail(String email) throws AuthenticationException;
}
