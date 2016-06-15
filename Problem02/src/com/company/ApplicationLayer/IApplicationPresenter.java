package com.company.ApplicationLayer;
import com.company.Utils.SimpleProtocolType;

/**
 * Created by yasic on 16-6-7.
 */
public interface IApplicationPresenter {
    /**
     * send message to transport layer
     * @param message the message which need to be sent
     * @param simpleProtocolType the protocol type of this message
     */
    void sendMessageToTransportLayer(String message, SimpleProtocolType simpleProtocolType);

    /**
     * send message to user
     * @param message the message which need to be sent
     */
    void sendMessageToUser(String message);

    /**
     * get message from user
     * @param message the message which comes from user program
     */
    void getMessageFromUser(String message);

    /**
     * get message from user and has been pointed the type of protocol in transport layer
     * @param message the message which comes from user program
     * @param simpleProtocolType the protocol type
     */
    void getMessageFromUser(String message, SimpleProtocolType simpleProtocolType);

    /**
     * get message from transport and is going to send the message to user
     * @param message the message which comes from the transport layer
     */
    void getMessageFromTransport(String message);
}
