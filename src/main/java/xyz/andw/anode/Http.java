package xyz.andw.anode;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.HttpPost;

public class Http {
    public static String post(String uri, String json) {
        try {
            Anode.LOGGER.info(uri);
            URL url = new URL(uri);
            HttpURLConnection req = (HttpURLConnection) url.openConnection();
            req.setRequestMethod("POST");
            req.setDoOutput(true);
            byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
            int length = jsonBytes.length;
            req.setConnectTimeout(5000);
            req.setFixedLengthStreamingMode(length);
            req.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            req.connect();

            OutputStream os = req.getOutputStream();
            os.write(jsonBytes);
            os.close();

            String result = "{}";
            if (req.getResponseCode() == 200) {
                InputStream is = req.getInputStream();
                result = String.valueOf(is.readAllBytes());
                is.close();
            }

            req.disconnect();
            return result;
        } catch (Exception e) {
            Anode.LOGGER.info(e.toString());
            e.printStackTrace();
        }

        return "{}";
    }
}
