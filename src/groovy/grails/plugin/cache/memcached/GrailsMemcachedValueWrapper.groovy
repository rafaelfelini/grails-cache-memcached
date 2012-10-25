package grails.plugin.cache.memcached

import grails.plugin.cache.GrailsValueWrapper

class GrailsMemcachedValueWrapper extends GrailsValueWrapper {

	Integer ttl

	public GrailsMemcachedValueWrapper(Object value, Integer ttl, Object nativeWrapper) {
		super(value, nativeWrapper)
		this.ttl = ttl
	}
}