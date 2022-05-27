package Hospital;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
//test
//tests
public class Identity {
    private int identityNumber;
    private int idbyRecordNumber;
    private String fullName;
    private Date dateOfBirth;
    private String gender;
    private String livingAddress;
    private String bloodType;
    private int phoneNumber;

    public Identity(int identityNumber, int idbyRecordNumber, String fullName, Date dateOfBirth, String gender, String livingAddress, String bloodType, int phoneNumber) {
        this.identityNumber = identityNumber;
        this.idbyRecordNumber = idbyRecordNumber;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.livingAddress = livingAddress;
        this.bloodType = bloodType;
        this.phoneNumber = phoneNumber;
    }

    public int getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(int identityNumber) {
        this.identityNumber = identityNumber;
    }

    public int getIdbyRecordNumber() {
        return idbyRecordNumber;
    }

    public void setIdbyRecordNumber(int idbyRecordNumber) {
        this.idbyRecordNumber = idbyRecordNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfBirthToString() {
        String[] d = new SimpleDateFormat("dd-MM-yyyy").format(this.dateOfBirth).split("-");
        return d[2] + "-" + d[1] + "-" + d[0];
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLivingAddress() {
        return livingAddress;
    }

    public void setLivingAddress(String livingAddress) {
        this.livingAddress = livingAddress;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
