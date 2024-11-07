package health.care.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AvailabilityDTO {
    private String id;
    private String caregiverId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private List<LocalDate> availableDates;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private List<LocalTime> availableTimes;

    public AvailabilityDTO() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }

    public List<LocalDate> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(List<LocalDate> availableDates) {
        this.availableDates = availableDates;
    }

    public List<LocalTime> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<LocalTime> availableTimes) {
        this.availableTimes = availableTimes;
    }


}
