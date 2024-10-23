package health.care.booking.controllers;

import health.care.booking.models.Appointment;
import health.care.booking.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointmentData) {
        Appointment newAppointment = appointmentService.createAppointment(appointmentData);
        return ResponseEntity.ok(newAppointment);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable String patientId) throws Exception {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/caregiver/{caregiverId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByCaregiver(@PathVariable String caregiverId) throws Exception {
        List<Appointment> appointments = appointmentService.getAppointmentsByCaregiver(caregiverId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/{patientId}/appointment/{appointmentId}")
    public ResponseEntity<Appointment> getSingleAppointment(@PathVariable String patientId, @PathVariable String appointmentId) throws Exception {
        Appointment appointment = appointmentService.getAppointmentById(patientId, appointmentId);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable String appointmentId, @RequestBody Appointment updatedAppointment, @RequestHeader("Role") String role) {
        try {
            Appointment updated = appointmentService.updateAppointment(appointmentId, updatedAppointment, role);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(null);
        }
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<String> deleteAppointment(@PathVariable String appointmentId, @RequestHeader("Role") String role) {
        try {
            appointmentService.deleteAppointment(appointmentId, role);
            return ResponseEntity.ok("Appointment canceled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Appointment not found");
        }
    }
}