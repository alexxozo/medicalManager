import connectivity.DatabaseConnection;
import models.*;
import services.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        // CREATE, READ, DELETE, UPDATE example

        // Cabinet Setup
        Location location = new Location("Colentina", "Romania", "Bucharest", 4);
        Cabinet cabinet = new Cabinet("MedCare", location, "sdfsdf", "1234", "BRD234");

        CabinetService cabinetService = new CabinetService();
        cabinetService.saveCabinetInfo(cabinet);

        // Doctor Setup
        Doctor doctor = new Doctor("Alex", "Simion", 20, "M", "Pediatru", "alex@yahoo.com", "123", "0721342423");
        cabinetService.addDoctor(cabinet, doctor);
//        cabinetService.removeDoctor(cabinet, doctor);
        cabinetService.printDoctors(cabinet);
    }

    public void basicSetupWithoutCSV() {
        // Services
        AppointmentService appointmentService = new AppointmentService();
        InvoiceService invoiceService = new InvoiceService();
        CabinetService cabinetService = new CabinetService();

        // Cabinet Setup
        Location location = new Location("Colentina", "Romania", "Bucharest", 4);
        Cabinet cabinet = new Cabinet("MedCare", location, "0721345345", "1234", "BRD234");
        // Debug
        System.out.println(cabinet.toString());

        // Doctor Setup
        Doctor doctor = new Doctor("Alex", "Simion", 20, "M", "Pediatru", "alex@yahoo.com", "123", "0721342423");
        // Debug
        System.out.println(doctor.toString());

        // Pacient Setup
        Pacient pacient = new Pacient("Gabriel", "Simion", 20, "M", "0734213343");
        // Debug
        System.out.println(pacient.toString());

        // Add the doctors to the cabinet
        cabinetService.addDoctor(cabinet, doctor);
        // Debug
        cabinetService.printDoctors(cabinet);

        // Make an appointment and it gets sorted in the list
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 12, 3);
        Date d = calendar.getTime();
        Appointment appointment = appointmentService.addAppointmentToAgenda(doctor, pacient, new Date(), cabinet);
        Appointment appointment2 = appointmentService.addAppointmentToAgenda(doctor, pacient, d, cabinet);
        // Debug
        System.out.println(appointment.toString());
        cabinetService.printAgenda(cabinet);

        // Remove an appointment
        appointmentService.removeAppointment(appointment2, cabinet);
        cabinetService.printAgenda(cabinet);

        // Give a prescription to a pacient
        ArrayList<String> medications = new ArrayList<>();
        medications.add("Algocalmin");
        medications.add("Nurofen");

        Prescription prescription = new Prescription(medications);
        appointmentService.addPrescription(appointment, prescription);
        // Debug
        System.out.println(appointment.toString());

        // End an appointment, create an invoice
        Invoice invoice = new Invoice(appointment.getPacient(), appointment.getDoctor(), "lala", 123.0);
        invoiceService.addInvoice(invoice, appointment);
        cabinetService.printInvoices(cabinet);

        // Add extra info
        appointmentService.addExtraInfo("test", appointment, cabinet);
        // Debug
        cabinetService.printAgenda(cabinet);
    }

    public void basicSetupWithCSV() {
        // Setup Services
        AppointmentService appointmentService = new AppointmentService();
        InvoiceService invoiceService = new InvoiceService();
        CabinetService cabinetService = new CabinetService();

        // Reset Audit
        AuditService auditService = new AuditService();
        auditService.resetAudit();

        // Setup Sample CSV data
        CsvFile cabinetInfo = new CsvFile("name,location,phone,cui,iban", "cabinet-info.csv");

        CsvEditor editor = new CsvEditor();
        editor.writeLine(cabinetInfo, "MedCare,Location{streetName='Colentina'; country='Romania'; city='Bucharest'; number=4},0721345345,1234,BRD234");

        // Create Cabinet with predefined data
        Cabinet cabinet = new Cabinet();

        // Create Cabinet Location
        Location location = new Location("Colentina", "Romania", "Bucharest", 4);
        cabinet.setLocation(location);

        // Create 2 doctors
        Doctor doctor1 = new Doctor("Alex", "Simion", 20, "M", "Pediatru", "alex@yahoo.com", "123", "0721342423");
        Doctor doctor2 = new Doctor("NotRemoved", "Sin", 20, "M", "Pediatru", "alex@yahoo.com", "123", "0721342423");

        // Add the doctors to the cabinet
        cabinetService.addDoctor(cabinet, doctor1);
        cabinetService.addDoctor(cabinet, doctor2);

        // Remove one
        cabinetService.removeDoctor(cabinet, doctor1);

        // Create a pacient
        Pacient pacient = new Pacient("Gabriel", "Simion", 20, "M", "0734213343");

        // Create 2 appointments and get them sorted in the list
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 12, 3);
        Date d = calendar.getTime();
        Appointment appointment1 = appointmentService.addAppointmentToAgenda(doctor2, pacient, new Date(), cabinet);
        Appointment appointment2 = appointmentService.addAppointmentToAgenda(doctor2, pacient, d, cabinet);

        // Remove the second one
        appointmentService.removeAppointment(appointment2, cabinet);

        // Give a prescription to a pacient
        ArrayList<String> medications = new ArrayList<>();
        medications.add("Algocalmin");
        medications.add("Nurofen");

        Prescription prescription = new Prescription(medications);
        appointmentService.addPrescription(appointment1, prescription);

        // End an appointment, create an invoice
        Invoice invoice = new Invoice(appointment1.getPacient(), appointment1.getDoctor(), "lala", 123.0);
        invoiceService.addInvoice(invoice, appointment1);
    }
}
