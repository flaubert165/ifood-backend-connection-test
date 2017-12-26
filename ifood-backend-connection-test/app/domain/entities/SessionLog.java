package domain.entities;

import domain.Entity;
import domain.enums.Status;

public class SessionLog implements Entity {

    private long id;
    private Status type;
    private long userId;

    @Override
    public long getId() { return id; }

    @Override
    public void setId(long id) { this.id = id;}

    public Status getType() {
        return type;
    }

    public void setType(Status type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
