package health.care.booking.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Document(collection = "availability")
public class Availability {
    @Id
    private String id;

    // en doktor/sjuksköterska sätter sig available
    @DBRef
    private User caregiverId;

    // en lista med datum som är tillgängliga
    private List<LocalDate> availableDates;

    // en lista med tider som är tillgängliga
    private List<LocalTime> availableTimes;

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
