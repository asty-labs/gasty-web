package com.jasty.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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
		try
		{
			ByteArrayInputStream fis = new ByteArrayInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream(fis);
			Object obj = in.readObject();
			in.close();
			return obj;
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
