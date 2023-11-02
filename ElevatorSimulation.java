import java.util.*;
import java.io.*;

public class ElevatorSimulation {
    static int floorCount = 32;
    double passangerApears = 0.03;
    int numberOfElevators = 1;
    int elevatorCapacity = 10;
    static int ticks = 500;

    private static Queue<Person>[] floors;

    public void passangers() {
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

        for (int i = 0; i < ticks; i++) {
            
        }
    }
}
