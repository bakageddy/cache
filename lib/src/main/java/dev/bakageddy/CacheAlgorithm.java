package dev.bakageddy.cache;

import java.util.Optional;

public interface CacheAlgorithm<Key, Value> {
	public Optional<Value> put(Key key, Value val);
	public Optional<Value> get(Key key);
	public void evict(Key key);
}
