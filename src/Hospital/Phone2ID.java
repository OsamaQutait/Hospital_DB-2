package Hospital;

public class Phone2ID {
    private int identity_number;
    private int phone_number;

    public Phone2ID(){}

    public Phone2ID(int identity_number, int phone_number) {
        this.identity_number = identity_number;
        this.phone_number = phone_number;
    }

    public int getIdentity_number() {
        return identity_number;
    }

    public void setIdentity_number(int identity_number) {
        this.identity_number = identity_number;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }
}
