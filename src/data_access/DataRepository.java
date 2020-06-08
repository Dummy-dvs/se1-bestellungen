package data_access;

import system.ComponentLifecycle;

import java.util.Optional;

public interface DataRepository<T,ID> extends ComponentLifecycle {
	<S extends T> S save( S entity );
	<S extends T> Iterable<S> saveAll( Iterable<S> entities );
	Optional<T> findById(ID id );
	boolean existsById( ID id ); /* stub */
	Iterable<T> findAll();
	Iterable<T> findAllById( Iterable<ID> ids ); /* stub */
	long count();
	void deleteById( ID id ); /* stub */
	void delete( T entity ); /* stub */
	void deleteAll( Iterable<? extends T> entities ); /* stub */
	void deleteAll();
}
