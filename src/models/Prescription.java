package models;

import java.util.List;

public class Prescription {
    private List<String> medications;

    public Prescription(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "medications=" + medications +
                '}';
    }
}
