import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.List;

public class ElevatorSimulation {
    public static int NUM_FLOORS = 32;
    public static double PASSENGER_PROBABILITY = 0.03;
    public static int NUM_ELEVATORS = 3;
    public static int ELEVATOR_CAPACITY = 10;
    public static int TICKS = 100;

    public static void main(String[] args) {
        Building building = new Building(NUM_FLOORS, NUM_ELEVATORS, ELEVATOR_CAPACITY, TICKS, PASSENGER_PROBABILITY);
        int tick = 0;

        while (tick < TICKS) {
            building.simulateTick();
            // Request elevators here as needed

            // Print elevator states
            for (int i = 0; i < building.elevators.length; i++) {
                Elevator elevator = building.elevators[i];
                System.out.println("Elevator " + i + ": Current Floor - " + elevator.getCurrentFloor()
                        + ", Destination Floor - " + elevator.getDestinationFloor());
            }

            tick++;
        }
    }
}
