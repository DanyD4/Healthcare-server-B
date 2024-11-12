package health.care.booking.services;

import health.care.booking.dto.AvailabilityDTO;
import health.care.booking.models.Availability;
import health.care.booking.models.User;
import health.care.booking.respository.AppointmentRepository;
import health.care.booking.respository.AvailabilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private static final Logger logger = LoggerFactory.getLogger(AvailabilityService.class);

   @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;


    //Skapar en ny tillgänglighetstid
    public Availability createAvailability(AvailabilityDTO availabilityDTO) {
        Availability availability = new Availability();  //Skapar ett nytt availability objekt
        availability.setId(availabilityDTO.getId());  //Sätter id för tillgängligheten
        availability.setCaregiverId(new User(availabilityDTO.getCaregiverId()));  //Sätter vårdgivarens id för tillgängligheten
        availability.setAvailableSlots(availabilityDTO.getAvailableSlots()); //Sättet de tillgängliga tiderna för tillgängligheten



        return availabilityRepository.save(availability);
    }



    //Lägg till en tillgängligtid
    public void addAvailabilitySlot(String caregiverId, LocalDateTime localDateTime) {
        List<Availability> availabilities = availabilityRepository.findByCaregiverId(caregiverId); //hämtar tillgänglighet för vårdgivaren

        //Kontrollerar om tiden redan finns i tillgängliga tider
        for (Availability availability : availabilities) {
            if (availability.getAvailableSlots().contains(localDateTime)) {
                logger.info("Tiden finns redan: " + localDateTime);


                return;
            }
        }

        //Om ingen tillgänglighet hittas för det specifika datumet, skapa en ny tid
        Availability newAvailability = new Availability();
        newAvailability.setCaregiverId(new User(caregiverId));
        newAvailability.setAvailableSlots(List.of(localDateTime));

        availabilityRepository.save(newAvailability);
        logger.info("Ny tid tillagd: " + localDateTime);

    }

    //Hämtar alla tillgänglighetstider för en specifik vårdgivare
    public List<AvailabilityDTO> getAllAvailabilitiesByCaregiverId(String caregiverId) {
        List<Availability> availabilities = availabilityRepository.findByCaregiverId(caregiverId); //Hämtar alla tillgängligheter för vårdgivaren

        //Konvertera tillgängligheterna till dto och returnera dem
        return availabilities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //Hämtar en specifik tillgänglighetstid för en vårdgivare
    public AvailabilityDTO getAvailabilityById(String caregiverId, String availabilityId) {
        Availability availability = availabilityRepository.findByCaregiverIdAndId(caregiverId, availabilityId); //Hämta tillgängligheten för vårdgivaren och tillgänglighets id

        return convertToDTO(availability);
   }


    //Uppdaterar en befintlig tillgänglighetstid
    public Availability updateAvailability(String availabilityId, AvailabilityDTO availabilityDTO) {
        Availability availability = availabilityRepository.findById(availabilityId).orElse(null); //Hämtar tillgängligheten med hjälp av id
        if (availability != null) {
            availability.setAvailableSlots(availabilityDTO.getAvailableSlots()); //Uppdaterar de tillgängliga tiderna
            availability.setBookedSlots(availabilityDTO.getBookedSlots());//Uppdaterar de bokade tiderna


            return availabilityRepository.save(availability);
        }
        return null;
    }


    //Tar bort en tillgänglighetstid

    // Avbokar en bokad tid och gör den tillgänglig igen
    public void deleteAvailability(String availabilityId) throws Exception {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new Exception("Appointment not found"));
        availabilityRepository.deleteById(availabilityId);

    }



    //som stillgalleriet ta bort sen
    private AvailabilityDTO convertToDTO(Availability availability) {
            AvailabilityDTO availabilityDTO = new AvailabilityDTO();
            availabilityDTO.setId(availability.getId());
            availabilityDTO.setCaregiverId(availability.getCaregiverId().getId());
            availabilityDTO.setAvailableSlots(availability.getAvailableSlots());
            availabilityDTO.setBookedSlots(availability.getBookedSlots());
            return availabilityDTO;

    }
        }
