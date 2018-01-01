package domain.entities;

import domain.Entity;

import java.util.Date;

public class UnavailabilitySchedule implements Entity {

    private long id;
    private Date start;
    private Date end;



    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) { this.id = id;}


    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
