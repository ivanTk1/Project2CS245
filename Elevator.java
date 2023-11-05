import java.util.LinkedList;
import java.util.Queue;

class Elevator {
    private int currentFloor;
    private int destinationFloor;
    private boolean isMovingUp;
    private Queue<Person> passengers;
    private int capacity;

    public Elevator(int capacity) {
        currentFloor = 1;
        destinationFloor = 1;
        isMovingUp = true;
        passengers = new LinkedList<>();
        this.capacity = capacity;
    }


    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getDestinationFloor() {
        return currentFloor;
    }

    public void move() {
        if (isMovingUp) {
            if (currentFloor < 32) {
                currentFloor += 5;
            }
        } else {
            if (currentFloor > 1) {
                currentFloor -= 5;
            }
        }
        if (currentFloor == destinationFloor) {
            passengers.poll();
        }
    }

    public void addPassenger(Person person) {
        if (passengers.size() < getCapacity()) {
            passengers.offer(person);
        }
    }
    

    public void setDestination(int floor) {
        destinationFloor = floor;
        isMovingUp = floor > currentFloor;
    }

    public boolean hasSpace() {
        return passengers.size() < capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
