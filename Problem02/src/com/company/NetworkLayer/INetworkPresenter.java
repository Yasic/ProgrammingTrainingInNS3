package com.company.NetworkLayer;

import com.company.Utils.SimpleProtocolType;

/**
 * Created by yasic on 16-6-7.
 */
public interface INetworkPresenter {
    boolean getMessageFromTransport(byte[] data, SimpleProtocolType simpleProtocolType);
    boolean getMessageFromDataLink(byte[] data);
    boolean sendMessageToTransport(byte[] data, SimpleProtocolType simpleProtocolType);
    boolean sendMessageToDataLink(byte[] data);
}
