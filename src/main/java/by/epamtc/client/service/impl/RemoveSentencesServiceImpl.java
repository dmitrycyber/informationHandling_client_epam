package by.epamtc.client.service.impl;

import by.epamtc.client.service.RemoveSentencesService;
import by.epamtc.informationHandle.entity.impl.Text;
import by.epamtc.informationHandle.models.MessageType;
import by.epamtc.informationHandle.models.RemoveWordModel;
import by.epamtc.informationHandle.models.Wrapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RemoveSentencesServiceImpl implements RemoveSentencesService {
    private static RemoveSentencesServiceImpl instance;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private RemoveSentencesServiceImpl(ObjectInputStream in, ObjectOutputStream out) {
        this.out = out;
        this.in = in;
    }

    public static RemoveSentencesServiceImpl getInstance(ObjectInputStream in, ObjectOutputStream out) {
        if (instance == null){
            return new RemoveSentencesServiceImpl(in, out);
        }
        return instance;
    }



    @Override
    public Text removeFixedLengthWordText(Text text, int length) {
        RemoveWordModel build = RemoveWordModel.builder()
                .length(length)
                .text(text).build();


        Wrapper request = new Wrapper(MessageType.REMOVE_WORDS_FIXED_LENGTH, build);

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
