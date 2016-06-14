package com.company.ApplicationLayer;
import com.company.Utils.SimpleProtocolType;

/**
 * Created by yasic on 16-6-7.
 */
public interface IApplicationPresenter {
    void sendMessageToTransportLayer(String message, SimpleProtocolType simpleProtocolType);
    void sendMessageToUser(String message);
    void getMessageFromUser(String message);
    void getMessageFromUser(String message, SimpleProtocolType simpleProtocolType);
    void getMessageFromTransport(String message);
}
