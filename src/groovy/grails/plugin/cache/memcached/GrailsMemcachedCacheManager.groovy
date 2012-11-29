package grails.plugin.cache.memcached

import grails.plugin.cache.GrailsCacheManager
import net.spy.memcached.MemcachedClient

import org.springframework.cache.Cache

public class GrailsMemcachedCacheManager implements GrailsCacheManager {

	MemcachedClient memcachedClient
	
	def grailsApplication

	Map<String, Cache> caches = [:]

	@Override
	public Cache getCache(String name) {
		
		Cache cache = caches.get(name)
		if (!cache) {
			
			Map regions = grailsApplication.config.memcached.regions ?: [:]
			if (regions.containsKey(name)) {
				
				def cacheConfig = regions.get(name)
				cache = new GrailsMemcachedCache(name, memcachedClient, cacheConfig.ttl)
			} else {
			
				cache = new GrailsMemcachedCache(name, memcachedClient, 360)
			}
			
			caches.put(name, cache)
		}

		return cache
	}

	@Override
	public Collection<String> getCacheNames() {
		
		return caches.keySet()
	}

	@Override
	public boolean cacheExists(String name) {
		
		return caches.containsKey(name)
	}

	@Override
	public boolean destroyCache(String name) {
		
		throw new RuntimeException('not allowed')
	}
}
