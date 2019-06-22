package client.util;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {
    public static void closeAll(Closeable... closeables) {
        for (Closeable temp : closeables) {
            if (temp != null) {
                try {
                    temp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
