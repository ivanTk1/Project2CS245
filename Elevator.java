import java.util.PriorityQueue;

public class Elevator {
    private int floorCount;
    private int elevatorCapacity;
    private int numberOfElevators;
    private PriorityQueue<Person> upQueue;
    private PriorityQueue<Person> downQueue;

    public Elevator(int floorCount, int elevatorCapacity, int numberOfElevators) {
        this.floorCount = floorCount;
        this.elevatorCapacity = elevatorCapacity;
        this.numberOfElevators = numberOfElevators;
    }

    public void addPerson(Person person) {
        // Logic to add a person to the appropriate queue
    }

    public Person getNextPerson(int currentFloor, boolean goingUp) {
        // Logic to get the next person based on direction
        return null; // Replace with actual logic
    }

    // Other methods and logic for elevator operation
}
