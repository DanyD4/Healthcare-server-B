package health.care.booking.controllers;

import health.care.booking.dto.AvailabilityDTO;
import health.care.booking.models.Availability;
import health.care.booking.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Availability> createAvailability(@RequestBody AvailabilityDTO availabilityDTO) {
        Availability availability = availabilityService.createAvailability(availabilityDTO);
        return new ResponseEntity<>(availability, HttpStatus.CREATED);
    }

    // hämta alla som tillhör en caregiver
    @GetMapping("caregiver/{caregiverId}")
    public ResponseEntity<List<Availability>> getAvailabilityByCaregiverId(@PathVariable String caregiverId) {
        List<Availability> availabilities = availabilityService.getAvailabilityByCaregiverId(caregiverId);
        return new ResponseEntity<>(availabilities, HttpStatus.OK);
    }

    // hämta en med id
    @GetMapping("/{id}")
    public ResponseEntity<Availability> getAvailabilityById(@PathVariable String id) {
        Optional<Availability> availability = availabilityService.getAvailabilityById(id);
        return availability.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /*public ResponseEntity<?> createAvailability(@RequestBody AvailabilityDTO availabilityDTO) {
        try {
            Availability newAvailability = availabilityService.createAvailability(availabilityDTO);  //skapar en ny tillgänglighet med hjälp av availabilityService
            AvailabilityDTO responseDTO = new AvailabilityDTO(); //skapar ett svar dto för att skicka tillbaka den nya tillgängligheten
            responseDTO.setId(newAvailability.getId());
            responseDTO.setCaregiverId(newAvailability.getCaregiverId().getId());
            responseDTO.setAvailableSlots(newAvailability.getAvailableSlots());

            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to create availability: " + e.getMessage());
        }
    }*/

    // Lägg till en tillgänglig tid
  //  @PreAuthorize("hasRole('ADMIN')")
   /* @PostMapping("/availability/add")
    public ResponseEntity<?> addAvailabilitySlot(@RequestParam String caregiverId, @RequestParam LocalDateTime localDateTime) {
        try {
            availabilityService.addAvailabilitySlot(caregiverId, localDateTime); //anropar addAvailabilitySlot metoden i availabilityService
            return ResponseEntity.ok("Availability slot added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to add availability slot: " + e.getMessage());
        }
    }*/



    //hämta alla tillgänglighetstider för en specifik vårdgivare
   /* @GetMapping("/caregivers/{caregiverId}/availability")
    public ResponseEntity<List<AvailabilityDTO>> getAllAvailabilitiesByCaregiverId(@PathVariable String caregiverId) {
        List<AvailabilityDTO> availability = availabilityService.getAllAvailabilitiesByCaregiverId(caregiverId); //hämtar alla tillgänglighetstider för en specifik vårdgivare med hjälp av availabilityService
        return ResponseEntity.ok(availability);
    }*/

    //hämta en specifik tillgänglighetstid för en vårdgivare
    /*@GetMapping("/caregivers/{caregiverId}/availability/{availabilityId}")
    public ResponseEntity<?> getAvailabilityById(@PathVariable String caregiverId, @PathVariable String availabilityId) {
        try {
            AvailabilityDTO availability = availabilityService.getAvailabilityById(caregiverId, availabilityId); //hämtar en specifik tillgänglighetstid med hjälp av availabilityService
            return ResponseEntity.ok(availability);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Availability not found: " + e.getMessage());
        }
    }*/



    //uppdatera en befintlig tillgänglighetstid
    /*@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/availability/{availabilityId}")

   public ResponseEntity<?> updateAvailability(@PathVariable String availabilityId, @RequestBody AvailabilityDTO availabilityDTO) {
        try {
            Availability updatedAvailability = availabilityService.updateAvailability(availabilityId,availabilityDTO);
            AvailabilityDTO responseDTO = new AvailabilityDTO(); //skapar ett svar dto för att skicka tillbaka den uppdaterade tillgängligheten
            responseDTO.setId(updatedAvailability.getId());
            responseDTO.setCaregiverId(updatedAvailability.getCaregiverId().getId());
            responseDTO.setAvailableSlots(updatedAvailability.getAvailableSlots());
            responseDTO.setBookedSlots(updatedAvailability.getBookedSlots());
            return ResponseEntity.ok(responseDTO); //returnerar en 200 ok respons med den uppdaterade tillgängligheten
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update availability: " + e.getMessage());
        }
    }*/


    //ta bort en tillgänglighetstid
   /* @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/availability/{availabilityId}")
    public ResponseEntity<?> deleteAvailability(@PathVariable String availabilityId) {
        try {
            availabilityService.deleteAvailability(availabilityId); //tar bort en tillgänglighetstid med hjälp av availabilityService
            return ResponseEntity.ok("Appointment canceled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Appointment not found");
        }
    }*/
}
