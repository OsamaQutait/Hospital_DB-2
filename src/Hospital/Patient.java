package Hospital;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
//test
public class Patient {
    private int recordNumber;
    private int lengthOfStay;
    private Date joinDateAndTime;
    private Date leaveDateAndTime;

    /*@FXML
    private JFXTextArea fullName;*/

    public Patient(int recordNumber, int lengthOfStay, Date joinDateAndTime, Date leaveDateAndTime) {
        this.recordNumber = recordNumber;
        this.lengthOfStay = lengthOfStay;
        this.joinDateAndTime = joinDateAndTime;
        this.leaveDateAndTime = leaveDateAndTime;
    }

    public int getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }

    public int getLengthOfStay() {
        return lengthOfStay;
    }

    public void setLengthOfStay(int lengthOfStay) {
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

    public String getJoinDateAndTimeToString() {
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
    }
}
