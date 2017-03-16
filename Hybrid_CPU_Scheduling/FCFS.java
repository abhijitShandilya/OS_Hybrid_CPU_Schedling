//package minimalOutput;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Javier Marroquin on 10/29/2016.
 *
 * code based on: http://www.javaengineeringprograms.com/round-robin-scheduling-algorithm-program-in-java/
 * with major alterations
 */


public class FCFS {

	 public static void runFCFS(String inputFileName) {
		/*
		 * Read input text file for process details
		 */
    String line = null;
    ArrayList<Process> procList = new ArrayList<Process>();
    boolean fileRead = false;

    try {
      // FileReader reads text files in the default encoding.
      FileReader fileReader = new FileReader(inputFileName);

      // wrap FileReader in BufferedReader.
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      // Read first line from text file = Number of Processes
      line = bufferedReader.readLine();
      line = bufferedReader.readLine();

      String[] numbersArray = line.split(" ");
      int numOfProc = Integer.parseInt(numbersArray[0]);
      int pNumber = 0;
      int arrival, burst;

      for (int i = 0; i < numOfProc; i++) { // For each process
        line = bufferedReader.readLine();
        String[] numArray = line.split(" "); // read line as a string

        arrival = Integer.parseInt(numArray[0]);
        burst = Integer.parseInt(numArray[1]);
        procList.add(new Process(pNumber, arrival, burst));
        pNumber++;
      }

      // close files.
      bufferedReader.close();
      fileRead = true;

    } catch (FileNotFoundException ex) {
      System.out.println("Unable to open file");
    } catch (IOException ex) {
      System.out.println("Error reading file");
    }

    if (fileRead) {
      /*
       * Completed Reading Input file. Now use that data
       */
		 System.out.println("\n\n ============== FCFS Algorithm Output ============== ");
      //System.out.println("Sorted by arrival time values:\nNo. | Arrival | Burst");


      // sort by arrival ; new labda way
      Collections.sort(procList, (Process p1, Process p2) -> p1.arrival - p2.arrival);
//      Collections.sort(procList, (Process p1, Process p2) -> p1.burst - p2.burst);

      for (Process p : procList) {
        //System.out.println(p);
      }

      //System.out.println("\nGantt chart");

      int clock = 0;

      for (Process p : procList) {

        // process hasn't arrived yet but is next in queue
        if (p.arrival > clock) {
          clock = p.arrival; // move clock to next process
        }

        p.waitingTime = clock - p.arrival;
        //System.out.print("p" + p.pNum + ": from: " + clock + "---");
        clock += p.burst;
        //System.out.println(clock);
        p.turnAroundTime = p.waitingTime + p.burst;

      }

      //System.out.println();
      //System.out.println("Num | WT | TAT");
      double totalWaiting = 0;
      double totalTAT = 0;

      for (Process p : procList) {
        //System.out.println(p.pNum + " | " + p.waitingTime + " | " + p.turnAroundTime);
        totalWaiting += p.waitingTime;
        totalTAT += p.turnAroundTime;
      }

      System.out.println("\nWT TT: " + (totalWaiting / procList.size()) + "\t" + (totalTAT / procList.size()));

    } else {
      System.out.println("ERROR: File not found/read ");
    }
  }
}
