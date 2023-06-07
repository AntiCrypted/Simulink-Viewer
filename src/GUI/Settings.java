package GUI;
/**
 * @Author: Abdallah Mohamed
 * @Github: AntiCrypted
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public abstract class Settings implements EventHandler <ActionEvent> {

    private static Scene scene;

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(){

        ImageView viewSettings = new ImageView(CustomTool.settingsImage());

        // Back Button
        Button btBack = CustomTool.button("Back", 190, 45, "25", 255, 500);
        btBack.setOnAction((ActionEvent e) -> {
            Main.getStage().setScene(Menu.getScene());
        });

        Pane pane = new Pane(viewSettings, btBack);
        scene = new Scene(pane, 700, 600);

    }

}
