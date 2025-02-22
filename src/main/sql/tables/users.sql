--CREATE TYPE vitals.user_role IF NOT EXISTS AS ENUM ('player', 'developer', 'recruiter', 'admin');

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_role') THEN
        CREATE TYPE vitals.user_role AS ENUM ('player', 'developer', 'recruiter', 'admin');
    END IF;
END $$;

CREATE TABLE IF NOT EXISTS vitals.users(
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    country VARCHAR(100) NOT NULL,
    role USER_ROLE NOT NULL,
    password VARCHAR(50) UNIQUE NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);