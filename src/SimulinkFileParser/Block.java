package SimulinkFileParser;
/**
 * @Author: Abdallah Mohamed
 * @Github: AntiCrypted
 */

import GUI.CustomTool;
import GUI.SystemModel;
import java.util.HashMap;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Block {

    ////////////////////////////// Data Fields /////////////////////////////////
    private String type;
    private String name;
    private String sid;
    private HashMap<String, String> properties = new HashMap<String, String>();
    ////////////////////////////////////////////////////////////////////////////


    ///////////////////////////// Constructors /////////////////////////////////
    public Block(String blockContent) {
        extractData(blockContent);
    }
    ////////////////////////////////////////////////////////////////////////////


    //////////////////////////////// Getters ///////////////////////////////////
    public String getSID() {
        return sid;
    }

    public HashMap<String, String> getProperties(){
        return properties;
    }
    ////////////////////////////////////////////////////////////////////////////


    ///////////////////////// Block Data Extraction ////////////////////////////
    /* extracts all the block data including its properties from the block
       content which was extracted from the model content (system_root.xml) */

    private void extractData(String blockContent)
    {
        String [] data = blockContent.split("\"");
        type = data[1];
        name = data[3];
        sid = data[5];
        for(int i = 7; i < data.length; i+=2)
            properties.put(data[i], data[i+1].substring(1, data[i+1].indexOf('<')));
    }
    ////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////// GUI //////////////////////////////////////
    // view block on the system model
    public void viewBlock(){

        double [] Position = CustomTool.stringToDoubleArray(properties.get("Position"));
        double left = Position[0];
        double top = Position[1];
        double right = Position[2];
        double bottom = Position[3];

        double width = right - left;
        double height = bottom - top;

        Rectangle block = new Rectangle(left, top, width, height);
        block.setStyle("-fx-fill: white; -fx-stroke: black");
        block.setStrokeWidth(1);

        Text blockName = new Text(right - width, bottom + 10, this.name);
        blockName.setStyle("-fx-fill: black; -fx-font-size: 8 pt");

        SystemModel.getPane().getChildren().addAll(block, blockName);

    }
    ////////////////////////////////////////////////////////////////////////////
}
