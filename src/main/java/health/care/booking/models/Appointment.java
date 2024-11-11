package health.care.booking.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "appointment")
public class Appointment {
    @Id
    private String id;

    //@DBRef
    private String patientId;//user

    //@DBRef
    private String caregiverId; //user

    private String availabilityId; //testar

    // Olika fält för datum och tid
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC" )
    private LocalDateTime localDateTime;


    private Status status;

    public Appointment() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() { // User
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getCaregiverId() { //USer
        return caregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(String availabilityId) {
        this.availabilityId = availabilityId;
    }
}