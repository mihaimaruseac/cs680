import java.util.Comparator;

public class OwnerComparator implements Comparator<FSElement> {
	public OwnerComparator() {}

	public int compare(FSElement o1, FSElement o2) {
		return o1.getOwner().compareTo(o2.getOwner());
	}
}
