package com.hospital.backend.serviceImpl;

import com.hospital.backend.entity.Patient;
import com.hospital.backend.repository.PatientRepository;
import com.hospital.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Patient save(Patient patient) {
        // Add null check but skip encoding
        if (patient.getPassword() != null && !patient.getPassword().isEmpty()) {
            // Skip encoding - just use password as-is
            // patient.setPassword(passwordEncoder.encode(patient.getPassword()));
            System.out.println("Password received: " + patient.getPassword()); // Debug log
        } else {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.getAllPatients();
    }

    @Override
    public Optional<Patient> getPatientById(int id) {
        return patientRepository.getPatientById(id);
    }

    @Override
    public Patient updatePatient(int id, Patient patient) {
        if (patientRepository.existsById(id)) {
            patient.setpId(id);
            return patientRepository.save(patient);
        }
        throw new RuntimeException("Patient not found with id: " + id);
    }

    @Override
    public void deletePatient(int id) {
        patientRepository.deletePatient(id);
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }



    @Override
    public List<Patient> getPatientsByBloodGroup(String bloodGroup) {
        return patientRepository.getPatientsByBloodGroup(bloodGroup);
    }

    @Override
    public boolean existsById(int id) {
        return patientRepository.existsById(id);
    }
    public List<Patient> searchPatientsByName(String name){
		return patientRepository.searchPatientsByName(name);
    	
    }

	@Override
	public List<Patient> findByContactContaining(String contact) {
		return patientRepository.findByContactContaining(contact);
	}

	@Override
	public List<Patient> findByDoctorId(int doctorId) {
	    return patientRepository.findByDoctorId(doctorId);
	}

	  @Override
	   @Transactional
	   public Patient changePassword(int id, String currentPassword, String newPassword) {
		    System.out.println("Changing password for patient ID: " + id);
		    
		    // Get patient by ID
		    Patient patient = patientRepository.getPatientById(id)
		            .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
		    
		    // Validate current password
		    if (!currentPassword.equals(patient.getPassword())) {
		        throw new IllegalArgumentException("Current password is incorrect");
		    }
		    
		    // Update password
		    int rowsUpdated = patientRepository.updatePassword(id, newPassword);
		    
		    if (rowsUpdated == 0) {
		        throw new RuntimeException("Failed to update password, no rows affected");
		    }
		    
		    // Return the updated patient
		    patient.setPassword(newPassword);
		    return patient;
		}
} 