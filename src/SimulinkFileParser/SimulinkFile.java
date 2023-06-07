package SimulinkFileParser;
/**
 * @Author: Abdallah Mohamed
 * @Github: AntiCrypted
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class SimulinkFile {

    ////////////////////////////// Data Fields /////////////////////////////////
    private String content = "";
    private String modelContent;
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<SignalLine> signalLines = new ArrayList<SignalLine>();
    private String path;
    ////////////////////////////////////////////////////////////////////////////


    ////////////////////////////// Constructors ////////////////////////////////
    public SimulinkFile(String path) {
        this.path = path;
        try{extractContent();} catch(FileNotFoundException e){}
        if (contentValidation()){
            extractModelContent();
            extractBlocks();
            extractSignalLines();
        }
    }
    ////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////// Getters //////////////////////////////////
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public ArrayList<SignalLine> getSignalLines() {
        return signalLines;
    }
    ////////////////////////////////////////////////////////////////////////////


    /////////////////////////// Validation Checks //////////////////////////////
    /* validation check, in case of true that means the file is valid */

    // checks if the mdl file is empty
    // if it's empty, then it's not valid
    private boolean contentValidation(){
        return !(content.strip().equals(""));
    }
    ////////////////////////////////////////////////////////////////////////////


    ///////////////////////////// Data Extraction //////////////////////////////
    /* the Model Information is stored in system_root.xml which is contained in
       the mdl file with the other ARXML files (AutoSAR files) */

    // extract mdl file content
    private void extractContent() throws FileNotFoundException {
        Scanner scan = new Scanner(new File(path));
        while(scan.hasNextLine())
            content += scan.nextLine() + "\n";
    }

    // extracts all the system_root.xml contents (Model Info) from the mdl file
    private void extractModelContent(){
        int startIndex = content.indexOf("__MWOPC_PART_BEGIN__ /simulink/systems/system_root.xml");
        int endIndex = content.indexOf("</System>", startIndex);
        modelContent = content.substring(startIndex + 55, endIndex + 9);
    }

    // extracts all the block contents (Block Info) from the Model Content
    private void extractBlocks(){
        int endIndex = 0;
        while(true){
            int startIndex = modelContent.indexOf("<Block", endIndex);
            if (startIndex == -1)
                break; // no more blocks to extract
            endIndex = modelContent.indexOf("</Block>", startIndex);
            String blockContent = modelContent.substring(startIndex, endIndex + 8);
            blocks.add(new Block(blockContent));
        }
    }

    // extracts all the signal line contents (Line Info) from the Model Content
    private void extractSignalLines(){
        int endIndex = 0;
        while(true){
            int startIndex = modelContent.indexOf("<Line>", endIndex);
            if (startIndex == -1)
                break; // no more signalLines to extract
            endIndex = modelContent.indexOf("</Line>", startIndex);
            String signalLineContent = modelContent.substring(startIndex, endIndex + 7);
            signalLines.add(new SignalLine(signalLineContent));
        }

    }
    ////////////////////////////////////////////////////////////////////////////
}
