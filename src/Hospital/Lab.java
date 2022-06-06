package Hospital;

public class Lab {
    private int lab_id;
    private String lab_name;
    private String lab_description;

    public Lab(){}
    public Lab(int lab_id, String lab_name, String lab_description) {
        this.lab_id = lab_id;
        this.lab_name = lab_name;
        this.lab_description = lab_description;
    }

    public int getLab_id() {
        return lab_id;
    }

    public void setLab_id(int lab_id) {
        this.lab_id = lab_id;
    }

    public String getLab_name() {
        return lab_name;
    }

    public void setLab_name(String lab_name) {
        this.lab_name = lab_name;
    }

    public String getLab_description() {
        return lab_description;
    }

    public void setLab_description(String lab_description) {
        this.lab_description = lab_description;
    }
}
