grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {

    inherits("global") {
        // uncomment to disable ehcache
		excludes 'ehcache'
    }
	
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    
	repositories {
        grailsCentral()
        mavenRepo 'http://files.couchbase.com/maven2/' //spymemcached
    }
    dependencies {
		compile('spy:spymemcached:2.7.3') {
			transitive = false
		}
    }

    plugins {
		compile ':cache:1.0.0'
		
        build(":tomcat:$grailsVersion",
              ":release:2.0.4",
              ":rest-client-builder:1.0.2") {
            export = false
        }
    }
}