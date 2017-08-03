package jacocoTest.client;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BackendClient {
    
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
    private static final int TIMEOUT = 5;
    private static final int MAX_REQUESTS_DISPATCHER = 10;
    private static final int MAX_IDLE_CONNECTIONS = 10;
    private static final int KEEP_ALIVE_DURATION = 2;
    private OkHttpClient client;
    private final String host;
    private final int port;

    public BackendClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Response sendGet(String url) throws IOException {
        return sendGet(buildUrl(url));
    }
    
    private Response sendGet(HttpUrl httpUrl) throws IOException {
        return send(getRequest().url(httpUrl.url()).build());
    }
    
    private Response send(Request req) throws IOException {
        return getClient().newCall(req).execute();
    }
    
    private HttpUrl buildUrl(String url) {
        return new HttpUrl.Builder().scheme("http").host(host).port(port).addPathSegments(url).build();
    }

    private OkHttpClient getClient() {
        if (null == client) {
            Dispatcher dispatcher = new Dispatcher();
            ConnectionPool pool = new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.MINUTES);
            dispatcher.setMaxRequests(MAX_REQUESTS_DISPATCHER);
            client = new OkHttpClient.Builder()
                    .connectionPool(pool)
                    .dispatcher(dispatcher)
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS).build();
        }
        return client;
    }

    private Request.Builder getRequest() {
        return new Request.Builder()
                .header("User-Agent", "IC JavaFX")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json");
    }
}
