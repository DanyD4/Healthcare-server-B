package health.care.booking.controllers;

import health.care.booking.models.Feedback;
import health.care.booking.services.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //Markerar denna klass som en REST-kontroller, vilket innebär att den hanterar HTTP-förfrågningar
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    //Hanterar POST-förfrågningar för att skapa ny feedback
    //http://localhost:8080/feedback
    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody Feedback feedback) {
        Feedback createdFeedback = feedbackService.createFeedback(feedback);
        return ResponseEntity.ok(createdFeedback); //Returnerar den skapade feedbacken med status 200
    }

    //Hanterar GET-förfrågningar för att hämta alla feedbacks
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    //Hanterar GET-förfrågningar för att hämta en specifik feedback baserat på ID
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable String id) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        return feedback.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Hanterar PUT-förfrågningar för att uppdatera en specifik feedback baserat på ID
    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable String id, @RequestBody Feedback feedback) {
        feedback.setId(id);
        Feedback updatedFeedback = feedbackService.updateFeedback(feedback);
        return ResponseEntity.ok(updatedFeedback);
    }

    //Hanterar DELETE-förfrågningar för att ta bort en specifik feedback baserat på ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable String id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
}
