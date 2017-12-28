package utils;

import domain.dto.outputs.UnavailabilityScheduleOutputDto;
import domain.enums.Status;

import java.sql.Date;
import java.sql.Time;
import java.time.*;
import java.util.List;

public class TimeIntervalHelper {

    public static final LocalTime OPENTIME = LocalTime.of(9,59,59, 00000) ;
    public static final LocalTime CLOSETIME = LocalTime.of(23,1,00, 00000) ;

    /**
     *
     * @return
     */
    public static Status verifyStatusWhenCreateAndLogout() {

        Time now = toSqlTime(LocalTime.now());
        Time openTime = toSqlTime(OPENTIME);
        Time closeTime = toSqlTime(CLOSETIME);

        return now.after(openTime) && now.before(closeTime) ? Status.AvailableOffline : Status.UnavailableOffline;
    }

    /**
     *
      * @param schedules
     * @return
     */
   public static Status verifyStatus(List<UnavailabilityScheduleOutputDto> schedules) {

       Time now = toSqlTime(LocalTime.now());

       if (schedules.size() == 0 && !isBetweenAvailableTime(now)) {
           return Status.UnavailableOffline;
       } else if (schedules.size() > 0) {
           for (UnavailabilityScheduleOutputDto schedule : schedules) {

               if ((now.before(schedule.getStart()) || now.equals(schedule.getStart())) &&
                       (now.after((schedule.getEnd())) || now.equals(schedule.getEnd())) ||
                       !isBetweenAvailableTime(now)) {
                   return Status.UnavailableOffline;
               } else if (isBetweenAvailableTime(now)) {
                   return Status.AvailableOnline;
               }
           }
       }

       return Status.AvailableOffline;
   }

    /**
     * convert localtime to sqltime
     * @param localTime
     * @return
     */
    public static Time toSqlTime(LocalTime localTime) {
        return Time.valueOf(localTime);
    }

    public static long calculateUnavailabiltyDuration(Instant start, Instant end){

        Duration duration = Duration.between(start, end);

        long minutes = duration.toMinutes();

        return minutes;
    }

    /**
     * check if two times are between the open and close times
     * @param start
     * @param end
     * @return boolean
     */
    public static boolean isBetweenAvailableTime(java.util.Date start, java.util.Date end){

        Time startTime = toSqlTime(LocalTime.of(start.getHours(),
                                    start.getMinutes(), start.getSeconds(), 00000));
        Time endTime = toSqlTime(LocalTime.of(end.getHours(),
                                        end.getMinutes(), end.getSeconds(), 00000));
        Time openTime = toSqlTime(OPENTIME);
        Time closeTime = toSqlTime(CLOSETIME);

        return startTime.after(openTime) && endTime.before(closeTime);
    }

    /**
     * check if current time is between open and close times
     * @param now
     * @return
     */
    public static boolean isBetweenAvailableTime(java.util.Date now){

        Time timeNow = toSqlTime(LocalTime.of(now.getHours(),
                now.getMinutes(), now.getSeconds(), 00000));
        Time openTime = toSqlTime(OPENTIME);
        Time closeTime = toSqlTime(CLOSETIME);

        return timeNow.after(openTime) && timeNow.before(closeTime);
    }

    public static long calculatesOfflineUserTime(java.util.Date lastRequest) throws Exception{
        long minutes;

        java.util.Date now = localDateTimeToDate();

        Time openTime = toSqlTime(OPENTIME);
        Time closeTime = toSqlTime(CLOSETIME);

        Time lastRequestTime = toSqlTime(LocalTime.of(lastRequest.getHours(),
                lastRequest.getMinutes(), lastRequest.getSeconds(), 00000));

        Time nowTime = toSqlTime(LocalTime.of(now.getHours(),
                now.getMinutes(), now.getSeconds(), 00000));

        // between opentime and closetime
        if(lastRequestTime.after(openTime) && nowTime.before(closeTime)){
            Duration duration = Duration.between(lastRequest.toInstant(), now.toInstant());
            minutes = duration.toMinutes();
            return minutes;
            // after opentime and closetime, check the proportional value
        } else if(lastRequestTime.after(openTime) && !nowTime.before(closeTime)){
            Duration duration = Duration.between(lastRequestTime.toInstant(), now.toInstant());
            minutes = duration.toMinutes();
            return minutes;
            // before opentime and closetime, check the proportional value
        } else if(!lastRequestTime.after(openTime) && nowTime.before(closeTime)){
            Duration duration = Duration.between(openTime.toInstant(), now.toInstant());
            minutes = duration.toMinutes();
            return minutes;
        } else{
            minutes = 0L;
            return minutes;
        }
    }

    public static java.util.Date localDateTimeToDate(){

        java.util.Date in = new java.util.Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());

        return java.util.Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
