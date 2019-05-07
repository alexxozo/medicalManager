package services;

import models.*;
import java.util.Date;

public class AppointmentService {

    private static CsvFile csvFile = null;
    private static CsvEditor csvEditor = null;

    public AppointmentService() {
        if (csvFile == null) {
            csvFile = new CsvFile("doctor,pacient,date", "appointment-info.csv");
            csvEditor = new CsvEditor();
        }
    }

    public Appointment addAppointmentToAgenda(Doctor doctor, Pacient pacient, Date date, Cabinet cabinet) {
        // add to agenda and return
        Appointment appointment = new Appointment(doctor, pacient, date, "");
        Agenda agenda = cabinet.getAgenda();
        agenda.addAppointment(appointment);
        cabinet.setAgenda(agenda);
        // TODO: add appointment to csv
        csvEditor.writeLine(csvFile, appointment.toCSV());
        return appointment;
    }

    public Appointment removeAppointment(Appointment appointment, Cabinet cabinet) {
        Agenda agenda = cabinet.getAgenda();
        agenda.removeAppointment(appointment);
        cabinet.setAgenda(agenda);
        // TODO: remove appointment from csv
        csvEditor.removeLineThatContains(csvFile, appointment.toCSV());
        return appointment;
    }

    public Boolean addExtraInfo(String extraInfo, Appointment appointment, Cabinet cabinet) {
        // TODO: replace info in csv
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
