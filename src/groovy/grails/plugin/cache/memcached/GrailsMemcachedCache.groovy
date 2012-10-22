package grails.plugin.cache.memcached

import grails.plugin.cache.GrailsCache
import net.spy.memcached.MemcachedClient

import org.springframework.cache.Cache.ValueWrapper

class GrailsMemcachedCache implements GrailsCache {

	String name

	MemcachedClient client

	Integer ttl

	public GrailsMemcachedCache(String name, MemcachedClient client, Integer ttl) {
		this.name = name
		this.client = client
		this.ttl = ttl
	}

	@Override
	public void clear() {
		client.flush()
	}

	@Override
	public void evict(Object key) {
		client.delete(key)
	}

	@Override
	public ValueWrapper get(Object key) {
		Object value = getNativeCache().get(key.toString());
		return value == null ? null : new GrailsMemcachedValueWrapper(value, ttl, null);
	}

	@Override
	public String getName() {
		name
	}

	@Override
	public Object getNativeCache() {
		client
	}

	@Override
	public void put(Object key, Object value) {
		client.set(key.toString(), ttl, value)
	}

	@Override
	public Collection<Object> getAllKeys() {
		throw new RuntimeException('No way to list all the keys on memcached servers.')
	}
}
