package GUI;
/**
 * @Author: Abdallah Mohamed
 * @Github: AntiCrypted
 */

import SimulinkFileParser.SimulinkFile;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public abstract class SystemModel {

    private static Pane pane = new Pane();

    public static Pane getPane() {
        return pane;
    }

    public static void setStage(SimulinkFile simulinkFile){

        Rectangle window = new Rectangle(0,0,2000,1200);
        window.setStyle("-fx-fill: white");

        pane.getChildren().add(window);

        // show blocks
        for (int i = 0; i< simulinkFile.getBlocks().size(); i++)
            simulinkFile.getBlocks().get(i).viewBlock();

        // show signal lines and branches
        for(int i = 0; i< simulinkFile.getSignalLines().size(); i++)
            simulinkFile.getSignalLines().get(i).viewSignalLine(simulinkFile.getBlocks());

        Scene scene = new Scene(pane, 1500, 700);

        Main.getStage().close();
        Stage stage = new Stage();
        stage.setTitle("System Model");
        stage.getIcons().add(CustomTool.iconImage());
        stage.setScene(scene);
        stage.show();

    }

}
