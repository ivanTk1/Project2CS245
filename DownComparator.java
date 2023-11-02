import java.util.Comparator;

public class DownComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        // Custom comparison logic for the downQueue (max-heap)
        return Integer.compare(p2.getDestFloor(), p1.getDestFloor());
    }
}
