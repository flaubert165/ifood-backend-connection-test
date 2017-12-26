package domain.entities;

import domain.Entity;

import java.util.Date;

public class UnavailabilitySchedule implements Entity {

    private long id;
    private Date startTime;
    private Date endTime;



    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) { this.id = id;}

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
