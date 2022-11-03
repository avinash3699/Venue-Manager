package core.venue;

import helper.DefensiveCopyHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Reservation {

    private final int accessId;
    private final String username;
    private int venueCode;
    private List<LocalDate> reservedDates;

    public Reservation(){
        accessId = 1;
        username = null;
        reservedDates = new ArrayList(){
            {
                add(LocalDate.parse("15-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
        };
        venueCode = 0;
    }

    public Reservation(int accessId, String username, int venueCode, LocalDate fromDate, LocalDate toDate) {
        this.accessId = accessId;
        this.username = username;
        this.venueCode = venueCode;
        reservedDates = new ArrayList<>();
        for (LocalDate date = fromDate; date.isBefore(toDate.plusDays(1)); date = date.plusDays(1)) {
            reservedDates.add(date);
        }
    }

    public Map<String, String> getMap(){
        return new LinkedHashMap<String, String>(){
            {
                put("Venue Code", String.valueOf(venueCode));
                put("Access Id", String.valueOf(accessId));
                put("Reserved Date(s)", String.valueOf(reservedDates));
            }
        };
    }

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

    public void setVenueCode(int venueCode) {
        this.venueCode = venueCode;
    }

    public void removeDates(LocalDate fromDate, LocalDate toDate) {
        for (LocalDate date = fromDate; date.isBefore(toDate.plusDays(1)); date = date.plusDays(1)) {
            reservedDates.remove(date);
        }
    }

}
