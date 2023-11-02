import java.util.Comparator;

public class UpComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        // Custom comparison logic for the upQueue (min-heap)
        return Integer.compare(p1.getDestFloor(), p2.getDestFloor());
    }
}
