import Bruce.Controller;
import Model.Model;
import UI.View;

/**
 * Entry point for the Bruce application using an MVC architecture.
 * <p>
 * This class starts the application by obtaining the {@link Model} singleton wise,
 * creating the {@link View}, and integrating them together via the {@link Controller},
 * starting the program loop.
 * </p>
 *
 * @author changikjoong
 * @since 1.0
 * Disclaimer: GPT was partly used to assist with creating the documentation.
 */

public class Bruce {
    public static void main(String[] args) {
        Model model = Model.getInstance();
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.runProgram();
    }
}
