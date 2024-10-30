package health.care.booking.controllers;

import health.care.booking.dto.AvailabilityDTO;
import health.care.booking.models.Availability;
import health.care.booking.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping("/availability") //kan kanske tas bort då generate finns
    public ResponseEntity<?> createAvailability(@RequestBody AvailabilityDTO availabilityDTO) {
        try {
            Availability newAvailability = availabilityService.createAvailability(availabilityDTO);
            AvailabilityDTO responseDTO = new AvailabilityDTO();
            responseDTO.setId(newAvailability.getId());
            responseDTO.setCaregiverId(newAvailability.getCaregiverId().getId());
            responseDTO.setAvailableDates(newAvailability.getAvailableDates());
            responseDTO.setAvailableTimes(newAvailability.getAvailableTimes());
            //responseDTO.setAvailableSlots(newAvailability.getAvailableSlots());
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to create availability: " + e.getMessage());
        }
    }

    @PostMapping("/generate") //skapa
    public ResponseEntity<String> generateAvailability(@RequestParam String caregiverId) {
        availabilityService.generateAvailability(caregiverId);
        return ResponseEntity.ok("Availability generated successfully");
    }



    //hämta alla tillgänglighetstider för en specifik vårdgivare
    @GetMapping("/caregivers/{caregiverId}/availability")
    public ResponseEntity<List<AvailabilityDTO>> getAllAvailabilitiesByCaregiverId(@PathVariable String caregiverId) {
        List<AvailabilityDTO> availability = availabilityService.getAllAvailabilitiesByCaregiverId(caregiverId);
        return ResponseEntity.ok(availability);
    }

    //hämta en specifik tillgänglighetstid för en vårdgivare
    @GetMapping("/caregivers/{caregiverId}/availability/{availabilityId}")
    public ResponseEntity<?> getAvailabilityById(@PathVariable String caregiverId, @PathVariable String availabilityId) {
        try {
            AvailabilityDTO availability = availabilityService.getAvailabilityById(caregiverId, availabilityId);
            return ResponseEntity.ok(availability);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Availability not found: " + e.getMessage());
        }
    }



    //uppdatera en befintlig tillgänglighetstid

    @PutMapping("/availability/{availabilityId}")
    public ResponseEntity<?> updateAvailability(@PathVariable String availabilityId, @RequestBody AvailabilityDTO availabilityDTO) {
        try {
            Availability updatedAvailability = availabilityService.updateAvailability(availabilityId, availabilityDTO);
            AvailabilityDTO responseDTO = new AvailabilityDTO();
            responseDTO.setId(updatedAvailability.getId());
            responseDTO.setCaregiverId(updatedAvailability.getCaregiverId().getId());
            responseDTO.setAvailableDates(updatedAvailability.getAvailableDates());
            responseDTO.setAvailableTimes(updatedAvailability.getAvailableTimes());
            //responseDTO.setAvailableSlots(updatedAvailability.getAvailableSlots());
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update availability: " + e.getMessage());
        }
    }


    //ta bort en tillgänglighetstid
    @DeleteMapping("/availability/{availabilityId}")
    public ResponseEntity<?> deleteAvailability(@PathVariable String availabilityId) {
        try {
            availabilityService.deleteAvailability(availabilityId);
            return ResponseEntity.ok( availabilityId + " has been deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete availability: " +e.getMessage());
        }
    }
}
