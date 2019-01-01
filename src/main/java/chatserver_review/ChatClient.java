package chatserver_review;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.Buffer;

public class ChatClient {
    private String ip;
    private int port;

    public ChatClient (String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void run(){
        Socket socket = null;
        ChatUser chatUser = null;
        BufferedReader br = null;

        try {
            socket = new Socket(ip, port);
            chatUser = new ChatUser(socket);
            br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("닉네임을 입력하세요!");
            String nickname = br.readLine();
            chatUser.setNickname(nickname);
            chatUser.write(nickname);

            ChatClientHandler chatClientHandler = new ChatClientHandler(chatUser);
            chatClientHandler.start();

            while(true){
                String line = br.readLine();
                chatUser.write(line);
            }
        }catch(Exception ex){
            System.out.println("연결이 끊어졌네요 !");
        }finally{
            chatUser.close();
        }
    }

}
