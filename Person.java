public class Person {
    private String name;
    private int curFloor;
    private int destFloor;
    private boolean waiting;

    public Person(String name, int curFloor, int destFloor, boolean waiting) {
        this.name = name;
        this.curFloor = curFloor;
        this.destFloor = destFloor;
        this.waiting = waiting;
    }

    // Getter for 'name'
    public String getName() {
        return name;
    }

    // Setter for 'name'
    public void setName(String name) {
        this.name = name;
    }

    // Getter for 'curFloor'
    public int getCurFloor() {
        return curFloor;
    }

    // Setter for 'curFloor'
    public void setCurFloor(int curFloor) {
        this.curFloor = curFloor;
    }

    // Getter for 'destFloor'
    public int getDestFloor() {
        return destFloor;
    }

    // Setter for 'destFloor'
    public void setDestFloor(int destFloor) {
        this.destFloor = destFloor;
    }

    // Getter for 'waiting'
    public boolean isWaiting() {
        return waiting;
    }

    // Setter for 'waiting'
    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }
}
