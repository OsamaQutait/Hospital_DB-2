package Hospital;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalStaff2Surgeries2Patient {
    private int staff_id;
    private int surgery_id;
    private int identity_number;
    private Date date_of_surgery;

    public MedicalStaff2Surgeries2Patient(){}

    public MedicalStaff2Surgeries2Patient(int staff_id, int surgery_id, int identity_number, Date date_of_surgery) {
        this.staff_id = staff_id;
        this.surgery_id = surgery_id;
        this.identity_number = identity_number;
        this.date_of_surgery = date_of_surgery;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getSurgery_id() {
        return surgery_id;
    }

    public void setSurgery_id(int surgery_id) {
        this.surgery_id = surgery_id;
    }

    public int getIdentity_number() {
        return identity_number;
    }

    public void setIdentity_number(int identity_number) {
        this.identity_number = identity_number;
    }

    public Date getDate_of_surgery() {
        return date_of_surgery;
    }

    public void setDate_of_surgery(Date date_of_surgery) {
        this.date_of_surgery = date_of_surgery;
    }

    public String getSurgeryDateToString() {
        if (this.date_of_surgery == null){
            return "null";
        }
        String[] d = new SimpleDateFormat("dd-MM-yyyy").format(this.date_of_surgery).split("-");
        return "'" + d[2] + "-" + d[1] + "-" + d[0] + "'";
    }
}
