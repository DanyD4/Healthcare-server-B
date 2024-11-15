package health.care.booking;

import health.care.booking.dto.AppointmentDTO;
import health.care.booking.models.Appointment;
import health.care.booking.models.Status;
import health.care.booking.models.User;
import health.care.booking.respository.AppointmentRepository;
import health.care.booking.respository.UserRepository;
import health.care.booking.services.AppointmentService;
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
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "jwt.secret=hfaiehfisehfosndfejndfeswljrfeowfnjehwbewios4ngvhtrwglp4rkledf",
        "jwt.expirationMs=36000000"
})


public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAppointment() {
        //Arrange
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setPatientId("1");
        appointmentDTO.setCaregiverId("2");
        appointmentDTO.setDateTime(LocalDateTime.now().plusDays(1));

        User patient = new User();
        patient.setId("1");
        patient.setUsername("patient1");

        User caregiver = new User();
        caregiver.setId("2");
        caregiver.setUsername("caregiver1");

        Appointment appointmentToSave = new Appointment();
        appointmentToSave.setPatientId(patient.getId());
        appointmentToSave.setCaregiverId(caregiver.getId());
        appointmentToSave.setLocalDateTime(appointmentDTO.getDateTime());

        Appointment savedAppointment = new Appointment();
        savedAppointment.setId("1");
        savedAppointment.setPatientId(patient.getId());
        savedAppointment.setCaregiverId(caregiver.getId());
        savedAppointment.setLocalDateTime(appointmentDTO.getDateTime());

        when(userRepository.findById("1")).thenReturn(Optional.of(patient));
        when(userRepository.findById("2")).thenReturn(Optional.of(caregiver));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(savedAppointment);

        //Act
        Appointment result = appointmentService.createAppointment(appointmentDTO);

        //Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals(appointmentDTO.getDateTime(), result.getLocalDateTime());
    }

    @Test
    public void testUpdateAppointmentStatus() {
        //Arrange
        String appointmentId = "1";
        Status newStatus = Status.COMPLETED;

        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(Status.SCHEDULED);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        //Act
        Appointment result = appointmentService.updateAppointmentStatus(appointmentId, newStatus);

        //Assert
        assertNotNull(result);
        assertEquals(newStatus, result.getStatus());
    }

    @Test
    public void testGetAppointmentsByPatient() throws Exception {
        //Arrange
        String patientId = "1";

        User patient = new User();
        patient.setId(patientId);
        patient.setUsername("patient1");

        Appointment appointment1 = new Appointment();
        appointment1.setId("1");
        appointment1.setPatientId(patientId);

        Appointment appointment2 = new Appointment();
        appointment2.setId("2");
        appointment2.setPatientId(patientId);

        when(userRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findByPatientId(patientId)).thenReturn(Arrays.asList(appointment1, appointment2));

        //Act
        List<Appointment> result = appointmentService.getAppointmentsByPatient(patientId);

        //Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAppointmentsByCaregiver() throws Exception {

        //Arrange
        String caregiverId = "2";

        User caregiver = new User();
        caregiver.setId(caregiverId);
        caregiver.setUsername("caregiver1");

        Appointment appointment1 = new Appointment();
        appointment1.setId("1");
        appointment1.setCaregiverId(caregiverId);

        Appointment appointment2 = new Appointment();
        appointment2.setId("2");
        appointment2.setCaregiverId(caregiverId);

        when(userRepository.findById(caregiverId)).thenReturn(Optional.of(caregiver));
        when(appointmentRepository.findByCaregiverId(caregiverId)).thenReturn(Arrays.asList(appointment1, appointment2));

        //Act
        List<Appointment> result = appointmentService.getAppointmentsByCaregiver(caregiverId);

        //Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAppointmentById() throws Exception {
        //Arrange
        String patientId = "1";
        String appointmentId = "1";

        User patient = new User();
        patient.setId(patientId);
        patient.setUsername("patient1");

        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setPatientId(patientId);

        when(userRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findByPatientIdAndId(patientId, appointmentId)).thenReturn(appointment);

        //Act
        Appointment result = appointmentService.getAppointmentById(patientId, appointmentId);

        //Assert
        assertNotNull(result);
        assertEquals(appointmentId, result.getId());
    }

    @Test
    public void testUpdateAppointment() throws Exception {
        //Arrange
        String appointmentId = "1";
        String role = "admin";

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setLocalDateTime(LocalDateTime.now().plusDays(1));
        updatedAppointment.setStatus(Status.COMPLETED);

        Appointment existingAppointment = new Appointment();
        existingAppointment.setId(appointmentId);
        existingAppointment.setLocalDateTime(LocalDateTime.now());
        existingAppointment.setStatus(Status.SCHEDULED);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(existingAppointment);

        //Act
        Appointment result = appointmentService.updateAppointment(appointmentId, updatedAppointment, role);

        //Assert
        assertNotNull(result);
        assertEquals(updatedAppointment.getLocalDateTime(), result.getLocalDateTime());
        assertEquals(updatedAppointment.getStatus(), result.getStatus());
    }

    @Test
    public void testUpdateAppointment_NotAdmin() {
        //Arrange
        String appointmentId = "1";
        String role = "user";

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setLocalDateTime(LocalDateTime.now().plusDays(1));
        updatedAppointment.setStatus(Status.COMPLETED);

        //Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            appointmentService.updateAppointment(appointmentId, updatedAppointment, role);
        });

        assertEquals("Only admin can update appointments", exception.getMessage());
        System.out.println("Test passed : " + exception.getMessage());
    }
}
