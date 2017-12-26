package business;

import com.google.inject.Inject;
import domain.dto.inputs.UserDto;
import domain.dto.outputs.UserOutputDto;
import domain.entities.User;
import exceptions.UserException;
import infrastructure.repositories.IUserRepository;
import utils.SecurityHelper;
import utils.TimeIntervalHelper;

import java.util.ArrayList;
import java.util.List;


public class UserService implements IUserRepository {

    private IUserRepository _repository;

    @Inject
    public UserService(IUserRepository repository){
        this._repository = repository;
    }

    @Override
    public void create(UserDto user) throws UserException {

        user.setStatus(TimeIntervalHelper.verifyStatusWhenCreateAndLogout());

        if(user != null){
            user.setPassword(SecurityHelper.generatePassword(user.getPassword()));
            this._repository.create(user);
        }else{
            throw new UserException();
        }
    }

    @Override
    public List<UserOutputDto> getUsers() throws UserException {

        return this._repository.getUsers();
    }

    @Override
    public UserOutputDto getUserById(long id) {
        return null;
    }

    @Override
    public void delete(long id) throws UserException {
        this._repository.delete(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

}
