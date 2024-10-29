package health.care.booking.services;

import health.care.booking.dto.AvailabilityDTO;
import health.care.booking.models.Availability;
import health.care.booking.models.User;
import health.care.booking.respository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

   @Autowired
    private AvailabilityRepository availabilityRepository;

    //Skapar en ny tillgänglighetstid
    public Availability createAvailability(AvailabilityDTO availabilityDTO) {
        Availability availability = new Availability();
        availability.setId(availabilityDTO.getId());
        availability.setCaregiverId(new User(availabilityDTO.getCaregiverId()));
        availability.setAvailableDates(availabilityDTO.getAvailableDates());
        availability.setAvailableTimes(availabilityDTO.getAvailableTimes());
        //availability.setAvailableSlots(availabilityDTO.getAvailableSlots());

        return availabilityRepository.save(availability);
    }

    //Hämtar alla tillgänglighetstider för en specifik vårdgivare
    public List<AvailabilityDTO> getAllAvailabilitiesByCaregiverId(String caregiverId) {
        List<Availability> availabilities = availabilityRepository.findByCaregiverId(caregiverId);

        return availabilities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //Hämtar en specifik tillgänglighetstid för en vårdgivare
    public AvailabilityDTO getAvailabilityById(String caregiverId, String availabilityId) {
        Availability availability = availabilityRepository.findByCaregiverIdAndId(caregiverId, availabilityId);

        return convertToDTO(availability);
   }



    //Uppdaterar en befintlig tillgänglighetstid
    public Availability updateAvailability(String availabilityId, AvailabilityDTO availabilityDTO) {
        Availability availability = availabilityRepository.findById(availabilityId).orElse(null);
        if (availability != null) {
            availability.setAvailableDates(availabilityDTO.getAvailableDates());
            availability.setAvailableTimes(availabilityDTO.getAvailableTimes());
           // availability.setAvailableSlots(availabilityDTO.getAvailableSlots());

            return availabilityRepository.save(availability);
        }
        return null;
    }

    //Tar bort en tillgänglighetstid
    public void deleteAvailability(String availabilityId) {

        availabilityRepository.deleteById(availabilityId);
    }

    //Konverterar Availability ent till AvailabilityDTO
    private AvailabilityDTO convertToDTO(Availability availability) {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setId(availability.getId());
        dto.setCaregiverId(availability.getCaregiverId().getId());
        dto.setAvailableDates(availability.getAvailableDates());
        dto.setAvailableTimes(availability.getAvailableTimes());
        //dto.setAvailableSlots(availability.getAvailableSlots());
        return dto;
    }
}
