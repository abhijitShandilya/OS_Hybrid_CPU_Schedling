//package minimalOutput;
public class Process{
  public int pNum;
  public int arrival;
  public int burst;
  public int waitingTime;
  public int turnAroundTime;
  public boolean added;

  public Process(int num, int a, int b) {
    pNum = num; //processor number
    arrival = a;
    burst = b;
    waitingTime = 0;
    turnAroundTime = 0;
    added = false;
  }

  public String toString() {
    return pNum + " | " + arrival + " | " + burst;
  }
}
