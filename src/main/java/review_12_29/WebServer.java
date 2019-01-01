package review_12_29;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    //각 서버는 port값을 갖는다
    private int port;

    // web server 가 port값을 갖도록 생성자 생성
    public WebServer(int port) {
        this.port = port;
    }

    public void run() {
        // 클라이언트의 접속을 대기한다.
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            // port값을 받아서 서버 소켓이 생성된다.

            while (true) {
                System.out.println("접속을 대기합니다.");
                Socket socket = serverSocket.accept(); // 클라이언트가 접속할때까지 대기
                // + Listens for a connection to be made to this socket and accepts it.

                HttpHandler httpHandler = new HttpHandler(socket);
                httpHandler.start();

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (Exception ignore) {
            }
        }
    }

    class HttpHandler extends Thread {
        private Socket socket;

        public HttpHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            BufferedReader in = null;
            PrintStream out = null;

            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintStream(socket.getOutputStream());

                String requestLine = in.readLine();
                String line = null;
                while ((line = in.readLine()) != null)
                    if ("".equals(line)) {
                        break;
                    }
                System.out.println(requestLine);
                System.out.println("헤더정보: " + line);

                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html; charset=UTF-8");
                out.println();
                out.println("<html><h1>Good Job :) </h1></html>");

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
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


        }
    }
}
