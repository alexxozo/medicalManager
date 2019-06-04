package repositories;

import connectivity.DatabaseConnection;
import models.Doctor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository {

    private DatabaseConnection connection =
            DatabaseConnection.getInstance();

    public void saveDoctor(Doctor doctor) {
        try {
            PreparedStatement statement = connection.getConnection()
                    .prepareStatement("INSERT INTO doctor VALUES (?,?,?,?,?,?,?,?)");

            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            statement.setInt(3, doctor.getAge());
            statement.setString(4, doctor.getSex());
            statement.setString(5, doctor.getType());
            statement.setString(6, doctor.getEmail());
            statement.setString(7, doctor.getPassword());
            statement.setString(8, doctor.getPhoneNumber());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Doctor> getDoctors() {
        List<Doctor> doctors = new ArrayList<Doctor>();
        String sql = "SELECT * FROM doctor";

        try {
            PreparedStatement preparedStatement = connection.getConnection()
                    .prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.setFirstName(resultSet.getString("firstname"));
                doctor.setLastName(resultSet.getString("lastname"));
                doctor.setAge(resultSet.getInt("age"));
                doctor.setSex(resultSet.getString("sex"));
                doctor.setType(resultSet.getString("type"));
                doctor.setEmail(resultSet.getString("email"));
                doctor.setPassword(resultSet.getString("pass"));
                doctor.setPhoneNumber(resultSet.getString("phone"));

                doctors.add(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public void deleteDoctor(Doctor doctor) {
        try {
            PreparedStatement statement = connection.getConnection()
                    .prepareStatement("DELETE FROM doctor WHERE email = ?");

            statement.setString(1, doctor.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
