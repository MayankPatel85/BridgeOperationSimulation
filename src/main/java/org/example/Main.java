package org.example;

import java.util.ArrayList;

/**
 * Main class
 */
public class Main {
    public static void main(String[] args) {
        RandomFrameGenerator randomFrameGenerator = new RandomFrameGenerator();
        // getting MAC Addresses from text file
        ArrayList<String> macAddresses = randomFrameGenerator.getMACAddressFromFile("BridgeFDB.txt");
        // generating 100 random frames
        Frame[] frames = randomFrameGenerator.generateFrames(macAddresses);
        // saving generated random frames to RandomFrames.txt file
        randomFrameGenerator.saveFramesToFile(frames);
        // performing BridgeFDB operation on generated random frames
        BridgeOperation.operateFrames(frames);
    }
}