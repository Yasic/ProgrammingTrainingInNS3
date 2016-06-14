package com.company.TransportLayer;

import com.company.Utils.SimpleProtocolType;

/**
 * Created by yasic on 16-6-7.
 */
public interface ITransportPresenter {
    boolean getMessageFromApplication(String data, SimpleProtocolType simpleProtocolType);
    boolean getMessageFromNetwork(byte[] data, SimpleProtocolType simpleProtocolType);
    boolean sendMessageToApplication(String data);
    boolean sendMessageToNetwork(byte[] data, SimpleProtocolType simpleProtocolType);
}
