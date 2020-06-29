package model;

import data_access.DataRepository;
import org.junit.*;
import testsuites.TestBuilder;

import java.util.Optional;

import static org.junit.Assert.*;


/**
 * 
 * JUnit4 tests for Article class.
 * 
 * Use of assertions, see:
 *   https://junit.org/junit4/javadoc/latest/org/junit/Assert.html
 * 
 * @author sgra64
 */
public class ArticleTest {

	/*
	 * Constants to compare expected results.
	 */
	private final static String ToasterID ="SKU‐868682";
	private final static String ToasterName = "Toaster";
	private final static long ToasterPrice = 2499;
	private final static int ToasterAmount = 1200;

	private final static String TasseID ="SKU-458362";
	private final static String TasseName = "Tasse";
	private final static long TassePrice = 299;
	private final static int TasseAmount = 1990;
	private final static String FalsePositive="SKU-458000";

	private DataRepository<Article,String> ArticleData = null;


	/**
	 * JUnit4 Test Setup Code
	 * ------------------------------------------------------------------------
	 * Setup method invoked once before any @Test method is executed.
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//System.out.println( ArticleTest.class.getSimpleName() + ".setUpBeforeClass() called." );
	}

	/**
	 * Setup method executed each time before a @Test method executes. Each @Test
	 * method executes with a new instance of this class.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		//System.out.println( this.getClass().getSimpleName() + ".setUp() called." );
		this.ArticleData = TestBuilder.getInstance().dataAccess().getArticleData();
	}


	/**
	 * JUnit4 Test Code
	 * ------------------------------------------------------------------------
	 */

	@Test
	public void testConstructor() {
		Article a = new Article( ToasterID, ToasterName,ToasterPrice,ToasterAmount );
		assertNotNull( a.getId() );				// Article id must not be null
		assertEquals( a.getId(), ToasterID );		// assert that correct ID is returned
		assertEquals( a.getDescription(), ToasterName );
		assertEquals( a.getUnitPrice(), ToasterPrice );
		assertEquals( a.getUnitsInStore(), ToasterAmount );
	}

	@Test
	public void testNegConstructor() {
		Article a = new Article( "", "", -10,-100 );
		assertEquals( a.getId(), "" );
		assertEquals( a.getDescription(), "" );		// "" is returned, not null
		assertEquals( a.getUnitPrice(), 0 );
		assertEquals( a.getUnitsInStore(), 0 );
	}

	@Test
	public void testNullConstructor() {
		Article a = new Article( null, null, 0,0 );
		assertNull( a.getId() );
		assertEquals( a.getDescription(), "" );		// "" is returned, not null
		assertEquals( a.getUnitPrice(), 0 );
		assertEquals( a.getUnitsInStore(), 0 );
	}

	@Test
	public void testArticleData() {

		assertNotNull( ArticleData );

		Optional<Article> optTasse = ArticleData.findById(TasseID);
		assertTrue( optTasse.isPresent() );
		Article aTasse = optTasse.get();
		assertNotNull( aTasse );
		assertEquals( aTasse.getId(), TasseID);		// asser
		assertEquals( aTasse.getDescription(), TasseName);
		assertEquals( aTasse.getUnitPrice(), TassePrice);
		assertEquals( aTasse.getUnitsInStore(), TasseAmount);
		aTasse.setUnitPrice(-5);
		assertEquals(aTasse.getUnitPrice(),0);
		aTasse.setDescription(null);
		assertEquals(aTasse.getDescription(),"");
		aTasse.setDescription(TasseName);
		aTasse.setUnitPrice(TassePrice);




		Optional<Article> optAnne = ArticleData.findById( FalsePositive );
		assertFalse( optAnne.isPresent() );

	}

	@Test
	public void testArticleDataUpdate() {

//		assertNotNull( ArticleData );
//
//		long count1 = ArticleData.count();
//
//		Article cLarry = new Article( idLarry, nameLarry, contactLarry );
//		ArticleData.save( cLarry );
//		long count2 = ArticleData.count();
//		assertEquals( count1 + 1, count2 );
//
//		// MUST throw exception
//		NoSuchElementException nse = assertThrows( NoSuchElementException.class, () -> {
//			Article cLarryNotFound = ArticleData.findById( "XX--invaild_id--??//" ).get();
//			assertNull( cLarryNotFound );
//		});
//		assertNotNull( nse );
//
//		Article cLarryFound = ArticleData.findById( idLarry ).get();
//		assertNotNull( cLarryFound );
//		assertSame( cLarry, cLarryFound );	// same object found as saved, cLarry == cLarryFound
	}

	@Test
	public void testArticleNameResolution() {

//		Article cEric = new Article( "C86516", "Eric Meyer", "eric2346@gmail.com" );
//		assertNotNull( cEric.getId() );
//		assertEquals( cEric.getFirstName(), "Eric" );
//		assertEquals( cEric.getLastName(), "Meyer" );
//		cEric.setName( "Meyer, Eric" );
//		assertEquals( cEric.getFirstName(), "Eric" );
//		assertEquals( cEric.getLastName(), "Meyer" );
//
//		Article c1 = new Article( "", "", "" );
//		c1.setName( "Nadine Ulla-Blumenfeld" );
//		assertEquals( c1.getFirstName(), "Nadine" );
//		assertEquals( c1.getLastName(), "Ulla-Blumenfeld" );
//
//		c1.setName( "Blumenfeld, Nadine Ulla" );
//		assertEquals( c1.getFirstName(), "Nadine Ulla" );
//		assertEquals( c1.getLastName(), "Blumenfeld" );
//
//		c1.setName( "Blumenfeld" );
//		assertEquals( c1.getFirstName(), "" );
//		assertEquals( c1.getLastName(), "Blumenfeld" );

		/*
		 * Ambitionierte Tests
		 * /
		c1.setName( "Blumenfeld," );
		assertEquals( c1.getFirstName(), "" );
		assertEquals( c1.getLastName(), "Blumenfeld" );

		c1.setName( ",Blumenfeld" );
		assertEquals( c1.getFirstName(), "" );
		assertEquals( c1.getLastName(), "Blumenfeld" );

		c1.setName( " , , Blumenfeld , ,, " );
		assertEquals( c1.getFirstName(), "" );
		assertEquals( c1.getLastName(), "Blumenfeld" );

		c1.setName( "Nadine Ulla-Mona Blumenfeld-Meyer" );
		assertEquals( c1.getFirstName(), "Nadine Ulla-Mona" );
		assertEquals( c1.getLastName(), "Blumenfeld-Meyer" );

		c1.setName( "Nadine Ulla-Mona-Blumenfeld-Meyer" );
		assertEquals( c1.getFirstName(), "Nadine" );
		assertEquals( c1.getLastName(), "Ulla-Mona-Blumenfeld-Meyer" );
		/* */
	}


	/**
	 * JUnit4 Test Tear-down Code
	 * ------------------------------------------------------------------------
	 * 
	 * Tear-down method invoked each time after a @Test method has finished.
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		//System.out.println( this.getClass().getSimpleName() + ".tearDown() called." );
	}

	/**
	 * Tear-down method invoked once after all @Test methods in this class have finished.
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//System.out.println( ArticleTest.class.getSimpleName() + ".tearDownAfterClass() called." );
		TestBuilder.getInstance().tearDown();
	}

}
