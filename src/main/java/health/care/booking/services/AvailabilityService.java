package health.care.booking.services;

import health.care.booking.dto.AvailabilityDTO;
import health.care.booking.models.Availability;
import health.care.booking.models.User;
import health.care.booking.respository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

   @Autowired
    private AvailabilityRepository availabilityRepository;

    //Skapar en ny tillgänglighetstid, kan kanske tas bort då vi har generate som är mer specifik
    public Availability createAvailability(AvailabilityDTO availabilityDTO) {
        Availability availability = new Availability();
        availability.setId(availabilityDTO.getId());
        availability.setCaregiverId(new User(availabilityDTO.getCaregiverId()));
        availability.setAvailableDates(availabilityDTO.getAvailableDates());
        availability.setAvailableTimes(availabilityDTO.getAvailableTimes());


        return availabilityRepository.save(availability);
    }

    //skapar tillgängliga tider för tre månader framåt
    public void generateAvailability(String caregiverId) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);

        List<LocalDate> availableDates = new ArrayList<>();
        List<LocalTime> availableTimes = new ArrayList<>();

        //Lägg till tider från 9 till 17 varje dag
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            availableDates.add(date);
        }

        for (int hour = 9; hour < 17; hour++) {
            availableTimes.add(LocalTime.of(hour, 0));
        }

        //Skapa och spara tillgänglighet
        Availability availability = new Availability();
        availability.setCaregiverId(new User(caregiverId));
        availability.setAvailableDates(availableDates);
        availability.setAvailableTimes(availableTimes);
        availabilityRepository.save(availability);
    }

    //Lägg till en tillgängligtid
    public void addAvailabilitySlot(String caregiverId, LocalDate date, LocalTime time) {
        List<Availability> availabilities = availabilityRepository.findByCaregiverId(caregiverId); //hämtar tillgänglighet för vårdgivaren

        for (Availability availability : availabilities) {
            if (availability.getAvailableDates().contains(date)) {
                availability.getAvailableTimes().add(time);
                availabilityRepository.save(availability);
                return;
            }
        }

        //Om ingen tillgänglighet hittas för det specifika datumet, skapa en ny tid
        Availability newAvailability = new Availability();
        newAvailability.setCaregiverId(new User(caregiverId));
        newAvailability.setAvailableDates(List.of(date));
        newAvailability.setAvailableTimes(List.of(time));
        availabilityRepository.save(newAvailability);
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

        return dto;
    }
}
