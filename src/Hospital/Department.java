package Hospital;

//this class represent the department class, where we save all department attributes as an object
public class Department {

	private int departmentID;
	private String departmentName;
	private int numberOfRooms;
	private int departmentFloor;

	public Department(){}

	public Department(int departmentID, String departmentName, int numberOfRooms, int departmentFloor) {
		this.departmentID = departmentID;
		this.departmentName = departmentName;
		this.numberOfRooms = numberOfRooms;
		this.departmentFloor = departmentFloor;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getDepartmentFloor() {
		return departmentFloor;
	}

	public void setDepartmentFloor(int departmentFloor) {
		this.departmentFloor = departmentFloor;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	
}
