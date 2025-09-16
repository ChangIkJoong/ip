import Model.Model;
import UI.View;
import Bruce.Controller;

import java.io.FileNotFoundException;

public class Bruce {
    public static void main(String[] args) throws FileNotFoundException {
        Model model = Model.getInstance();
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.runProgram();
    }
}
