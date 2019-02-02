package rnd.mate00.ebooks.jms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IncomingMessage {

    @JsonProperty(value = "messageType")
    MessageType messageType;

    @JsonProperty(value = "contents")
    String contents;

    public IncomingMessage() {}

    public IncomingMessage(MessageType messageType, String contents) {
        this.messageType = messageType;
        this.contents = contents;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "IncomingMessage{" +
                "messageType=" + messageType +
                ", contents='" + contents + '\'' +
                '}';
    }
}
