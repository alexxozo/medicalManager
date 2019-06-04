package services;

import models.*;
import repositories.CabinetRepository;
import repositories.DoctorRepository;

import java.util.List;

public class CabinetService {

    private static CsvFile csvFile = null;
    private static CsvEditor csvEditor = null;

    private static CabinetRepository cabinetRepository = new CabinetRepository();
    private static DoctorRepository doctorRepository = new DoctorRepository();

    public CabinetService() {
        if (csvFile == null) {
            csvFile = new CsvFile("firstName,lastName,age,sex,type,email,password,phone", "doctor-info.csv");
            csvEditor = new CsvEditor();
        }
    }

    public void saveCabinetInfo(Cabinet cabinet) {
        // TODO: add cabinet info to database
        cabinetRepository.saveCabinetInfo(cabinet);
    }

    public Doctor addDoctor(Cabinet cabinet, Doctor doctor) {
        List<Doctor> doctorsList = cabinet.getDoctorsList();
        doctorsList.add(doctor);
        cabinet.setDoctorsList(doctorsList);
        // TODO: add doctor to csv
        csvEditor.writeLine(csvFile, doctor.toCSV());
        // TODO: add doctor to database
        doctorRepository.saveDoctor(doctor);
        return doctor;
    }

    public Doctor removeDoctor(Cabinet cabinet, Doctor doctor) {
        List<Doctor> doctorsList = cabinet.getDoctorsList();
        doctorsList.remove(doctor);
        cabinet.setDoctorsList(doctorsList);
        // TODO: remove doctor from csv
        csvEditor.removeLineThatContains(csvFile, doctor.getLastName());
        // TODO: remove doctor from database
        doctorRepository.deleteDoctor(doctor);
        return doctor;
    }

    public void printDoctors(Cabinet cabinet) {
        List<Doctor> doctorsList = doctorRepository.getDoctors();
        cabinet.setDoctorsList(doctorsList);
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
