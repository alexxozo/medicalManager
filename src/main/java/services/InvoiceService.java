package services;

import models.Appointment;
import models.CsvFile;
import models.Invoice;

public class InvoiceService {

    private static CsvFile csvFile = null;
    private static CsvEditor csvEditor = null;

    public InvoiceService() {
        if (csvFile == null) {
            csvFile = new CsvFile("invoice,appointment", "invoice-info.csv");
            csvEditor = new CsvEditor();
        }
    }

    public Invoice addInvoice(Invoice invoice, Appointment appointment) {
        appointment.setInvoice(invoice);
        // TODO: add invoice to csv
        csvEditor.writeLine(csvFile, invoice.toString() + ',' + appointment.toString());
        return invoice;
    }
}
