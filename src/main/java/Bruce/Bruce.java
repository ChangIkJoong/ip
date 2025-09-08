package Bruce;

import Model.Model;
import UI.View;

public class Bruce {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.runProgram();
    }
}
