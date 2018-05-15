import processInformation.ProcessControlBlock;
import simulation.SimulationGUI;

import java.util.Random;
import java.util.Scanner;

public class Main extends Thread{
	public Main(){
		int pID[]= {1,2,3,4,5,6,7,8,9,10};
		int arrivalTime[] = {50,34,10,49,29,5,7,48,0,24};
		int burstTime[] = {31,38,7,46,22,36,17,18,27,35};
		int priority[] = {15,20,9,5,5,5,5,5,5,5};
		
		Scanner scan = new Scanner(System.in);
		System.out.println("[1] Higher before lower");
		System.out.println("[2] Fixed time slice");
		System.out.print("Enter MLFQ choice: ");
		
		int choice = scan.nextInt();
		scan.nextLine();
		
		if(choice == 1){
			MLFQ schedAlgo = new MLFQ(1);	
			schedAlgo.execute();
			schedAlgo.getGanttChart().printContents();
		}else if(choice == 2){
			MLFQ schedAlgo = new MLFQ(2);	
			schedAlgo.execute();
			schedAlgo.getGanttChart().printContents();
		}else{
			System.out.println("Invalid input");
		}
		
	}
	
	public static void main(String args[]){
		Main main = new Main();
	}
}
