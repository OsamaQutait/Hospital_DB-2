package Hospital;

import java.util.Date;

public class Payment {
    private int payment_id;
    private Date issued_date;
    private float invoice;
    private float coverage;
    private int identity_number;

    public Payment(){}

    public Payment(int payment_id, Date issued_date, float invoice, float coverage, int identity_number) {
        this.payment_id = payment_id;
        this.issued_date = issued_date;
        this.invoice = invoice;
        this.coverage = coverage;
        this.identity_number = identity_number;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public Date getIssued_date() {
        return issued_date;
    }

    public void setIssued_date(Date issued_date) {
        this.issued_date = issued_date;
    }

    public float getInvoice() {
        return invoice;
    }

    public void setInvoice(float invoice) {
        this.invoice = invoice;
    }

    public float getCoverage() {
        return coverage;
    }

    public void setCoverage(float coverage) {
        this.coverage = coverage;
    }

    public int getIdentity_number() {
        return identity_number;
    }

    public void setIdentity_number(int identity_number) {
        this.identity_number = identity_number;
    }
}
