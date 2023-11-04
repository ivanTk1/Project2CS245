import java.util.*;
import java.io.*;

public class ElevatorSimulation {
    static int floorCount = 32;
    static double passangerApears = 0.03;
    static int numberOfElevators = 1;
    static int elevatorCapacity = 10;
    static int ticks = 500;
    static int curFloor = 1;

    private static Queue<Person>[] floors;

    public static void passangers() {
        Random random = new Random();
        double chancePersonApears = random.nextDouble();
        int destFloor;
        int count = 1;
    
        for(int i = 0; i < floorCount; i++){
            if (chancePersonApears < passangerApears) {
                do {
                    destFloor = random.nextInt(floorCount);
                } while (destFloor == i);
                Person person = new Person("Passanger" + count, i, destFloor, true);
                floors[i].offer(person); 
    
                count++;
            } 
        }
    }
    
    public static void main(String[] args) throws IOException {

        floors = new Queue[floorCount];
        for (int i = 0; i < floorCount; i++) {
            floors[i] = new LinkedList<>();
        } 
        Elevator elevator = new Elevator(floorCount, elevatorCapacity, numberOfElevators);


        for (int i = 0; i < ticks; i++) {
            passangers();
            System.out.println("Current Floor" + curFloor);
            
            //get people from current floor who are going in same direction
            //move up 5 floors checking for people who are going up too
            //once all up passangers are gone go to next person in queue
            //


        }
    }
}
