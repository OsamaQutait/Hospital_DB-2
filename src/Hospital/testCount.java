package Hospital;

public class testCount {
    private int labID;
    private int count;

    public testCount(int labID, int count) {
        this.labID = labID;
        this.count = count;
    }

    public int getLabID() {
        return labID;
    }

    public int getCount() {
        return count;
    }

    public void setLabID(int labID) {
        this.labID = labID;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
