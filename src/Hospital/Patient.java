package Hospital;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Patient {
    private int identityNumber;
    private String emergencyStatus;
    private String visitReason;

    public Patient(int identityNumber, String emergencyStatus, String visitReason) {
        this.identityNumber = identityNumber;
        this.emergencyStatus = emergencyStatus;
        this.visitReason = visitReason;
    }

    public int getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(int identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getEmergencyStatus() {
        return emergencyStatus;
    }

    public void setEmergencyStatus(String emergencyStatus) {
        this.emergencyStatus = emergencyStatus;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    /*public String getJoinDateAndTimeToString() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(this.joinDateAndTime);
    }

    public String getLeaveDateAndTimeToString() {
        if (this.leaveDateAndTime == null){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(this.leaveDateAndTime);
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
    }*/
}
