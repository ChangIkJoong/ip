public class Deadline extends Task {
    protected String endDate;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Deadline(String taskDescription, String endDate) {
        super(taskDescription);
        this.endDate = endDate;
    }
}