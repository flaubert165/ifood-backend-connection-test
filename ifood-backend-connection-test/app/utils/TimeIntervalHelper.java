package utils;

import domain.dto.outputs.UnavailabilityScheduleOutputDto;
import domain.enums.Status;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

public class TimeIntervalHelper {

    public static final LocalTime OPENTIME = LocalTime.of(9,59,59, 00000) ;
    public static final LocalTime CLOSETIME = LocalTime.of(23,1,00, 00000) ;

    public static Status verifyStatusWhenCreateAndLogout() {

        Time now = toSqlTime(LocalTime.now());
        Time openTime = toSqlTime(OPENTIME);
        Time closeTime = toSqlTime(CLOSETIME);

        return now.after(openTime) && now.before(closeTime) ? Status.AvailableOffline : Status.UnavailableOffline;
    }

    public static Status verifyStatusWhenSignIn() {

        Time now = toSqlTime(LocalTime.now());
        Time openTime = toSqlTime(OPENTIME);
        Time closeTime = toSqlTime(CLOSETIME);

        return now.after(openTime) && now.before(closeTime) ? Status.AvailableOnline : Status.UnavailableOffline;
    }

   /*public static Status verifyStatus(List<UnavailabilityScheduleOutputDto> schedules){

        Time now = toSqlTime(LocalTime.now());
        Time openTime = toSqlTime(OPENTIME);
        Time closeTime = toSqlTime(CLOSETIME);

        for(UnavailabilityScheduleOutputDto schedule : schedules){

            if((schedule.getStart().after(now) || schedule.getStart().equals(now)) &&
                    (schedule.getEnd().before(now) || schedule.getEnd().equals(now))){
                return Status.UnavailableOffline;
            } else if(isBetweenAvailableTime()){

            }
        }
    }*/


    public static Time toSqlTime(LocalTime localTime) {
        return Time.valueOf(localTime);
    }

    public static long calculateUnavailabiltyDuration(Instant start, Instant end){

        Duration duration = Duration.between(start, end);

        long minutes = duration.toMinutes();

        return minutes;
    }

    public static boolean isBetweenAvailableTime(java.util.Date start, java.util.Date end){

        Time startTime = toSqlTime(LocalTime.of(start.getHours(),
                                    start.getMinutes(), start.getSeconds(), 00000));
        Time endTime = toSqlTime(LocalTime.of(end.getHours(),
                                        end.getMinutes(), end.getSeconds(), 00000));
        Time openTime = toSqlTime(OPENTIME);
        Time closeTime = toSqlTime(CLOSETIME);

        return startTime.after(openTime) && endTime.before(closeTime);
    }
}
