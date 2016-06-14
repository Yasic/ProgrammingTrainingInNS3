package com.company.DataLinkLayer;

/**
 * Created by yasic on 16-6-7.
 */
public interface IDataLinkPresenter {
    boolean getMessageFromNetwork(byte[] data);
    boolean getMessageFromPHY(byte[] data);
    boolean sendMessageToNetwork(byte[] data);
    boolean sendMessageToPHY(byte[] data);
}
