package by.epamtc.client.main;

import by.epamtc.client.entity.MessageType;
import by.epamtc.informationHandle.entity.impl.Text;
import by.epamtc.informationHandle.models.Wrapper;

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
    private static ObjectOutputStream objOut;
    private static final int port = 4004;
    private static String filename = "text.txt";


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
                objOut = new ObjectOutputStream(outputStream);

                while (true) {
                    System.out.println("Write your mess to server:");
                    String messageType = reader.readLine();
                    if (messageType.equals(MessageType.STOP_MESSAGING)) {
                        Wrapper wrapper = new Wrapper();
                        wrapper.setMessageType(MessageType.STOP_MESSAGING);
                        objOut.writeObject(wrapper);
                        objOut.flush();
                        break;
                    }

                    if (messageType.equals(MessageType.FILE_OBJECT)){
                        String path = Client.class.getClassLoader().getResource(filename).getPath();

                        Wrapper request = new Wrapper(MessageType.FILE_OBJECT, path);
                        objOut.writeObject(request);
                        objOut.flush();
                        Wrapper response = (Wrapper) objIn.readObject();
                        Text message = (Text) response.getMessage();
                        System.out.println(message);
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
