package infrastructure.repositories;

import domain.enums.Status;
import exceptions.AuthenticationException;

public interface IAuthenticationRepository {
    void login(long id, Status status) throws AuthenticationException;
    void logout(long id, Status status) throws AuthenticationException;
}
