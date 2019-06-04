package models;

import services.AuditService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CsvFile {

    private String [] columns;
    private String fileName;
    private Path path;
    private String csvFolder = "src/main/java/CSV";

    private static AuditService auditService = null;

    public CsvFile(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFolder + '/' + file.getName()));
            this.columns = bufferedReader.readLine().split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fileName = file.getName();
        this.path = Paths.get(file.getPath());
    }

    public CsvFile(String colString, String fileName) {
        if (this.auditService == null) {
            this.auditService = new AuditService();
        }

        this.columns = colString.split(",");
        this.fileName = fileName;
        this.path = Paths.get(csvFolder + "/" + fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString(), false))) {
            writer.write(colString);
            writer.flush();
            auditService.addEntry("created file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path getPath() {
        return this.path;
    }

    public String[] getColumns() {
        return columns;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
