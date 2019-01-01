package chatserver_review;

public class ChatClientMain {
    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient("172.30.1.4", 9999);
        chatClient.run();
    }
}
