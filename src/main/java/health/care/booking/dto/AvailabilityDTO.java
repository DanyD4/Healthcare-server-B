package health.care.booking.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AvailabilityDTO {
    private String id;
    private String caregiverId;
    private List<LocalDateTime> availableSlots;

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
}
