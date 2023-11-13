import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Elevator {
    private String name;
    private int curFloor;
    private int destFloor;
    private boolean isEmpty = true;
    private int action;
    private PriorityQueue<Person> minHeapGoingUp = new PriorityQueue<>(Comparator.comparing(Person::getDestFloor));
    private PriorityQueue<Person> maxHeapGoingDown = new PriorityQueue<>(Comparator.comparing(Person::getDestFloor).reversed());

    private int passengerCount = 0;
    private int maxCapacity; // New field for maximum capacity

    // Initialize goingUp and goingDown at the class level

    public Elevator(String name, int curFloor, int destFloor, int  action, int maxCapacity) {
        this.name = name;
        this.curFloor = curFloor;
        this.destFloor = destFloor;
        this.action = action;
        this.maxCapacity = maxCapacity;
    }
    public void updateDirection(int newDirection) {
        action = newDirection;
    }

    public Person peekNextPersonInUpHeap() {
        if (action == 1) {
            return minHeapGoingUp.peek();
        }
        return null; // The elevator is not going up
    }

    public Person peekNextPersonInDownHeap() {
        if (action == -1) {
            return maxHeapGoingDown.peek();
        }
        return null; // The elevator is not going down
    }

    public boolean inAction() {
        if(action == 0){
            return false;
        }
        return true;
    }
    

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
    


    public boolean isAtMaxCapacity() {
        return passengerCount >= maxCapacity;
    }

    public void printPassengers() {
        System.out.println("Passengers in " + name + ":");

        if (action == 1 || action == 2) {
            System.out.println("Going Up:");
            for (Person passenger : minHeapGoingUp) {
                System.out.println(passenger);
            }
        } else if(action == -1 || action == -2) {
            System.out.println("Going Down:");
            for (Person passenger : maxHeapGoingDown) {
                System.out.println(passenger);
            }
        }

        System.out.println("Total passengers: " + passengerCount);
    }

    public List<Person> getPassengers() {
        List<Person> passengers = new ArrayList<>();

        if (action == 1 || action == 2) {
            passengers.addAll(minHeapGoingUp);
        } else if (action == -1 || action == -2) {
            passengers.addAll(maxHeapGoingDown);
        }

        return passengers;
    }


    public Person removePassenger() {
        Person nextPassenger = null;
        if (action == 1) {
            if (!minHeapGoingUp.isEmpty()) {
                nextPassenger = minHeapGoingUp.poll();
                if (minHeapGoingUp.isEmpty()) {
                    isEmpty = true;
                }
            }
        } else {
            if (!maxHeapGoingDown.isEmpty()) {
                nextPassenger = maxHeapGoingDown.poll();
                if (maxHeapGoingDown.isEmpty()) {
                    isEmpty = true;
                }
            }
        }

        if (nextPassenger != null) {
            destFloor = nextPassenger.getDestFloor();
            if (passengerCount > 0) {
                passengerCount--;
            }
            updateDirection(); // Update the direction after removing a passenger
        }
    
        return nextPassenger;
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
