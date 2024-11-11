package health.care.booking.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "availability")
public class Availability {
    @Id
    private String id;

    // en doktor/sjuksköterska sätter sig available
    @DBRef
    private User caregiverId;


    // en lista med tider som är tillgängliga
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private List<LocalDateTime> availableSlots = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private List<LocalDateTime> bookedSlots = new ArrayList<>();

    public Availability() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(User caregiverId) {
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
