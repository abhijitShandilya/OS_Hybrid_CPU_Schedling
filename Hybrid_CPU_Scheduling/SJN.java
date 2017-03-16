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

Shortest job next (SJN), also known as shortest job first (SJF)
 */


public class SJN {

	 public static void runSJN(String inputFileName) {
		/*
		 * Read input text file for process details
		 */
    String line = null;
    ArrayList<Process> procList = new ArrayList<Process>();
    ArrayList<Process> readyQueue = new ArrayList<Process>();


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
        String[] numArray = line.split(" "); // read line as astring

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

    // START USING INPUT
    if (fileRead) {
      // sort processes by arrival for easier access
      Collections.sort(procList, (Process p1, Process p2) -> p1.arrival - p2.arrival);

		 System.out.println("\n\n ============== SJN Algorithm Output ============== ");

      //System.out.println("\nSorted values (procList):\nNo. | Arrival | Burst");
      for (Process p : procList) {
        //System.out.println(p);
      }

      //System.out.println("\nGantt chart");
      Process current;
      boolean allAdded = false;
      int nextArrival = 0;

      // set clock to first process
      int clock = procList.get(0).arrival;

      do {
        // check all processes in procList for arrival <= clock
        for (Process p : procList) {
          // has arrived and has not been added to queue
          if (p.arrival <= clock && !p.added) {
            // add to ready queue
            p.added = true;
            readyQueue.add(p);
          }
        }
        // sort ready queue by burst
        Collections.sort(readyQueue, (Process p1, Process p2) -> p1.burst - p2.burst);

        // check if all processes have been added to readyQueue
        allAdded = true;
        for (Process p : procList) {
          if (!p.added) {
            allAdded = false;
            nextArrival = p.arrival;
            break; // at least one process has not been added, break
          }
        }

        // there is something to run
        if (readyQueue.size() > 0) {
          // run the next process from the ready queue
          current = readyQueue.get(0);
          readyQueue.remove(0);

          // increase clock by ready queue[0]
          //System.out.print("p" + current.pNum + ": from: " + clock + "---");
          current.waitingTime = clock - current.arrival;
          clock += current.burst;
          //System.out.println(clock);
          current.turnAroundTime = current.burst + current.waitingTime;

        } else {
          if (!allAdded) {
            clock = nextArrival; //move clock to the next process
          }
        }

      }while (!allAdded || readyQueue.size() > 0);

      //System.out.println();
      //System.out.println("Num | WT | TAT");
      double totalWaiting = 0;
      double totalTAT = 0;

      for (Process p : procList) {
        //System.out.println(p.pNum + " | " + p.waitingTime + " | " + p.turnAroundTime);
        totalWaiting += p.waitingTime;
        totalTAT += p.turnAroundTime;
      }
      System.out.println("\naverage WT TT: " + (totalWaiting / procList.size()) + "\t" + (totalTAT / procList.size()));

    } else {
      System.out.println("ERROR: File not found/read ");
    }
  }
}
