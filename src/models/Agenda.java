package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agenda {
    private List<Appointment> appointments;

    public Agenda() {
        this.appointments = new ArrayList<Appointment>();
    }

    public Appointment addAppointment(Appointment appointment) {
        appointments.add(appointment);
        Collections.sort(appointments);
        return appointment;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
