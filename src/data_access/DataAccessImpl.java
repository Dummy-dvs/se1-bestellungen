package data_access;

import java.util.Optional;
import java.util.function.BiFunction;

import model.Article;
import model.Customer;
import model.Order;


/**
 * Local singleton instance that implements the DataAccess interface.
 * 
 * @author sgra64
 *
 */
class DataAccessImpl implements DataAccess {

	private static DataAccess instance = new DataAccessImpl();

	private final DataRepository<Customer,String> customers;

	private final DataRepository<Article,String> articles;

	private final DataRepository<Order,Long> orders;

	private Optional<MockDataBuilder> mockDataBuilder = Optional.empty();


	/*
	 * Constructor.
	 */
	DataAccessImpl() {

		//BiFunction<ID,ID,Boolean> compareId
		BiFunction<Long,Long,Boolean> compareLongId = ( id1, id2 ) -> { return id1 == id2; };

		BiFunction<String,String,Boolean> compareStringId =
			( id1, id2 ) -> { return id1 != null && id1.equals( id2 ); };


		this.customers = new DataRepositoryImpl<Customer,String>(
			//Function<T,ID> getId,					, BiFunction<ID,ID,Boolean> compareId
			customer -> { return customer.getId(); }, compareStringId
		);

		this.articles = new DataRepositoryImpl<Article,String>(
			article -> { return article.getId(); }, compareStringId
		);

		this.orders = new DataRepositoryImpl<Order,Long>(
			order -> { return order.getId(); }, compareLongId
		);
	}


	/*
	 * Public method to access singleton DataAccessImpl instance.
	 */
	public static DataAccess getInstance() {
		return instance;
	}


	@Override
	public void startup() {
		this.customers.startup();
		this.articles.startup();
		this.orders.startup();
	}

	@Override
	public void shutdown() {
		this.orders.shutdown();
		this.articles.shutdown();
		this.customers.shutdown();
	}


	@Override
	public DataRepository<Customer,String> customers() {
		return this.customers;
	}

	@Override
	public DataRepository<Article,String> articles() {
		return this.articles;
	}

	@Override
	public DataRepository<Order,Long> orders() {
		return this.orders;
	}


	@Override
	public Optional<MockDataBuilder> buildMockData() {
		if( ! mockDataBuilder.isPresent() ) {
			mockDataBuilder = Optional.of( new MockDataBuilderImpl( this ) );
		}
		return mockDataBuilder;
	}

}
