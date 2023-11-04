import java.util.Comparator;

public class DownComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return Integer.compare(p2.getDestFloor(), p1.getDestFloor());
    }
}
