package models;

import java.util.ArrayList;
import java.util.List;

public class Cabinet {
    private String name;
    private Location location;
    private String phoneNumber;
    private String CUI;
    private String IBAN;
    private List<Doctor> doctorsList;
    private List<Invoice> invoicesList;
    private Agenda agenda;

    public Cabinet(String name, Location location, String phoneNumber, String CUI, String IBAN) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.CUI = CUI;
        this.IBAN = IBAN;
        this.agenda = new Agenda();
        this.invoicesList = new ArrayList<Invoice>();
        this.doctorsList = new ArrayList<Doctor>();
    }

    public Boolean removeDoctor(Doctor doc) {
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCUI() {
        return CUI;
    }

    public void setCUI(String CUI) {
        this.CUI = CUI;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public List<Doctor> getDoctorsList() {
        return doctorsList;
    }

    public void setDoctorsList(List<Doctor> doctorsList) {
        this.doctorsList = doctorsList;
    }

    public List<Invoice> getInvoicesList() {
        return invoicesList;
    }

    public void setInvoicesList(List<Invoice> invoicesList) {
        this.invoicesList = invoicesList;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    @Override
    public String toString() {
        return "Cabinet{" +
                "name='" + name + '\'' +
                ", location=" + location +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", CUI='" + CUI + '\'' +
                ", IBAN='" + IBAN + '\'' +
                '}';
    }
}
