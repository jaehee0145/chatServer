package review_12_29.Ver2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private int port;

    public WebServer(int port){
        this.port = port;
    }

    public void run(){

        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(port);



            while(true){
                System.out.println("대기중입니다.");
                Socket socket = serverSocket.accept();


            }
        }
        catch(IOException ex){ex.printStackTrace();}
        finally{}
    }

}

class HttpHandler extends Thread{
    private Socket socket;

    public HttpHandler (Socket socket){
        this.socket = socket;
    }

    public void run (){
    }
}

