import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class ShellTest {
	@Test
	public void testMainHelpExit() throws UnsupportedEncodingException {
		InputStream oldIn = System.in;
		PrintStream oldOut = System.out;

		InputStream in = new ByteArrayInputStream("help\nexit\n".getBytes("UTF-8"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);

		System.setIn(in);
		System.setOut(out);

		Shell.main(new String[0]);

		System.setIn(oldIn);
		System.setOut(oldOut);
	}

	@Test
	public void testMainNoInput() throws UnsupportedEncodingException {
		InputStream oldIn = System.in;
		PrintStream oldOut = System.out;

		InputStream in = new ByteArrayInputStream("".getBytes("UTF-8"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);

		System.setIn(in);
		System.setOut(out);

		Shell.main(new String[0]);

		System.setIn(oldIn);
		System.setOut(oldOut);

		String expected = "Welcome to MMShell. Type exit or press ^D to exit. Type help for help\nUser: root, current dir: root, type command below, help for help\n";
		String actual = baos.toString();
		assertThat(actual, is(expected));
	}

	@Test
	public void testMainPWD() throws UnsupportedEncodingException {
		InputStream oldIn = System.in;
		PrintStream oldOut = System.out;

		InputStream in = new ByteArrayInputStream("pwd".getBytes("UTF-8"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);

		System.setIn(in);
		System.setOut(out);

		Shell.main(new String[0]);

		System.setIn(oldIn);
		System.setOut(oldOut);

		String expected = "Welcome to MMShell. Type exit or press ^D to exit. Type help for help\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\n";
		String actual = baos.toString();
		assertThat(actual, is(expected));
	}

	@Test
	public void testMainEmpty() throws UnsupportedEncodingException {
		InputStream oldIn = System.in;
		PrintStream oldOut = System.out;

		InputStream in = new ByteArrayInputStream("\n".getBytes("UTF-8"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);

		System.setIn(in);
		System.setOut(out);

		Shell.main(new String[0]);

		System.setIn(oldIn);
		System.setOut(oldOut);

		String expected = "Welcome to MMShell. Type exit or press ^D to exit. Type help for help\nUser: root, current dir: root, type command below, help for help\nUser: root, current dir: root, type command below, help for help\n";
		String actual = baos.toString();
		assertThat(actual, is(expected));
	}

	@Test
	public void testMainInvalid() throws UnsupportedEncodingException {
		InputStream oldIn = System.in;
		PrintStream oldOut = System.out;

		InputStream in = new ByteArrayInputStream("stuff".getBytes("UTF-8"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);

		System.setIn(in);
		System.setOut(out);

		Shell.main(new String[0]);

		System.setIn(oldIn);
		System.setOut(oldOut);

		String expected = "Welcome to MMShell. Type exit or press ^D to exit. Type help for help\nUser: root, current dir: root, type command below, help for help\nInvalidCommandException: stuff: no such command\nUser: root, current dir: root, type command below, help for help\n";
		String actual = baos.toString();
		assertThat(actual, is(expected));
	}

	@Test
	public void testMainHistory() throws UnsupportedEncodingException {
		InputStream oldIn = System.in;
		PrintStream oldOut = System.out;

		InputStream in = new ByteArrayInputStream("pwd\nundo\nhistory\npwd\nredo".getBytes("UTF-8"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);

		System.setIn(in);
		System.setOut(out);

		Shell.main(new String[0]);

		System.setIn(oldIn);
		System.setOut(oldOut);

		String expected = "Welcome to MMShell. Type exit or press ^D to exit. Type help for help\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nInvalidCommandException: Command cannot be undone. Sorry\nUser: root, current dir: root, type command below, help for help\n [ ] pwd\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\n";
		String actual = baos.toString();
		assertThat(actual, is(expected));
	}

	@Test
	public void testMainLongHistory() throws UnsupportedEncodingException {
		InputStream oldIn = System.in;
		PrintStream oldOut = System.out;

		InputStream in = new ByteArrayInputStream("pwd\npwd\npwd\npwd\nmkdir test\nhistory\npwd\npwd\npwd\npwd\npwd\npwd\npwd\npwd\npwd\npwd\npwd\nredo\nredo\nundo\nhistory\npwd\nredo".getBytes("UTF-8"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);

		System.setIn(in);
		System.setOut(out);

		Shell.main(new String[0]);

		System.setIn(oldIn);
		System.setOut(oldOut);

		String expected = "Welcome to MMShell. Type exit or press ^D to exit. Type help for help\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nInvalidCommandException: Cannot create test : name exists in same directory.\nUser: root, current dir: root, type command below, help for help\n [ ] pwd\n [ ] pwd\n [ ] pwd\n [ ] pwd\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nInvalidCommandException: Command cannot be undone. Sorry\nUser: root, current dir: root, type command below, help for help\n [ ] pwd\n [ ] pwd\n [ ] pwd\n [ ] pwd\n [ ] pwd\n [ ] pwd\n [ ] pwd\n [ ] pwd\n [ ] redo\n [ ] redo\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\nroot\nUser: root, current dir: root, type command below, help for help\n";
		String actual = baos.toString();
		assertThat(actual, is(expected));
	}

	@Test
	public void testMainPrinting() throws UnsupportedEncodingException {
		InputStream oldIn = System.in;
		PrintStream oldOut = System.out;

		InputStream in = new ByteArrayInputStream("mkdir test\ncd test\ntouch file\ncd\nhistory\ntouch asdf\nrmdir asdf test\nundo".getBytes("UTF-8"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);

		System.setIn(in);
		System.setOut(out);

		Shell.main(new String[0]);

		System.setIn(oldIn);
		System.setOut(oldOut);

		String expected = "Welcome to MMShell. Type exit or press ^D to exit. Type help for help\nUser: root, current dir: root, type command below, help for help\nUser: root, current dir: root, type command below, help for help\nUser: root, current dir: test, type command below, help for help\nUser: root, current dir: test, type command below, help for help\nUser: root, current dir: root, type command below, help for help\n [U] mkdir test\n [U] cd test\n [U] touch file\n [U] cd\nUser: root, current dir: root, type command below, help for help\nUser: root, current dir: root, type command below, help for help\nInvalidCommandException: Cannot remove directory asdf : it is a file\nUser: root, current dir: root, type command below, help for help\nUser: root, current dir: root, type command below, help for help\n";
		String actual = baos.toString();
		assertThat(actual, is(expected));
	}

	@Test
	public void testOwnerPrinting() throws UnsupportedEncodingException {
		InputStream oldIn = System.in;
		PrintStream oldOut = System.out;

		InputStream in = new ByteArrayInputStream("mkdir test1 test2\nchown test2 test\ndir\nsort asdf test\nsort owner test\nsort\nsort owner .\nln test2 link\nls".getBytes("UTF-8"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);

		System.setIn(in);
		System.setOut(out);

		Shell.main(new String[0]);

		System.setIn(oldIn);
		System.setOut(oldOut);

		String expected = "Welcome to MMShell. Type exit or press ^D to exit. Type help for help\nUser: root, current dir: root, type command below, help for help\nUser: root, current dir: root, type command below, help for help\nInvalidCommandException: test : doesn't exist\nUser: root, current dir: root, type command below, help for help\ntest1/\t\t\troot\t\t0\ntest2/\t\t\troot\t\t0\nUser: root, current dir: root, type command below, help for help\nInvalidCommandException: test : doesn't exist\nUser: root, current dir: root, type command below, help for help\nInvalidCommandException: test : doesn't exist\nUser: root, current dir: root, type command below, help for help\nInvalidCommandException: Must have exactly two arguments: method and directory\nUser: root, current dir: root, type command below, help for help\ntest1/\t\t\troot\t\t0\ntest2/\t\t\troot\t\t0\nUser: root, current dir: root, type command below, help for help\njava.lang.StringIndexOutOfBoundsException: String index out of range: -1\nUser: root, current dir: root, type command below, help for help\ntest1/\ntest2/\nUser: root, current dir: root, type command below, help for help\n";
		String actual = baos.toString();
		assertThat(actual, is(expected));
	}
}
