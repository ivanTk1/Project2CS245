import java.util.*; 
import java.io.*; 
public class Elevator {

    public void passangerApears() {
        
        


        
    }


    public static void main(String[] args) throws IOException{
        
        Person person1 = new Person("Bob", 11, 25, true);

        int floorCount = 32;
        double passangerApears = 0.03;
        int numberOfElevators = 1;
        int elevatorCapacity = 10;
        int ticks = 500;

        Queue<Integer>[] floors = new Queue[floorCount];
        for (int i = 0; i < floorCount; i++) {
            floors[i] = new LinkedList<>();
        }
        floors[0].offer(1);


        for(int i = 0; i < ticks; i++){

        }




    }
    


}
