package grails.plugin.cache.memcached

import grails.plugin.cache.GrailsCacheManager
import net.spy.memcached.MemcachedClient

import org.springframework.cache.Cache

public class GrailsMemcachedCacheManager implements GrailsCacheManager {

	MemcachedClient memcachedClient
	
	def grailsApplication

	def caches = [:]

	@Override
	public Cache getCache(String name) {
		
		def cache = caches.get(name)
		if (!cache) {
			println grailsApplication.config.grails.cache.config
			
			// TODO arrumar aqui
//			if (!cacheConfig) throw new RuntimeException("Cache ${name} not configured.")
			
			cache = new GrailsMemcachedCache(name, memcachedClient, 1800)//cacheConfig.ttl)
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
