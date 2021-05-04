package org.apache.flink.statefun.examples.datastream;

import java.util.ArrayList;
import java.util.List;

public class VectorClock {

  private List<Integer> currentTime;
  private int currentProcessIndex;

  public VectorClock(int size, int currentProcessIndex) {
    this.currentTime = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      currentTime.add(0);
    }
    this.currentProcessIndex = currentProcessIndex;
  }

  public VectorClock(List<Integer> currentTime, int currentProcessIndex) {
    this.currentTime = new ArrayList<>(currentTime);
    this.currentProcessIndex = currentProcessIndex;
  }

  public void setCurrentTime(List<Integer> currentTime) {
    this.currentTime =new ArrayList<>(currentTime);
  }

  public void increment() {
    currentTime.set(currentProcessIndex, currentTime.get(currentProcessIndex) + 1);
  }

  public void updateClock(List<Integer> messageTimestamp) {
    for (int i = 0; i < messageTimestamp.size(); ++i) {
      if (i == currentProcessIndex) {
        currentTime.set(currentProcessIndex, currentTime.get(currentProcessIndex) + 1);
      } else {
        currentTime.set(i, Integer.max(currentTime.get(i), messageTimestamp.get(i)));
      }
    }
  }

  public List<Integer> getCurrentTime() {
    return currentTime;
  }
}
