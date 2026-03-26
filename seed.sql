SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE order_medicines;
TRUNCATE TABLE reminders;
TRUNCATE TABLE order_items;
TRUNCATE TABLE orders;
TRUNCATE TABLE medicines;
TRUNCATE TABLE prescriptions;
TRUNCATE TABLE appointments;
TRUNCATE TABLE patients;
TRUNCATE TABLE doctors;
TRUNCATE TABLE users;
SET FOREIGN_KEY_CHECKS=1;

INSERT INTO users (id, email, password, role, phone, full_name) VALUES
(1, 'admin@healthagent.com', 'Welcome@123', 'ADMIN', '+1555010000', 'Admin User'),
(2, 'dr.aliya@healthagent.com', 'Welcome@123', 'DOCTOR', '+1555010001', 'Dr. Aliya Raman'),
(3, 'dr.marcus@healthagent.com', 'Welcome@123', 'DOCTOR', '+1555010002', 'Dr. Marcus Chen'),
(4, 'dr.sofia@healthagent.com', 'Welcome@123', 'DOCTOR', '+1555010003', 'Dr. Sofia Patel'),
(5, 'ava.patient@healthagent.com', 'Welcome@123', 'PATIENT', '+916300964932', 'Ava Thompson'),
(6, 'liam.patient@healthagent.com', 'Welcome@123', 'PATIENT', '+918096267553', 'Liam Johnson'),
(7, 'olivia.patient@healthagent.com', 'Welcome@123', 'PATIENT', '+917569152179', 'Olivia Stone'),
(8, 'noah.patient@healthagent.com', 'Welcome@123', 'PATIENT', '+1555010104', 'Noah Kim'),
(9, 'emma.patient@healthagent.com', 'Welcome@123', 'PATIENT', '+1555010105', 'Emma Garcia'),
(10, 'lucas.patient@healthagent.com', 'Welcome@123', 'PATIENT', '+1555010106', 'Lucas Rivera'),
(11, 'mia.patient@healthagent.com', 'Welcome@123', 'PATIENT', '+1555010107', 'Mia Brooks'),
(12, 'ethan.patient@healthagent.com', 'Welcome@123', 'PATIENT', '+1555010108', 'Ethan Ford');

INSERT INTO doctors (id, user_id, specialty, license_number) VALUES
(1, 2, 'Cardiology', 'LIC-1001'),
(2, 3, 'Endocrinology', 'LIC-1002'),
(3, 4, 'General Medicine', 'LIC-1003');

INSERT INTO patients (id, user_id, address) VALUES
(1, 5, '12 River Lane'),
(2, 6, '33 Pine Street'),
(3, 7, '89 Orchard Blvd'),
(4, 8, '15 Oak Way'),
(5, 9, '7 Maple Drive'),
(6, 10, '62 Cedar Ave'),
(7, 11, '40 Juniper Road'),
(8, 12, '19 Birch Court');

INSERT INTO appointments (id, patient_id, doctor_id, start_time, end_time, reason, status) VALUES
(1, 1, 1, DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 1 DAY), INTERVAL 30 MINUTE), 'Chest discomfort', 'CONFIRMED'),
(2, 2, 2, DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 2 DAY), INTERVAL 30 MINUTE), 'Diabetes follow-up', 'CONFIRMED'),
(3, 3, 3, DATE_ADD(NOW(), INTERVAL 3 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 3 DAY), INTERVAL 30 MINUTE), 'Annual checkup', 'CONFIRMED'),
(4, 4, 1, DATE_ADD(NOW(), INTERVAL 4 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 4 DAY), INTERVAL 30 MINUTE), 'Blood pressure review', 'REQUESTED'),
(5, 5, 3, DATE_ADD(NOW(), INTERVAL 5 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 5 DAY), INTERVAL 30 MINUTE), 'Sleep concerns', 'CONFIRMED'),
(6, 6, 2, DATE_ADD(NOW(), INTERVAL 6 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 6 DAY), INTERVAL 30 MINUTE), 'Thyroid consult', 'CONFIRMED'),
(7, 7, 3, DATE_ADD(NOW(), INTERVAL 7 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 7 DAY), INTERVAL 30 MINUTE), 'Nutrition coaching', 'CONFIRMED'),
(8, 8, 1, DATE_ADD(NOW(), INTERVAL 8 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 8 DAY), INTERVAL 30 MINUTE), 'Cardio rehab', 'REQUESTED');

