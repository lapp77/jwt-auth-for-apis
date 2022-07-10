BEGIN;

-- sets up project tables
CREATE TABLE IF NOT EXISTS public.user_profile (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    role TEXT NOT NULL CHECK (role in ('MGR', 'ADMIN', 'REPORTEE')),
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    address TEXT NOT NULL,
    age INTEGER NOT NULL,
    phone TEXT NOT NULL,
    tag TEXT NOT NULL DEFAULT ''
);

CREATE TABLE IF NOT EXISTS public.user_credentials (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username TEXT NOT NULL REFERENCES user_profile(email),
    password TEXT NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS public.user_manager (
    user_id INTEGER NOT NULL REFERENCES user_profile,
    manager_id INTEGER NOT NULL REFERENCES user_profile,
    UNIQUE (user_id, manager_id)
);

CREATE TABLE IF NOT EXISTS public.items  (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    type TEXT NOT NULL,
    name TEXT NOT NULL,
    price INTEGER NOT NULL,
    description TEXT NOT NULL
);


-- adds initial data

-- create user profiles
INSERT INTO public.user_profile(role, first_name, last_name, email, address, age, phone, tag) VALUES ('MGR', 'John', 'Doe', 'john@email.com', 'Bernard St', 23, '202-555-0139', 'user');
INSERT INTO public.user_profile(role, first_name, last_name, email, address, age, phone, tag) VALUES ('ADMIN', 'Alice', 'Sprinter', 'alice@email.com', 'South St', 34, '202-555-0120', 'owner');

-- create user credentials
-- john password: abcd1234#
INSERT INTO public.user_credentials (username, password, enabled) VALUES ('john@email.com', '$2a$10$QNcuxrpgt7SJKiPEK73kQu47Lwng4DOS55rqzOFIFh..t8N1e4Sfm', true);
-- alice password: abcd1234#
INSERT INTO public.user_credentials (username, password, enabled) VALUES ('alice@email.com', '$2a$10$rFeq4KG9Q5nAEeA4slCydeSdWMMydGU7OHd.gur5.0qsgRJuK5jXS', true);

-- create items
INSERT INTO public.items(type, name, price, description) VALUES ('HARDWARE', 'ASX motherboard', 10099, 'a printed circuit board');
INSERT INTO public.items(type, name, price, description) VALUES ('HARDWARE', 'OTT RAM memory', 2099, 'volatile computer memory');
INSERT INTO public.items(type, name, price, description) VALUES ('SOFTWARE', 'Linux CD', 0, 'a free operating system kernel');

COMMIT;