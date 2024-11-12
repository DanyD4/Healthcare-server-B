package health.care.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


//skapar ett availability dto objekt för att hålla information om den nya tillgängligheten.
//sätter id, vårdgivarens id och de tillgängliga tiderna i detta objekt.
//Sedan skickar man tillbaka detta objekt som svar till klienten.

public class AvailabilityDTO {
    private String id;
    private String caregiverId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private List<LocalDateTime> availableSlots = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private List<LocalDateTime> bookedSlots = new ArrayList<>();


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


    public List<LocalDateTime> getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(List<LocalDateTime> availableSlots) {
        this.availableSlots = availableSlots;
    }

    public List<LocalDateTime> getBookedSlots() {
        return bookedSlots;
    }

    public void setBookedSlots(List<LocalDateTime> bookedSlots) {
        this.bookedSlots = bookedSlots;
    }
}
