import GUI.Controllers.BaseController;
import GUI.Models.ModelsHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/CEOView.fxml"));


        Parent root = loader.load();
        Scene scene = new Scene(root);



        BaseController controller = loader.getController();
        controller.setModel(ModelsHandler.getInstance());
        controller.setup();

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image("GUI/Images/WUAV.png"));
        primaryStage.show();
    }
}