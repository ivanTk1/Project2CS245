import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Elevator {
    private String name;
    private int curFloor;
    private int destFloor;
    private boolean isEmpty = true;
    private static int action;
    //keep non static interfering with other elevators
    private PriorityQueue<Person> minHeapGoingUp = new PriorityQueue<>(Comparator.comparing(Person::getDestFloor));
    private PriorityQueue<Person> maxHeapGoingDown = new PriorityQueue<>(Comparator.comparing(Person::getDestFloor).reversed());

    private int passengerCount = 0;
    private int maxCapacity; 

    // Initialize goingUp and goingDown at the class level

    /**
     * Constructs an elevator with the given parameters.
     *
     * @param name        The name or identifier of the elevator.
     * @param curFloor    The current floor of the elevator.
     * @param destFloor   The destination floor of the elevator.
     * @param action      The current action or direction of the elevator.
     * @param maxCapacity The maximum capacity of the elevator.
     */
    public Elevator(String name, int curFloor, int destFloor, int  action, int maxCapacity) {
        this.name = name;
        this.curFloor = curFloor;
        this.destFloor = destFloor;
        this.action = action;
        this.maxCapacity = maxCapacity;
    }
     /**
     * Updates the direction of the elevator to the specified new direction.
     *
     * @param newDirection The new direction for the elevator.
     */
    public void updateNewDirection(int newDirection) {
        action = newDirection;
    }

     /**
     * Peeks at the next person in the up-heap (going up direction) without removing them.
     *
     * @return The next person in the up-heap, or null if the elevator is not going up.
     */
    public Person peekNextPersonInUpHeap() {
        if (action == 1) {
            return minHeapGoingUp.peek();
        }
        return null; // The elevator is not going up
    }

     /**
     * Peeks at the next person in the down-heap (going down direction) without removing them.
     *
     * @return The next person in the down-heap, or null if the elevator is not going down.
     */
    public Person peekNextPersonInDownHeap() {
        if (action == -1) {
            return maxHeapGoingDown.peek();
        }
        return null; // The elevator is not going down
    }
     /**
     * Checks if the elevator is currently in action (moving) or not.
     *
     * @return True if the elevator is in action, false otherwise.
     */
    public static boolean inAction() {
        if(action == 0){
            return false;
        }
        return true;
    }
    
    /**
     * Adds a passenger to the elevator. The passenger is added to the appropriate heap
     * (up-heap or down-heap) based on their destination floor. The direction of the elevator
     * is updated, and the passenger count is incremented.
     *
     * @param passenger The passenger to be added to the elevator.
     */
    public void addPassenger(Person passenger) {
        if (passengerCount < maxCapacity) {
            if (isEmpty) {
                // Update destFloor only if the elevator is empty
                destFloor = passenger.getDestFloor();
            }
            if (passenger.getDestFloor() > passenger.getCurFloor()) {
                minHeapGoingUp.add(passenger);
            } else {
                maxHeapGoingDown.add(passenger);
            }
            updateDirection(); // Update the direction after adding a passenger
            isEmpty = false;
            passengerCount++;
        } else {
            System.out.println("Elevator is at maximum capacity. Cannot add more passengers.");
            System.exit(0);
        }
    }
    
/**
     * Checks if the elevator is at its maximum passenger capacity.
     *
     * @return True if the elevator is at maximum capacity, false otherwise.
     */
    public boolean isAtMaxCapacity() {
        return passengerCount >= maxCapacity;
    }

    public void printPassengers() {
        System.out.println("Passengers in " + name + ":");

    
            System.out.println("Passangers:");
            for (Person passenger : minHeapGoingUp) {
                System.out.println(passenger);
            }
        

        System.out.println("Total passengers: " + passengerCount);
    }
    /**
     * Gets the list of passengers in the elevator.
     *
     * @return A list of passengers in the elevator.
     */
    public List<Person> getPassengers() {
        List<Person> passengers = new ArrayList<>();

        if (action == 1 || action == 2) {
            passengers.addAll(minHeapGoingUp);
        } else if (action == -1 || action == -2) {
            passengers.addAll(maxHeapGoingDown);
        }

        return passengers;
    }

   /**
 * Removes passengers from the elevator who want to get off at the current floor.
 * The destination floor is updated, and the direction is updated accordingly.
 *
 * @return The list of removed passengers, or an empty list if no passengers are removed.
 */
public List<Person> removePassengers() {
    List<Person> removedPassengers = new ArrayList<>();

    if (action == 1) {
        while (!minHeapGoingUp.isEmpty() && minHeapGoingUp.peek().getDestFloor() == curFloor) {
            Person nextPassenger = minHeapGoingUp.poll();
            removedPassengers.add(nextPassenger);
            if (minHeapGoingUp.isEmpty()) {
                isEmpty = true;
            }
        }
    } else if (action == -1) {
        while (!maxHeapGoingDown.isEmpty() && maxHeapGoingDown.peek().getDestFloor() == curFloor) {
            Person nextPassenger = maxHeapGoingDown.poll();
            removedPassengers.add(nextPassenger);
            if (maxHeapGoingDown.isEmpty()) {
                isEmpty = true;
            }
        }
    } else {
        System.out.println("Error removing passengers");
        System.exit(0);
    }

    if (!removedPassengers.isEmpty()) {
        destFloor = removedPassengers.get(removedPassengers.size() - 1).getDestFloor();
        passengerCount -= removedPassengers.size();
        updateDirection(); // Update the direction after removing passengers
    }

    return removedPassengers;
}

    private void updateDirection() {
        if (!minHeapGoingUp.isEmpty() && minHeapGoingUp.peek().getDestFloor() > curFloor) {
            action = 1; // Set direction to going up
        } else if (!maxHeapGoingDown.isEmpty() && maxHeapGoingDown.peek().getDestFloor() < curFloor) {
            action = -1; // Set direction to going down
        } else {
            // If both heaps are empty or no passenger is going in the current direction,
            // set the direction based on the destination floor of the next passenger
            action = 0;
        }
    }
    

    

    // Getters
    public String getName() {
        return name;
    }

    public int getCurFloor() {
        return curFloor;
    }

    public int getDestFloor() {
        return destFloor;
    }

    public int isAction() {
        return action;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCurFloor(int curFloor) {
        this.curFloor = curFloor;
    }

    public void setDestFloor(int destFloor) {
        // Check if the elevator is empty before updating the destination floor
        if (isEmpty) {
            this.destFloor = destFloor;
        }else{
            printPassengers();
            System.out.println("CAN NOT SET DEST FLOOR WITH PASSANGERS");
        }
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setAction(int action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "name='" + name + '\'' +
                ", curFloor=" + curFloor +
                ", destFloor=" + destFloor +
                ", upOrDown=" + action + '}';
    }
}
