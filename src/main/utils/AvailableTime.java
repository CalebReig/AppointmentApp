package main.utils;

import main.Main;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

/**
 * This class keeps track of available appointment times for a given day.
 */
public class AvailableTime {

    private LocalDate startDay;
    private LocalDate endDay;
    private static final LocalTime[] availableStartTimes = new LocalTime[28];
    private static final LocalTime[] availableEndTimes = new LocalTime[28];

    /**
     * This is a constructor method.
     * It creates an instance of the <em>AvailableTime</em> class.
     * @param day The day that the appointment will start.
     */
    public AvailableTime(LocalDate day) {
        this.startDay = day;
        int[] times = getOffsetTimes();
        setupStartTimes(times[0]);
        setupEndTimes(times[1]);
    }

    /**
     * This method returns the appointment start day.
     * @return Returns the appointment start day.
     */
    public LocalDate getStartDay() {return this.startDay;}

    /**
     * This method returns the appointment end day.
     * @return Returns the appointment end day.
     */
    public LocalDate getEndDay() {return this.endDay;}

    /**
     * This method will convert 8am and 9am EST to the user's system time
     * @return Returns the business opening time from UTC to the system time.
     */
    private int[] getOffsetTimes() {
        ZoneOffset localOffset = Main.LOCALTIMEZONE.getRules().getOffset(LocalDateTime.now(Main.UTC));
        ZoneOffset estOffset = Main.EST.getRules().getOffset(LocalDateTime.now(Main.UTC));
        int offsetDirection = 1;
        if (String.valueOf(localOffset).charAt(0) == '-') {offsetDirection = -1;}
        int offset = (Integer.parseInt(String.valueOf(localOffset).substring(1, 3)) * offsetDirection) +
                Integer.parseInt(String.valueOf(estOffset).substring(1, 3));
        int start1 = 8 + offset;
        int start2 = 9 + offset;


        int[] oldTimes = {start1, start2};
        int[] newTimes = new int[2];
        for (int i = 0; i < 2; i++) {
            if (oldTimes[i] >= 24) {
                newTimes[i] = oldTimes[i] % 24;
            } else {
                newTimes[i] = oldTimes[i];
            }
        }
        return newTimes;
    }

    /**
     * This method fills the <em>availableStartTimes</em> array with times in
     * in blocks of 30 minutes corresponding to the available appointment times.
     * @param start The hour in the system timezone that is the same as 8am EST
     */
    private void setupStartTimes(int start) {
        for (int i=0; i < 14; i++) {
            if (start == 24) {start = 0;}
            availableStartTimes[2 * i] = LocalTime.of(start, 0);
            availableStartTimes[2 * i + 1] = LocalTime.of(start, 30);
            start++;
        }
    }

    /**
     * This method fills the <em>availableEndTimes</em> array with times in
     * in blocks of 30 minutes corresponding to the available appointment times.
     * @param start The hour in the system timezone that is the same as 9am EST
     */
    private void setupEndTimes(int start) {
        for (int i = 0; i < 14; i++) {
            if (start == 24) {
                start = 0;
                availableEndTimes[2 * i] = LocalTime.of(23, 30);
            }
            else {
                availableEndTimes[2 * i] = LocalTime.of(start - 1, 30);
            }
            availableEndTimes[2* i + 1] = LocalTime.of(start, 0);
            start++;
        }
    }

    /**
     * This method returns an array of available appointment start times.
     * @return Returns an array of available start times.
     */
    public LocalTime[] getAvailableStartTimes() {return availableStartTimes;}

    /**
     * This method returns an array of available appointment end times.
     * @return Returns an array of available end times.
     */
    public LocalTime[] getAvailableEndTimes() {return availableEndTimes;}

    /**
     * This method will alter the opposite available times array once a
     * time is chosen in the given array. The altered array of available times
     * is shortened to a maximum length of 4 to represent a maximum appointment
     * length of 2 hours. This ensures the user will not be able to schedule
     * an appointment outside of these bounds.
     * @param time The time chosen of a given array of times.
     * @param reference A reference to which available times array was selected
     *                  ("start" or "end")
     * @return Returns a new array of available times within 2 hours of the given time.
     */
    public static LocalTime[] alterAvailableTimes(LocalTime time, String reference) {
        LocalTime[] availableTimes = new LocalTime[4];
        int refIndex;
        int newIndex = 0;
        if (reference.equals("start")) {
            refIndex = getIndex(availableStartTimes, time);
            for (int i = refIndex; i < refIndex + 4; i++) {
                if ((i < availableEndTimes.length) && (i >= 0)) {
                    availableTimes[newIndex] = availableEndTimes[i];
                    newIndex++;
                }
            }
        }
        else if (reference.equals("end")) {
            refIndex = getIndex(availableEndTimes, time);
            for (int i = refIndex; i > refIndex - 4; i--) {
                if (i > -1) {
                    availableTimes[3-newIndex] = availableStartTimes[i];
                    newIndex++;
                }
            }
        }
        return availableTimes;
    }

    /**
     * This method calculates if the day an appointment ends is the same day
     * as when it starts.
     * @param start The time the appointment starts.
     * @param end The time the appointment ends.
     */
    public void setupEndDay(LocalTime start, LocalTime end) {
        if ((start != null && end != null) && (start.isAfter(end))) {
            this.endDay = startDay.plusDays(1);
        }
        else if ((start != null && end != null) && (start.isBefore(end))){
            this.endDay = startDay;
        }
    }

    /**
     * This method returns the index of a given time in an array of times.
     * @param availableTimes An array of available appointment times
     * @param time A time that is in an array of available times
     * @return Returns the index of a time within an array of times.
     */
    private static int getIndex(LocalTime[] availableTimes, LocalTime time) {
        for (int i=0; i < availableTimes.length; i++) {
            if (availableTimes[i].equals(time)) {return i;}
        }
        return -1;
    }
}
