package health.care.booking.dto;

import java.time.LocalDateTime;
import java.util.List;


//skapar ett availability dto objekt för att hålla information om den nya tillgängligheten.
//sätter id, vårdgivarens id och de tillgängliga tiderna i detta objekt.
//Sedan skickar man tillbaka detta objekt som svar till klienten.


/* @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private List<LocalDateTime> availableSlots;*/


public class AvailabilityDTO {

    private String caregiverId;
    private List<LocalDateTime> availableSlots;




    public AvailabilityDTO() {
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
