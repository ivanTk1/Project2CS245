import java.io.FileInputStream;
import java.io.IOException;
import java.util.*; //time is by ticks

public class ElevatorSimulation {
    static int floorCount = 32;
    static double passengerAppears = 0.03;
    static int numberOfElevators = 1;
    static int elevatorCapacity = 10;
    static int ticks = 5;
    static String structures = "linked";

    static int count = 0;

    static Queue<Person>[] floorsQueues;

    static Map<String, Long> passengerCreationTimes = new HashMap<>();
    static List<Long> timeTakenList = new ArrayList<>();

    /**
     * Loads configuration properties from a file. If the file is not found or an error occurs
     * during loading, default values are used.
     */
    public static void loadProperties() {
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            // Load the properties file
            prop.load(input);

            // Read and update the values
            floorCount = Integer.parseInt(prop.getProperty("floorCount", String.valueOf(floorCount)));
            passengerAppears = Double.parseDouble(prop.getProperty("passengerAppears", String.valueOf(passengerAppears)));
            numberOfElevators = Integer.parseInt(prop.getProperty("numberOfElevators", String.valueOf(numberOfElevators)));
            elevatorCapacity = Integer.parseInt(prop.getProperty("elevatorCapacity", String.valueOf(elevatorCapacity)));
            ticks = Integer.parseInt(prop.getProperty("ticks", String.valueOf(ticks)));
            structures = prop.getProperty("structures", structures);
        } catch (IOException ex) {
            System.out.println("Error loading properties. Using default values.");
        }
    }

    /**
     * Creates people randomly based on the configured passenger appearance probability.
     * For each floor, a random number is generated, and if it is less than the configured
     * probability, a passenger is created with a random destination floor.
     */
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
                passengerCreationTimes.put(passenger.getName(), System.currentTimeMillis());

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
    /**
     * Locates the nearest waiting passenger for the given elevator. It iterates through
     * all floors, considering both up and down queues, to find the closest waiting passenger.
     * The elevator's destination floor and direction are updated accordingly.
     *
     * @param elevator The elevator to locate a new passenger for.
     */
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
          //  System.out.println(closestPassenger.getCurFloor());

            elevator.setDestFloor(closestPassenger.getCurFloor());
        
            System.out.println(elevator.getName() + " is empty and going to pick up " + closestPassenger.getName() + " on floor " + elevator.getDestFloor());
        
            if (elevator.getDestFloor() > elevator.getCurFloor()) {
                elevator.updateNewDirection(2);
            } else if (elevator.getDestFloor() < elevator.getCurFloor()) {
                elevator.updateNewDirection(-2);
            }
        }
    }
    /**
     * Handles dropping off passengers at their destination floors. It checks both up and down
     * heaps in the elevator to see if any passengers have reached their destination floor,
     * removing them from the elevator. Additionally, it records the time taken for each passenger.
     *
     * @param elevator The elevator to drop off passengers.
     */
    public static void dropOff(Elevator elevator) {
        // drop off going up
        if (elevator.peekNextPersonInUpHeap() != null) {
            while (elevator.peekNextPersonInUpHeap() != null && elevator.peekNextPersonInUpHeap().getDestFloor() == elevator.getCurFloor()) {
                System.out.println(elevator.getName() + " just dropped off " + elevator.peekNextPersonInUpHeap().getName());
                elevator.removePassengers();
            }
        }
        // drop off going down
        if (elevator.peekNextPersonInDownHeap() != null) {
            while (elevator.peekNextPersonInDownHeap() != null && elevator.peekNextPersonInDownHeap().getDestFloor() == elevator.getCurFloor()) {
                System.out.println(elevator.getName() + " just dropped off " + elevator.peekNextPersonInDownHeap().getName());
                elevator.removePassengers();
            }
        }

        for (Person passenger : elevator.getPassengers()) {
            long creationTime = passengerCreationTimes.get(passenger.getName());
            long currentTime = System.currentTimeMillis();
            long timeTaken = currentTime - creationTime;
            //System.out.println(passenger.getName() + " took " + timeTaken + " milliseconds to reach the destination.");
            timeTakenList.add(timeTaken);  // Add the time taken to the list for later calculation
        }

    }
     /**
     * Handles picking up passengers waiting on the current floor. It checks the elevator's
     * direction and corresponding floor queue to pick up passengers, adding them to the
     * elevator and marking them as not waiting.
     *
     * @param elevator The elevator to pick up passengers.
     */
    public static void pickUp(Elevator elevator) {
        int action = elevator.isAction();
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
    
    /**
     * The main method that runs the elevator simulation. It initializes elevator queues,
     * creates elevator instances, and simulates passenger creation, movement, and actions
     * for a specified number of ticks. Finally, it calculates and prints statistics on
     * passenger wait times.
     *
     * @param args Command-line arguments (not used in this simulation).
     */
    public static void main(String[] args) {
        loadProperties();
    
        // makes all the "floors" even is up/odd is down
        floorsQueues = new Queue[floorCount * 2];
    
        if ("linked".equals(structures)) {
            for (int i = 0; i < floorCount * 2; i++) {
                floorsQueues[i] = new LinkedList<>();
            }
        } else if ("array".equals(structures)) {
            for (int i = 0; i < floorCount * 2; i++) {
                floorsQueues[i] = new ArrayDeque<>();
            }
        }
    
        List<Elevator> elevators = new ArrayList<>();
    
        for (int j = 0; j < numberOfElevators; j++) {
            Elevator elevator = new Elevator("elevator" + j, 0, 0, 0, elevatorCapacity);
            elevators.add(elevator);
        }
    
        // Person person1 = new Person("passenger1", 3, 9, true);
        // Person person2 = new Person("passenger2", 4, 9, true);
        // Person person3 = new Person("passenger2", 5, 9, true);
    
        // Add the created people to the queues
        // floorsQueues[3 * 2].offer(person1);  
        // floorsQueues[4 * 2].offer(person2);  
        // floorsQueues[5 * 2].offer(person3); 
    
        for (int i = 0; i < ticks; i++) {
            createPeople();
            for (int j = 0; j < 5; j++) {
                for (Elevator elevator : elevators) {
                    dropOff(elevator);
                    pickUp(elevator);
                    if (!elevator.inAction()) {
                        locateNewPassenger(elevator);
    
                        if (elevator.getDestFloor() > elevator.getCurFloor()) {
                            elevator.updateNewDirection(2);
                        } else if (elevator.getDestFloor() < elevator.getCurFloor()) {
                            elevator.updateNewDirection(-2);
                        } else {
                            elevator.setAction(0);
                        }
                    }

                    // -2 = going down to pick up
                    // -1 = going down to drop off
                    //  0 = doing nothing (waiting got passanger)
                    //  1 = going up to drop off
                    //  2 = going up to pick up

                    if (elevator.inAction()) {
                        // Update position only when the elevator is in action and not already at the destination
                        if (elevator.isAction() == 1 || elevator.isAction() == 2) {
                            elevator.setCurFloor(elevator.getCurFloor() + 1);
                        } else if (elevator.isAction() == -1 || elevator.isAction() == -2) {
                            elevator.setCurFloor(elevator.getCurFloor() - 1);
                        }
                    }
                    System.out.println(elevator.getName() + " is on floor " + elevator.getCurFloor());

                }
            }
        }
        long total = 0;
        for (Long timeTaken : timeTakenList) {
            total += timeTaken;
        }
        double averageTime = (double) total / timeTakenList.size();
        System.out.println("Average time taken by passengers: " + averageTime + " milliseconds");
    
        // Find and print the longest and shortest time taken by passengers
        if (!timeTakenList.isEmpty()) {
            long longestTime = Collections.max(timeTakenList);
            long shortestTime = Collections.min(timeTakenList);
            System.out.println("Longest time taken by a passenger: " + longestTime + " milliseconds");
            System.out.println("Shortest time taken by a passenger: " + shortestTime + " milliseconds");
        } else {
            System.out.println("No passengers in the simulation.");
        }
    }
    
}