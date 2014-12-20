public class UserPermsCommand extends EditUserCommand {
	public UserPermsCommand(String user) {
		super(user, "uperm");
	}

	@Override
	protected void executeOnUserFound(User u) throws MultipleExceptionsException {
		FileSystem.getInstance().displayPerms(u);
	}
}
