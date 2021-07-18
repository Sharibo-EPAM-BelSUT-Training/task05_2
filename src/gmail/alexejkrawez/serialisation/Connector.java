package gmail.alexejkrawez.serialisation;

import gmail.alexejkrawez.box.Box;
import gmail.alexejkrawez.exceptions.WriteReadException;

import java.io.*;

public class Connector {

    public static void writeBox(String fileName, Box box) throws WriteReadException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(box);
        } catch (IOException e) {
            throw new WriteReadException("A read-write error has occurred", e);
        }
    }

    public static Box readBox(String fileName, Box box) throws WriteReadException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("BoxOut"))) {
            box = (Box) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new WriteReadException("A read-write error has occurred", e);
        }
        return box;
    }

}
