SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS order_medicines;
DROP TABLE IF EXISTS reminders;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS medicines;
DROP TABLE IF EXISTS prescriptions;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS doctor;
DROP TABLE IF EXISTS users;
SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL,
  phone VARCHAR(255),
  full_name VARCHAR(255),
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_email (email)
);

CREATE TABLE doctors (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  specialty VARCHAR(255),
  license_number VARCHAR(255),
  PRIMARY KEY (id),
  UNIQUE KEY uk_doctors_user (user_id),
  CONSTRAINT fk_doctors_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE patients (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  address VARCHAR(255),
  PRIMARY KEY (id),
  UNIQUE KEY uk_patients_user (user_id),
  CONSTRAINT fk_patients_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE appointments (
  id BIGINT NOT NULL AUTO_INCREMENT,
  patient_id BIGINT NOT NULL,
  doctor_id BIGINT NOT NULL,
  start_time DATETIME,
  end_time DATETIME,
  reason VARCHAR(255),
  status VARCHAR(50),
  PRIMARY KEY (id),
  CONSTRAINT fk_appointments_patient FOREIGN KEY (patient_id) REFERENCES patients (id),
  CONSTRAINT fk_appointments_doctor FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);

CREATE TABLE prescriptions (
  id BIGINT NOT NULL AUTO_INCREMENT,
  patient_id BIGINT NOT NULL,
  doctor_id BIGINT NOT NULL,
  instructions VARCHAR(2000),
  image_file_name VARCHAR(255),
  image_content_type VARCHAR(255),
  image_data LONGBLOB,
  created_at DATETIME,
  PRIMARY KEY (id),
  CONSTRAINT fk_prescriptions_patient FOREIGN KEY (patient_id) REFERENCES patients (id),
  CONSTRAINT fk_prescriptions_doctor FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);

CREATE TABLE medicines (
  id BIGINT NOT NULL AUTO_INCREMENT,
  prescription_id BIGINT NOT NULL,
  medicine_name VARCHAR(255),
  dosage VARCHAR(255),
  frequency VARCHAR(255),
  duration VARCHAR(255),
  time_of_day VARCHAR(255),
  meal_timing VARCHAR(255),
  PRIMARY KEY (id),
  CONSTRAINT fk_medicines_prescription FOREIGN KEY (prescription_id) REFERENCES prescriptions (id)
);

CREATE TABLE orders (
  id BIGINT NOT NULL AUTO_INCREMENT,
  patient_id BIGINT NOT NULL,
  ordered_at DATETIME,
  status VARCHAR(50),
  delivery_address VARCHAR(255),
  PRIMARY KEY (id),
  CONSTRAINT fk_orders_patient FOREIGN KEY (patient_id) REFERENCES patients (id)
);

CREATE TABLE order_items (
  id BIGINT NOT NULL AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  medicine_name VARCHAR(255),
  dosage VARCHAR(255),
  quantity INT,
  PRIMARY KEY (id),
  CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE reminders (
  id BIGINT NOT NULL AUTO_INCREMENT,
  patient_id BIGINT NOT NULL,
  type VARCHAR(50),
  message VARCHAR(255),
  due_at DATETIME,
  sent BIT(1) NOT NULL DEFAULT 0,
  created_at DATETIME,
  PRIMARY KEY (id),
  CONSTRAINT fk_reminders_patient FOREIGN KEY (patient_id) REFERENCES patients (id)
);
