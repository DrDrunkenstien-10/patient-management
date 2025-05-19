-- Enable UUID support
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Schemas
CREATE SCHEMA IF NOT EXISTS admin;
CREATE SCHEMA IF NOT EXISTS staff;

-- Enums (must not use DO $$ in data.sql)
-- Manually check and ensure enums exist before app start

-- CREATE TYPE admin.system_admin_role AS ENUM ('system_admin');
-- CREATE TYPE admin.admin_access_level AS ENUM ('admin');

-- CREATE TYPE staff.gender_enum AS ENUM ('M', 'F', 'O');
-- CREATE TYPE staff.receptionist_access_level AS ENUM ('READ_ONLY', 'SCHEDULING', 'BILLING', 'ADMIN');
-- CREATE TYPE staff.receptionist_status AS ENUM ('ACTIVE', 'INACTIVE', 'TERMINATED');

-- Tables

CREATE TABLE IF NOT EXISTS admin.system_admin (
    system_admin_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    role admin.system_admin_role NOT NULL,
    access_level admin.admin_access_level NOT NULL,
    last_login TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS staff.doctor (
    doctor_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    gender staff.gender_enum NOT NULL,
    date_of_birth DATE,
    qualification TEXT NOT NULL,
    specialization TEXT NOT NULL,
    license_number TEXT NOT NULL,
    affiliated_hospital TEXT NOT NULL,
    contact_email TEXT NOT NULL,
    contact_phone TEXT NOT NULL,
    practice_location TEXT NOT NULL,
    role_code TEXT
);

CREATE TABLE IF NOT EXISTS staff.receptionist (
    receptionist_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    gender staff.gender_enum NOT NULL,
    date_of_birth DATE,
    employee_code TEXT NOT NULL,
    department TEXT NOT NULL,
    contact_email TEXT NOT NULL,
    contact_phone TEXT NOT NULL,
    assigned_facility TEXT NOT NULL,
    role_title TEXT NOT NULL,
    access_level staff.receptionist_access_level NOT NULL,
    last_login TIMESTAMPTZ,
    status staff.receptionist_status NOT NULL
);

-- Optional sample data

INSERT INTO admin.system_admin (name, role, access_level, last_login)
VALUES ('Alice Admin', 'system_admin', 'admin', NOW());
