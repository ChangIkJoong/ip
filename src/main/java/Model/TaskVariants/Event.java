package Model.TaskVariants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected String startStringDate;
    protected String endStringDate;
    /*
    private boolean hasLocalDate;
    private final LocalDate startLocalDate;
    private final LocalDate endLocalDate;
    */

    public Event(String taskDescription, String inputStartDate, String inputEndDate) {
        super(taskDescription);
        //this.hasLocalDate = false;
        this.startStringDate = inputStartDate;
        this.endStringDate = inputEndDate;
        /*
        this.startLocalDate = tryParsing(inputStartDate);
        this.endLocalDate = tryParsing(inputEndDate);
        */
    }

    /*
    private LocalDate tryParsing(String parseString) {
        try {
            this.hasLocalDate = true;
            return LocalDate.parse(parseString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("no time/date added, will be default date.");
        }
        return LocalDate.MAX;
    }
     */

    public String getStartDate() {
        return startStringDate;
    }

    public String getEndDate() {
        return endStringDate;
    }

}