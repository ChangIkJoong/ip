package Model.TaskVariants;

public class Deadline extends Task {
    private final String stringDate;

    public Deadline(String taskDescription, String inputDate) {
        super(taskDescription);
        this.stringDate = inputDate;
    }

    public String getEndDate() {
        return stringDate;
    }
}