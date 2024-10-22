package health.care.booking.respository;

import health.care.booking.models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByUserId(User userId);
    Booking findByUserIdAndId(User userId, String bookingId);
}