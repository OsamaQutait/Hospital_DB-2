package Hospital;

// this class represent the room class, where we save all room attributes as an object
public class room {
	
	private String roomID;
	private int AvailableBeds;
	private int totalNumberOfBeds;
	private String roomDescription;
	private float AccommodationCost;
	private int departmentID;
	
	public room(String roomID,int AvailableBeds,int totalNumberOfBeds,String roomDescription,float AccommodationCost,int departmentID){
		this.roomID=roomID;
		this.AvailableBeds=AvailableBeds;
		this.totalNumberOfBeds=totalNumberOfBeds;
		this.roomDescription=roomDescription;
		this.AccommodationCost=AccommodationCost;
		this.departmentID=departmentID;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public int getAvailableBeds() {
		return AvailableBeds;
	}

	public void setAvailableBeds(int availableBeds) {
		AvailableBeds = availableBeds;
	}

	public int getTotalNumberOfBeds() {
		return totalNumberOfBeds;
	}

	public void setTotalNumberOfBeds(int totalNumberOfBeds) {
		this.totalNumberOfBeds = totalNumberOfBeds;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public float getAccommodationCost() {
		return AccommodationCost;
	}

	public void setAccommodationCost(float accommodationCost) {
		AccommodationCost = accommodationCost;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	
}
