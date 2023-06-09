package at.fhtw.swen2.tutorial.toServer;

import at.fhtw.swen2.tutorial.presentation.view.ImageView;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
@Component
public class MapQuestImage {
    private static final String API_URL = "https://www.mapquestapi.com/staticmap/v5/map?start=%s&end=%s&size=%d,%d@2x&format=png&key=%s";

    private static final String apiKey = "ySmPoP4ZPSU290woGOMvF9PQcVCD8NT6";

    private static String filename = "map2.png";

    private static final Logger logger = LogManager.getLogger(MapQuestImage.class);
    public Image getImage(String start, String end) throws IOException {
        byte[] imageBytes = getImageBytes(start, end);
        Image image = null;
        logger.debug("getting image");
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            image = new Image(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    private byte[] getImageBytes(String start, String end) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = String.format(API_URL, start, end, 500, 400, apiKey);
        Request request = new Request.Builder()
                .url(url)
                .build();

        logger.debug("getting image bytes");
        try (Response response = client.newCall(request).execute()) {
            logger.info("response "+response);
            return response.body().bytes();
        }
    }
}
