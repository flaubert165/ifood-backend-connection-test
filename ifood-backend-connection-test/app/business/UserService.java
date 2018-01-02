package business;

import com.google.inject.Inject;
import domain.dto.inputs.UserDto;
import domain.dto.outputs.UnavailabilityScheduleOutputDto;
import domain.dto.outputs.UserOutputDto;
import domain.entities.User;
import domain.enums.Status;
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
    private UnavailabilityScheduleService _schedulesService;

    @Inject
    public UserService(IUserRepository repository, UnavailabilityScheduleService schedulesService){
        this._repository = repository;
        this._schedulesService = schedulesService;
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

        List<UserOutputDto> users = this._repository.getUsers();

        for(UserOutputDto user : users){

            try {

                 List<UnavailabilityScheduleOutputDto> schedules = this._schedulesService.getByUserId(user.getId());

                 user.setStatus(TimeIntervalHelper.verifyStatus(schedules));
                 this._repository.updateStatus(user.getStatus(), user.getId());
                 this.updateOfflineTime(user.getId(), user.getLastRequest());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return users;
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
    public void updateLastRequest(java.util.Date lastRequest, long userId) throws UserException {
        this._repository.updateLastRequest(lastRequest, userId);
    }

    @Override
    public void updateMinutesOffline(long minutes, long userId) throws UserException {
        this._repository.updateMinutesOffline(minutes, userId);
    }

    @Override
    public void updateStatus(Status status, long userId) throws UserException {
        this._repository.updateStatus(status, userId);
    }

    public void updateOfflineTime(long userId, java.util.Date lastRequest) throws Exception{

        java.util.Date now = TimeIntervalHelper.localDateTimeToDate();

        if(lastRequest == null){
            this._repository.updateLastRequest(now, userId);
        }else {
            this._repository.updateLastRequest(now, userId);
            long minutes = TimeIntervalHelper.calculatesOfflineUserTime(lastRequest);
            this._repository.updateMinutesOffline(minutes, userId);
        }

    }
}
