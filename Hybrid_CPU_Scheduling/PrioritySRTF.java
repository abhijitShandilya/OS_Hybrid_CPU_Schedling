//package minimalOutput;
import java.util.Arrays;

// Gives correct output for all inputs
// http://campuscoke.blogspot.com/2015/01/shortest-remaining-time-first-srtf-cpu.html

public class PrioritySRTF {
	public static void runPrioritySRTF(int[] procArr, int[] procBurst) {
		int n;
		System.out.println(
				"\n\n ============== Hybrid of Priority & SRTF Algorithm Output ============== ");
		n = procArr.length;
		int proc[][] = new int[n + 1][5];// proc[][0] is the AT array,[][1] -
											// BT,[][2] - WT,[][3] - TT
		for (int i = 1; i <= n; i++) {
			// System.out.println("Please enter the Arrival Time for Process " +
			// i + ": ");
			proc[i][0] = procArr[i - 1]; // Integer.parseInt(br.readLine());
			// System.out.println("Please enter the Burst Time for Process " + i
			// + ": ");
			proc[i][1] = procBurst[i - 1]; // Integer.parseInt(br.readLine());
			proc[i][4] = procBurst[i - 1]; // Integer.parseInt(br.readLine());
		}
	

		// Calculation of Total Time and Initialization of Time Chart array
		int total_time = 0;
		for (int i = 1; i <= n; i++) {
			total_time += proc[i][1];
		}
		int time_chart[] = new int[total_time];

		for (int i = 0; i < total_time; i++) {
			// Selection of shortest process which has arrived
			int sel_proc = 0;

			float minWTbyBT = 99999f;
			float waitingFactor = 2f; // execute if WT/BT >= 0.5

			int min = Integer.MAX_VALUE;
			for (int j = 1; j <= n; j++) {
				if (proc[j][0] <= i)// Condition to check if Process has arrived
				{
					if (proc[j][1] < min && proc[j][1] != 0) {
						min = proc[j][1];
						sel_proc = j;
					}
				}
			}

			// Hybrid of SRTF & Priority
			for (int p = 1; p <= n; p++) {
				// System.out.println();
				if (proc[p][1] != 0) {
					
					 /*System.out.print("process id =" + p);
					 System.out.print("\t"+proc[p][2]);
					 System.out.print("\t"+proc[p][4]);*/
					 
					float factor = (float) (proc[p][2]) / (float) (proc[p][4]);
					//System.out.println("\t"+factor);
					//System.out.println(factor);
					if (factor < minWTbyBT) {
						minWTbyBT = factor;
						if (factor > waitingFactor) {
							sel_proc = p;
							// System.out.println("in for "+p);
							
							//To Do
							// run it only for 1/10 of its remaining BT
							// i++ multiple
							// skip below part : mostly
							
						}
					}
				}
			}
			//System.out.println();

			minWTbyBT = 0f;

			// Assign selected process to current time in the Chart
			time_chart[i] = sel_proc;

			// Decrement Remaining Time of selected process by 1 since it has
			// been assigned the CPU for 1 unit of time
			proc[sel_proc][1]--;

			// WT and TT Calculation
			for (int j = 1; j <= n; j++) {
				if (proc[j][0] <= i) // check if arrived
				{
					if (proc[j][1] != 0) // if not already completed
					{
						proc[j][3]++;// If process has arrived and it has not
										// already completed execution its TT is
										// incremented by 1
						if (j != sel_proc)// If the process has not been
											// currently assigned the CPU and
											// has arrived its WT is incremented
											// by 1
							proc[j][2]++;
					}
					// if completed in this round of cpu run
					else if (j == sel_proc)// This is a special case in which
											// the process has been assigned CPU
											// and has completed its execution
						proc[j][3]++;
				}
			}

			/*
			 * for(int p = 1; p <= n; p++) {
			 * 
			 * // System.out.println("=="); System.out.print("\t"+proc[p][2]);
			 * System.out.print("\t"+proc[p][3]);
			 * //System.out.println(proc[p][3] / proc[p][2]); }
			 */

			// System.out.println("===============================");
			// Printing the Time Chart
			if (i != 0) {
				if (sel_proc != time_chart[i - 1])
				// If the CPU has been assigned to a different Process we need
				// to print the current value of time and the name of
				// the new Process
				{
					//System.out.print("--" + i + "--<P" + sel_proc + ">");
				}
			} else// If the current time is 0 i.e the printing has just started
					// we need to print the name of the First selected Process
				{
					//System.out.print(i + "--<P" + sel_proc + ">");
				}
			if (i == total_time - 1)// All the process names have been printed
									// now we have to print the time at which
									// execution ends
				{
					//System.out.print("--" + (i + 1));
				}

		}
		//System.out.println();
		//System.out.println();

		// Printing the WT and TT for each Process
		/*System.out.println("P\t WT \t TT ");
		for (int i = 1; i <= n; i++) {
			System.out.printf("%d\t%2dms\t%2dms", i, proc[i][2], proc[i][3]);
			System.out.println();
		}

		System.out.println();*/

		// Printing the average WT & TT
		float WT = 0, TT = 0;
		for (int i = 1; i <= n; i++) {
			WT += proc[i][2];
			TT += proc[i][3];
		}
		WT /= n;
		TT /= n;
	     System.out.println("The Average WT TT: " + WT + "\t" + TT);
	}

}