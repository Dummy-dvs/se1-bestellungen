package system.builder;

import java.util.function.Consumer;

import data_access.DataAccess;
import data_access.DataAccessImplFactory;
import system.ComponentLifecycle;
import system.Logic;


/**
 * Public singleton to build-up system from its components.
 *
 * @author sgra64
 */
public class Builder implements ComponentLifecycle {

    private static Builder instance = new Builder();

    private final Logic logic;

    private final DataAccess dataAccess;
    private final MockDataBuilder mockDataBuilder;

    private int phase = 0;


    /*
     * Private constructor to prevent object creation from outside.
     */
    private Builder() {
        this.dataAccess = DataAccessImplFactory.getInstance();
        this.logic = system.LogicImplFactory.getInstance(dataAccess);    // inject dependency on dataAccess into logic
        this.mockDataBuilder = new MockDataBuilderImpl(dataAccess);

    }

    /*
     * Public method to access singleton Builder instance.
     */
    public static Builder getInstance() {
        return instance;
    }


    @Override
    public void startup() {
        dataAccess.startup();
        logic.startup();
    }

    @Override
    public void shutdown() {
        logic.shutdown();        // reverse order to startup()
        dataAccess.shutdown();
    }


    /**
     * Builder's build functions.
     */
    public Builder build(int phase) {
        return build(phase, null);
    }

    public Builder build(int phase, Consumer<Builder> addOnCallback) {
        if (this.phase == phase - 1) {
            this.phase = phase;

            switch (phase) {

                case 1:    // Phase 1: build Customer and Article data
                    mockDataBuilder.buildCustomers();
                    mockDataBuilder.buildArticles();
                    break;

                case 2:    // Phase 2: build Orders based on Customer and Article data
                    mockDataBuilder.buildOrders(order -> {
                        logic.fillOrder(order);
                    });
                    break;

            }
            if (addOnCallback != null) {
                addOnCallback.accept(this);
            }

        } else {
            System.out.println(this.getClass().getSimpleName()
                    + ".build() not in sync, expected phase " + (this.phase + 1)
                    + ", but saw phase " + phase);
        }
        return this;
    }

    public Logic logic() {
        return logic;
    }

    public DataAccess dataAccess() {
        return dataAccess;
    }

}
