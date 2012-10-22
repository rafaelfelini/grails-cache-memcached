package grails.plugin.cache.memcached

public class SerializingTranscoder extends net.spy.memcached.transcoders.SerializingTranscoder {

	@Override
	protected Object deserialize(byte[] data) {
		try {
			return new ObjectInputStream(new ByteArrayInputStream(data)) {
				@Override
				protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
					return Class.forName(desc.getName(), false, this.class.getClassLoader())
				}
			}.readObject();
		} catch (Exception e) {
			log.error(e.message, e)
		}
		return null
	}

	@Override
	protected byte[] serialize(Object object) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream()
			new ObjectOutputStream(bos).writeObject(object)
			return bos.toByteArray()
		} catch (IOException e) {
			log.error e.message, e
		}
		return null
	}
}
