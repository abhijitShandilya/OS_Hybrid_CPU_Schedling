//package minimalOutput;
//Main Program : This calls others

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class App {

	public static void main(String[] args) {
		int numberOfInputFiles = 7;
		for (int fileNo = 1; fileNo <= numberOfInputFiles; fileNo++){

		/*
		 * Read input text file for process details
		 */

		int numOfProc = 0; // Number of processes
		String[] procName = null; // String[i] = Name of i-th process
		int[] procArr = null; // procArr[i] = Arrival time of i-th process
		int[] procBurst = null; // procBurst[i] = Burst time of i-th process
		int[] procPri = null; // procPri[i] = Priority of i-th process
		Boolean	PriorityUsed = false;

		// This will reference one line at a time
		String line = null;

		String inputFileName = "input" + fileNo + ".txt";
		
		System.out.println("Results for file : " + inputFileName + "\n");

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(inputFileName);

			// wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// Read first line from text file = Number of Processes
			line = bufferedReader.readLine();
			line = bufferedReader.readLine();
			String[] numbersArray = line.split(" ");
			numOfProc = Integer.parseInt(numbersArray[0]);
			PriorityUsed = ( (Integer.parseInt(numbersArray[1])) == 1 ? true : false) ;

			/*
			 * Create 3 arrays from input file : Length of array = Number of
			 * processes
			 */

			// String[i] = Name of i-th process
			procName = new String[numOfProc];
			// procArr[i] = Arrival time of i-th process
			procArr = new int[numOfProc];
			// procBurst[i] = Burst time of i-th process
			procBurst = new int[numOfProc];
			
			if (PriorityUsed) procPri = new int[numOfProc];

			int count = 0; // Process number assigned by us

			for (int i = 0; i < numOfProc; i++) { // For each process

				line = bufferedReader.readLine();
				String[] numArray = line.split(" "); // read line as astring
														// array

				procName[i] = "P" + String.valueOf(++count);
				// first value in text file is arrival time
				procArr[i] = Integer.parseInt(numArray[0]);
				// Second vale, seperated by space, is Burst time
				procBurst[i] = Integer.parseInt(numArray[1]);
				
				if (PriorityUsed) procPri[i] = Integer.parseInt(numArray[2]);
			}

			// close files.
			bufferedReader.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file");
		} catch (IOException ex) {
			System.out.println("Error reading file");
		}

		/*
		 * Completed Reading Input file. Now use that data
		 */

		/* just to test if input is read correctly */
		/*
		System.out.print("Inputted values:\n\nNo.\tArrival\tBurst");
		if (PriorityUsed) System.out.print("\tPriority");
		
		System.out.println();

		for (int i = 0; i < numOfProc; i++) {
			System.out.print(procName[i] + " ");
			System.out.print("\t  "+procArr[i] + " ");
			System.out.print("\t  "+procBurst[i] + " ");
			if (PriorityUsed) System.out.print("\t  "+procPri[i]);
			System.out.println();
		}*/
		

        int[] procArrArray = Arrays.copyOf(procArr,numOfProc);
        int[] procBurstArray = Arrays.copyOf(procBurst,numOfProc);
		
		SRTF.runSRTF(procArr, procBurst);
		if (PriorityUsed) Priority.runPriority(procArr, procBurst, procPri);
		RoundRobin.runRoundRobin(procArrArray, procBurstArray, numOfProc);
		SJN.runSJN(inputFileName);
		FCFS.runFCFS(inputFileName);
		PrioritySRTF.runPrioritySRTF(procArr, procBurst);
		
		System.out.println("\n\n\n\n\n");

	}
		
	}

}
