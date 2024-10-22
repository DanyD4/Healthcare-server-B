import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking bookingData) {
        Booking newBooking = bookingService.createBooking(bookingData);
        return ResponseEntity.ok(newBooking);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable User userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/user/{userId}/booking/{bookingId}")
    public ResponseEntity<Booking> getSingleBooking(@PathVariable User userId, @PathVariable String bookingId) {
        Booking booking = bookingService.getSingleBooking(userId, bookingId);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable String bookingId, @RequestBody Booking updatedData, @RequestHeader("Role") String role) {
        try {
            Booking updatedBooking = bookingService.updateBooking(bookingId, updatedData, role);
            return ResponseEntity.ok(updatedBooking);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(null);  // Forbidden om inte admin
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable String bookingId, @RequestHeader("Role") String role) {
        try {
            bookingService.deleteBooking(bookingId, role);
            return ResponseEntity.ok("Booking canceled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Booking not found");
        }
    }
}