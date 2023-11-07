import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Elevator {
    private String name;
    private int curFloor;
    private int destFloor;
    private boolean isEmpty = true;
    private static boolean upOrDown;
    private static PriorityQueue<Person> minHeapGoingUp = new PriorityQueue<>(Comparator.comparing(Person::getCurFloor));
    private static PriorityQueue<Person> maxHeapGoingDown = new PriorityQueue<>(Comparator.comparing(Person::getCurFloor).reversed());

    private int passengerCount = 0;

    // Initialize goingUp and goingDown at the class level
   
    public Elevator(String name, int curFloor, int destFloor, boolean upOrDown) {
        this.name = name;
        this.curFloor = curFloor;
        this.destFloor = destFloor;
        this.upOrDown = upOrDown;
    }

    public Person peekNextPersonInUpHeap() {
        if (upOrDown) {
            return minHeapGoingUp.peek();
        }
        return null; // The elevator is not going up
    }
    
    public Person peekNextPersonInDownHeap() {
        if (!upOrDown) {
            return maxHeapGoingDown.peek();
        }
        return null; // The elevator is not going down
    }
    

    public boolean inAction() {
        if (destFloor == curFloor && isEmpty) {
            return false;
        }
        return true;
    }    

    public void addPassenger(Person passenger) {
        if (upOrDown) {
            minHeapGoingUp.add(passenger);
        } else {
            maxHeapGoingDown.add(passenger);
        }
        isEmpty = false; // Elevator is not empty when passengers are added
        passengerCount++;
    }
    
    
    public Person getNextPassenger() {
        if (upOrDown) {
            if (!minHeapGoingUp.isEmpty()) {
                Person passenger = minHeapGoingUp.poll();
                if (minHeapGoingUp.isEmpty()) {
                    isEmpty = true; // Elevator is empty when no passengers are left
                }
                passengerCount--;
                return passenger;
            }
        } else {
            if (!maxHeapGoingDown.isEmpty()) {
                Person passenger = maxHeapGoingDown.poll();
                if (maxHeapGoingDown.isEmpty()) {
                    isEmpty = true; // Elevator is empty when no passengers are left
                }
                passengerCount--;
                return passenger;
            }
        }
        return null; // No passenger to pick up
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

    public static boolean isUpOrDown() {
        return upOrDown;
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
        this.destFloor = destFloor;
    }

    public void setUpOrDown(boolean upOrDown) {
        Elevator.upOrDown = upOrDown;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "name='" + name + '\'' +
                ", curFloor=" + curFloor +
                ", destFloor=" + destFloor +
                ", upOrDown=" + upOrDown + '}';
    }
}
