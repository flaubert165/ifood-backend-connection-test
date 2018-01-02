package domain.dto.outputs;

import domain.enums.Status;

import java.sql.Time;
import java.util.Date;

public class UserOutputDto {
    private long id;
    private String name;
    private Status status;
    private String token;
    private long minutesOffline;
    private Date lastRequest;

    public UserOutputDto(){

    }

    public UserOutputDto(long id, String name, Status status, long minutesOffline){
        setId(id);
        setName(name);
        setStatus(status);
        setMinutesOffline(minutesOffline);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getMinutesOffline() {
        return minutesOffline;
    }

    public void setMinutesOffline(long minutesOffline) {
        this.minutesOffline = minutesOffline;
    }

    public Date getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(Date lastRequest) {
        this.lastRequest = lastRequest;
    }
}
