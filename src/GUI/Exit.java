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
import javafx.stage.Modality;
import javafx.stage.Stage;


public abstract class Exit implements EventHandler <ActionEvent> {

    public static void setStage(){

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Exit");
        stage.getIcons().add(CustomTool.iconImage());

        ImageView viewExit = new ImageView(CustomTool.exitImage());
        Pane pane = new Pane(viewExit);

        //////////////////////////// Buttons ///////////////////////////////////
        // yes button
        Button btYes = CustomTool.button("Yes", 120, 10, "15", 20, 100);
        btYes.setOnAction((ActionEvent e) -> {
            stage.close();
            Main.getStage().close();
        });

        // no button
        Button btNo = CustomTool.button("No", 120, 10, "15", 160, 100);
        btNo.setOnAction((ActionEvent e) -> {stage.close();});
        ////////////////////////////////////////////////////////////////////////

        pane.getChildren().addAll(btYes, btNo);
        Scene scene = new Scene(pane, 300, 150);
        stage.setScene(scene);
        stage.showAndWait();

    }

}
