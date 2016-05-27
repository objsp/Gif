package core;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016-05-22 0022.
 */
public class HttpLoader {

    public static InputStream getInInputStreamFromUrl(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            return conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
