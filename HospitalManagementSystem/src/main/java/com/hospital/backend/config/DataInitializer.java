package com.hospital.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {

        /* =======================
           ADMIN INITIALIZATION
           ======================= */

        Integer adminCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM admin",
                Integer.class
        );

        if (adminCount == null || adminCount == 0) {
            jdbcTemplate.update("""
                INSERT INTO admin (admin_id,email,password)
                VALUES (1,'admin@hospital.com','admin123')
            """);
            System.out.println("✅ Admin user created");
        } else {
            System.out.println("ℹ️ Admin already exists");
        }

        /* =======================
           DOCTOR & SPECIALIZATION
           ======================= */

        Integer doctorCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM doctor",
                Integer.class
        );

        if (doctorCount != null && doctorCount > 0) {
            System.out.println("ℹ️ Doctors already exist. Skipping doctor seed.");
            return;
        }

        System.out.println("🚀 Inserting doctors & specializations...");

        jdbcTemplate.update("""
            INSERT INTO speclization (Sp_Name) VALUES
            ('Gynecology'),
            ('Gastroenterology'),
            ('Psychiatry'),
            ('Pulmonology'),
            ('Endocrinology'),
            ('Nephrology'),
            ('Ophthalmology'),
            ('Urology')
        """);

        jdbcTemplate.update("""
            INSERT INTO doctor
            (Dr_name, Mobile_no, Email_id, Gender, Age, Experience, Password, Sp_Id, picture)
            VALUES
            ('Dr. Ananya Mehta','9000000001','ananya.mehta@hospital.com','Female',42,14,'password123',1,
             'https://images.unsplash.com/photo-1559839734-2b71ea197ec2'),
            ('Dr. Arjun Malhotra','9000000002','arjun.malhotra@hospital.com','Male',38,11,'password123',2,
             'https://images.unsplash.com/photo-1612349317150-e413f6a5b16d'),
            ('Dr. Kavita Rao','9000000003','kavita.rao@hospital.com','Female',35,9,'password123',3,
             'https://images.unsplash.com/photo-1607746882042-944635dfe10e'),
            ('Dr. Rohan Verma','9000000004','rohan.verma@hospital.com','Male',45,16,'password123',4,
             'https://images.unsplash.com/photo-1551601651-2a8555f1a136'),
            ('Dr. Neha Kapoor','9000000005','neha.kapoor@hospital.com','Female',34,7,'password123',5,
             'https://images.unsplash.com/photo-1598257006458-087169a1f08d'),
            ('Dr. Sandeep Iyer','9000000006','sandeep.iyer@hospital.com','Male',48,19,'password123',6,
             'https://images.unsplash.com/photo-1584516150909-c43483ee7932'),
            ('Dr. Pooja Nair','9000000007','pooja.nair@hospital.com','Female',36,10,'password123',7,
             'https://images.unsplash.com/photo-1527613426441-4da17471b66d'),
            ('Dr. Amit Kulkarni','9000000008','amit.kulkarni@hospital.com','Male',41,13,'password123',8,
             'https://images.unsplash.com/photo-1622253692010-333f2da6031d')
        """);

        System.out.println("✅ Doctors & specializations inserted");
    }
}
