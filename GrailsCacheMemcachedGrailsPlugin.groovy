import grails.plugin.cache.memcached.GrailsMemcachedCacheManager
import grails.plugin.cache.memcached.SerializingTranscoder;
import grails.plugin.cache.web.filter.filter.MemcachedPageFragmentCachingFilter

class GrailsCacheMemcachedGrailsPlugin {
    def version = "0.1"
    def grailsVersion = "2.0 > *"
	def loadAfter = ['cache']
    def dependsOn = [:]
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Grails Cache Memcached Plugin"
    def author = "Rafael Felini"
    def authorEmail = "rafael.felini@gmail.com"
    def description = 'A memcached-based implementation of the Cache plugin'
    def documentation = "http://grails.org/plugin/grails-cache-memcached"

    def license = "APACHE"
    def developers = [ [ name: "Rafael Felini", email: "rafael.felini@gmail.com" ]]
    def scm = [ url: "https://github.com/rafaelfelini/grails-cache-memcached/issues" ]

    def doWithSpring = {
		transcoder(SerializingTranscoder)
		
		if (!application.config.memcached.servers) {
			
			log.error 'Please, provide the configuration "cache.memcached.servers"'
		}
		
		memcachedClient(net.spy.memcached.spring.MemcachedClientFactoryBean) {
			servers = application.config.memcached.servers
			transcoder = ref('transcoder')
		}
		
		grailsCacheManager(GrailsMemcachedCacheManager) {
			memcachedClient = ref('memcachedClient')
			grailsApplication = ref('grailsApplication')
		}
		
		grailsCacheFilter(MemcachedPageFragmentCachingFilter) {
			cacheManager = ref('grailsCacheManager')
			nativeCacheManager = ref('memcachedClient')
			
			// TODO this name might be brittle - perhaps do by type?
			cacheOperationSource = ref('org.springframework.cache.annotation.AnnotationCacheOperationSource#0')
			keyGenerator = ref('webCacheKeyGenerator')
			expressionEvaluator = ref('webExpressionEvaluator')
		}
    }
}