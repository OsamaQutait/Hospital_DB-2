package Hospital;

public class MedicalStaff2Surgeries {

	private int surgery_id;
	private int staff_id;
	
	public MedicalStaff2Surgeries(int surgery_id,int staff_id) {
        this.staff_id = staff_id;
        this.surgery_id=surgery_id;
    }

	public int getSurgery_id() {
		return surgery_id;
	}

	public void setSurgery_id(int surgery_id) {
		this.surgery_id = surgery_id;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}
	
}
