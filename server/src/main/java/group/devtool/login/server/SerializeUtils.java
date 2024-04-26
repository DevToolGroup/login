package group.devtool.login.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class SerializeUtils {

  private SerializeUtils() {

  }

  public static byte[] serialize(Serializable serializable) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(bos);
      oos.writeObject(serializable);
      return bos.toByteArray();
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    } finally {
      try {
        if (null != oos) {
          oos.close();
        }
      } catch (IOException e) {
          // do nothing
      }
    }
  }

  public static <T> T deserialize(byte[] body, Class<T> clazz) {
    ByteArrayInputStream bis = new ByteArrayInputStream(body);
    ObjectInputStream ois = null;
    try {
      ois = new ObjectInputStream(bis);
      return clazz.cast(ois.readObject());
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

}
