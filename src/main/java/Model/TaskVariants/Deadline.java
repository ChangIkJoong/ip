package Model.TaskVariants;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private final String stringDate;
    /*
    private final LocalDate endDate;
    private boolean hasLocalDate;
    */

    public Deadline(String taskDescription, String inputDate) {
        super(taskDescription);
        this.stringDate = inputDate;

        /*
        this.hasLocalDate = false;
        this.endDate = tryParsing(inputDate);
        */
    }

    public String getEndDate() {
        return stringDate;
    }

    /*
    private LocalDate tryParsing(String parseString) {
        try {
            this.hasLocalDate = true;
            return LocalDate.parse(parseString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("no time/date added, input will be default date.");
        }
        return LocalDate.MAX;
    }
     */
}