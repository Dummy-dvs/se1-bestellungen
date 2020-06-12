import data_access.DataAccess;
import model.Order;
import system.builder.Builder;
import system.Logic;


/**
 * Public class with main() function to run the system.
 * 
 * @author sgra64
 *
 */
public class Application_3 {


	public static void main( String[] args ) {
		System.out.println( "Bestellsystem SE1 (v3)." );
		
		Builder builder = Builder.getInstance();
		builder.startup();

		Logic logic = builder.logic();
		DataAccess dataAccess = builder.dataAccess();

		builder
			.build( 1, _builder -> {
				logic.printInventory();
			})
			.build( 2 );

		Iterable<Order> orders = dataAccess.getOrderData().findAll();
		logic.printOrders( orders );
		logic.printInventory();

		builder.shutdown();
	}
}
