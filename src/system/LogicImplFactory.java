package system;

import data_access.DataAccess;

public abstract class LogicImplFactory {
    private static Logic logicImpl = null;

    /**
     * Public factory method that creates singleton instance of
     * private Logic implementation class.
     *
     * @param dataAccess argument passed to LogicImpl
     * @return reference to Logic implementation instance
     */
    public static Logic getInstance(DataAccess dataAccess) {
        if (logicImpl == null) {
            logicImpl = new LogicImpl(dataAccess);
        }
        return logicImpl;
    }
}