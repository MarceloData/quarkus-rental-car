package org.acme.billing;

import java.util.List;

import org.acme.billing.model.Invoice;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/invoice")
public class InvoiceResource {

    @GET
    public List<Invoice> allInvoices() {
        return Invoice.listAll();
    }
}
