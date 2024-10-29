package health.care.booking.respository;

import health.care.booking.models.Availability;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilityRepository extends MongoRepository<Availability, String> {
    //Hämtar alla tillgänglighetstider för en specifik vårdgivare
    List<Availability> findByCaregiverId(String caregiverId);

    //Hämtar en specifik tillgänglighetstid för en vårdgivare baserat på caregiverId och availabilityId
    Availability findByCaregiverIdAndId(String caregiverId, String availabilityId);
}