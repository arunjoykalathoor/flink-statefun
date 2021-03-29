package org.apache.flink.statefun.sdk;

public class Message {
    String data;
    int[] timeVector;

    public Message(String data, int[] timeVector) {
        this.data = data;
        this.timeVector = timeVector;
    }

    public String getData() {
        return data;
    }

    public int[] getTimeVector() {
        return timeVector;
    }
}