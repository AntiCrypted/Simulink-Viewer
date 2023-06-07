package GUI;
/**
 * @Author: Abdallah Mohamed
 * @Github: AntiCrypted
 */

import SimulinkFileParser.Block;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;


public abstract class CustomTool {

    //////////////////////////////// Images ////////////////////////////////////
    public static Image iconImage(){
        return new Image("GUI/Images/Icon.png");
    }

    public static Image menuImage(){
        return new Image("GUI/Images/Menu.png");
    }

    public static Image settingsImage(){
        return new Image("GUI/Images/Settings.png");
    }

    public static Image aboutImage(){
        return new Image("GUI/Images/About.png");
    }

    public static Image exitImage(){
        return new Image("GUI/Images/Exit.png");
    }
    ////////////////////////////////////////////////////////////////////////////

    // custom button
    public static Button button(String text, double width, double height, String fontSize, double layoutX, double layoutY){
        Button bt = new Button(text);
        bt.setMinSize(width, height);
        // Gold: #FFD700
        bt.setStyle("-fx-background-color: #FFD700;"
                  + "-fx-border-color: white;"
                  + "-fx-text-fill: white;"
                  + "-fx-font-size: "+fontSize+" pt;"
                  + "-fx-font-family: 'Bauhaus 93';");
        bt.setLayoutX(layoutX);
        bt.setLayoutY(layoutY);
        return bt;
    }

    // converts a string consisting of an array or vector to a double array
    public static double [] stringToDoubleArray(String vector){
        vector = vector.replace("[", " ").replace("]", " ").strip();
        String [] numbers = vector.replace(";", ",").split(",");
        double [] doubleArray = new double[numbers.length];
        for(int i = 0; i < numbers.length; i++)
            doubleArray[i] = Double.parseDouble(numbers[i]);
        return doubleArray;
    }

    // extracts the SID from the Src or Dst (in signal lines an branches)
    public static String extractSID(String s){
        return s.substring(0, s.indexOf("#"));
    }

    // extract the inport or outport index
    public static int extractPortIndex(String s){
        return Integer.parseInt(s.substring(s.indexOf(":") + 1));
    }

    // extracts the index of the block with the same SID passed to the method
    private static int BlockIndex(ArrayList<Block> blocks, String sid){
        int index;
        for(index = 0; index < blocks.size(); index++){
            if (blocks.get(index).getSID().equals(sid))
                break;
        }
        return index;
    }

    // gets the postion of a block as an array of doubles
    public static double [] BlockPosition(ArrayList<Block> blocks, String sid){
        int index = BlockIndex(blocks, sid);
        return stringToDoubleArray(blocks.get(index).getProperties().get("Position"));
    }

    // gets the inports and outports number of a block as an array of doubles
    public static double [] BlockPorts(ArrayList<Block> blocks, String sid){
        int index = BlockIndex(blocks, sid);
        if(blocks.get(index).getProperties().get("Ports") != null){
            double [] blockPorts = CustomTool.stringToDoubleArray(blocks.get(index).getProperties().get("Ports"));
            if (blockPorts.length == 1){
                double [] allBlockPorts = {blockPorts[0], 1};
                return allBlockPorts;
            }
            return blockPorts;
        }
        else{
            double [] defaultBlockPorts = {1, 1};
            return defaultBlockPorts;
        }
    }

    // checks if the block is mirrored
    public static boolean isBlockMirrored(ArrayList<Block> blocks, String sid){
        int index = BlockIndex(blocks, sid);
        if (blocks.get(index).getProperties().get("BlockMirror") != null)
            return (blocks.get(index).getProperties().get("BlockMirror").equals("on"));
        return false;
    }

}
