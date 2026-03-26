DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS reminders CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS medicines CASCADE;
DROP TABLE IF EXISTS prescriptions CASCADE;
DROP TABLE IF EXISTS appointments CASCADE;
DROP TABLE IF EXISTS patients CASCADE;
DROP TABLE IF EXISTS doctors CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL,
  phone VARCHAR(255),
  full_name VARCHAR(255)
);

CREATE TABLE doctors (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL UNIQUE,
  specialty VARCHAR(255),
  license_number VARCHAR(255),
  CONSTRAINT fk_doctors_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE patients (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL UNIQUE,
  address VARCHAR(255),
  CONSTRAINT fk_patients_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE appointments (
  id BIGSERIAL PRIMARY KEY,
  patient_id BIGINT NOT NULL,
  doctor_id BIGINT NOT NULL,
  start_time TIMESTAMP,
  end_time TIMESTAMP,
  reason VARCHAR(255),
  status VARCHAR(50),
  CONSTRAINT fk_appointments_patient FOREIGN KEY (patient_id) REFERENCES patients (id),
  CONSTRAINT fk_appointments_doctor FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);

CREATE TABLE prescriptions (
  id BIGSERIAL PRIMARY KEY,
  patient_id BIGINT NOT NULL,
  doctor_id BIGINT NOT NULL,
  instructions VARCHAR(2000),
  image_file_name VARCHAR(255),
  image_content_type VARCHAR(255),
  image_data BYTEA,
  created_at TIMESTAMP,
  CONSTRAINT fk_prescriptions_patient FOREIGN KEY (patient_id) REFERENCES patients (id),
  CONSTRAINT fk_prescriptions_doctor FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);

CREATE TABLE medicines (
  id BIGSERIAL PRIMARY KEY,
  prescription_id BIGINT NOT NULL,
  medicine_name VARCHAR(255),
  dosage VARCHAR(255),
  frequency VARCHAR(255),
  duration VARCHAR(255),
  time_of_day VARCHAR(255),
  meal_timing VARCHAR(255),
  CONSTRAINT fk_medicines_prescription FOREIGN KEY (prescription_id) REFERENCES prescriptions (id)
);

CREATE TABLE orders (
  id BIGSERIAL PRIMARY KEY,
  patient_id BIGINT NOT NULL,
  ordered_at TIMESTAMP,
  status VARCHAR(50),
  delivery_address VARCHAR(255),
  CONSTRAINT fk_orders_patient FOREIGN KEY (patient_id) REFERENCES patients (id)
);

CREATE TABLE order_items (
  id BIGSERIAL PRIMARY KEY,
  order_id BIGINT NOT NULL,
  medicine_name VARCHAR(255),
  dosage VARCHAR(255),
  quantity INT,
  CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE reminders (
  id BIGSERIAL PRIMARY KEY,
  patient_id BIGINT NOT NULL,
  type VARCHAR(50),
  message VARCHAR(255),
  due_at TIMESTAMP,
  sent BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP,
  CONSTRAINT fk_reminders_patient FOREIGN KEY (patient_id) REFERENCES patients (id)
);
