-- -- FUNCTION: public.update_updated_at_column()

-- -- DROP FUNCTION IF EXISTS public.update_updated_at_column();

-- CREATE OR REPLACE FUNCTION public.update_updated_at_column()
-- 	RETURNS trigger
-- 	LANGUAGE 'plpgsql'
-- 	COST 100
-- 	VOLATILE NOT LEAKPROOF
-- AS $BODY$
-- BEGIN
--    NEW.updated_at = NOW();
--    RETURN NEW;
-- END;
-- $BODY$;

-- ALTER FUNCTION public.update_updated_at_column()
-- 	OWNER TO postgres;

-- CREATE SCHEMA IF NOT EXISTS appointment
-- 	AUTHORIZATION postgres;

-- -- Enum: appointment.appointment_status_enum
-- DO $$ BEGIN
-- 	CREATE TYPE appointment.appointment_status_enum AS ENUM ('VISITED', 'NOT_VISITED', 'CANCELLED', 'RESCHEDULED');
-- EXCEPTION
-- 	WHEN duplicate_object THEN null;
-- END $$;

-- CREATE TABLE IF NOT EXISTS appointment.appointment (
-- 	appointment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
-- 	doctor_id UUID NOT NULL,
-- 	patient_id UUID NOT NULL,
-- 	slot_id UUID NOT NULL,
-- 	appointment_time TIME NOT NULL,
-- 	status appointment.appointment_status_enum NOT NULL DEFAULT 'NOT_VISITED',
-- 	rank INT NOT NULL CHECK (rank > 0),
-- 	created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
-- 	updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
-- );

-- CREATE OR REPLACE TRIGGER set_appointment_updated_at
-- 	BEFORE UPDATE ON appointment.appointment
-- 	FOR EACH ROW
-- 	EXECUTE FUNCTION public.update_updated_at_column();
