package SimulinkFileParser;
/**
 * @Author: Abdallah Mohamed
 * @Github: AntiCrypted
 */

import GUI.CustomTool;
import GUI.SystemModel;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class Branch {

    private HashMap<String, String> properties = new HashMap<String, String>();


    ////////////////////////////// Constructor /////////////////////////////////
    Branch(String branchContent){
        extractData(branchContent);
    }
    ////////////////////////////////////////////////////////////////////////////


    /////////////////////////// Branch Data Extraction /////////////////////////
    /* extracts the properties of a branch from a particular line*/

    private void extractData(String branchContent){
        String [] data = branchContent.split("\"");
        for(int i = 1; i < data.length; i+=2)
            properties.put(data[i], data[i+1].substring(1, data[i+1].indexOf('<')));
    }
    ////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////// GUI /////////////////////////////////////
    // view the branch on the system model
    public void viewBranch(double startX, double startY, ArrayList<Block> blocks){

        if(properties.get("Points") != null){
            Circle intersection = new Circle (startX, startY, 2);
            double [] points = CustomTool.stringToDoubleArray(properties.get("Points"));
            for(int i = 0; i < points.length; i += 2){
                double endX = startX + points[i];
                double endY = startY + points[i+1];
                Line branch = new Line(startX, startY, endX, endY);
                SystemModel.getPane().getChildren().addAll(branch, intersection);
                startX = endX;
                startY = endY;
            }
        }

        if(properties.get("Dst") != null){
            String dstSID = CustomTool.extractSID(properties.get("Dst"));
            int dstInportIndex = CustomTool.extractPortIndex(properties.get("Dst"));
            double [] dstBlockPorts = CustomTool.BlockPorts(blocks, dstSID);
            double [] dstBlockPosition = CustomTool.BlockPosition(blocks, dstSID);
            double endX = dstBlockPosition[0];
            double endY = dstBlockPosition[1] + ((dstBlockPosition[3]-dstBlockPosition[1])/(dstBlockPorts[0]+1))*dstInportIndex;
            Text arrow = new Text(endX-8, endY+4, ">");
            arrow.setStyle("-fx-font-size: 15 pt");
            if (CustomTool.isBlockMirrored(blocks, dstSID)){
                endX = dstBlockPosition[2];
                arrow.setText("<");
                arrow.setX(endX-2);
            }
            Line branch = new Line(startX, startY, endX, endY);
            SystemModel.getPane().getChildren().addAll(branch, arrow);
        }

    }
    ////////////////////////////////////////////////////////////////////////////
}
