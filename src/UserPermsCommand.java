public class UserPermsCommand extends EditUserCommand {
	public UserPermsCommand(String user) {
		super(user, "uperm");
	}

	@Override
	protected void executeOnUserFound(User u) throws MultipleExceptionsException {
		FileSystem.getInstance().displayPerms(u);
	}

	@Override
	protected void executeOnUserNotFound(UserNotFoundException e) throws MultipleExceptionsException {
		MultipleExceptionsException up = new MultipleExceptionsException();
		up.addException(e);
		throw up;
	}
}
