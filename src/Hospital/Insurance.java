package Hospital;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Insurance {
    private int insuranceID;
    private int paymentCoverage;
    private Date expiryDate;
    private int identityNumber;

    public Insurance(){

    }

    public Insurance(int insuranceID, int paymentCoverage, Date expiryDate, int identityNumber) {
        this.insuranceID = insuranceID;
        this.paymentCoverage = paymentCoverage;
        this.expiryDate = expiryDate;
        this.identityNumber = identityNumber;
    }

    public int getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(int insuranceID) {
        this.insuranceID = insuranceID;
    }

    public int getPaymentCoverage() {
        return paymentCoverage;
    }

    public void setPaymentCoverage(int paymentCoverage) {
        this.paymentCoverage = paymentCoverage;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(int identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getExpiryDateToString() {
        if (this.expiryDate == null){
            return "null";
        }
        String[] d = new SimpleDateFormat("dd-MM-yyyy").format(this.expiryDate).split("-");
        return "'" + d[2] + "-" + d[1] + "-" + d[0] + "'";
    }
}
