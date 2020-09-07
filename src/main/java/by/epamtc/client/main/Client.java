package by.epamtc.client.main;

import by.epamtc.client.service.CloseSocketService;
import by.epamtc.client.service.GetTextService;
import by.epamtc.client.service.RemoveSentencesService;
import by.epamtc.client.service.ServiceFactory;
import by.epamtc.informationHandle.entity.impl.Text;
import by.epamtc.informationHandle.models.MessageType;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

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
    private static CloseSocketService closeSocketService;
    private static GetTextService getTextService;
    private static RemoveSentencesService removeSentencesService;
    private static Text text;


    public static void main(String[] args) {
        try {
            clientSocket = new Socket("localhost", port);
            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
            objIn = new ObjectInputStream(inputStream);
            objOut = new ObjectOutputStream(outputStream);
            closeSocketService = ServiceFactory.closeSocketService(objOut);
            getTextService = ServiceFactory.getTextService(objIn, objOut);
            removeSentencesService = ServiceFactory.removeSentencesService(objIn, objOut);

            while (true) {
                System.out.println("Write your mess to server:");
                String messageType = reader.readLine();
                if (messageType.equals(MessageType.STOP_MESSAGING)) {
                    closeSocketService.closeSocket();
                    break;
                }
                if (messageType.equals(MessageType.FILE_OBJECT)){
                    String file = Client.class.getClassLoader().getResource(filename).getPath();
                    text = getTextService.getText(file);
                }
                if (messageType.equals(MessageType.REMOVE_WORDS_FIXED_LENGTH)){
                    System.out.println("Input word length: ");
                    int wordLength = Integer.parseInt(reader.readLine());
                    if (text != null){
                        Text text = removeSentencesService.removeFixedLengthWordText(Client.text, wordLength);
                        System.out.println(text);
                    }
                    else {
                        System.out.println("Text is null!!");
                    }
                }

//                else {
//                    String messageFromServer = in.readLine();
//                    System.out.println(messageFromServer);
//                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Client is close!");
            try {
                clientSocket.close();
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
