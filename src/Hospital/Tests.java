package Hospital;

public class Tests {
    private int testID;
    private String testName;
    private float testPrice;
    private int labID;

    public Tests(int testID, String testName, float testPrice, int labID) {
        this.testID = testID;
        this.testName = testName;
        this.testPrice = testPrice;
        this.labID = labID;
    }

    public int getTestID() {
        return testID;
    }
    public String getTestName() {
        return testName;
    }
    public float getTestPrice() {
        return testPrice;
    }
    public int getLabID() {
        return labID;
    }
    public void setTestID(int testID) {
        this.testID = testID;
    }
    public void setTestName(String testName) {
        this.testName = testName;
    }
    public void setTestPrice(float testPrice) {
        this.testPrice = testPrice;
    }
    public void setLabID(int labID) {
        this.labID = labID;
    }
}
