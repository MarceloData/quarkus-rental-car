package org.acme.billing.data;

import org.acme.billing.model.Invoice;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class InvoiceConfirmation {
    public Invoice invoice;
    public boolean paid;
}
