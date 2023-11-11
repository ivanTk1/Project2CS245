import java.util.*;

public class ElevatorSimulation {
    static int floorCount = 32;
    static double passengerAppears = 0.002;
    static int numberOfElevators = 1;
    static int elevatorCapacity = 10;
    static int ticks = 100;
    static int count = 0;

    static Queue<Person>[] floorsQueues;

    public static void createPeople() {
        Random random = new Random();

        for (int i = 0; i < floorCount; i++) {
            double randomValue = random.nextDouble();
            if (randomValue < passengerAppears) {
                int destFloor = random.nextInt(floorCount);
                if (destFloor >= i) {
                    destFloor++;
                }
                Person passenger = new Person("passenger" + count, i, destFloor, true);
                System.out.println(passenger.getName() + " is waiting on floor " + passenger.getCurFloor() + " going to " + passenger.getDestFloor());
                count++;
                // Adds passengers to the correct direction
                if (passenger.getCurFloor() > passenger.getDestFloor()) {
                    floorsQueues[i * 2 + 1].offer(passenger); // Change index for the down direction
                } else if (passenger.getCurFloor() < passenger.getDestFloor()) {
                    floorsQueues[i * 2].offer(passenger); // Change index for the up direction
                } else {
                    System.out.println(passenger.toString());
                    System.out.println("SOMETHING WENT WRONG ADDING PASSENGERS TO QUEUES");
                    System.exit(0);
                }
            }
        }
    }

    public static void locateNewPassenger(Elevator elevator) {
        int currentFloor = elevator.getCurFloor();
        Person closestPassenger = null;
        int closestDistance = Integer.MAX_VALUE;

        for (int floor = 0; floor < floorCount; floor++) {
            if (!floorsQueues[floor * 2 + 1].isEmpty()) {
                Person passenger = floorsQueues[floor * 2 + 1].peek();
                if (passenger.isWaiting()) {
                    int distance = Math.abs(currentFloor - passenger.getCurFloor());
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestPassenger = passenger;
                    }
                }
            }
            if (!floorsQueues[floor * 2].isEmpty()) {
                Person passenger = floorsQueues[floor * 2].peek();
                if (passenger.isWaiting()) {
                    int distance = Math.abs(currentFloor - passenger.getCurFloor());
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestPassenger = passenger;
                    }
                }
            }
        }

        if (closestPassenger != null) {
            closestPassenger.setWaiting(false);
            elevator.setDestFloor(closestPassenger.getCurFloor());
            
            System.out.println(elevator.getName() + " is empty and going to pick up " + closestPassenger.getName() + " on floor " + elevator.getDestFloor());
        }
    }

    public static void dropOff(Elevator elevator) {
        // drop off going up
        if (elevator.peekNextPersonInUpHeap() != null) {
            if (elevator.peekNextPersonInUpHeap().getDestFloor() == elevator.getCurFloor()) {
                System.out.println(elevator.getName() + " just dropped off " + elevator.peekNextPersonInUpHeap().getName());
                elevator.removePassenger();
            }
        }
        // drop off going down
        if (elevator.peekNextPersonInDownHeap() != null) {
            if (elevator.peekNextPersonInDownHeap().getDestFloor() == elevator.getCurFloor()) {
                System.out.println(elevator.getName() + " just dropped off " + elevator.peekNextPersonInDownHeap().getName());
                elevator.removePassenger();
            }
        }
    }

    public static void pickUp(Elevator elevator) {
        if (elevator.isUpOrDown() || elevator.isEmpty()) { // going up
            //System.out.println("IS EMPTY 1 " + elevator.isEmpty());

            if (!floorsQueues[elevator.getCurFloor() * 2].isEmpty()) {
                while (elevator.getPassengerCount() < elevatorCapacity && !floorsQueues[elevator.getCurFloor() * 2].isEmpty()) {
                    Person person = floorsQueues[elevator.getCurFloor() * 2].poll();
                    System.out.println(elevator.getName() + " picked up " + person.getName());
                    elevator.addPassenger(person);
                }
                //elevator.printPassengers();
            }
        } 
         if (!elevator.isUpOrDown() || elevator.isEmpty()) { // going down
            // System.out.println("IS EMPTY 2 " + elevator.isEmpty());

            if (!floorsQueues[elevator.getCurFloor() * 2 + 1].isEmpty()) {

                while (elevator.getPassengerCount() < elevatorCapacity && !floorsQueues[elevator.getCurFloor() * 2 + 1].isEmpty()) {
                    Person person = floorsQueues[elevator.getCurFloor() * 2 + 1].poll();
                    System.out.println(elevator.getName() + " picked up " + person.getName());
                    elevator.addPassenger(person);
                }
            }
          //  elevator.printPassengers();
        }
    
      //  System.out.println("DEST FLOOR = " + elevator.getDestFloor());
      //  System.out.println(" IS UP OR DOWN " + elevator.isUpOrDown());
      //  System.out.println(" IS IN ACTION " + elevator.inAction());
      //  elevator.printPassengers();
    }

    public static void main(String[] args) {
        // makes all the "floors" even is up/odd is down
        floorsQueues = new Queue[floorCount * 2];

        for (int i = 0; i < floorCount * 2; i++) {
            floorsQueues[i] = new LinkedList<>();
        }

        List<Elevator> elevators = new ArrayList<>();

        for (int j = 0; j < numberOfElevators; j++) {
            Elevator elevator = new Elevator("elevator" + j, 0, 0, true, elevatorCapacity);
            elevators.add(elevator);
        }

        for (int i = 0; i < ticks; i++) {
            createPeople();

            for (Elevator elevator : elevators) {

                pickUp(elevator);
                dropOff(elevator);
                if (!elevator.inAction()) {
                    //System.out.println(elevator.isEmpty());
                    locateNewPassenger(elevator);
                    if(elevator.getDestFloor()>elevator.getCurFloor()){
                        elevator.updateDirection(true);
                    }
                    if(elevator.getDestFloor()<elevator.getCurFloor()){
                        elevator.updateDirection(false);
                    }
                }
                //elevator.printPassengers();
                if (elevator.inAction()) {
                    if (elevator.isUpOrDown() && elevator.getCurFloor() < floorCount - 1) {
                        elevator.setCurFloor(elevator.getCurFloor() + 1);
                    } else if (!elevator.isUpOrDown() && elevator.getCurFloor() > 0) {
                        elevator.setCurFloor(elevator.getCurFloor() - 1);
                    }
                    
                }
                System.out.println(elevator.getName() + " is on floor " + elevator.getCurFloor());
            }
        }
    }
}
