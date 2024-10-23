package health.care.booking.respository;

import health.care.booking.models.Appointment;
import health.care.booking.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {


    List<Appointment> findByPatientId(User patientId);

    List<Appointment> findByCaregiverId(User caregiverId);

    Appointment findByPatientIdAndId(User patientId, String appointmentId);
}