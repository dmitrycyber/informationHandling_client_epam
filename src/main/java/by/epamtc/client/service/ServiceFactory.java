package by.epamtc.client.service;

import by.epamtc.client.service.impl.CloseSocketServiceImpl;
import by.epamtc.client.service.impl.GetTextServiceImpl;
import by.epamtc.client.service.impl.RemoveSentencesServiceImpl;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServiceFactory {

    public static RemoveSentencesService removeSentencesService(ObjectInputStream in, ObjectOutputStream out){
        return RemoveSentencesServiceImpl.getInstance(in, out);
    }

    public static GetTextService getTextService(ObjectInputStream in, ObjectOutputStream out){
        return GetTextServiceImpl.getInstance(in, out);
    }

    public static CloseSocketService closeSocketService(ObjectOutputStream out){
        return CloseSocketServiceImpl.getInstance(out);
    }
}
