package com.hospital.backend.repositoryImpl;

import com.hospital.backend.entity.Prescription;
import com.hospital.backend.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PrescriptionRepositoryImpl implements PrescriptionRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Prescription> prescriptionRowMapper = new RowMapper<Prescription>() {
        @Override
        public Prescription mapRow(ResultSet rs, int rowNum) throws SQLException {
            Prescription prescription = new Prescription();
            prescription.setPrId(rs.getInt("Pr_ID"));
            prescription.setApId(rs.getInt("Ap_Id"));
            prescription.setpId(rs.getInt("P_ID"));
            prescription.setMedicine(rs.getString("medicine"));
            prescription.setAdvice(rs.getString("advice"));
            prescription.setRemark(rs.getString("remark"));
            return prescription;
        }
    };

    @Override
    public List<Prescription> getPrescriptionsByDoctor(int doctorId) {
        // Join with appointments to get doctor ID
        String sql = "SELECT p.* FROM prescription p JOIN appointment a ON p.Ap_Id = a.Ap_ID WHERE a.DR_ID = ?";
        return jdbcTemplate.query(sql, prescriptionRowMapper, doctorId);
    }

    @Override
    public List<Prescription> getPrescriptionsByPatient(int patientId) {
        String sql = "SELECT * FROM prescription WHERE P_ID = ?";
        return jdbcTemplate.query(sql, prescriptionRowMapper, patientId);
    }

    @Override
    public List<Prescription> getPrescriptionsByAppointment(int appointmentId) {
        String sql = "SELECT * FROM prescription WHERE Ap_Id = ?";
        return jdbcTemplate.query(sql, prescriptionRowMapper, appointmentId);
    }

    @Override
    public List<Prescription> getPrescriptionsByDate(String date) {
        // Assuming we need to join with appointments to get the date
        String sql = "SELECT p.* FROM prescription p JOIN appointment a ON p.Ap_Id = a.Ap_ID WHERE a.appointment_date = ?";
        return jdbcTemplate.query(sql, prescriptionRowMapper, date);
    }



    @Override
    public Prescription createPrescription(Prescription prescription) {
        if (prescription.getPrId() == 0) {
            String sql = "INSERT INTO prescription (Ap_Id, P_ID, medicine, advice, remark) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, 
                prescription.getApId(),
                prescription.getpId(),
                prescription.getMedicine(),
                prescription.getAdvice(),
                prescription.getRemark());
            return prescription;
        } else {
            String sql = "UPDATE prescription SET Ap_Id = ?, P_ID = ?, medicine = ?, advice = ?, remark = ? WHERE Pr_ID = ?";
            jdbcTemplate.update(sql,
                prescription.getApId(),
                prescription.getpId(),
                prescription.getMedicine(),
                prescription.getAdvice(),
                prescription.getRemark(),
                prescription.getPrId());
            return prescription;
        }
    }

    @Override
    public void deletePrescription(int id) {
        String sql = "DELETE FROM prescription WHERE Pr_ID = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        String sql = "SELECT * FROM prescription";
        return jdbcTemplate.query(sql, prescriptionRowMapper);
    }
    

    @Override
    public Prescription updatePrescription(int id, Prescription prescription) {
        if (prescription.getPrId() == 0) {
            throw new IllegalArgumentException("Prescription ID must be provided for update");
        }
        return createPrescription(prescription);
    }





    @Override
    public Optional<Prescription> getPrescriptionById(int id) {
        try {
            String sql = "SELECT * FROM prescription WHERE PR_ID = ?";
            Prescription prescription = jdbcTemplate.queryForObject(sql, prescriptionRowMapper, id);
            return Optional.ofNullable(prescription);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM prescription WHERE PR_ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}