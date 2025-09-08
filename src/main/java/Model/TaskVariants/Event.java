package Model.TaskVariants;

public class Event extends Task {
    protected String startDate;
    protected String endDate;

    public Event(String taskDescription, String startDate, String endDate) {
        super(taskDescription);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}