package by.epamtc.client.service.impl;

import by.epamtc.client.service.CloseSocketService;
import by.epamtc.informationHandle.models.MessageType;
import by.epamtc.informationHandle.models.Wrapper;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class CloseSocketServiceImpl implements CloseSocketService {
    private static CloseSocketService instance;
    private ObjectOutputStream out;

    private CloseSocketServiceImpl(ObjectOutputStream out) {
        this.out = out;
    }

    public static CloseSocketService getInstance(ObjectOutputStream out) {
        if (instance == null){
            return new CloseSocketServiceImpl(out);
        }
        return instance;
    }

    @Override
    public void closeSocket() {
        Wrapper wrapper = new Wrapper();
        wrapper.setMessageType(MessageType.STOP_MESSAGING);
        try {
            out.writeObject(wrapper);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
