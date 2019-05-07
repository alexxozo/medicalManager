package models;

import services.CsvEditor;

import java.io.File;
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

    private static CsvFile csvFile = null;
    private static CsvEditor csvEditor = null;

    public Cabinet() {
        File file = new File("src/CSV/cabinet-info.csv");
        csvFile = new CsvFile(file);
        csvEditor = new CsvEditor();
        if (file.exists()) {
            List<String> info = csvEditor.readDataToList(csvFile);
            String [] data = info.get(1).split(",");
            // Instantiate from file
            this.name = data[0];
            this.phoneNumber = data[2];
            this.CUI = data[3];
            this.IBAN = data[4];
            this.agenda = new Agenda();
            this.invoicesList = new ArrayList<Invoice>();
            this.doctorsList = new ArrayList<Doctor>();
        }
    }

    public Cabinet(String name, Location location, String phoneNumber, String CUI, String IBAN) {
        if (csvFile == null) {
            csvFile = new CsvFile("name,location,phone,cui,iban", "cabinet-info.csv");
            csvEditor = new CsvEditor();
        }
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.CUI = CUI;
        this.IBAN = IBAN;
        this.agenda = new Agenda();
        this.invoicesList = new ArrayList<Invoice>();
        this.doctorsList = new ArrayList<Doctor>();

        csvEditor.writeLine(csvFile, this.name + ',' + this.location.toString() + ',' + this.phoneNumber + ',' + this.CUI + ',' + this.IBAN);
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
