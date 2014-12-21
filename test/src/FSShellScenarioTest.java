import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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
		cmds.add("touch a b");
		cmds.add("dir");
		cmds.add("ls");
		cmds.add("sort owner");
		cmds.add("uperm root");
		cmds.add("rm a b");
		runScenario(cmds, "");
	}

	@Test(expected=MultipleExceptionsException.class)
	public void testScenario3() throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException {
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("undo");
		runScenario(cmds, "");
	}

	@Test(expected=MultipleExceptionsException.class)
	public void testScenario4() throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException {
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("redo");
		runScenario(cmds, "");
	}

	@Test(expected=MultipleExceptionsException.class)
	public void testScenario5() throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException {
		ArrayList<String> cmds = new ArrayList<String>();
		for (int i = 0; i < 10; i++) cmds.add("ls");
		cmds.add("touch file");
		cmds.add("undo");
		cmds.add("redo");
		cmds.add("redo");
		runScenario(cmds, "");
	}

	@Test
	public void testScenario6() throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException {
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("ls");
		cmds.add("touch file");
		cmds.add("history");
		runScenario(cmds, "");
	}

	@Test(expected=MultipleExceptionsException.class)
	public void testScenario7() throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException {
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("help");
		cmds.add("help history");
		cmds.add("help file");
		runScenario(cmds, "");
	}

	@Test(expected=MultipleExceptionsException.class)
	public void testScenario8() throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException {
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("mkuser test");
		runScenario(cmds, "a\nb\n");
	}

	@Test
	public void testScenario9() throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException {
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("mkuser test");
		cmds.add("lsusers");
		String actual = runScenario(cmds, "a\na\n").trim();
		String expected = "Password: Confirm password: test root";
		assertThat(actual, is(expected));
	}

	@Test(expected=MultipleExceptionsException.class)
	public void testScenario10() throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException {
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("passwd");
		runScenario(cmds, "a\nb\n");
	}

	@Test
	public void testScenario11() throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException, IOException, Exception {
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("touch file");
		cmds.add("mkdir test");
		cmds.add("ln file link");
		cmds.add("mkuser test");
		cmds.add("pgrant rw root test");
		cmds.add("ugrant gr test");
		runScenario(cmds, "a\na\n");

		File f = File.createTempFile("tmp_ser", "");
		f.deleteOnExit();
		Serializer cut = new Serializer(f.getAbsolutePath());
		cut.serialize();
		cut.deserialize();
	}

	@Test
	public void testScenario12() throws InvalidCommandException, MultipleExceptionsException {
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("touch file");
		cmds.add("touch file");
		try {runScenario(cmds, "a\na\n");} catch (Exception e) {}
		CommandFactory.getCommand("undo").execute();
	}

	private String runScenario(ArrayList<String> cmds, String input) throws InvalidCommandException, MultipleExceptionsException, UnsupportedEncodingException {
		ByteArrayOutputStream baos = setupConsole(input);
		setupCUTs();

		for (String cmd : cmds) {
			Command c = CommandFactory.getCommand(cmd);
			if (c.goesToHistory())
				CommandHistory.getInstance().push(c);
			c.executeCommand();
		}

		return baos.toString();
	}

	private void setupCUTs() {
		Console.resetConsole();
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
