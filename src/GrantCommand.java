public abstract class GrantCommand extends EditUserCommand {
	public GrantCommand(String user, String base) {
		super(user, base);
	}

	@Override
	protected void executeOnUserNotFound(UserNotFoundException e) throws MultipleExceptionsException {
		MultipleExceptionsException up = new MultipleExceptionsException();
		up.addException(e);
		throw up;
	}
}
