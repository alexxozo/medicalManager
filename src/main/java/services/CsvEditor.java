package services;

import models.CsvFile;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvEditor {

    private static AuditService auditService = null;

    public CsvEditor() {
        if (auditService == null) {
            auditService = new AuditService();
        }
    }

    public void writeLine(CsvFile file, String line) {
        String [] data = line.split(",");
        // Check if the columns size is the same as the input line
        if (file.getColumns().length == data.length) {
            Path path = file.getPath();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString(), true))) {
                writer.newLine();
                writer.write(line);
                writer.flush();
                auditService.addEntry("added line in file: " + file.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeData(CsvFile file, String lines) {
        String [] data = lines.split("\n");
        Path path = file.getPath();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString(), true));
            for (String line : data) {
                if (file.getColumns().length == line.split(",").length) {
                    writer.newLine();
                    writer.write(line);
                    writer.flush();
                    auditService.addEntry("added line in file: " + file.getFileName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readDataToString(CsvFile file) {
        Path path = file.getPath();
        String result = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toString()))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
            result = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> readDataToList(CsvFile file) {
        List<String> list = new ArrayList<String>();
        Path path = file.getPath();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toString()))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Boolean removeLineThatContains(CsvFile file, String containsText) {
        List<String> list = readDataToList(file);
        Boolean found = false;

        String header = list.get(0);
        list.remove(0);

        File oldCsvFile = new File(file.getPath().toString());
        oldCsvFile.delete();

        CsvFile newCsv = new CsvFile(header, file.getFileName());

        for (String element : list) {
            if (element.indexOf(containsText) == -1) {
                writeLine(newCsv, element);
            } else {
                found = true;
            }
        }
        return found;
    }

}
