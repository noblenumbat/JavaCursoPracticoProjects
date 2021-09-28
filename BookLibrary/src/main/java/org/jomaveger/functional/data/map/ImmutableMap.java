package org.jomaveger.functional.data.map;

import org.jomaveger.functional.control.Result;
import org.jomaveger.functional.data.ImmutableList;
import org.jomaveger.functional.data.ImmutableTree;
import org.jomaveger.functional.tuples.Tuple2;

public class ImmutableMap<K, V> {

	private final ImmutableTree<MapEntry<Integer, ImmutableList<Tuple2<K, V>>>> delegate;

	private ImmutableMap() {
		this.delegate = ImmutableTree.empty();
	}

	public ImmutableMap(ImmutableTree<MapEntry<Integer, ImmutableList<Tuple2<K, V>>>> delegate) {
	    this.delegate = delegate;
	}

	public ImmutableMap<K, V> add(K key, V value) {
		Tuple2<K, V> tuple = new Tuple2<>(key, value);
		ImmutableList<Tuple2<K, V>> ltkv = 
			getAll(key).map((ImmutableList<Tuple2<K, V>> lt) -> lt.foldLeft(ImmutableList.of(tuple), (l, t) -> t._1.equals(key) ? l : l.cons(t)))
				.getOrElse(() -> ImmutableList.of(tuple));
		return new ImmutableMap<>(delegate.insert(MapEntry.mapEntry(key.hashCode(), ltkv)));
	}

	public boolean contains(K key) {
		return getAll(key).map(lt -> lt.exists(t -> t._1.equals(key))).getOrElse(false);
	}

	public ImmutableMap<K, V> remove(K key) {		
		ImmutableList<Tuple2<K, V>> ltkv = 
			getAll(key).map((ImmutableList<Tuple2<K, V>> lt) -> 
				lt.foldLeft(ImmutableList.empty(), 
					(ImmutableList<Tuple2<K, V>> l, Tuple2<K, V> t) -> t._1.equals(key) ? l : l.cons(t)))
						.getOrElse(ImmutableList.empty());

		return ltkv.isEmpty() ? new ImmutableMap<>(delegate.remove(MapEntry.mapEntry(key.hashCode())))
				: new ImmutableMap<>(delegate.insert(MapEntry.mapEntry(key.hashCode(), ltkv)));
	}

	public Result<Tuple2<K, V>> get(K key) {
		return getAll(key).flatMap(lt -> lt.find(t -> t._1.equals(key)));
	}

	private Result<ImmutableList<Tuple2<K, V>>> getAll(K key) {
		return delegate.get(MapEntry.mapEntry(key.hashCode())).flatMap(x -> x.value.map(lt -> lt.map(t -> t)));
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public static <K, V> ImmutableMap<K, V> empty() {
		return new ImmutableMap<>();
	}

	@Override
	public String toString() {
		return String.format("Map[%s]", this.delegate);
	}
}
