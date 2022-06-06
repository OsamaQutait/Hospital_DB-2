package Hospital;

import java.text.SimpleDateFormat;

public class Surgeries {

    private int surgery_id;
    private String surgery_name;
    private float surgery_price;

    public Surgeries(){}

    public Surgeries(int surgery_id, String surgery_name, float surgery_price) {
        this.surgery_price = surgery_price;
        this.surgery_name = surgery_name;
        this.surgery_id = surgery_id;
    }

    public int getSurgery_id() {
        return surgery_id;
    }

    public String getSurgery_name() {
        return surgery_name;
    }

    public float getSurgery_price() {
        return surgery_price;
    }

    public void setSurgery_id(int surgery_id) {
        this.surgery_id = surgery_id;
    }

    public void setSurgery_name(String surgery_name) {
        this.surgery_name = surgery_name;
    }

    public void setSurgery_price(float surgery_price) {
        this.surgery_price = surgery_price;
    }
}
