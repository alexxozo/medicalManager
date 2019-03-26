package services;

import models.Appointment;
import models.Invoice;

public class InvoiceService {

    public Invoice addInvoice(Invoice invoice, Appointment appointment) {
        appointment.setInvoice(invoice);
        return invoice;
    }
}
