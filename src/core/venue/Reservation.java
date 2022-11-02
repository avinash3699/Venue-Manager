package core.venue;

import helper.DefensiveCopyHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Reservation {

    private final int accessId;
    private final String username;
    private int venueCode;

    public List<LocalDate> getReservedDates() {
        return DefensiveCopyHelper.getDefensiveCopyList(reservedDates);
    }

    private List<LocalDate> reservedDates;

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
                put("Reserved Dates", String.valueOf(reservedDates));
            }
        };
    }

    public int getAccessId() {
        return accessId;
    }

    public int getVenueCode() {
        return venueCode;
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
