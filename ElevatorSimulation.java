import java.util.*;
import java.io.*;

public class ElevatorSimulation {
    static int floorCount = 32;
    static double passengerAppears = 0.03;
    static int numberOfElevators = 1;
    int elevatorCapacity = 10;
    static int ticks = 500;
    static int count = 0;

    static Queue<Person>[] floorsQueues;

    public static void createPeople() {
        Random random = new Random();

        for (int i = 0; i < floorCount; i++) {
            double randomValue = random.nextDouble();
            if (randomValue < passengerAppears) {
                int destFloor = random.nextInt(floorCount);
                if(destFloor >= i){
                    destFloor++;
                }
                Person passenger = new Person("passenger" + count, i, destFloor, true);
                System.out.println(passenger.getName() + " is waiting on floor " + passenger.getCurFloor() + " going to " + passenger.getDestFloor());
                count++;
                // Adds passengers to the correct direction
                if (passenger.getCurFloor() > passenger.getDestFloor()) {
                    floorsQueues[i*2].offer(passenger);
                } else if (passenger.getCurFloor() < passenger.getDestFloor()) {
                    floorsQueues[(i*2)+1].offer(passenger);
                } else {
                    System.out.println(passenger.toString()); 
                    System.out.println("SOMETHING WENT WRONG ADDING PASSENGERS TO QUEUES");
                    System.exit(0);
                }
            }
        }
    }

    public static void locateNextPassenger(Elevator elevator) {
        int currentFloor = elevator.getCurFloor();
        Person closestPassenger = null;
        int closestDistance = Integer.MAX_VALUE;
    
        // Check both up and down queues starting from the current floor
        for (int floor = 0; floor < floorCount; floor++) {
            if (!floorsQueues[(floor * 2) + 1].isEmpty()) {
                Person passenger = floorsQueues[(floor * 2) + 1].peek();
                if (!passenger.isWaiting()) {
                    int distance = Math.abs(currentFloor - passenger.getCurFloor());
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestPassenger = passenger;
                    }
                }
            }
            if (!floorsQueues[floor * 2].isEmpty()) {
                Person passenger = floorsQueues[floor * 2].peek();
                if (!passenger.isWaiting()) {
                    int distance = Math.abs(currentFloor - passenger.getCurFloor());
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestPassenger = passenger;
                    }
                }
            }
        }
    
         closestPassenger.setWaiting(false);
         elevator.setDestFloor(closestPassenger.getCurFloor());
    }
    
    public void elevatorMovement(Elevator elevator){ // DELOAD PASSANGERS AS WELL

        if(elevator.getCurFloor() < elevator.getDestFloor()){ //going up
            if(elevator.peekNextPersonInUpHeap().getCurFloor() == elevator.getCurFloor()){
                elevator.getNextPassenger();
            }
            elevator.setUpOrDown(true);
            if(!floorsQueues[elevator.getCurFloor()*2].isEmpty()){
                while(elevator.getPassengerCount() < elevatorCapacity && !floorsQueues[elevator.getCurFloor()*2].isEmpty()){
                    Person person = floorsQueues[elevator.getCurFloor()*2].poll();
                    elevator.addPassenger(person);
                }
            }
            elevator.setCurFloor(elevator.getCurFloor()+1);

        }else if(elevator.getDestFloor() < elevator.getCurFloor()){ //going down
        if(elevator.peekNextPersonInDownHeap().getCurFloor() == elevator.getCurFloor()){
                elevator.getNextPassenger();
            }

            elevator.setUpOrDown(false);
            if(!floorsQueues[elevator.getCurFloor()*2+1].isEmpty()){
                while(elevator.getPassengerCount() < elevatorCapacity && !floorsQueues[elevator.getCurFloor()*2].isEmpty()){
                    Person person = floorsQueues[elevator.getCurFloor()*2+1].poll();
                    elevator.addPassenger(person);
                }
            }
            elevator.setCurFloor(elevator.getCurFloor()-1);
            
        }
    }
   
    public static void main(String[] args) {
        //makes all the "floors" even is up/odd is down
        Queue<Person>[] floorsQueues = new Queue[floorCount * 2];

        for (int i = 0; i < floorCount * 2; i++) {
            floorsQueues[i] = new LinkedList<>();
        }

        List<Elevator> elevators = new ArrayList<>();

        for (int j = 0; j < numberOfElevators; j++) {
            Elevator elevator = new Elevator("elevator" + j, 0, 0, true);
            elevators.add(elevator);
        }        

        for (int i = 0; i < ticks; i++) {
            createPeople();

            for (Elevator elevator : elevators) {


                if(!elevator.inAction()){
                    locateNextPassenger(elevator);
                }
            }


        }
    }
}
