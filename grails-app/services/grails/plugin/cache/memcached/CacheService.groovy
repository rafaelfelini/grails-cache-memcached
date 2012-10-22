package grails.plugin.cache.memcached

import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class CacheService {

	// net.spy.memcached.MemcachedClient
	def memcachedClient

	public void add(String key, Object value, int expiration) {
		memcachedClient.add(key, expiration, value)
	}

	public Object get(String key) {
		Future<Object> future = memcachedClient.asyncGet(key)
		try {
			return future.get(1, TimeUnit.SECONDS)
		} catch (Exception e) {
			future.cancel(false)
		}
		return null
	}

	public void clear() {
		memcachedClient.flush()
	}

	public void delete(String key) {
		memcachedClient.delete(key)
	}

	public Map<String, Object> get(String[] keys) {
		Future<Map<String, Object>> future = memcachedClient.asyncGetBulk(tc, keys)
		try {
			return future.get(1, TimeUnit.SECONDS)
		} catch (Exception e) {
			future.cancel(false)
		}
		return Collections.<String, Object>emptyMap()
	}

	public long incr(String key, int by) {
		return memcachedClient.incr(key, by, 0)
	}

	public long decr(String key, int by) {
		return memcachedClient.decr(key, by, 0)
	}

	public void replace(String key, Object value, int expiration) {
		memcachedClient.replace(key, expiration, value)
	}

	public boolean safeAdd(String key, Object value, int expiration) {
		Future<Boolean> future = memcachedClient.add(key, expiration, value)
		try {
			return future.get(1, TimeUnit.SECONDS)
		} catch (Exception e) {
			future.cancel(false)
		}
		return false
	}

	public boolean safeDelete(String key) {
		Future<Boolean> future = memcachedClient.delete(key)
		try {
			return future.get(1, TimeUnit.SECONDS)
		} catch (Exception e) {
			future.cancel(false)
		}
		return false
	}

	public boolean safeReplace(String key, Object value, int expiration) {
		Future<Boolean> future = memcachedClient.replace(key, expiration, value)
		try {
			return future.get(1, TimeUnit.SECONDS)
		} catch (Exception e) {
			future.cancel(false)
		}
		return false
	}

	public boolean safeSet(String key, Object value, int expiration) {
		Future<Boolean> future = memcachedClient.set(key, expiration, value)
		try {
			return future.get(1, TimeUnit.SECONDS)
		} catch (Exception e) {
			future.cancel(false)
		}
		return false
	}

	public void set(String key, Object value, int expiration) {
		memcachedClient.set(key, expiration, value)
	}

	public void stop() {
		memcachedClient.shutdown()
	}
}