import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.List;

class Building {
    private Elevator[] elevators;
    private List<Person> people;
    private double passengerProbability;

    public Building(int numFloors, int numElevators, int elevatorCapacity, int ticks, double passengerProbability) {
        elevators = new Elevator[numElevators];
        for (int i = 0; i < numElevators; i++) {
            elevators[i] = new Elevator(elevatorCapacity);
        }

        people = new LinkedList<>();
        this.passengerProbability = passengerProbability;
        // Initialize people on random floors
        Random random = new Random();
        for (int i = 0; i < numFloors; i++) {
            if (random.nextDouble() < passengerProbability) {
                int destination = random.nextInt(numFloors) + 1;
                people.add(new Person("Person" + i, i + 1, destination, true));
            }
        }
    }


    public void simulateTick() {
        // Randomly generate passengers on each floor
        Random random = new Random();
        for (int floor = 1; floor <= 32; floor++) {
            if (random.nextDouble() < 0.03) {
                int destination = random.nextInt(32) + 1;
                int elevatorIndex = random.nextInt(elevators.length);
                if (elevators[elevatorIndex].hasSpace()) {
                    elevators[elevatorIndex].addPassenger(destination);
                }
            }
        }

        // Move elevators
        for (Elevator elevator : elevators) {
            elevator.move();
        }
    }

    public void requestElevator(int floor, int destination) {
        int closestElevatorIndex = 0;
        int minDistance = Math.abs(elevators[0].getCurrentFloor() - floor);

        for (int i = 1; i < elevators.length; i++) {
            int distance = Math.abs(elevators[i].getCurrentFloor() - floor);
            if (distance < minDistance) {
                closestElevatorIndex = i;
                minDistance = distance;
            }
        }

        elevators[closestElevatorIndex].setDestination(destination);
    }
}