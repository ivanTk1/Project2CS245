import java.util.*; 
import java.io.*; 
public class Elevator {










    public static void main(String[] args) throws IOException{
        FileReader reader = new FileReader("db.properties");         
        Properties p = new Properties(); 
        p.load(reader); 

        Person person1 = new Person("Bob", 11, 25, true);
        System.out.println(person1.getName() + " is going to " + person1.getDestFloor() + " from " + person1.getCurFloor());

        for(int i = 0; i < )




    }
    


}
