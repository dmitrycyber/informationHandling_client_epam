import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static InputStream inputStream;
    private static OutputStream outputStream;

    private static BufferedReader in;
    private static BufferedWriter out;

    private static ObjectInputStream objIn;
    private static final int port = 4004;

    private static final String bookMess = "book";
    private static final String stopMess = "stop";


    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket("localhost", port);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                inputStream = clientSocket.getInputStream();
                outputStream = clientSocket.getOutputStream();
                objIn = new ObjectInputStream(inputStream);

                while (true) {
                    System.out.println("Write your mess to server:");
                    String word = reader.readLine();
                    if (word.equals(stopMess)) {
                        out.write(word + "\n");
                        out.flush();
                        break;
                    }

                    out.write(word + "\n");
                    out.flush();

                    if (word.equals(bookMess)){
                        Book book = (Book) objIn.readObject();
                        System.out.println(book);
                    }
                    else {
                        String messageFromServer = in.readLine();
                        System.out.println(messageFromServer);

                    }
                }


            } finally {
                System.out.println("Client is close!");
                clientSocket.close();
                inputStream.close();
                outputStream.close();
            }
        } catch (Exception e) {
            System.err.print(e);
        }
    }

}
