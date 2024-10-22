import health.care.booking.respository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private AvailabilityRepository bookingRepository;

    public Booking createBooking(Booking bookingData) {
        bookingData.setAppointmentTime(LocalDateTime.now());  // Sätter tid om det behövs
        return bookingRepository.save(bookingData);
    }

    public List<Booking> getBookingsByUser(User userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking getSingleBooking(User userId, String bookingId) {
        return bookingRepository.findByUserIdAndId(userId, bookingId);
    }

    public Booking updateBooking(String bookingId, Booking updatedData, String role) throws Exception {
        if (!role.equals("admin")) {
            throw new Exception("Only admin can update bookings");
        }
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking not found"));

        // Uppdatera fälten som kan ändras
        booking.setAppointmentTime(updatedData.getAppointmentTime());
        booking.setServiceDetails(updatedData.getServiceDetails());
        booking.setStatus(updatedData.getStatus());

        return bookingRepository.save(booking);
    }

    public void deleteBooking(String bookingId, String role) throws Exception {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking not found"));

        bookingRepository.delete(booking);
        sendNotification(bookingId, role);
    }

    private void sendNotification(String bookingId, String role) {
        if (role.equals("admin")) {
            System.out.println("Admin has canceled the booking with ID " + bookingId);
        } else {
            System.out.println("User has canceled the booking with ID " + bookingId);
        }
    }
}