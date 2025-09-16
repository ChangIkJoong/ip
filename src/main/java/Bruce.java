import Bruce.Controller;
import Model.Model;
import UI.View;

public class Bruce {
    public static void main(String[] args) {
        Model model = Model.getInstance();
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.runProgram();
    }
}
