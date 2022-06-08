package Hospital;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Payment {
    private int payment_id;
    private Date issued_date;
    private float invoice;
    private float total_bill;
    private float coverage;
    private int identity_number;
    private float lengthOfStay;
    private float roomPrice;

    public Payment(){}

    public Payment(Date issued_date, float invoice, float total_bill, float coverage, int identity_number) {
        this.issued_date = issued_date;
        this.invoice = invoice;
        this.coverage = coverage;
        this.identity_number = identity_number;
        this.total_bill = total_bill;
    }

    public Payment(int payment_id, Date issued_date, float invoice, float total_bill, float coverage, int identity_number) {
        this.payment_id = payment_id;
        this.issued_date = issued_date;
        this.invoice = invoice;
        this.coverage = coverage;
        this.identity_number = identity_number;
        this.total_bill = total_bill;
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

    public String getIssued_dateToString() {
        if (issued_date == null) {
            return "null";
        }
        return "'" + new SimpleDateFormat("yyyy-MM-dd").format(this.issued_date) + "'";
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

    public float getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(float total_bill) {
        this.total_bill = total_bill;
    }

    public float getLengthOfStay() {
        return lengthOfStay;
    }

    public void setLengthOfStay(float lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }

    public float getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(float roomPrice) {
        this.roomPrice = roomPrice;
    }
}
