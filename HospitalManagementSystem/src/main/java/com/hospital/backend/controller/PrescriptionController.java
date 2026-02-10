package com.hospital.backend.controller;

import com.hospital.backend.entity.Prescription;
import com.hospital.backend.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "*")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    @GetMapping("/{id}")
    public Prescription getPrescriptionById(@PathVariable int id) {
        return prescriptionService.getPrescriptionById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
    }

    @PostMapping
    public Prescription createPrescription(@RequestBody Prescription prescription) {
        return prescriptionService.createPrescription(prescription);
    }

    @PutMapping("/{id}")
    public Prescription updatePrescription(@PathVariable int id, @RequestBody Prescription prescription) {
        return prescriptionService.updatePrescription(id, prescription);
    }

    @DeleteMapping("/{id}")
    public void deletePrescription(@PathVariable int id) {
        prescriptionService.deletePrescription(id);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Prescription> getPrescriptionsByDoctor(@PathVariable int doctorId) {
        return prescriptionService.getPrescriptionsByDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<Prescription> getPrescriptionsByPatient(@PathVariable int patientId) {
        return prescriptionService.getPrescriptionsByPatient(patientId);
    }

    @GetMapping("/appointment/{appointmentId}")
    public List<Prescription> getPrescriptionsByAppointment(@PathVariable int appointmentId) {
        return prescriptionService.getPrescriptionsByAppointment(appointmentId);
    }

    @GetMapping("/date/{date}")
    public List<Prescription> getPrescriptionsByDate(@PathVariable String date) {
        return prescriptionService.getPrescriptionsByDate(date);
    }
}
