package com.hospital.backend.serviceImpl;

import com.hospital.backend.EmailService;
import com.hospital.backend.entity.Appointment;
import com.hospital.backend.entity.Patient;
import com.hospital.backend.repository.AppointmentRepository;
import com.hospital.backend.repository.PatientRepository;
import com.hospital.backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.createAppointment(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.getAllAppointments();
    }

    @Override
    public Optional<Appointment> getAppointmentById(int id) {
        return appointmentRepository.getAppointmentById(id);
    }

    @Override
    public Appointment updateAppointment(int id, Appointment appointment) {
        // Fetch the existing appointment
        Appointment existing = appointmentRepository.getAppointmentById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        // Check if date or time changed
        boolean dateOrTimeChanged = 
            !existing.getAppointmentDate().equals(appointment.getAppointmentDate()) ||
            !existing.getAppointmentTime().equals(appointment.getAppointmentTime());

        // If status is Pending and date/time changed, update status and send email
        if (dateOrTimeChanged && "PENDING".equalsIgnoreCase(existing.getStatus())) {
            existing.setStatus("SCHEDULED");
            int patientId = existing.getpId(); // or getPatient_id(), adjust as per your entity
            Patient patient = patientRepository.getPatientById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
            // Send email to patient
            String to = patient.getEmail();
            String subject = "Your Appointment is Scheduled";
            String text = "Dear " + patient.getName() +
                          ",\nYour appointment has been scheduled for " +
                          appointment.getAppointmentDate() + " at " + appointment.getAppointmentTime() + ".";
            emailService.sendSimpleMessage(to, subject, text);
        }
       
        // Update the rest of the fields
        existing.setAppointmentDate(appointment.getAppointmentDate());
        existing.setAppointmentTime(appointment.getAppointmentTime());
        // ... update other fields as needed ...

        // Save and return
        return appointmentRepository.updateAppointment(existing);
    }

    @Override
    public void deleteAppointment(int id) {
        appointmentRepository.deleteAppointment(id);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        return appointmentRepository.getAppointmentsByDoctor(doctorId);
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        return appointmentRepository.getAppointmentsByPatient(patientId);
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.getAppointmentsByStatus(status);
    }

    @Override
    public List<Appointment> getAppointmentsByDate(String date) {
        return appointmentRepository.getAppointmentsByDate(date);
    }

    @Override
    public List<Appointment> getUpcomingAppointments(int doctorId) {
        return appointmentRepository.getUpcomingAppointments(doctorId);
    }

    @Override
    public List<Appointment> getPastAppointments(int doctorId) {
        return appointmentRepository.getPastAppointments(doctorId);
    }

    @Override
    public List<Appointment> getTodayAppointments(int doctorId) {
        return appointmentRepository.getTodayAppointments(doctorId);
    }

	@Override
	public Appointment updateStatus(int id, String status) {
		return appointmentRepository.updateStatus(id, status);
	}


}