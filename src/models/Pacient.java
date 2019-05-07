package models;

public class Pacient extends Human {
    private String phoneNumber;
    private Prescription prescription;

    public Pacient(String firstName, String lastName, int age, String sex, String phoneNumber) {
        super(firstName, lastName, age, sex);
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "phoneNumber='" + phoneNumber + '\'' +
                "; prescription=" + prescription +
                "; firstName='" + firstName + '\'' +
                "; lastName='" + lastName + '\'' +
                "; age=" + age +
                "; sex='" + sex + '\'' +
                '}';
    }
}