INSERT INTO prescriptions (id, patient_id, doctor_id, instructions, created_at) VALUES
(1, 1, 1, 'Monitor blood pressure daily.', NOW()),
(2, 2, 2, 'Check glucose before meals.', NOW()),
(3, 3, 3, 'Maintain hydration and rest.', NOW()),
(4, 4, 1, 'Limit sodium intake.', NOW()),
(5, 5, 3, 'Establish sleep routine.', NOW()),
(6, 6, 2, 'Track energy levels.', NOW()),
(7, 5, 1, 'Take medicines at scheduled times.', NOW()),
(8, 7, 2, 'Follow daily medicine schedule.', NOW()),
(9, 8, 1, 'Follow daily medicine schedule.', NOW());

INSERT INTO medicines (id, prescription_id, medicine_name, dosage, frequency, duration, time_of_day, meal_timing) VALUES
(1, 1, 'Atorvastatin', '10mg', 'Once daily', '30 days', '08:15', 'After meals'),
(2, 1, 'Amlodipine', '5mg', 'Once daily', '30 days', '20:15', 'After meals'),
(3, 2, 'Metformin', '500mg', 'Twice daily', '60 days', '08:00,20:00', 'After meals'),
(4, 2, 'Insulin Glargine', '10 units', 'Nightly', '30 days', '21:00', 'Before meals'),
(5, 3, 'Vitamin D', '2000 IU', 'Once daily', '90 days', '22:44', 'After meals'),
(6, 4, 'Lisinopril', '10mg', 'Once daily', '60 days', '07:30', 'After meals'),
(7, 5, 'Melatonin', '3mg', 'Nightly', '14 days', '22:00', 'After meals'),
(8, 6, 'Levothyroxine', '50mcg', 'Once daily', '30 days', '07:00', 'Before meals'),
(9, 7, 'Paracetamol', '500mg', 'Twice daily', '5 days', '08:00,20:00', 'After meals'),
(10, 7, 'Vitamin C', '500mg', 'Once daily', '10 days', '09:00', 'After meals'),
(11, 8, 'Cetirizine', '10mg', 'Once daily', '10 days', '19:45', 'After meals'),
(12, 9, 'Amoxicillin', '500mg', 'Twice daily', '7 days', '09:30,21:30', 'After meals');

INSERT INTO orders (id, patient_id, ordered_at, status, delivery_address) VALUES
(1, 1, NOW(), 'PROCESSING', '12 River Lane'),
(2, 2, NOW(), 'PLACED', '33 Pine Street'),
(3, 4, NOW(), 'PROCESSING', '15 Oak Way'),
(4, 6, NOW(), 'PLACED', '62 Cedar Ave');

INSERT INTO order_items (order_id, medicine_name, dosage, quantity) VALUES
(1, 'Atorvastatin', '10mg', 1),
(1, 'Amlodipine', '5mg', 1),
(2, 'Metformin', '500mg', 2),
(3, 'Lisinopril', '10mg', 1),
(4, 'Levothyroxine', '50mcg', 1);

INSERT INTO reminders (id, patient_id, type, message, due_at, sent, created_at) VALUES
(1, 1, 'APPOINTMENT_REMINDER', 'Appointment tomorrow at 10:00 AM', DATE_ADD(NOW(), INTERVAL 1 DAY), 1, NOW()),
(2, 2, 'MEDICINE_REMINDER', 'Take Metformin after breakfast', NOW(), 1, NOW()),
(3, 3, 'ORDER_CONFIRMATION', 'Order confirmed: RX-102', NOW(), 1, NOW()),
(4, 5, 'REFILL_ALERT', 'Refill your Melatonin prescription', DATE_ADD(NOW(), INTERVAL 5 DAY), 1, NOW()),
(5, 7, 'APPOINTMENT_CONFIRMATION', 'Appointment confirmed for next week', DATE_ADD(NOW(), INTERVAL 7 DAY), 1, NOW());
