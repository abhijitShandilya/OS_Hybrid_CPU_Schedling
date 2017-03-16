//package minimalOutput;
/**
 * Created by Javier Marroquin on 10/29/2016.
 *
 * code based on: http://www.javaengineeringprograms.com/round-robin-scheduling-algorithm-program-in-java/
 * with major alterations
 */
public class RoundRobin {

	 public static void runRoundRobin(int[] procArr, int[] procBurst, int numOfProc) {
		 System.out.println("\n\n ============== RoundRobin Algorithm Output ============== ");

        int sum = 0;
        int q = 2; //quantum
        int clock = 0;
        int a[] = new int[numOfProc]; // copy of burst time
        int waitingTime[] = new int[numOfProc]; // waiting time
        int turnAroundTime[] = new int[numOfProc]; // turn around time

        // copy of procBurst (for use when printing values)
        for (int i = 0; i < numOfProc; i++)
            a[i] = procBurst[i];

        do {
            for (int i = 0; i < numOfProc; i++) {
                if (procArr[i] <= clock) {
                    if (procBurst[i] > q) {
                        procBurst[i] -= q; // did 'q' amount of work on the process
                        //System.out.print("p" + (i+1) + ": from: " + clock + "---");
                        clock += q;
                        //System.out.println(clock);
                        for (int j = 0; j < numOfProc; j++) {
                            // increase waiting time for all the other processes to the amount of quantum 'q'
                            if ((j != i) && (procBurst[j] != 0))
                                waitingTime[j] += q;
                        }
                    } else {
                        if (procBurst[i] != 0) {
                            // procBurst is smaller than 'q'
                            // increase waiting time for all the other processes to the amount of burst for process 'i'
                            for (int j = 0; j < numOfProc; j++) {
                                if ((j != i) && (procBurst[j] != 0))
                                    waitingTime[j] += procBurst[i];
                            }
                            // current burst is done, mark as '0'
                            //System.out.print("p" + (i + 1) + ": from: " + clock + "---");
                            clock += procBurst[i];
                            //System.out.println(clock);
                            procBurst[i] = 0;
                        }
                    }
                }
            }

            // re-evaluate the total amount of burst time in the array
            sum = 0;
            for (int k = 0; k < numOfProc; k++)
                sum = sum + procBurst[k];
        }
        while (sum != 0); // a process needs to execute

        // find turn around time
        for (int i = 0; i < numOfProc; i++) {
            waitingTime[i] = waitingTime[i] - procArr[i];
            turnAroundTime[i] = waitingTime[i] + a[i];
        }

        // print important values
        //System.out.println();
        //System.out.println("Clock at the end: " + clock);
        //System.out.println("process\t\tBT\tWT\tTAT");
        for (int i = 0; i < numOfProc; i++) {
            //System.out.println("process" + (i + 1) + "\t" + a[i] + "\t" + waitingTime[i] + "\t" + turnAroundTime[i]);
        }

        // print averages
        float avg_wt = 0;
        float avg_tat = 0;
        for (int j = 0; j < numOfProc; j++) {
            avg_wt += waitingTime[j];
        }
        for (int j = 0; j < numOfProc; j++) {
            avg_tat += turnAroundTime[j];
        }

        System.out.println("\naverage WT TT : " + (avg_wt / numOfProc) + "\t" + (avg_tat / numOfProc));

    }
}