package health.care.booking.services;

import health.care.booking.models.Appointment;
import health.care.booking.models.Feedback;
import health.care.booking.respository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Feedback createFeedback(Feedback feedback) {
        //Kontrollera om appointment finns
        Optional<Appointment> appointment = appointmentRepository.findById(feedback.getAppointmentId().getId());
        if (appointment.isPresent()) {
            return feedbackRepository.save(feedback);
        } else {
            throw new IllegalArgumentException("Appointment does not exist");
        }
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> getFeedbackById(String id) {
        return feedbackRepository.findById(id);
    }

    public Feedback updateFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(String id) {
        feedbackRepository.deleteById(id);
    }
}