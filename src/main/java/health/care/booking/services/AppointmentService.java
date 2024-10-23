package health.care.booking.services;

import health.care.booking.models.Appointment;
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

    // Skapa en ny bokning
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // Hämta alla bokningar för en specifik patient baserat på patientens ID
    public List<Appointment> getAppointmentsByPatient(String patientId) throws Exception {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new Exception("User not found"));

        return appointmentRepository.findByPatientId(patient);
    }

    // Hämta alla bokningar för en specifik vårdgivare baserat på caregiverId
    public List<Appointment> getAppointmentsByCaregiver(String caregiverId) throws Exception {
        User caregiver = userRepository.findById(caregiverId)
                .orElseThrow(() -> new Exception("User not found"));

        return appointmentRepository.findByCaregiverId(caregiver);
    }

    // Hämta en specifik bokning via patientId och boknings-ID
    public Appointment getAppointmentById(String patientId, String appointmentId) throws Exception {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new Exception("User not found"));

        return appointmentRepository.findByPatientIdAndId(patient, appointmentId);
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

    // Ta bort en bokning (kan utföras av både användare och admin)
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
    }
}