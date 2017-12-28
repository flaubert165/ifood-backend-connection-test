package domain.entities;

import domain.Entity;
import domain.enums.Status;
import java.util.Date;

public class User implements Entity {
    private long id;
    private String email;
    private String password;
    private String name;
    private Status status;
    private long minutesOffline;
    private Date lastRequest;

    public User(){

    }

    public User(long id, String email, String password, String name,
                 Status status, long minutesOffline, Date lastRequest){

        setId(id);
        setEmail(email);
        setPassword(password);
        setName(name);
        setStatus(status);
        setMinutesOffline(minutesOffline);
        setLastRequest(lastRequest);
    }

    @Override
    public long getId() { return id; }

    @Override
    public void setId(long id) { this.id = id;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
