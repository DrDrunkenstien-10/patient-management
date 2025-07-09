-- -- Enable UUID support
-- CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- -- Schemas
-- CREATE SCHEMA IF NOT EXISTS admin;
-- CREATE SCHEMA IF NOT EXISTS staff;

-- -- Enums (must not use DO $$ in data.sql)
-- -- Manually check and ensure enums exist before app start

-- CREATE TYPE admin.system_admin_role AS ENUM ('system_admin');
-- CREATE TYPE admin.admin_access_level AS ENUM ('admin');

-- CREATE TYPE staff.gender_enum AS ENUM ('M', 'F', 'O');
-- CREATE TYPE staff.receptionist_access_level AS ENUM ('READ_ONLY', 'SCHEDULING', 'BILLING', 'ADMIN');
-- CREATE TYPE staff.receptionist_status AS ENUM ('ACTIVE', 'INACTIVE', 'TERMINATED');

-- -- Tables

-- CREATE TABLE IF NOT EXISTS admin.system_admin (
--     system_admin_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
--     name TEXT NOT NULL,
--     role admin.system_admin_role NOT NULL,
--     access_level admin.admin_access_level NOT NULL,
--     last_login TIMESTAMPTZ,
--     created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
-- );

-- CREATE TABLE IF NOT EXISTS staff.doctor (
--     doctor_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
--     name TEXT NOT NULL,
--     gender staff.gender_enum NOT NULL,
--     date_of_birth DATE,
--     qualification TEXT NOT NULL,
--     specialization TEXT NOT NULL,
--     license_number TEXT NOT NULL,
--     affiliated_hospital TEXT NOT NULL,
--     contact_email TEXT NOT NULL,
--     contact_phone TEXT NOT NULL,
--     practice_location TEXT NOT NULL,
--     role_code TEXT,
--     created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
-- );

-- CREATE TABLE IF NOT EXISTS staff.receptionist (
--     receptionist_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
--     name TEXT NOT NULL,
--     gender staff.gender_enum NOT NULL,
--     date_of_birth DATE,
--     employee_code TEXT NOT NULL,
--     department TEXT NOT NULL,
--     contact_email TEXT NOT NULL,
--     contact_phone TEXT NOT NULL,
--     assigned_facility TEXT NOT NULL,
--     role_title TEXT NOT NULL,
--     access_level staff.receptionist_access_level NOT NULL,
--     last_login TIMESTAMPTZ,
--     status staff.receptionist_status NOT NULL,
--     created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
-- );

-- CREATE OR REPLACE FUNCTION update_updated_at_column()
-- RETURNS TRIGGER AS $$
-- BEGIN
--    NEW.updated_at = NOW();
--    RETURN NEW;
-- END;
-- $$ language 'plpgsql';

-- -- For system_admin
-- CREATE TRIGGER set_admin_updated_at
-- BEFORE UPDATE ON admin.system_admin
-- FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- -- For doctor
-- CREATE TRIGGER set_doctor_updated_at
-- BEFORE UPDATE ON staff.doctor
-- FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- -- For receptionist
-- CREATE TRIGGER set_receptionist_updated_at
-- BEFORE UPDATE ON staff.receptionist
-- FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- -- Optional sample data

-- INSERT INTO admin.system_admin (name, role, access_level, last_login)
-- VALUES ('Alice Admin', 'system_admin', 'admin', NOW());

