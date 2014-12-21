import java.util.ArrayList;
import java.util.Arrays;

public abstract class TUIDisplay {
	private static final int TUI_TEXT_WIDTH = 90;

	public static void simpleDisplayText(String message) {
		Console.defaultConsole().printf(message + "\n");
	}

	public static void arrayDisplayText(String[] messages) {
		ArrayList<String> temp = new ArrayList<String>();
		for (String s : messages)
			temp.add(s);
		arrayDisplayText(temp);
	}

	public static void arrayDisplayText(ArrayList<String> messages) {
		int maxElementSize = maxElementSize(messages);
		int minElementSize = minElementSize(messages);
		int minColumns = TUI_TEXT_WIDTH / (1 + maxElementSize);
		int maxColumns = TUI_TEXT_WIDTH / (1 + minElementSize);
		ArrayList<ArrayList<String>> columns;

		int colGuess = (minColumns + 4 * maxColumns) / 5;

		if (colGuess >= messages.size())
			colGuess = messages.size();

		if (colGuess <= 0)
			colGuess = 1;

		columns = splitInColumns(messages, colGuess);
		while (colGuess > 1) {
			int sizeTaken = sizeTakenByColumns(columns);

			if (sizeTaken <= TUI_TEXT_WIDTH)
				break;

			colGuess--;
			columns = splitInColumns(messages, colGuess);
		}

		columnDisplayText(columns);
	}

	public static void columnDisplayText(ArrayList<ArrayList<String>> columns) {
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		int rows = padColumns(columns);

		for (ArrayList<String> column : columns)
			sizes.add(maxElementSize(column) + 1);

		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < columns.size(); i++) {
				String toDisplay = columns.get(i).get(j);
				int spaceToDisplay = sizes.get(i);
				int spaceTaken = toDisplay.length();
				int spaceRemaining = spaceToDisplay - spaceTaken;
				char[] bytes = new char[spaceRemaining];
				Arrays.fill(bytes, ' ');
				String padding = new String(bytes);
				Console.defaultConsole().printf(toDisplay + padding);
			}
			Console.defaultConsole().printf("\n");
		}
	}

	private static int maxElementSize(ArrayList<String> messages) {
		int maxSize = 0;

		for (String s : messages) {
			if (maxSize < s.length())
				maxSize = s.length();
		}

		return maxSize;
	}

	private static int minElementSize(ArrayList<String> messages) {
		int minSize = TUI_TEXT_WIDTH;

		for (String s : messages) {
			if (minSize > s.length())
				minSize = s.length();
		}

		return minSize;
	}

	private static ArrayList<ArrayList<String>> splitInColumns(
			ArrayList<String> messages, int colGuess) {
		ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < colGuess; i++)
			ret.add(new ArrayList<String>());

		int currentColumn = 0;

		for (String s : messages) {
			ret.get(currentColumn++).add(s);
			if (currentColumn == colGuess)
				currentColumn = 0;
		}

		return ret;
	}

	private static int sizeTakenByColumns(ArrayList<ArrayList<String>> columns) {
		int size = 0;

		for (ArrayList<String> column : columns)
			size += maxElementSize(column) + 1;

		return size;
	}

	private static int padColumns(ArrayList<ArrayList<String>> columns) {
		int rows = columns.get(0).size();

		for (ArrayList<String> column : columns)
			if (column.size() != rows)
				column.add("");

		return rows;
	}
}
