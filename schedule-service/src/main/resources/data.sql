-- -- ====================================
-- -- Extensions
-- -- ====================================
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- -- ====================================
-- -- Create Schema
-- -- ====================================
-- CREATE SCHEMA IF NOT EXISTS schedule
-- AUTHORIZATION postgres;
-- -- ====================================
-- -- SCHEMA: slot========================
-- -- ====================================

-- CREATE SCHEMA IF NOT EXISTS slot
--     AUTHORIZATION postgres;

-- -- ====================================
-- -- Enum for schedule_type
-- -- ====================================
-- DO $$
-- BEGIN
--     IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'schedule_type_enum') THEN
--         CREATE TYPE schedule.schedule_type_enum AS ENUM ('week', 'month', 'custom');
--     END IF;
-- END
-- $$;

-- -- ====================================
-- -- Function to update `updated_at`
-- -- ====================================
-- CREATE OR REPLACE FUNCTION public.update_updated_at_column()
-- RETURNS TRIGGER AS $$
-- BEGIN
--    NEW.updated_at = now();
--    RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;

-- -- ====================================
-- -- Table: schedule.schedule
-- -- ====================================
-- CREATE TABLE schedule.schedule (
--     schedule_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
--     doc_id UUID NOT NULL,
--     schedule_type schedule.schedule_type_enum NOT NULL,
--     start_date DATE NOT NULL,
--     end_date DATE NOT NULL,
--     created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
--     updated_at TIMESTAMP WITH TIME ZONE DEFAULT now()
-- );
-- ALTER TABLE IF EXISTS schedule.schedule
--     OWNER to postgres;

-- -- ====================================
-- -- Table: schedule.availability
-- -- ====================================
-- CREATE TABLE schedule.availability (
--     availability_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
--     doc_id UUID NOT NULL,
--     slot_id UUID NOT NULL,
--     date DATE NOT NULL,
--     availability BOOLEAN NOT NULL DEFAULT TRUE,
--     unavailability_reason TEXT,
--     created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
--     updated_at TIMESTAMP WITH TIME ZONE DEFAULT now()
-- );
-- ALTER TABLE IF EXISTS schedule.availability
--     OWNER to postgres;

-- -- =====================================
-- -- Table: slot.slot
-- -- =====================================

-- CREATE TABLE IF NOT EXISTS slot.slot
-- (
--     slot_id uuid NOT NULL DEFAULT gen_random_uuid(),
--     name text COLLATE pg_catalog."default" NOT NULL,
--     start_time time without time zone NOT NULL,
--     end_time time without time zone NOT NULL,
--     capacity integer NOT NULL,
--     session_duration integer NOT NULL,
--     created_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     doctor_id uuid,
--     CONSTRAINT slot_pkey PRIMARY KEY (slot_id),
--     CONSTRAINT slot_capacity_check CHECK (capacity > 0),
--     CONSTRAINT slot_session_duration_check CHECK (session_duration > 0)
-- )

-- TABLESPACE pg_default;

-- ALTER TABLE IF EXISTS slot.slot
--     OWNER to postgres;

-- -- ====================================
-- -- Triggers
-- -- ====================================
-- CREATE TRIGGER trg_update_schedule_updated_at
-- BEFORE UPDATE ON schedule.schedule
-- FOR EACH ROW
-- EXECUTE FUNCTION public.update_updated_at_column();

-- CREATE TRIGGER trg_update_availability_updated_at
-- BEFORE UPDATE ON schedule.availability
-- FOR EACH ROW
-- EXECUTE FUNCTION public.update_updated_at_column();

-- -- Trigger: set_slot_updated_at

-- -- DROP TRIGGER IF EXISTS set_slot_updated_at ON slot.slot;

-- CREATE OR REPLACE TRIGGER set_slot_updated_at
--     BEFORE UPDATE 
--     ON slot.slot
--     FOR EACH ROW
--     EXECUTE FUNCTION public.update_updated_at_column();


-- Insert dummy 
INSERT INTO slot.slot (slot_id, name, start_time, end_time, capacity, session_duration, doctor_id) 
VALUES (gen_random_uuid(), 'Morning Slot', '09:00', '12:00', 10, 30, gen_random_uuid());
INSERT INTO schedule.schedule (schedule_id, doc_id, schedule_type, start_date, end_date) 
VALUES (gen_random_uuid(), gen_random_uuid(), 'week', '2025-06-01', '2025-06-07');
INSERT INTO schedule.availability (availability_id, doc_id, slot_id, date, availability, unavailability_reason) 
VALUES (gen_random_uuid(), gen_random_uuid(), (SELECT slot_id FROM slot.slot LIMIT 1), '2025-06-01', FALSE, 'Doctor on leave'), 
(gen_random_uuid(), gen_random_uuid(), (SELECT slot_id FROM slot.slot LIMIT 1), '2025-06-02', TRUE, NULL);

