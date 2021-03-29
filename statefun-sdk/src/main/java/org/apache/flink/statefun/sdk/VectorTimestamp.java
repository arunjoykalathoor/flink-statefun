package org.apache.flink.statefun.sdk;

public class VectorTimestamp {
    int[] timeVector;

    public VectorTimestamp(int size) {
        timeVector = new int[size + 1];
    }
    //TODO
    public VectorTimestamp(int[] timeVector) {
        this.timeVector = timeVector;
    }

    //TODO
    public int[] getTimeVector() {
        return timeVector;
    }
}
