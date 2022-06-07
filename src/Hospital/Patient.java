package Hospital;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Patient {
    private String visitReason;
    private String emergencyStatus;
    private float lengthOfStay;
    private Date joinDateAndTime;
    private Date leaveDateAndTime;
    private int identityNumber;
    private String roomID;

    public Patient(){

    }

    public Patient(String visitReason, String emergencyStatus, float lengthOfStay, Date joinDateAndTime, Date leaveDateAndTime, int identityNumber, String roomID) {
        this.visitReason = visitReason;
        this.emergencyStatus = emergencyStatus;
        this.lengthOfStay = lengthOfStay;
        this.joinDateAndTime = joinDateAndTime;
        this.leaveDateAndTime = leaveDateAndTime;
        this.identityNumber = identityNumber;
        this.roomID = roomID;
    }
    public Patient(String visitReason, String emergencyStatus, float lengthOfStay, Date joinDateAndTime, int identityNumber, String roomID) {
        this.visitReason = visitReason;
        this.emergencyStatus = emergencyStatus;
        this.lengthOfStay = lengthOfStay;
        this.joinDateAndTime = joinDateAndTime;
        this.leaveDateAndTime = leaveDateAndTime;
        this.identityNumber = identityNumber;
        this.roomID = roomID;
    }
    public Patient(String visitReason, String emergencyStatus, float lengthOfStay, Date joinDateAndTime, int identityNumber) {
        this.visitReason = visitReason;
        this.emergencyStatus = emergencyStatus;
        this.lengthOfStay = lengthOfStay;
        this.joinDateAndTime = joinDateAndTime;
        this.leaveDateAndTime = leaveDateAndTime;
        this.identityNumber = identityNumber;
        this.roomID = roomID;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getEmergencyStatus() {
        return emergencyStatus;
    }

    public void setEmergencyStatus(String emergencyStatus) {
        this.emergencyStatus = emergencyStatus;
    }

    public float getLengthOfStay() {
        return lengthOfStay;
    }

    public void setLengthOfStay(float lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }

    public Date getJoinDateAndTime() {
        return joinDateAndTime;
    }

    public void setJoinDateAndTime(Date joinDateAndTime) {
        this.joinDateAndTime = joinDateAndTime;
    }

    public Date getLeaveDateAndTime() {
        return leaveDateAndTime;
    }

    public void setLeaveDateAndTime(Date leaveDateAndTime) {
        this.leaveDateAndTime = leaveDateAndTime;
    }

    public int getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(int identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getJoinDateAndTimeToString() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(this.joinDateAndTime);
    }

    public String getLeaveDateAndTimeToString() {
        if (this.leaveDateAndTime == null){
            return null;
        }
        return "'" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(this.leaveDateAndTime) + "'";
    }

    public LocalDate getJoinDateLocalDate() {
        String[] d = new SimpleDateFormat("dd-MM-yyyy").format(this.joinDateAndTime).split("-");
        return LocalDate.of(Integer.parseInt(d[2]), Integer.parseInt(d[1]), Integer.parseInt(d[0]));
    }

    public LocalTime getJoinTime(){
        return LocalTime.of(this.joinDateAndTime.getHours(), this.joinDateAndTime.getMinutes(), this.joinDateAndTime.getSeconds());
    }

    public LocalTime getLeaveTime(){
        if (this.leaveDateAndTime != null) {
            return LocalTime.of(this.leaveDateAndTime.getHours(), this.leaveDateAndTime.getMinutes(), this.leaveDateAndTime.getSeconds());
        }
        return null;
    }

    public LocalDate getLeaveDateLocalDate() {
        if (this.leaveDateAndTime != null) {
            String[] d = new SimpleDateFormat("dd-MM-yyyy").format(this.leaveDateAndTime).split("-");
            return LocalDate.of(Integer.parseInt(d[2]), Integer.parseInt(d[1]), Integer.parseInt(d[0]));
        }
        return null;
    }
}
