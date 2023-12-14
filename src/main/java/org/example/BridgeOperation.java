package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Performs bridge forward/discard/broadcast operation
 * Updates BridgeFDB.txt file
 */
public class BridgeOperation {

    /**
     * Reads old BridgeFDB from the file
     * and stores them in Map of key as MAC Address and value as port number
     * @return map of MAC address and port number
     */
    static HashMap<String, String> getBridgeFDBFromFile() {
        HashMap<String, String> bridgeFDB = new HashMap<>();
        File bridgeFDBFile = new File("BridgeFDB.txt");
        try {
            // reading the file
            Scanner scanner = new Scanner(bridgeFDBFile);
            while(scanner.hasNextLine()) {
                // storing MAC Address as key and port number as value
                bridgeFDB.put(scanner.nextLine(), scanner.nextLine());
            }
        } catch (FileNotFoundException exception) {
            System.out.println("BridgeFDB file not found.");
        }
        return bridgeFDB;
    }

    /**
     * Updates the text file with updated port number
     * @param bridgeFDB map of MAC Address and port number
     */
    static void updateBridgeFDBFile(HashMap<String, String> bridgeFDB) {
        File bridgeFDBFile = new File("BridgeFDB.txt");
        try {
            // saving to BridgeFDB.txt file
            FileWriter bridgeFDBWriter = new FileWriter(bridgeFDBFile);
            for(String source: bridgeFDB.keySet()) {
                bridgeFDBWriter.write(source + "\n" + bridgeFDB.get(source) + "\n");
            }
            bridgeFDBWriter.close();
        } catch (Exception exception) {
            System.out.println("Error updating BridgeFDB file.");
        }
    }

    /**
     * Performs Bridge forward/discard/broadcast operation
     * @param frames array of Frame objects
     */
    static void operateFrames(Frame[] frames) {
        // getting old BRidgeFDB from text file
        HashMap<String, String> bridgeFDB = getBridgeFDBFromFile();

        File file = new File("BridgeOutput.txt");
        String operation = "";
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (int i = 0; i < frames.length; i++) {
                // checking if BridgeFDB has the source MAC Address
                // otherwise adding the source
                if(bridgeFDB.containsKey(frames[i].sourceMAC)) {
                    // checking if source port is equal to the frame port
                    // otherwise updating the port as the host has moved
                    if(!bridgeFDB.get(frames[i].sourceMAC).equals(frames[i].port)) {
                        bridgeFDB.replace(frames[i].sourceMAC, frames[i].port);
                    }
                } else {
                    bridgeFDB.put(frames[i].sourceMAC, frames[i].port);
                }
                // checking if BridgeFDB has the destination MAC Address
                // otherwise broadcasting the frame
                if(bridgeFDB.containsKey(frames[i].destinationMAC)) {
                    // checking if source and destination are on same collision domain
                    // if in same collision domain then discarding the frame
                    // otherwise forwarding on to specific port number
                    if(bridgeFDB.get(frames[i].destinationMAC).equals(frames[i].port)) {
                        operation = frames[i].sourceMAC + "\t" + frames[i].destinationMAC + "\t" + frames[i].port + "\t" + " Discarded";
                    } else {
                        operation = frames[i].sourceMAC + "\t" + frames[i].destinationMAC + "\t" + frames[i].port + "\t" + " Forwarded on port " + frames[i].port;
                    }
                } else {
                    operation = frames[i].sourceMAC + "\t" + frames[i].destinationMAC + "\t" + frames[i].port + "\t" + " Broadcast";
                }
                // for not adding a new line at the end of file
                if(i == frames.length - 1) {
                    fileWriter.write(operation);
                } else {
                    fileWriter.write(operation + "\n");
                }
            }
            fileWriter.close();
            // updating the BridgeFDB.txt file
            updateBridgeFDBFile(bridgeFDB);
        } catch (Exception e) {
            System.out.println("Error creating bridge FDB file.");
        }
    }

}
