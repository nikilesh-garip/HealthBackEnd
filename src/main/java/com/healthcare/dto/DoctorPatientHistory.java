package com.healthcare.dto;

import java.util.List;

public class DoctorPatientHistory {
    private PatientSummary patient;
    private List<AppointmentSummary> appointments;
    private List<PrescriptionSummary> prescriptions;

    public DoctorPatientHistory(PatientSummary patient,
                                List<AppointmentSummary> appointments,
                                List<PrescriptionSummary> prescriptions) {
        this.patient = patient;
        this.appointments = appointments;
        this.prescriptions = prescriptions;
    }

    public PatientSummary getPatient() {
        return patient;
    }

    public List<AppointmentSummary> getAppointments() {
        return appointments;
    }

    public List<PrescriptionSummary> getPrescriptions() {
        return prescriptions;
    }
}
