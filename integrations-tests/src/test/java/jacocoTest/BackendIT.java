package jacocoTest;

import jacocoTest.client.BackendClient;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {App.class})
public class BackendIT extends AbstractTestNGSpringContextTests {

    @Value("${local.server.port}")
    private int targetWebServerPort;

    private BackendClient client;

    @BeforeClass
    public void init() {
        client = new BackendClient("127.0.0.1", targetWebServerPort);
    }

    @Test
    public void test() throws IOException {
        Response response = client.sendGet("test/one");
        String string = response.body().string();
        assertEquals("success", string);
    }
}
