package GUI;
/**
 * @Author: Abdallah Mohamed
 * @Github: AntiCrypted
 *
 * Simulink Viewer V1.0
 *
 */

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    private static final Stage stage = new Stage();

    public static Stage getStage() {
        return stage;
    }

    public static void main (String [] args){
        launch(args);
    }

    @Override
    public void start (Stage stage){

        Menu.setStage();
        Main.stage.setTitle("Simulink Viewer");
        Main.stage.getIcons().add(CustomTool.iconImage());
        Main.stage.setResizable(false);
        Main.stage.show();

    }

}
