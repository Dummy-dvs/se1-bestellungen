package data_access;


public abstract class DataAccessImplFactory {
    private static DataAccess logicImpl = null;

    /**
     * Public factory method that creates singleton instance of
     * private Logic implementation class.
     *
     * @param dataAccess argument passed to LogicImpl
     * @return reference to Logic implementation instance
     */
    public static DataAccess getInstance() {
        if (logicImpl == null) {
            logicImpl = new DataAccessImpl();
        }
        return logicImpl;
    }
}