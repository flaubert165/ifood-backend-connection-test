package business;

import com.google.inject.Inject;
import domain.dto.inputs.UserDto;
import domain.dto.outputs.UserOutputDto;
import domain.entities.User;
import exceptions.AuthenticationException;
import exceptions.UserException;
import infrastructure.repositories.IUserRepository;
import utils.SecurityHelper;
import utils.TimeIntervalHelper;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class UserService implements IUserRepository{

    private IUserRepository _repository;

    @Inject
    public UserService(IUserRepository repository){
        this._repository = repository;
    }

    public void create(UserDto user) throws UserException {

        user.setStatus(TimeIntervalHelper.verifyStatusWhenCreateAndLogout());

        if(user != null){
            user.setPassword(SecurityHelper.generatePassword(user.getPassword()));
            this._repository.create(user);
        }else{
            throw new UserException();
        }
    }

    public List<UserOutputDto> getUsers() throws UserException {

        return this._repository.getUsers();
    }

    @Override
    public UserOutputDto getUserById(long id) throws UserException {
        return this._repository.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) throws UserException {
        return this._repository.getUserByEmail(email);
    }

    public void delete(long id) throws UserException {
        this._repository.delete(id);
    }

    @Override
    public Date getLastRequest(long id) throws AuthenticationException {
        return this._repository.getLastRequest(id);
    }

    @Override
    public void updateLastRequest(java.util.Date lastRequest, long userId) {
        this._repository.updateLastRequest(lastRequest, userId);
    }

    @Override
    public void updateMinutesOffline(long minutes, long userId) {
        this._repository.updateMinutesOffline(minutes, userId);
    }

}
