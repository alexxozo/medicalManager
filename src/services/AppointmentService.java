package services;

import models.*;
import java.util.Date;

public class AppointmentService {

    public Appointment addAppointmentToAgenda(Doctor doctor, Pacient pacient, Date date, Cabinet cabinet) {
        // add to agenda and return
        Appointment appointment = new Appointment(doctor, pacient, date, "");
        Agenda agenda = cabinet.getAgenda();
        agenda.addAppointment(appointment);
        cabinet.setAgenda(agenda);
        return appointment;
    }

    public Boolean removeAppointment(Appointment appointment) {
        return true;
    }

    public Boolean addExtraInfo(Appointment appointment) {
        return true;
    }

    public Pacient addPrescription(Appointment appointment, Prescription prescription) {
        Pacient pacient = appointment.getPacient();
        pacient.setPrescription(prescription);
        return pacient;
    }
}