-- INSERT INTO staff.doctor (
--     name, gender, date_of_birth, qualification, specialization, license_number,
--     affiliated_hospital, contact_email, contact_phone, practice_location, role_code
-- )
-- VALUES
-- ('Dr. Alice Morgan', 'F', '1980-04-12', 'MBBS, MD', 'Cardiology', 'LIC123456', 'Central General Hospital', 'alice.morgan@example.com', '555-123-4567', '123 Heart St, New York, NY', 'DOC01'),
-- ('Dr. Brian Chen', 'M', '1975-06-22', 'MBBS, MS', 'Orthopedics', 'LIC234567', 'Metro Care Center', 'brian.chen@example.com', '555-234-5678', '456 Bone Ave, Los Angeles, CA', 'DOC02'),
-- ('Dr. Clara Singh', 'F', '1983-11-08', 'MBBS, MD', 'Dermatology', 'LIC345678', 'Sunshine Clinic', 'clara.singh@example.com', '555-345-6789', '789 Skin Dr, Chicago, IL', 'DOC03'),
-- ('Dr. Daniel Roy', 'M', '1990-03-15', 'MBBS', 'Pediatrics', 'LIC456789', 'Children Health Hub', 'daniel.roy@example.com', '555-456-7890', '321 Kids Ln, Houston, TX', 'DOC04'),
-- ('Dr. Emma Wright', 'F', '1985-09-20', 'MBBS, MD', 'Neurology', 'LIC567890', 'Brain Care Hospital', 'emma.wright@example.com', '555-567-8901', '654 Brain Blvd, Seattle, WA', 'DOC05'),
-- ('Dr. Farhan Malik', 'M', '1979-01-11', 'MBBS, MS', 'General Surgery', 'LIC678901', 'City Hospital', 'farhan.malik@example.com', '555-678-9012', '987 Scalpel St, Miami, FL', 'DOC06'),
-- ('Dr. Grace Lin', 'F', '1992-05-27', 'MBBS', 'Family Medicine', 'LIC789012', 'Wellness Center', 'grace.lin@example.com', '555-789-0123', '222 Family Rd, Denver, CO', 'DOC07'),
-- ('Dr. Haruto Saito', 'M', '1987-07-19', 'MBBS, MD', 'Oncology', 'LIC890123', 'Cancer Care Institute', 'haruto.saito@example.com', '555-890-1234', '333 Cure Ave, Boston, MA', 'DOC08'),
-- ('Dr. Irene Novak', 'F', '1991-12-05', 'MBBS', 'Internal Medicine', 'LIC901234', 'HealthFirst Hospital', 'irene.novak@example.com', '555-901-2345', '444 Vital St, San Diego, CA', 'DOC09'),
-- ('Dr. John Park', 'M', '1982-02-17', 'MBBS, MS', 'Urology', 'LIC012345', 'Westside Medical Center', 'john.park@example.com', '555-012-3456', '555 Flow Rd, Austin, TX', 'DOC10'),
-- ('Dr. Kavita Rao', 'F', '1978-08-30', 'MBBS, MD', 'Gynecology', 'LIC112233', 'Women’s Wellness Hospital', 'kavita.rao@example.com', '555-112-2334', '666 Blossom St, San Jose, CA', 'DOC11'),
-- ('Dr. Leo Carter', 'M', '1986-04-10', 'MBBS, MD', 'Psychiatry', 'LIC223344', 'MindCare Clinic', 'leo.carter@example.com', '555-223-3445', '777 Calm Ln, Portland, OR', 'DOC12'),
-- ('Dr. Maya Patel', 'F', '1993-10-25', 'MBBS', 'Ophthalmology', 'LIC334455', 'Vision Health Center', 'maya.patel@example.com', '555-334-4556', '888 Vision Blvd, Atlanta, GA', 'DOC13'),
-- ('Dr. Nathan Blake', 'M', '1984-03-05', 'MBBS, MD', 'Endocrinology', 'LIC445566', 'Hormone Clinic', 'nathan.blake@example.com', '555-445-5667', '999 Hormone St, Phoenix, AZ', 'DOC14'),
-- ('Dr. Olivia Zhang', 'F', '1989-06-18', 'MBBS', 'Emergency Medicine', 'LIC556677', 'Rapid Response Hospital', 'olivia.zhang@example.com', '555-556-6778', '101 ER Rd, Philadelphia, PA', 'DOC15');
