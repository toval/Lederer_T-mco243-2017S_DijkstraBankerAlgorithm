package dijkstrasbanker;

import java.util.ArrayList;
import java.util.Random;

public class Program
{

	final static int NUM_PROCS = 6; // How many concurrent processes
	final static int TOTAL_RESOURCES = 30; // Total resources in the system
	final static int MAX_PROC_RESOURCES = 13; // Highest amount of resources any process could need
	final static int ITERATIONS = 30; // How long to run the program
	static Random rand = new Random();
	static int totalHeldResources = 0;
	
	public static void main(String[] args)
	{
		
		// The list of processes:
		ArrayList<Proc> processes = new ArrayList<Proc>();
		for (int i = 0; i < NUM_PROCS; i++)
			processes.add(new Proc(MAX_PROC_RESOURCES - rand.nextInt(3))); // Initialize to a new Proc, with some small range for its max
		
		// Run the simulation:
		for (int i = 0; i < ITERATIONS; i++)
		{
			// loop through the processes and for each one get its request
			for (int j = 0; j < processes.size(); j++)
			{
				// Get the request
				int currRequest = processes.get(j).resourceRequest(TOTAL_RESOURCES - totalHeldResources);

				// just ignore processes that don't ask for resources
				if (currRequest == 0)
					continue;
				
		
				else{
					if(currRequest < 0){
						//finished, release its resources, add a negative
						totalHeldResources += currRequest;
						//System.out.println("Process " + j + " finished, returned " + Math.abs(currRequest));
						System.out.println("Process " + j + " finished, returned " + Math.abs(currRequest));
					}
					else{
					//add the process request and if true-keep it, 
					
					processes.get(j).addResources(currRequest);

					boolean simulate = simulation(processes,currRequest);
						if(simulate){
						//safe state
							System.out.println("Process " + j + " requested " + currRequest + ", granted");
							totalHeldResources += currRequest;
						}
						else{
							processes.get(j).addResources(-currRequest);
							System.out.println("Proces " + j + " requested " + currRequest + ", denied");	
						}
					}//end else
				// At the end of each iteration, give a summary of the current status:
				System.out.println("\n***** STATUS *****");
				System.out.println("Total Available: " + (TOTAL_RESOURCES - totalHeldResources));
				for (int k = 0; k < processes.size(); k++)
					System.out.println("Process " + k + " holds: " + processes.get(k).getHeldResources() + ", max: " +
							processes.get(k).getMaxResources() + ", claim: " + 
							(processes.get(k).getMaxResources() - processes.get(k).getHeldResources()));
				System.out.println("***** STATUS *****\n");
				
			}
		}

	}
	}
	public static boolean simulation(ArrayList<Proc> processes,int currRequest){
		int totalAvail = TOTAL_RESOURCES - totalHeldResources - currRequest;
		boolean found = false;
		
		ArrayList<Proc>tempProc = new ArrayList<Proc>();
		//copy into the temp arraylist
		for(int i =0;i<processes.size();i++){
			tempProc.add(i,processes.get(i));
		}
		while(!tempProc.isEmpty()){
			found = false;
				for(int i = 0;i<tempProc.size();i++){
				if(tempProc.get(i).getMaxResources() - tempProc.get(i).getHeldResources()
						<= totalAvail){ 
				totalAvail += tempProc.get(i).getHeldResources();	
		
				//take process off the processor
				tempProc.remove(i);
				
				found = true;
			}//end if
		}//end for
			if(!found)return false;
	
		}
	return true;
	
	}
}