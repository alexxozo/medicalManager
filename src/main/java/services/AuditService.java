package services;

import models.CsvFile;

import java.io.*;
import java.nio.file.Path;
import java.sql.Timestamp;

public class AuditService {

    String auditFile = "src/main/java/CSV/audit.csv";
    BufferedWriter writer;

    public AuditService() {
        try {
            File tempFile = new File(auditFile);
            if (!tempFile.exists()) {
                writer = new BufferedWriter(new FileWriter(auditFile, false));
                writer.write("action,timestamp");
            } else {
                writer = new BufferedWriter(new FileWriter(auditFile, true));
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addEntry(String actionType) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            writer.newLine();
            writer.write(actionType + ',' + timestamp);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetAudit() {
        try {
            writer = new BufferedWriter(new FileWriter(auditFile, false));
            writer.write("action,timestamp");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
