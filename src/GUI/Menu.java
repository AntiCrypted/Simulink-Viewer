package GUI;
/**
 * @Author: Abdallah Mohamed
 * @Github: AntiCrypted
 */

import SimulinkFileParser.SimulinkFile;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;


public abstract class Menu implements EventHandler<ActionEvent> {

    private static Scene scene;

    public static Scene getScene() {
        return scene;
    }

    public static void setStage(){

        ImageView viewMenu = new ImageView(CustomTool.menuImage());
        Pane pane = new Pane(viewMenu);

        //////////////////////////// Buttons ///////////////////////////////////
        // View File Button
        Button btVeiwFile = CustomTool.button("View File", 190, 45, "25", 255,340);
        btVeiwFile.setOnAction((ActionEvent e) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Simulink MDL File");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Simulink File","*.mdl"));
            File file = fileChooser.showOpenDialog(Main.getStage());
            SimulinkFile simulinkFile = new SimulinkFile(file.getAbsolutePath());
            SystemModel.setStage(simulinkFile);
        });

        // Settings Button
        Button btSettings = CustomTool.button("Settings", 190, 45,"25", 255,400);
        btSettings.setOnAction((ActionEvent e) -> {
            Settings.setScene();
            Main.getStage().setScene(Settings.getScene());
        });

        // About Button
        Button btAbout = CustomTool.button("About", 190, 45, "25", 255,460);
        btAbout.setOnAction((ActionEvent e) -> {
            About.setScene();
            Main.getStage().setScene(About.getScene());
        });

        // Exit Button
        Button btExit = CustomTool.button("Exit", 190, 45, "25", 255,520);
        btExit.setOnAction((ActionEvent e) -> {Exit.setStage();});
        ////////////////////////////////////////////////////////////////////////

        pane.getChildren().addAll(btVeiwFile, btSettings, btAbout, btExit);
        scene = new Scene(pane, 700, 600);
        Main.getStage().setScene(scene);

    }

}
