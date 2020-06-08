package data_access;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

class DataRepositoryImpl<T, ID> implements DataRepository<T, ID> {
	private final List<T> list;
	private final Function<T, ID> _getId; // T getId( ID id );
	private final BiFunction<ID, ID, Boolean> _compareId; // Boolean compareId( ID id, ID id2 );

	private ID getId(T element) {
		return _getId.apply(element);
	}

	private boolean compareId(ID a, ID b) {
		return _compareId.apply(a, b);
	}

	/**
	 * Constructor.
	 *
	 * @param _getId functional interface to obtain id from entity
	 * @param _compareId functional interface to compare two entity id's
	 */
	DataRepositoryImpl(Function<T, ID> _getId, BiFunction<ID, ID, Boolean> _compareId) {
		this.list = new ArrayList<T>();
		this._getId = _getId;
		this._compareId = _compareId;
	}

	@Override
	public <S extends T> S save(S entity) {
		var id = _getId.apply(entity);
		for (var e : list) {
			if (compareId(getId(e), (id)))
				return null;
		}
		list.add(entity);
		return entity;
// Add entity to list, only if entity with same ID is not yet present in
// list. If ID is present, replace entity in list. Return saved entity.
	}

	@Override
	public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
		var addedOnes = new LinkedList<S>();
		for (var e : entities) {
			if (save(e) != null)
				addedOnes.add(e);
		}
		return addedOnes;
	}

	@Override
	public Optional<T> findById(ID id) {
		var idx = _find(id);
		return idx == -1 ? Optional.empty() : Optional.of(list.get(idx));
	}

	@Override
	public boolean existsById(ID id) {
		return _find(id) != -1;
	}

	@Override
	public Iterable<T> findAll() {
		return Collections.unmodifiableList(list);
	}

	@Override
	public Iterable<T> findAllById(Iterable<ID> ids) {
		var ret = new LinkedList<T>();
		for (var i : ids)
			findById(i).ifPresent(ret::add);
		return ret;
	}

	@Override
	public long count() {
		return list.size();
	}

	@Override
	public void deleteById(ID id) {
		findById(id).ifPresent(this::delete);
	}

	@Override
	public void delete(T entity) {
		list.remove(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends T> entities) {
		for(var e:entities)
			delete(e);
	}

	// ...
	@Override
	public void deleteAll() {
		list.clear();
	}

	// Returns object index in list, if object with id is found, or ‐1 otherwise
	private int _find(ID id) {
		if (id != null) {
			int len = list.size();
			for (int i = 0; i < len; i++) {
				T entity = list.get(i);
				ID id2 = getId(entity); // Aufruf der Func getId(entity)
				if (compareId(id, id2)) { // Aufruf der Func compareId zum
					return i; // Vergleich generischer ID
				}
			}
		}
		return -1;
	}

	@Override
	public void startup() {

	}

	@Override
	public void shutdown() {

	}
}
