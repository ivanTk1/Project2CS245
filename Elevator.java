import java.util.LinkedList;
import java.util.Queue;

class Elevator {
    private int currentFloor;
    private int destinationFloor;
    private boolean isMovingUp;
    private Queue<Integer> passengers;

    public Elevator() {
        currentFloor = 1;
        destinationFloor = 1;
        isMovingUp = true;
        passengers = new LinkedList<>();
    }

    public int getCurrentFloor() {
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

     public void addPassenger(int destination) {
        if (passengers.size() < 10) {
            passengers.offer(destination);
        }
    }

    public void setDestination(int floor) {
        destinationFloor = floor;
        isMovingUp = floor > currentFloor;
    }

    public boolean hasSpace() {
        return passengers.size() < 10;
    }
}
