package my.examples.miniwebserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;

public class WebServer {

    private int port; //서버는 모두 포트를 갖는다.

    public WebServer(int port) {
        this.port = port;
    }

    public void run() {
        // 접속을 대기한다.
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("접속 대기");
                Socket socket = serverSocket.accept(); //대기, 블러킹

                HttpHandler httpHandler = new HttpHandler(socket);
                httpHandler.start(); //쓰레드를 실행
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
}

class HttpHandler extends Thread {
    private Socket socket;

    public HttpHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() { // 별도로 동작할 코드를 run메소드에 작성
        final String baseDir = "c:/users/user/Desktop";

        BufferedReader in = null;
        PrintStream out = null;
        FileInputStream fis = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream());

            String requestLine = in.readLine();
            String []s = requestLine.split(" ");
            String httpMethod = s[0];
            String httpPath = s[1];
            if(httpPath.equals("/"))
                httpPath = "/index.html";
            String filePath = baseDir + httpPath;

            File file = new File(filePath);
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if(mimeType ==null)
                mimeType = "text/html";

            String line = null;
            while ((line = in.readLine()) != null) {
                if ("".equals(line))  //line을 앞에 넣으면 nullE 발생가능성 있으므로
                    break;
                System.out.println("헤더 정보:" + line);
            }

            out.println("HTTP/1.1 200 OK");
            out.println("Content-type: text/html; charset=UTF-8");
            out.println("Content-length:" + file.length());
            out.println();
//            out.println("<html><h1>hello</h1></html>");

            byte[] buffer = new byte[1024];
            int readCount = 0;
            fis = new FileInputStream(file);
            while ((readCount = fis.read(buffer)) != -1){
                out.write(buffer, 0, readCount);
            }
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

//이하는 쓰레드 (무한히 반복)
// 누군 접속을 하면, 서버는 한줄씩 읽는다. ( 빈줄 나올때까지)
// path에 해당하는 리소스를 찾는다.
// 리소스가 있을 경우, 상태 코드를 보낸다.(첫줄)
//헤더들을 보낸다. (body의 길이, body가 무슨 mime type인지)
// 빈줄을 보낸다.
// body를 보낸다.

// 요청이 오면 핸들러에게 부탁하고 나는 계속 대기
// 흐름이 있다. 대기하는 흐름이 핸들러에게 부탁하고 다시 대기>대기하는 흐름+ 핸들러 흐름

