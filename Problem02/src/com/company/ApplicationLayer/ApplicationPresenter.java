package com.company.ApplicationLayer;
import com.company.TransportLayer.ITransportPresenter;
import com.company.TransportLayer.TransportPresenter;
import com.company.Utils.SimpleProtocolType;

/**
 * Created by yasic on 16-6-7.
 */
public class ApplicationPresenter implements IApplicationPresenter {

    @Override
    public void sendMessageToTransportLayer(String message, SimpleProtocolType type) {
        ITransportPresenter iTransportPresenter;
        iTransportPresenter = new TransportPresenter();
        iTransportPresenter.getMessageFromApplication(message, type);
    }

    @Override
    public void sendMessageToUser(String message) {

    }

    @Override
    public void getMessageFromUser(String message) {
        sendMessageToTransportLayer(message, SimpleProtocolType.UDP);
    }

    @Override
    public void getMessageFromUser(String message, SimpleProtocolType simpleProtocolType) {
        sendMessageToTransportLayer(message, simpleProtocolType);
    }

    @Override
    public void getMessageFromTransport(String message) {

    }
}
