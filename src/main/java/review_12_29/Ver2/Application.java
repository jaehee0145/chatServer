package review_12_29.Ver2;

public class Application {
    public static void main(String[] args) {

        WebServer server = new WebServer(9990);
        server.run();
    }
}