package services;

import models.Agenda;
import models.Appointment;
import models.Cabinet;
import models.Doctor;

import java.util.List;

public class CabinetService {

    public Doctor addDoctor(Cabinet cabinet, Doctor doctor) {
        List<Doctor> doctorsList = cabinet.getDoctorsList();
        doctorsList.add(doctor);
        cabinet.setDoctorsList(doctorsList);
        return doctor;
    }

    public void printDoctors(Cabinet cabinet) {
        List<Doctor> doctorsList = cabinet.getDoctorsList();
        for (Doctor doctor : doctorsList) {
            System.out.println(doctorsList.toString());
        }
    }

    public void printAgenda(Cabinet cabinet) {
        System.out.println("--AGENDA--");
        Agenda agenda = cabinet.getAgenda();
        for (Appointment appointment : agenda.getAppointments()) {
            System.out.println(appointment.toString());
        }
        System.out.println("--AGENDA END--");
    }

    public void printInvoices(Cabinet cabinet) {
        System.out.println("--INVOICES--");
        Agenda agenda = cabinet.getAgenda();
        for (Appointment appointment : agenda.getAppointments()) {
            if (appointment.getInvoice() != null)
                System.out.println(appointment.getInvoice().toString());
        }
        System.out.println("--INVOICES END--");
    }
}