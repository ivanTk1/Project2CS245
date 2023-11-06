import java.util.*;
import java.io.*;

public class ElevatorSimulation {
    static int floorCount = 32;
    static double passengerAppears = 0.03;
    static int numberOfElevators = 1;
    int elevatorCapacity = 10;
    static int ticks = 500;
    static int count = 0;
    public static Elevator elevator;
    static Queue<Person>[] floorsQueues;

    // Initialize goingUp and goingDown at the class level
    private static PriorityQueue<Person> goingDown = new PriorityQueue<>(new Comparator<Person>() {
        @Override
        public int compare(Person person1, Person person2) {
            // Compare based on curFloor in descending order
            return Integer.compare(person2.getCurFloor(), person1.getCurFloor());
        }
    });

    private static PriorityQueue<Person> goingUp = new PriorityQueue<>(new Comparator<Person>() {
        @Override
        public int compare(Person person1, Person person2) {
            // Compare based on curFloor in ascending order
            return Integer.compare(person1.getCurFloor(), person2.getCurFloor());
        }
    });

    public static void createPeople() {
        Random random = new Random();

        for (int i = 0; i < floorCount; i++) {
            double randomValue = random.nextDouble();
            if (randomValue < passengerAppears) {
                int destFloor = random.nextInt(floorCount);
                if(destFloor >= i){
                    destFloor++;
                }
                Person passenger = new Person("passenger" + count, i, destFloor, true);
                System.out.println(passenger.getName() + " is waiting on floor " + passenger.getCurFloor() + " going to " + passenger.getDestFloor());
                count++;
                // Adds passengers to the correct direction
                if (passenger.getCurFloor() > passenger.getDestFloor()) {
                    floorsQueues[i].offer(passanger);
                } else if (passenger.getCurFloor() < passenger.getDestFloor()) {
                    floorsQueues[i+1].offer(passanger);
                } else {
                    System.out.println(passenger.toString()); 
                    System.out.println("SOMETHING WENT WRONG ADDING PASSENGERS TO HEAP");
                    System.exit(0);
                }
            }
        }
    }

   
    public static void main(String[] args) {
        Queue<Person>[] floorsQueues = new Queue[floorCount * 2];
        
        for (int i = 0; i < floorCount * 2; i++) {
            floorsQueues[i] = new LinkedList<>();
        }

        floorsQueues[0].offer(null);
        


        for(int j = 0; j < numberOfElevators; j++){
            Elevator elevator = new Elevator("elevator" + j, 0, 0, true, null);
        }

        for (int i = 0; i < ticks; i++) {
            createPeople();
        }
    }
}
