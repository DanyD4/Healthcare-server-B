package health.care.booking.respository;

import health.care.booking.models.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {



    List<Appointment> findByPatientId(String patientId);

    List<Appointment> findByCaregiverId(String caregiverId);

    Appointment findByPatientIdAndId(String patientId, String appointmentId);

}