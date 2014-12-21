import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class FSShellScenarioTest {
	@Test
	public void testScenario1() throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException {
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("touch file");
		cmds.add("touch file2 file3");
		cmds.add("mkdir test test2");
		cmds.add("touch test2/file3");
		cmds.add("cd test2");
		cmds.add("rm file3");
		cmds.add("cd");
		cmds.add("pwd");
		cmds.add("exit");

		String output = runScenario(cmds, "");

		String expectedOutput = "root/\n";
		String actualOutput = output;
		assertThat(actualOutput, is(expectedOutput));

		Directory expectedDir = FileSystem.getInstance().getRoot();
		Directory actualDir = FileSystem.getInstance().getCurrent();
		assertThat(actualDir, is(expectedDir));

		Directory d = FileSystem.getInstance().getCurrent();
		int expectedElems = 5;
		int actualElems = d.getChildren().size();
		assertThat(actualElems, is(expectedElems));

		d = (Directory)FileSystem.getInstance().resolvePath("test2");
		expectedElems = 0;
		actualElems = d.getChildren().size();
		assertThat(actualElems, is(expectedElems));
	}

	@Test
	public void testScenario2() throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException {
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("lsusers");
		cmds.add("dir");
		cmds.add("ls");
		cmds.add("sort owner");
		cmds.add("uperm root");

		String expected = runScenario(cmds, "");
		String actual = "";
		assertThat(actual, is(expected));
	}

	private String runScenario(ArrayList<String> cmds, String input) throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException {
		InputStream oldIn = System.in;
		PrintStream oldOut = System.out;
		ByteArrayOutputStream baos = setupConsole(input);
		setupCUTs();

		for (String cmd : cmds) {
			Command c = CommandFactory.getCommand(cmd);
			if (c.goesToHistory())
				CommandHistory.getInstance().push(c);
			c.executeCommand();
		}

		System.setIn(oldIn);
		System.setOut(oldOut);
		return baos.toString();
	}

	private void setupCUTs() {
		ArrayList<User> users = new ArrayList<User>();
		User root = new User("root", "TODO");
		root.addPermission(UserPermissionType.PERMISSION_ROOT);
		users.add(root);

		FileSystem.getInstance().setUpUsers(users);
		FileSystem.getInstance().setUp();
		CommandHistory.getInstance().clear();
	}

	private ByteArrayOutputStream setupConsole(String input) throws UnsupportedEncodingException {

		InputStream in = new ByteArrayInputStream(input.getBytes("UTF-8"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);

		System.setIn(in);
		System.setOut(out);

		return baos;
	}
}
