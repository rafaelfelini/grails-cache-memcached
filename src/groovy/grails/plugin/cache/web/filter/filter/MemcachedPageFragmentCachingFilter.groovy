package grails.plugin.cache.web.filter.filter

import grails.plugin.cache.memcached.GrailsMemcachedCacheManager
import grails.plugin.cache.web.PageInfo
import grails.plugin.cache.web.filter.PageFragmentCachingFilter

import org.springframework.cache.Cache
import org.springframework.cache.Cache.ValueWrapper

class MemcachedPageFragmentCachingFilter extends PageFragmentCachingFilter {

	@Override
	protected int getTimeToLive(ValueWrapper wrapper) {
		return 1800 // TODO Arrumar aqui... wrapper?.ttl
	}

	@Override
	protected GrailsMemcachedCacheManager getNativeCacheManager() {
		return (GrailsMemcachedCacheManager) super.getNativeCacheManager()
	}

	@Override
	protected void put(Cache cache, String key, PageInfo pageInfo, Integer timeToLiveSeconds) {
		// The cache is set on GrailsMemcachedCache and comes from config.
		cache.put(key, pageInfo)
	}
}
