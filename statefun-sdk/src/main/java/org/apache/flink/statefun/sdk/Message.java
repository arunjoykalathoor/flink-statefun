package org.apache.flink.statefun.sdk;

import java.util.List;

public class Message {
    private String data;
    private List<Integer> timeVector;

    public Message(String data, List<Integer>timeVector) {
        this.data = data;
        this.timeVector = timeVector;
    }

    public String getData() {
        return data;
    }

    public List<Integer> getTimeVector() {
        return timeVector;
    }
}