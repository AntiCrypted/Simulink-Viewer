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
import javafx.scene.text.Text;


public class SignalLine {

    ///////////////////////////// Data Fields //////////////////////////////////
    private HashMap<String, String> properties = new HashMap<String, String>();
    private ArrayList<Branch> branches = new ArrayList<Branch>();
    ////////////////////////////////////////////////////////////////////////////


    //////////////////////////// Constructors //////////////////////////////////
    SignalLine(String signalLineContent){
        extractData(signalLineContent);
        extractBranches(signalLineContent);
    }
    ////////////////////////////////////////////////////////////////////////////


    /////////////////////////// Line Data Extraction ///////////////////////////
    /* extracts all the signal signalLine data and its branches */

    // extracts the signalLine data
    private void extractData(String signalLineContent){
        String [] data = signalLineContent.split("\"");
        int i = 1;
        while(true){
            properties.put(data[i], data[i+1].substring(1, data[i+1].indexOf('<')));
            // break if it reached a branch, or there's no more data to extraxt
            if((data[i+1].contains("<Branch>")) || (i == data.length-2))
                break;
            i += 2;
        }
    }

    // extracts all the branches from a particular signal signalLine
    private void extractBranches(String signalLineContent){
        int endIndex = 0;
        while(true){
            int startIndex = signalLineContent.indexOf("<Branch>", endIndex);
            if (startIndex == -1)
                break; // no more branches to extract
            endIndex = signalLineContent.indexOf("</Branch>", startIndex);
            String branchContent = signalLineContent.substring(startIndex, endIndex + 9);
            branches.add(new Branch(branchContent));
        }
    }
    ////////////////////////////////////////////////////////////////////////////


    //////////////////////////////// GUI ///////////////////////////////////////
    // view the signal line on the system model
    public void viewSignalLine(ArrayList<Block> blocks){

        String srcSID = CustomTool.extractSID(properties.get("Src"));
        int srcOutportIndex = CustomTool.extractPortIndex(properties.get("Src"));
        double [] srcBlockPorts = CustomTool.BlockPorts(blocks, srcSID);
        double [] srcBlockPosition = CustomTool.BlockPosition(blocks, srcSID);
        double startX = srcBlockPosition[2];
        double startY = srcBlockPosition[1]
                +((srcBlockPosition[3]-srcBlockPosition[1])/(srcBlockPorts[1]+1))*srcOutportIndex;
        if (CustomTool.isBlockMirrored(blocks, srcSID)){
            startX = srcBlockPosition[0];
        }

        if(properties.get("Points") != null){
            double [] points = CustomTool.stringToDoubleArray(properties.get("Points"));
            for(int i = 0; i < points.length; i += 2){
                double endX = startX + points[i];
                double endY = startY + points[i+1];
                Line signalLine = new Line(startX, startY, endX, endY);
                SystemModel.getPane().getChildren().add(signalLine);
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
            double endY = dstBlockPosition[1]
                    +((dstBlockPosition[3]-dstBlockPosition[1])/(dstBlockPorts[0]+1))*dstInportIndex;
            Text arrow = new Text(endX-8, endY+4, ">");
            arrow.setStyle("-fx-font-size: 15 pt");
            if (CustomTool.isBlockMirrored(blocks, dstSID)){
                endX = dstBlockPosition[2];
                arrow.setText("<");
            }
            Line signalLine = new Line(startX, startY, endX, endY);
            SystemModel.getPane().getChildren().addAll(signalLine, arrow);
        }

        for(int i = 0; i < branches.size(); i++)
            branches.get(i).viewBranch(startX, startY, blocks);

    }
    ////////////////////////////////////////////////////////////////////////////
}
