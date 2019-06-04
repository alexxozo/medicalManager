package repositories;

import connectivity.DatabaseConnection;
import models.Cabinet;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CabinetRepository {

    private DatabaseConnection connection =
            DatabaseConnection.getInstance();

    public void saveCabinetInfo(Cabinet cabinet) {
        try {
            PreparedStatement statement = connection.getConnection()
                    .prepareStatement("INSERT INTO cabinet VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE " +
                            "name = 'MedCare', " +
                            "location = VALUES(location), " +
                            "phone = VALUES(phone), " +
                            "cui = VALUES(cui), " +
                            "iban = VALUES(iban)");

            statement.setString(1, cabinet.getName());
            statement.setString(2, cabinet.getLocation().getCountry());
            statement.setString(3, cabinet.getPhoneNumber());
            statement.setString(4, cabinet.getCUI());
            statement.setString(5, cabinet.getIBAN());

            statement.executeUpdate();

            System.out.println("Cabinet info updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
