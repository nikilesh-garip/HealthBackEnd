# HealthAgent Backend

## Setup

1. Update database and Twilio credentials in src/main/resources/application.yml.
2. Set Hugging Face API key in terminal before starting backend:
   ```
   $env:HF_API_KEY="your_hf_key"
   ```
3. Run the API:
   ```
   mvn spring-boot:run
   ```

## Endpoints

- POST /auth/register
- POST /auth/login
- POST /appointments
- GET /appointments/patient/{patientId}
- GET /appointments/doctor/{doctorId}
- PUT /appointments/{id}
- DELETE /appointments/{id}
- POST /prescriptions
- GET /prescriptions/patient/{patientId}
- POST /orders
- GET /orders/patient/{patientId}
- GET /orders/{orderId}
