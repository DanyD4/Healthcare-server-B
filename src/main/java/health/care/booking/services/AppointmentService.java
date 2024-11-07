package health.care.booking.services;

import health.care.booking.dto.AvailabilityDTO;
import health.care.booking.models.Appointment;
import health.care.booking.models.Status;
import health.care.booking.models.User;
import health.care.booking.respository.AppointmentRepository;
import health.care.booking.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AvailabilityService availabilityService;


   /* // Skapa en ny bokning
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }*/

    //Skapa en ny bokning
   /* public Appointment createAppointment(Appointment appointment) throws Exception {
        User patient = userRepository.findById(appointment.getPatientId())
                .orElseThrow(() -> new Exception("Patient not found"));
        User caregiver = userRepository.findById(appointment.getCaregiverId())
                .orElseThrow(() -> new Exception("Caregiver not found"));

        appointment.setPatientId(patient.getId());
        appointment.setCaregiverId(caregiver.getId());

        return appointmentRepository.save(appointment);
    }*/

    //Boka en tid
    public Appointment createAppointment(Appointment appointmentData) throws Exception {
        //Kontrollera tillgänglighet
        List<AvailabilityDTO> availableSlots = availabilityService.getAllAvailabilitiesByCaregiverId(appointmentData.getCaregiverId());
        boolean isAvailable = availableSlots.stream().anyMatch(slot ->
                slot.getAvailableDates().contains(appointmentData.getDate()) && slot.getAvailableTimes().contains(appointmentData.getTime())
        );

        if (!isAvailable) {
            throw new Exception("Tiden är inte tillgänglig");
        }

        //skapa bokning
        appointmentData.setStatus(Status.SCHEDULED);
        return appointmentRepository.save(appointmentData);
    }


    //Uppdatera status för en bokning
    public Appointment updateAppointmentStatus(String appointmentId, Status status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Bokning inte hittad"));

        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }



    // Hämta alla bokningar för en specifik patient baserat på patientens ID
    public List<Appointment> getAppointmentsByPatient(String patientId) throws Exception {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new Exception("User not found"));


        return appointmentRepository.findByPatientId(patient.getId());

    }

    // Hämta alla bokningar för en specifik vårdgivare baserat på caregiverId
    public List<Appointment> getAppointmentsByCaregiver(String caregiverId) throws Exception {
        User caregiver = userRepository.findById(caregiverId)
                .orElseThrow(() -> new Exception("User not found"));


        return appointmentRepository.findByCaregiverId(caregiver.getId());

    }

    // Hämta en specifik bokning via patientId och boknings-ID
    public Appointment getAppointmentById(String patientId, String appointmentId) throws Exception {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new Exception("User not found"));


        return appointmentRepository.findByPatientIdAndId(patient.getId(), appointmentId);
    }

    // Uppdatera en bokning (endast för administratörer)
    public Appointment updateAppointment(String appointmentId, Appointment updatedAppointment, String role) throws Exception {
        if (!role.equals("admin")) {
            throw new Exception("Only admin can update appointments");
        }

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new Exception("Appointment not found"));

        appointment.setDate(updatedAppointment.getDate());
        appointment.setTime(updatedAppointment.getTime());
        appointment.setStatus(updatedAppointment.getStatus());

        return appointmentRepository.save(appointment);
    }

  /* // Ta bort en bokning (kan utföras av både användare och admin)
    public void deleteAppointment(String appointmentId, String role) throws Exception {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new Exception("Appointment not found"));

        appointmentRepository.delete(appointment);
        sendNotification(appointmentId, role);
    }

    // Notifiering
    private void sendNotification(String appointmentId, String role) {
        if (role.equals("admin")) {
            System.out.println("Admin has canceled the appointment with ID " + appointmentId);
        } else {
            System.out.println("User has canceled the appointment with ID " + appointmentId);
        }
    }*/

    //Avbokar en bokning och uppdaterar status till CANCELLED
    public void cancelAppointment(String appointmentId, String role) throws Exception {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new Exception("Appointment not found"));

        //Uppdatera status till CANCELLED
        appointment.setStatus(Status.CANCELLED);
        appointmentRepository.save(appointment);

        //Gör den avbokade tiden tillgänglig igen
        availabilityService.addAvailabilitySlot(appointment.getCaregiverId(), appointment.getDate(), appointment.getTime());

        System.out.println("Appointment with ID " + appointmentId + " has been cancelled.");
    }
}
