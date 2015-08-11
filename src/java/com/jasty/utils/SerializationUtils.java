package com.jasty.utils;

import java.io.*;

/**
 * Methods for object serialization/deserialization
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class SerializationUtils {

	public static byte[] serializeObject(Serializable obj) {
			try {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(os);
				out.writeObject(obj);
				out.close();
				return os.toByteArray();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
	}

	public static Object deserializeObject(byte[] bytes) {
        final StringBuilder className = new StringBuilder();
		try
		{
			ByteArrayInputStream fis = new ByteArrayInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream(fis) {
				/** although it does nothing special, there were unfixable ClassNotFoundExceptions on timo's machine without this overload */
				protected Class resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                    className.append(objectStreamClass.getName());
                    return super.resolveClass(objectStreamClass);
				}
			};
			Object obj = in.readObject();
			in.close();
			return obj;
		}
		catch(Exception e)
		{
			throw new RuntimeException("Class: " + className.toString() + ", " + e.toString());
		}
	}
}
