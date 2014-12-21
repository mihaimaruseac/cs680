import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Directory extends FSElement {
	private ArrayList<FSElement> children;

	public Directory(String name, Directory parent, User owner) {
		super(name, parent, owner);
		children = new ArrayList<FSElement>();
	}

	private static Comparator<FSElement> getNameComparator() {
		return new Comparator<FSElement>() {
			public int compare(FSElement o1, FSElement o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
	}

	@Override
	public int getSize() {
		int size = 0;
		for (FSElement el : children)
			size += el.getSize();
		return size;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public boolean isLink() {
		return false;
	}

	public boolean isEmpty() {
		return children.size() == 0;
	}

	/* Keep sorted alphabetically */
	public void addChild(FSElement child) throws ElementExistsException {
		int ix = Collections.binarySearch(children, child, getNameComparator());

		if (ix >= 0)
			throw new ElementExistsException(child.getName());

		children.add(-(ix + 1), child);
	}

	public FSElement getElement(String name) throws InvalidPathException {
		int ix = Collections.binarySearch(children, new Directory(name, null, null), getNameComparator());

		if (ix < 0)
			throw new InvalidPathException(name);

		return children.get(ix);
	}

	public void remove(FSElement element) {
		children.remove(element);
	}

	public ArrayList<FSElement> getChildren() {
		return children;
	}
}
