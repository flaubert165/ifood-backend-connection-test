package infrastructure.repositories;

import domain.dto.inputs.UserDto;
import domain.dto.outputs.UserOutputDto;
import domain.entities.User;
import exceptions.UserException;

import java.sql.Date;
import java.util.List;

public interface IUserRepository {
    void create(UserDto user) throws UserException;
    List<UserOutputDto> getUsers() throws UserException;
    UserOutputDto getUserById(long id) throws UserException;
    User getUserByEmail(String email) throws UserException;
    void delete(long id) throws UserException;
    Date getLastRequest(long id) throws UserException;
    /*void updateLastRequest() throws UserException;*/
}
