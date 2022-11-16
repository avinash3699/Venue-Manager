package core.venue;

import helper.DefensiveCopyHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Reservation{

    //TODO reservation date and time can be stored

    // instance variables
    private final int accessId;
    private final String username;
    private int venueCode;
    private final List<LocalDate> reservedDates;

    // to instantiate a Reservation object with default values when the database is loaded
    public Reservation(){
        accessId = 1;
        username = null;
        venueCode = 0;
        reservedDates = new ArrayList<>();
    }

    // parameterized constructor to instantiate a Reservation object after successful reservation
    public Reservation(int accessId, String username, int venueCode, LocalDate fromDate, LocalDate toDate) {
        this.accessId = accessId;
        this.username = username;
        this.venueCode = venueCode;
        reservedDates = new ArrayList<>();
        for (LocalDate date = fromDate; date.isBefore(toDate.plusDays(1)); date = date.plusDays(1)) {
            reservedDates.add(date);
        }
    }

    // Copy Constructor
    public Reservation(Reservation reservation) {
        this.accessId = reservation.accessId;
        this.username = reservation.username;
        this.venueCode = reservation.venueCode;
        reservedDates = new ArrayList<>();
        for(LocalDate date: reservation.reservedDates)
            reservedDates.add(date);
    }

    // function to get values of the current object as a map
    // used to display the reservation details after successful reservation
    //                                         after successful change of venue
    //                                         before cancelling a venue
    //                                         of a user
    public Map<String, String> getMap(){
        return new LinkedHashMap<String, String>(){
            {
                put("Venue Code", String.valueOf(venueCode));
                put("Access Id", String.valueOf(accessId));
                put("Reserved Date(s)", String.valueOf(reservedDates));
            }
        };
    }

    // used to remove a range of dates from the object's reserved dates
    // invoked by the cancellation process
    public void removeDates(LocalDate fromDate, LocalDate toDate) {
        for (LocalDate date = fromDate; date.isBefore(toDate.plusDays(1)); date = date.plusDays(1)) {
            reservedDates.remove(date);
        }
    }

    // getters
    public int getAccessId() {
        return accessId;
    }

    public int getVenueCode() {
        return venueCode;
    }

    public List<LocalDate> getReservedDates() {
        return DefensiveCopyHelper.getDefensiveCopyList(reservedDates);
    }

    public String getUsername() {
        return username;
    }

    // setters
    public void setVenueCode(int venueCode) {
        this.venueCode = venueCode;
    }
}
