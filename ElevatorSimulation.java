import java.util.*; 

public class ElevatorSimulation {
    static int floorCount = 25;
    static double passengerAppears = 0.3;
    static int numberOfElevators = 4;
    static int elevatorCapacity = 10;
    static int ticks = 25;
    static int count = 0;

    static Queue<Person>[] floorsQueues;

    public static void createPeople() {
        Random random = new Random();

        for (int i = 0; i < floorCount; i++) {
            double randomValue = random.nextDouble();
            if (randomValue < passengerAppears) {
                int destFloor = random.nextInt(floorCount-1);
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
        
            if (elevator.getDestFloor() > elevator.getCurFloor()) {
                elevator.updateDirection(2);
            } else if (elevator.getDestFloor() < elevator.getCurFloor()) {
                elevator.updateDirection(-2);
            }
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
        int action = elevator.isAction();
       // System.out.println(action);
       if(!elevator.isAtMaxCapacity()){
            if (action == 1 || action == 2 || action == -2) { // Going up
                if (!floorsQueues[elevator.getCurFloor() * 2].isEmpty()) {
                    while (elevator.getPassengerCount() < elevatorCapacity && !floorsQueues[elevator.getCurFloor() * 2].isEmpty()) {
                        Person person = floorsQueues[elevator.getCurFloor() * 2].poll();
                        System.out.println(elevator.getName() + " picked up " + person.getName());
                        person.setWaiting(false);
                        elevator.addPassenger(person);
                    }
                }
            } 
            if (action == -1 || action == -2 || action == 2) { // Going down
                if (!floorsQueues[elevator.getCurFloor() * 2 + 1].isEmpty()) {
                    while (elevator.getPassengerCount() < elevatorCapacity && !floorsQueues[elevator.getCurFloor() * 2 + 1].isEmpty()) {
                        Person person = floorsQueues[elevator.getCurFloor() * 2 + 1].poll();
                        System.out.println(elevator.getName() + " picked up " + person.getName());
                        person.setWaiting(false);
                        elevator.addPassenger(person);
                    }
                }
            }
        }
    }
    

    
    public static void main(String[] args) {
        // makes all the "floors" even is up/odd is down
        floorsQueues = new Queue[floorCount * 2];

        for (int i = 0; i < floorCount * 2; i++) {
            floorsQueues[i] = new LinkedList<>();
        }

        List<Elevator> elevators = new ArrayList<>();

        for (int j = 0; j < numberOfElevators; j++) {
            Elevator elevator = new Elevator("elevator" + j, 0, 0, 0, elevatorCapacity);
            elevators.add(elevator);
        }

      //  Person person1 = new Person("passenger1", 3, 10, true);
       // Person person2 = new Person("passenger2", 4, 8, true);
       // Person person3 = new Person("passenger2", 9, 0, true);

        // Add the created people to the queues
       // floorsQueues[3 * 2].offer(person1);  // 
      //  floorsQueues[4 * 2].offer(person2);  // Add to the up 
      //  System.out.println("PROBLEM HERE");
       // floorsQueues[ 9* 2 + 1].offer(person3);  // Add to the up 





        for (int i = 0; i < ticks; i++) {
           createPeople();
            for(int j = 0; j < 5; j++){
                for (Elevator elevator : elevators) {
                    dropOff(elevator);
                    pickUp(elevator);
                  //  System.out.println(elevator.inAction());
                  //  System.out.println(elevator.getPassengerCount());
                    if (!elevator.inAction()) {
                       // System.out.println(elevator.getPassengerCount());
                        locateNewPassenger(elevator);
                
                        if (elevator.getDestFloor() > elevator.getCurFloor()) {
                            elevator.updateDirection(2);
                        } else if (elevator.getDestFloor() < elevator.getCurFloor()) {
                            elevator.updateDirection(-2);
                        }else{
                            elevator.setAction(0);
                        }
                    }
                    
                 System.out.println("ELEVATOR DEST FLOOR: " + elevator.getDestFloor());
                  elevator.printPassengers();
                    //if(elevator.getCurFloor() == floorCount && elevator.getPassengerCount() == 0){
                   //     elevator.setAction(0);
                   // }
                   // if(elevator.getCurFloor() == 0 && elevator.getPassengerCount() == 0){
                    //    elevator.setAction(0);
                   //
                // }
                    if (elevator.inAction()) {
                        // Update position only when the elevator is in action and not already at the destination
                        if (elevator.isAction() == 1 || elevator.isAction() == 2 ) {
                            elevator.setCurFloor(elevator.getCurFloor() + 1);
                        } else if (elevator.isAction() == -1 || elevator.isAction() == -2 ) {
                            elevator.setCurFloor(elevator.getCurFloor() - 1);
                        }
                        System.out.println(elevator.getName() + " is on floor " + elevator.getCurFloor());
                    }
                    
                
        }
    }
    }
    }
}
