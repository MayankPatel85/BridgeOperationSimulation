package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Generates random frames from the MAC Address given in file
 */
public class RandomFrameGenerator {

    /**
     * reads all MAC Addresses from the file
     * and store them in an array list
     * @param fileName the name of file
     * @return array list of MAC Addresses
     */
    ArrayList<String> getMACAddressFromFile(String fileName) {
        // for storing the MAC addresses
        ArrayList<String> macAddresses = new ArrayList<>();
        try {
            // reading the file
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                // skipping the port numbers
                if(!(currentLine.equals("1") || currentLine.equals("2") || currentLine.equals("3") || currentLine.equals("4"))) {
                    macAddresses.add(currentLine);
                }
            }
        } catch (FileNotFoundException err) {
            System.out.println("File with " + fileName + "not found.");
        }
        return macAddresses;
    }

    /**
     * generates array of 100 random object of Frame
     * @param macAddresses array list of MAC Address
     * @return array of Frame objects
     */
    Frame[] generateFrames(ArrayList<String> macAddresses) {
        int totalMACs = macAddresses.size();
        Frame[] frames = new Frame[100];
        for (int i = 0; i < 100; i++) {
            // generating random source MAC from the available MAC addresses
            int randomSourceMAC = (int) (Math.random() * (totalMACs));
            // generating random destination MAC from the available MAC addresses
            int randomDestinationMAC = (int) (Math.random() * (totalMACs));
            // generating random port number between 1 to 4
            int randomPort = (int) (Math.random() * (4));
            // generating frame object
            Frame frame = new Frame(macAddresses.get(randomSourceMAC), macAddresses.get(randomDestinationMAC), String.valueOf(randomPort + 1));
            frames[i] = frame;
        }
        return frames;
    }

    /**
     * saves the random generated frames to RandomFrames.txt file
     * @param frames array of Frame objects
     */
    void saveFramesToFile(Frame[] frames) {
        File randomFrameFile = new File("RandomFrames.txt");
        try{
            FileWriter randomFrameWriter = new FileWriter(randomFrameFile);
            for (int i = 0; i < frames.length; i++) {
                randomFrameWriter.write(frames[i].sourceMAC + "\n");
                randomFrameWriter.write(frames[i].destinationMAC + "\n");
                if(i == frames.length - 1) {
                    randomFrameWriter.write(frames[i].port);
                } else {
                    randomFrameWriter.write(frames[i].port + "\n");
                }
            }
            randomFrameWriter.close();
        } catch (Exception e) {
            System.out.println("Error saving frames to file");
            e.printStackTrace();
        }
    }

}
