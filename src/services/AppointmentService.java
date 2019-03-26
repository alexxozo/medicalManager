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

    public Appointment removeAppointment(Appointment appointment, Cabinet cabinet) {
        Agenda agenda = cabinet.getAgenda();
        agenda.removeAppointment(appointment);
        cabinet.setAgenda(agenda);
        return appointment;
    }

    public Boolean addExtraInfo(String extraInfo, Appointment appointment, Cabinet cabinet) {
        Appointment updatedAppointment = new Appointment(appointment);
        updatedAppointment.setExtraInfo(extraInfo);

        Agenda agenda = cabinet.getAgenda();
        agenda.removeAppointment(appointment);
        agenda.addAppointment(updatedAppointment);

        cabinet.setAgenda(agenda);
        return true;
    }

    public Pacient addPrescription(Appointment appointment, Prescription prescription) {
        Pacient pacient = appointment.getPacient();
        pacient.setPrescription(prescription);
        return pacient;
    }
}
