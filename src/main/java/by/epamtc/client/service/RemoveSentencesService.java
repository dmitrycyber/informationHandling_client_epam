package by.epamtc.client.service;

import by.epamtc.informationHandle.entity.impl.Text;

public interface RemoveSentencesService {
    Text removeFixedLengthWordText(Text text, int length);
}
