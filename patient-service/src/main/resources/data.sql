-- CREATE EXTENSION IF NOT EXISTS plpgsql;
-- CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- -- Schemas
-- CREATE SCHEMA IF NOT EXISTS patient;

-- CREATE TYPE patient.gender_enum AS ENUM ('M', 'F', 'O');

-- CREATE TABLE IF NOT EXISTS patient.patient (
--     patient_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
--     full_name TEXT NOT NULL,
--     gender patient.gender_enum NOT NULL,
--     date_of_birth DATE NOT NULL,
--     aadhaar_number VARCHAR(12), -- optional
--     contact_phone VARCHAR(15) NOT NULL,
--     email TEXT NOT NULL,
--     address TEXT NOT NULL,
--     medical_history TEXT,
--     allergies TEXT,
--     medications TEXT,
--     consents TEXT,
--     emergency_contact TEXT,
--     created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
--     updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
-- );

-- CREATE OR REPLACE FUNCTION update_updated_at_column()
-- RETURNS TRIGGER AS $$
-- BEGIN
--    NEW.updated_at = NOW();
--    RETURN NEW;
-- END;
-- $$ language 'plpgsql';

-- CREATE TRIGGER set_updated_at
-- BEFORE UPDATE ON patient.patient
-- FOR EACH ROW
-- EXECUTE FUNCTION update_updated_at_column();


-- INSERT INTO patient.patient (
--     full_name,
--     gender,
--     date_of_birth,
--     aadhaar_number,
--     contact_phone,
--     email,
--     address,
--     medical_history,
--     allergies,
--     medications,
--     consents,
--     emergency_contact
-- )
-- VALUES (
--     'John Doe',
--     'M',
--     '1990-05-15',
--     '123456789012',
--     '9876543210',
--     'john.doe@example.com',
--     '123 Main Street, City, State, ZIP',
--     'No major illnesses.',
--     'Peanuts',
--     'None',
--     'Consent for treatment granted',
--     'John Doe - 9876543211'
-- );


