package models;

import java.util.Date;

public class Appointment implements Comparable<Appointment> {
    private Doctor doctor;
    private Pacient pacient;
    private Date date;
    private String extraInfo;
    private Invoice invoice;

    public Appointment(Doctor doctor, Pacient pacient, Date date, String extraInfo) {
        this.doctor = doctor;
        this.pacient = pacient;
        this.date = date;
        this.extraInfo = extraInfo;
        this.invoice = null;
    }

    public Appointment(Appointment appointment) {
        this.doctor = appointment.doctor;
        this.pacient = appointment.pacient;
        this.date = appointment.date;
        this.extraInfo = appointment.extraInfo;
        this.invoice = appointment.invoice;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "doctor=" + doctor +
                ", pacient=" + pacient +
                ", date=" + date +
                ", extraInfo='" + extraInfo + '\'' +
                '}';
    }

    @Override
    public int compareTo(Appointment o) {
        return this.getDate().compareTo(o.getDate());
    }
}
