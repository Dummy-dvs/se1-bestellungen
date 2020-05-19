package system;

public class Builder implements  ComponentLifecycle {
	private static final Builder instance = new Builder();
	private final Logic logic;
	private final DataAccess dataAccess;
	/*
	 * Private constructor to prevent object creation from outside.
	 */
	private Builder() {
		this.logic = new LogicImpl();
		this.dataAccess = new MockDataImpl();
	}
	/*
	 * Public method to access singleton Builder instance.
	 */
	public static Builder getInstance() {
		return instance;
	}
	@Override
	public void startup() {
		logic.startup();
		dataAccess.startup();
	}
	@Override
	public void shutdown() {
		logic.shutdown(); // in reverse order
		dataAccess.shutdown();
	}
	public DataAccess dataAccess() {
		return dataAccess;
	}
	public Logic logic() {
		return logic;
	}
}
