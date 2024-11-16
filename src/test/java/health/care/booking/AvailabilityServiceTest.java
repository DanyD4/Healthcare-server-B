package health.care.booking;

import health.care.booking.dto.AvailabilityDTO;
import health.care.booking.models.Availability;
import health.care.booking.models.User;
import health.care.booking.respository.AvailabilityRepository;
import health.care.booking.respository.UserRepository;
import health.care.booking.services.AvailabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")  // Activate the test profile
@TestPropertySource(properties = {
        "jwt.secret=hfaiehfisehfosndfejndfeswljrfeowfnjehwbewios4ngvhtrwglp4rkledf",
        "jwt.expirationMs=36000000"
})



public class AvailabilityServiceTest {

    @Mock
    private AvailabilityRepository availabilityRepository;


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AvailabilityService availabilityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    //Arrange: Förbered allt du behöver för testet (skapa objekt, ställ in mockar).
    //Act: Utför handlingen du vill testa (anropa metoden).
    //Assert: Kontrollera att resultatet är som förväntat (använd assertions).

    @Test
    public void testCreateAvailability() {
        //Arrange
        AvailabilityDTO availabilityDTO = new AvailabilityDTO();
        availabilityDTO.setCaregiverId("caregiver1");
        availabilityDTO.setAvailableSlots(Arrays.asList(LocalDateTime.now(), LocalDateTime.now().plusHours(1)));

        User caregiver = new User();
        caregiver.setId("1");
        caregiver.setUsername("caregiver1");

        Availability availabilityToSave = new Availability();
        availabilityToSave.setCaregiverId(caregiver);
        availabilityToSave.setAvailableSlots(availabilityDTO.getAvailableSlots());

        Availability savedAvailability = new Availability();
        savedAvailability.setId("1");
        savedAvailability.setCaregiverId(caregiver);
        savedAvailability.setAvailableSlots(availabilityDTO.getAvailableSlots());

        when(userRepository.findById("caregiver1")).thenReturn(Optional.of(caregiver));
        when(availabilityRepository.save(any(Availability.class))).thenReturn(savedAvailability);

        //Act
        Availability result = availabilityService.createAvailability(availabilityDTO);

        //Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals(availabilityDTO.getAvailableSlots(), result.getAvailableSlots());
    }




    @Test
    public void testGetAllAvailabilities() {
        //Arrange
        Availability availability1 = new Availability();
        availability1.setId("1");
        Availability availability2 = new Availability();
        availability2.setId("2");

        when(availabilityRepository.findAll()).thenReturn(Arrays.asList(availability1, availability2));

        //Act
        List<Availability> result = availabilityService.getAllAvailabilities();

        //Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAvailabilityByCaregiverId() {
        //Arrange
        User caregiver = new User();
        caregiver.setId("1");
        caregiver.setUsername("caregiver1");

        Availability availability = new Availability();
        availability.setId("1");
        availability.setCaregiverId(caregiver);

        when(userRepository.findById("1")).thenReturn(Optional.of(caregiver));
        when(availabilityRepository.findByCaregiverId("1")).thenReturn(Arrays.asList(availability));

        //act
        List<Availability> result = availabilityService.getAvailabilityByCaregiverId("1");

        //Assert
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    public void testGetAvailabilityByCaregiverId_InvalidCaregiverUsername() {
        //Arrange
        when(userRepository.findById("invalid")).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            availabilityService.getAvailabilityByCaregiverId("invalid");
        });
    }

    @Test
    public void testCreateAvailability_InvalidCaregiverUsername() {
        //Arrange
        AvailabilityDTO availabilityDTO = new AvailabilityDTO();
        availabilityDTO.setCaregiverId("invalid");

        when(userRepository.findById("invalid")).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            availabilityService.createAvailability(availabilityDTO);
        });
    }



    @Test
    public void testDeleteAvailability_NotFound() {
        //Arrange
        when(availabilityRepository.findById("invalid")).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(Exception.class, () -> {
            availabilityService.deleteAvailability("invalid");
        });


    }
}


