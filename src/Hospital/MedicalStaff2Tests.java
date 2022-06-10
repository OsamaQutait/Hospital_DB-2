package Hospital;

public class MedicalStaff2Tests {
	private int test_id;
	private int staff_id;
	
	public MedicalStaff2Tests(int test_id,int staff_id) {
        this.staff_id = staff_id;
        this.test_id=test_id;
    }

	public int getTest_id() {
		return test_id;
	}

	public void setTest_id(int test_id) {
		this.test_id = test_id;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}
	
	
}
