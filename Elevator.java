import java.util.Comparator;
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
    private int maxCapacity; // New field for maximum capacity

    // Initialize goingUp and goingDown at the class level

    public Elevator(String name, int curFloor, int destFloor, boolean upOrDown, int maxCapacity) {
        this.name = name;
        this.curFloor = curFloor;
        this.destFloor = destFloor;
        this.upOrDown = upOrDown;
        this.maxCapacity = maxCapacity;
    }
    public void updateDirection(boolean newDirection) {
        upOrDown = newDirection;
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
        if (passengerCount < maxCapacity) {
            if (passenger.getDestFloor() > passenger.getCurFloor()) {
                minHeapGoingUp.add(passenger);
                if (minHeapGoingUp.peek().getDestFloor() > destFloor) {
                    destFloor = minHeapGoingUp.peek().getDestFloor();
                }
            } else {
                maxHeapGoingDown.add(passenger);
                if (maxHeapGoingDown.peek().getDestFloor() < destFloor) {
                    destFloor = maxHeapGoingDown.peek().getDestFloor();
                }
            }
            updateDirection(); // Update the direction after adding a passenger
            isEmpty = false;
            passengerCount++;
        } else {
            System.out.println("Elevator is at maximum capacity. Cannot add more passengers.");
        }
    }


    public boolean isAtMaxCapacity() {
        return passengerCount >= maxCapacity;
    }

    public void printPassengers() {
        System.out.println("Passengers in " + name + ":");

        if (upOrDown) {
            System.out.println("Going Up:");
            for (Person passenger : minHeapGoingUp) {
                System.out.println(passenger);
            }
        } else {
            System.out.println("Going Down:");
            for (Person passenger : maxHeapGoingDown) {
                System.out.println(passenger);
            }
        }

        System.out.println("Total passengers: " + passengerCount);
    }

    public Person removePassenger() {
        Person nextPassenger = null;
        if (upOrDown) {
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
            updateDestinationAfterRemoval();
            passengerCount--;
        }
    
        return nextPassenger;
    }
    
    private void updateDestinationAfterRemoval() {
        if (upOrDown) {
            if (!minHeapGoingUp.isEmpty()) {
                destFloor = minHeapGoingUp.peek().getDestFloor();
            }
        } else {
            if (!maxHeapGoingDown.isEmpty()) {
                destFloor = maxHeapGoingDown.peek().getDestFloor();
            }
        }
        updateDirection(); // Update the direction after removing a passenger
    }
    

    private void updateDirection() {
        if (!minHeapGoingUp.isEmpty() && (maxHeapGoingDown.isEmpty() || minHeapGoingUp.peek().getDestFloor() <= maxHeapGoingDown.peek().getCurFloor())) {
            upOrDown = true; // Set direction to going up
        } else if (!maxHeapGoingDown.isEmpty() && (minHeapGoingUp.isEmpty() || maxHeapGoingDown.peek().getDestFloor() >= minHeapGoingUp.peek().getCurFloor())) {
            upOrDown = false; // Set direction to going down
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
        // Check if the elevator is empty before updating the destination floor
        if (isEmpty) {
            this.destFloor = destFloor;
        } else {
            System.out.println("Cannot update destination floor while the elevator has passengers.");
        }
    }

    public boolean isEmpty() {
        return isEmpty;
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
