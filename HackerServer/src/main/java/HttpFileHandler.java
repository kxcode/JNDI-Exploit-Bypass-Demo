import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class HttpFileHandler implements HttpHandler {
    public HttpFileHandler() {
    }

    public void handle(HttpExchange httpExchange) {
        try {
            System.out.println("new http request from " + httpExchange.getRemoteAddress() + " " + httpExchange.getRequestURI());
            String uri = httpExchange.getRequestURI().getPath();
            InputStream inputStream = HttpFileHandler.class.getResourceAsStream(uri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            if (inputStream == null){
                System.out.println("Not Found");
                httpExchange.close();
                return;
            }else{
                while(inputStream.available() > 0) {
                    byteArrayOutputStream.write(inputStream.read());
                }

                byte[] bytes = byteArrayOutputStream.toByteArray();
                httpExchange.sendResponseHeaders(200, (long)bytes.length);
                httpExchange.getResponseBody().write(bytes);
                httpExchange.close();
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }
}
