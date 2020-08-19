package by.epamtc.client.entity;

import java.io.Serializable;
import java.util.Objects;

public class Wrapper implements Serializable {
    private String messageType;
    private Object message;

    public Wrapper(String messageType, Object message) {
        this.messageType = messageType;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "messageType='" + messageType + '\'' +
                ", message=" + message +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wrapper wrapper = (Wrapper) o;
        return Objects.equals(messageType, wrapper.messageType) &&
                Objects.equals(message, wrapper.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageType, message);
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Wrapper() {
    }
}
