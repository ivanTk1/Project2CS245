import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;



public class Elevator {
    private String name;
    private int curFloor;
    private int destFloor;
    private static boolean upOrDown;
    private List<Person> passengerList;
    private Queue<Person> nextInLine;


    public Elevator(String name, int curFloor, int destFloor, boolean upOrDown, Person[] passengerList) {
        this.name = name;
        this.curFloor = curFloor;
        this.destFloor = destFloor;
        this.upOrDown = upOrDown;
        this.passengerList = new ArrayList<>();;
        this.nextInLine = new LinkedList<>();

    }

    public void enqueueString(Person value) {
        nextInLine.offer(value);
    }

    public Person dequeueString() {
        return nextInLine.poll();
    }

    public Person peekNextString() {
        return nextInLine.peek();
    }

    public int getQueueSize() {
        return nextInLine.size();
    }

    public void addPassenger(Person person) {
        passengerList.add(person);
    }

    public void removePassenger(Person person) {
        passengerList.remove(person);
    }

    public static List<Person> getPassengerList() {
        return passengerList;
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
        this.upOrDown = upOrDown;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "name='" + name + '\'' +
                ", curFloor=" + curFloor +
                ", destFloor=" + destFloor +
                ", upOrDown=" + upOrDown +
                ", passengerList=" + passengerList +
                ", nextInLine=" + nextInLine +
                '}';
    }
}
