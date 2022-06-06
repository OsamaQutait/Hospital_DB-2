package Hospital;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalStaff2Tests2Patient {
    private int staff_id;
    private int test_id;
    private int identity_number;
    private Date date_of_test;

    public MedicalStaff2Tests2Patient(){}

    public MedicalStaff2Tests2Patient(int staff_id, int test_id, int identity_number, Date date_of_test) {
        this.staff_id = staff_id;
        this.test_id = test_id;
        this.identity_number = identity_number;
        this.date_of_test = date_of_test;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public int getIdentity_number() {
        return identity_number;
    }

    public void setIdentity_number(int identity_number) {
        this.identity_number = identity_number;
    }

    public Date getDate_of_test() {
        return date_of_test;
    }

    public void setDate_of_test(Date date_of_test) {
        this.date_of_test = date_of_test;
    }

    public String getTestDateToString() {
        if (this.date_of_test == null){
            return "null";
        }
        String[] d = new SimpleDateFormat("dd-MM-yyyy").format(this.date_of_test).split("-");
        return "'" + d[2] + "-" + d[1] + "-" + d[0] + "'";
    }
}
