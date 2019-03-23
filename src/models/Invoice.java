package models;

public class Invoice {
    private Pacient pacient;
    private Doctor doctor;
    private String procedure;
    private double payment;

    public Invoice(Pacient pacient, Doctor doctor, String procedure, double payment) {
        this.pacient = pacient;
        this.doctor = doctor;
        this.procedure = procedure;
        this.payment = payment;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "pacient=" + pacient +
                ", doctor=" + doctor +
                ", procedure='" + procedure + '\'' +
                ", payment=" + payment +
                '}';
    }
}
