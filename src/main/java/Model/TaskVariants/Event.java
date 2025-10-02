package Model.TaskVariants;

public class Event extends Task {
    protected String startStringDate;
    protected String endStringDate;

    public Event(String taskDescription, String inputStartDate, String inputEndDate) {
        super(taskDescription);
        this.startStringDate = inputStartDate;
        this.endStringDate = inputEndDate;
    }

    public String getStartDate() {
        return startStringDate;
    }

    public String getEndDate() {
        return endStringDate;
    }

}