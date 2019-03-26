import models.*;
import services.AppointmentService;
import services.CabinetService;
import services.InvoiceService;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
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
}
