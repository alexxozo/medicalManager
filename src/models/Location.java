package models;

public class Location {
    private String streetName;
    private String country;
    private String city;
    private int number;

    public Location(String streetName, String country, String city, int number) {
        this.streetName = streetName;
        this.country = country;
        this.city = city;
        this.number = number;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Location{" +
                "streetName='" + streetName + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", number=" + number +
                '}';
    }
}
