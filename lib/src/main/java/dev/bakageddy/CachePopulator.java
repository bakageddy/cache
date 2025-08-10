package dev.bakageddy.cache;

public interface CachePopulator {
	public <Err> Result<Void, Err> populateCache(
		final CacheSettings settings,
		CacheAlgorithm cache,
		CacheDataSource data_source
	);
}
