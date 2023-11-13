class Person {
    private String name;
    private int curFloor;
    private int destFloor;
    private boolean waiting;
    private long startTime; 

    public Person(String name, int curFloor, int destFloor, boolean waiting) {
        this.name = name;
        this.curFloor = curFloor;
        this.destFloor = destFloor;
        this.waiting = waiting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurFloor() {
        return curFloor;
    }

    public void setCurFloor(int curFloor) {
        this.curFloor = curFloor;
    }

    public int getDestFloor() {
        return destFloor;
    }

    public void setDestFloor(int destFloor) {
        this.destFloor = destFloor;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", curFloor=" + curFloor +
                ", destFloor=" + destFloor +
                ", waiting=" + waiting +
                '}';
    }
}