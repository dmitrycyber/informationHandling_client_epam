package by.epamtc.client.service.impl;

import by.epamtc.client.service.GetTextService;
import by.epamtc.informationHandle.entity.impl.Text;
import by.epamtc.informationHandle.models.MessageType;
import by.epamtc.informationHandle.models.Wrapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GetTextServiceImpl implements GetTextService {
    private static GetTextServiceImpl instance;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private GetTextServiceImpl(ObjectInputStream in, ObjectOutputStream out){
        this.out = out;
        this.in = in;
    }


    public static GetTextServiceImpl getInstance(ObjectInputStream in, ObjectOutputStream out) {
        if (instance == null){
            return new GetTextServiceImpl(in, out);
        }
        return instance;
    }

    @Override
    public Text getText(String message) {
        Wrapper request = new Wrapper(MessageType.FILE_OBJECT, message);
        Text text = null;
        try {
            out.writeObject(request);
            out.flush();
            Wrapper response = (Wrapper) in.readObject();
            text = (Text) response.getMessage();
            System.out.println(text);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return text;
    }
}
