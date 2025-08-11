package dev.bakageddy.cache;

public interface CachePopulator {
	public <Err> Result<Void, Err> populateCache(
		CacheSettings settings,
		CacheAlgorithm cache,
		CacheDataSource data_source
	);
}
