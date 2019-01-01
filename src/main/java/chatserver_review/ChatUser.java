package chatserver_review;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatUser {
    //필드
    private String nickname;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    // 생성자

    public ChatUser(Socket socket) {

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

        } catch (Exception ex) {
            throw new RuntimeException("ChatUser 생성시 오류");
        }
    }

    public void close() {
        try {
            in.close();
        } catch (Exception ignore) {
        }
        try {
            out.close();
        } catch (Exception ignore) {
        }
        try {
            socket.close();
        } catch (Exception ignore) {
        }

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    public void write(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException ioe) {
            throw new RuntimeException("메세지 전송시 오류");
        }
    }

    public String read() {
        try{
            return in.readUTF();
        }
        catch(Exception ex){
            throw new RuntimeException ("메세지 읽을 때 오류");
        }
    }
}