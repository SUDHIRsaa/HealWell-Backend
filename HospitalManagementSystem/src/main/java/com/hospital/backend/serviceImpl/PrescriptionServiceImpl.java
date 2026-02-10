package com.hospital.backend.serviceImpl;

import com.hospital.backend.entity.Prescription;
import com.hospital.backend.repository.PrescriptionRepository;
import com.hospital.backend.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.createPrescription(prescription);
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.getAllPrescriptions();
    }

    @Override
    public Optional<Prescription> getPrescriptionById(int id) {
        return prescriptionRepository.getPrescriptionById(id);
    }

    @Override
    public Prescription updatePrescription(int id, Prescription prescription) {
        if (prescriptionRepository.existsById(id)) {
            prescription.setPrId(id);
            return prescriptionRepository.createPrescription(prescription);
        }
        throw new RuntimeException("Prescription not found with id: " + id);
    }

    @Override
    public void deletePrescription(int id) {
    	
        prescriptionRepository.deletePrescription(id);
    }

    @Override
    public List<Prescription> getPrescriptionsByDoctor(int doctorId) {
        return prescriptionRepository.getPrescriptionsByDoctor(doctorId);
    }

    @Override
    public List<Prescription> getPrescriptionsByPatient(int patientId) {
        return prescriptionRepository.getPrescriptionsByPatient(patientId);
    }

    @Override
    public List<Prescription> getPrescriptionsByAppointment(int appointmentId) {
        return prescriptionRepository.getPrescriptionsByAppointment(appointmentId);
    }

    @Override
    public List<Prescription> getPrescriptionsByDate(String date) {
        return prescriptionRepository.getPrescriptionsByDate(date);
    }

    @Override
    public boolean existsById(int id) {
        return prescriptionRepository.existsById(id);
    }
}