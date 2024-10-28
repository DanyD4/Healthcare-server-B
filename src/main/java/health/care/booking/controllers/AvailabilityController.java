package health.care.booking.controllers;

import health.care.booking.models.Availability;
import health.care.booking.models.User;
import health.care.booking.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AvailabilityController {
    @Autowired
    private AvailabilityService availabilityService;

    //Endpoint för att skapa en ny tillgänglighetstid
    @PostMapping("/availabilities")
    public ResponseEntity<Availability> createAvailability(@RequestBody Availability availability) {
        return new ResponseEntity<>(availabilityService.createAvailability(availability), HttpStatus.CREATED);
    }

    //Endpoint för att hämta alla tillgänglighetstider för en specifik vårdgivare
    //http://localhost:8080/api/auth/caregivers/exempelpå id 12345/availabilities
    @GetMapping("/caregivers/{caregiverId}/availabilities")
    public List<Availability> getAllAvailabilitiesByCaregiverId(@PathVariable String caregiverId) {
        return availabilityService.getAllAvailabilitiesByCaregiverId(caregiverId);
    }

    //Endpoint för att hämta en specifik tillgänglighetstid för en vårdgiare
    @GetMapping("/caregivers/{caregiverId}/availabilities/{availabilityId}")
    public ResponseEntity<Availability> getAvailabilityById(@PathVariable String caregiverId, @PathVariable String availabilityId) {
        return ResponseEntity.ok(availabilityService.getAvailabilityById(caregiverId, availabilityId));
    }

    //Endpoint för att uppdatera en befintli tillgänglighetstid
    @PutMapping("/availabilities/{availabilityId}")
    public ResponseEntity<Availability> updateAvailability(@PathVariable String availabilityId, @RequestBody Availability availabilityDetails, Principal principal) {
        if (!isAdmin(principal)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(availabilityService.updateAvailability(availabilityId, availabilityDetails));
    }

    //Endpoint för att ta bort en tillgänglighetstid
    @DeleteMapping("/availabilities/{availabilityId}")
    public Map<String, Boolean> deleteAvailability(@PathVariable String availabilityId, Principal principal) {
        if (!isAdmin(principal)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can delete availability slots");
        }
        availabilityService.deleteAvailability(availabilityId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    //Kontrollera om användaren är admin
   private boolean isAdmin(Principal principal) {

        //Konvertera Principal till Authentication för att få användarens detaljer
        User user = (User) ((Authentication) principal).getPrincipal();

        //Går igenom alla roller som användaren har
        for (Role role : user.getRoles()) {
            if (role.getRole() == ERole.ROLE_ADMIN) {
                return true;
            }
        }

        return false;*/
    }}
