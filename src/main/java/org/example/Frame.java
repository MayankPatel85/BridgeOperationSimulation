package org.example;

/**
 * Model class for Frame
 * contains source MAC Address, destination MAC Address and Port number
 */
public class Frame {
    String sourceMAC;
    String destinationMAC;
    String port;

    public Frame(String sourceMAC, String destinationMAC, String port) {
        this.sourceMAC = sourceMAC;
        this.destinationMAC = destinationMAC;
        this.port = port;
    }
}
