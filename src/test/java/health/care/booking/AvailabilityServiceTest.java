package health.care.booking;

import health.care.booking.dto.AvailabilityDTO;
import health.care.booking.models.Availability;
import health.care.booking.models.User;
import health.care.booking.respository.AvailabilityRepository;
import health.care.booking.services.AvailabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AvailabilityServiceTest {

    @Mock
    private AvailabilityRepository availabilityRepository;

    @InjectMocks
    private AvailabilityService availabilityService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testGetAllAvailabilitiesByCaregiverId() {
        // Arrange
        String caregiverId = "caregiver1";
        Availability availability1 = new Availability();
        availability1.setId("1");
        availability1.setCaregiverId(new User(caregiverId));
        availability1.setAvailableDates(List.of(LocalDate.of(2024, 10, 29)));
        availability1.setAvailableTimes(List.of(LocalTime.of(10, 0)));

        Availability availability2 = new Availability();
        availability2.setId("2");
        availability2.setCaregiverId(new User(caregiverId));
        availability2.setAvailableDates(List.of(LocalDate.of(2024, 10, 30)));
        availability2.setAvailableTimes(List.of(LocalTime.of(11, 0)));

        when(availabilityRepository.findByCaregiverId(caregiverId)).thenReturn(List.of(availability1, availability2));

        //Act testgdi
        List<AvailabilityDTO> result = availabilityService.getAllAvailabilitiesByCaregiverId(caregiverId);

        //Assert
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());
        verify(availabilityRepository, times(1)).findByCaregiverId(caregiverId);
    }

    @Test
    public void testGetAvailabilityById() {
        // Arrange
        String caregiverId = "caregiver1";
        String availabilityId = "1";
        Availability availability = new Availability();
        availability.setId(availabilityId);
        User caregiver = new User(caregiverId);
        availability.setCaregiverId(caregiver);
        availability.setAvailableDates(List.of(LocalDate.of(2024, 10, 29)));
        availability.setAvailableTimes(List.of(LocalTime.of(10, 0)));

        when(availabilityRepository.findByCaregiverIdAndId(caregiverId, availabilityId)).thenReturn(availability);

        //Act test
        AvailabilityDTO result = availabilityService.getAvailabilityById(caregiverId, availabilityId);

        //Assert
        assertNotNull(result);
        assertEquals(availabilityId, result.getId());
        assertEquals(caregiverId, result.getCaregiverId());
        assertEquals(List.of(LocalDate.of(2024, 10, 29)), result.getAvailableDates());
        assertEquals(List.of(LocalTime.of(10, 0)), result.getAvailableTimes());
        verify(availabilityRepository, times(1)).findByCaregiverIdAndId(caregiverId, availabilityId);
    }


    @Test
    public void testUpdateAvailability() {
        // Arrange
        String availabilityId = "1";
        AvailabilityDTO availabilityDTO = new AvailabilityDTO();
        availabilityDTO.setAvailableDates(List.of(LocalDate.of(2024, 11, 1)));
        availabilityDTO.setAvailableTimes(List.of(LocalTime.of(12, 0)));

        Availability availability = new Availability();
        availability.setId(availabilityId);
        availability.setAvailableDates(List.of(LocalDate.of(2024, 10, 29)));
        availability.setAvailableTimes(List.of(LocalTime.of(10, 0)));

        when(availabilityRepository.findById(availabilityId)).thenReturn(Optional.of(availability));
        when(availabilityRepository.save(any(Availability.class))).thenReturn(availability);

        // Act
        Availability result = availabilityService.updateAvailability(availabilityId, availabilityDTO);

        // Assert
        assertNotNull(result);
        assertEquals(List.of(LocalDate.of(2024, 11, 1)), result.getAvailableDates());
        assertEquals(List.of(LocalTime.of(12, 0)), result.getAvailableTimes());
        verify(availabilityRepository, times(1)).findById(availabilityId);
        verify(availabilityRepository, times(1)).save(any(Availability.class));
    }

    @Test
    public void testDeleteAvailability() {
        // Arrange
        String availabilityId = "1";

        doNothing().when(availabilityRepository).deleteById(availabilityId);

        // Act
        availabilityService.deleteAvailability(availabilityId);

        // Assert
        verify(availabilityRepository, times(1)).deleteById(availabilityId);
    }
}
