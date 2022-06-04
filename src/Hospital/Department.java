package Hospital;

//this class represent the department class, where we save all department attributes as an object
public class Department {

	private int departmentID;
	private String departmentName;
	private int departmentFloor;
	private int numberOfRooms;
	
	public Department(int departmentID,String departmentName,int departmentFloor, int numberOfRooms){
		this.departmentID=departmentID;
		this.departmentName=departmentName;
		this.departmentFloor=departmentFloor;
		this.numberOfRooms=numberOfRooms;
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
