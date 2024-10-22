package health.care.booking.services;

import health.care.booking.models.Availability;
import health.care.booking.respository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {
    @Autowired
    private AvailabilityRepository availabilityRepository;

    //Skapar en ny tillgänglighet och sparar den i databasen
    public Availability createAvailability(Availability availability){
        return availabilityRepository.save(availability);
    }

    //Hämtar allatillgänglighetstider för en specifik vårdgivare
    public List<Availability> getAllAvailabilitiesByCaregiverId(String caregiverId){
        return availabilityRepository.findByCaregiverId(caregiverId);
    }

    //Hämtar en specifik tillgänglighetstid för en vårdgivare baserat på caregiverId och availabilityId
    public Availability getAvailabilityById(String caregiverId, String availabilityId) {
        return availabilityRepository.findByCaregiverIdAndId(caregiverId, availabilityId);
    }

    //Uppdaterar en befintlig tillgänglighetsti med nya detaljer
    public Availability updateAvailability(String availabilityId, Availability availabilityDetails){
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new IllegalArgumentException("Availability not found for this id: " + availabilityId));
        availability.setCaregiverId(availabilityDetails.getCaregiverId());
        availability.setAvailableSlots(availabilityDetails.getAvailableSlots());
        return availabilityRepository.save(availability);
    }

    //Tar bort en tillgänglighetstid från databasen
    public void deleteAvailability(String availabilityId) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new IllegalArgumentException("Availability not found for this id: " + availabilityId));
        availabilityRepository.delete(availability);
    }
}