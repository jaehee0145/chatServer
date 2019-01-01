package review_12_29;

public class Application {

    public static void main(String[] args) {
        WebServer server = new WebServer(9900);
        server.run();
    }
}
