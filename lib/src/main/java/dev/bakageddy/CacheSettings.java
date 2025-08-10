package dev.bakageddy.cache;

public interface CacheSettings {
	public <Err> Result<CacheSettings, Err> parseSettings();
}
