package com.company.ApplicationLayer;
import com.company.TransportLayer.ITransportPresenter;
import com.company.TransportLayer.TransportPresenter;
import com.company.Utils.SimpleProtocolType;

/**
 * Created by yasic on 16-6-7.
 */
public class ApplicationPresenter implements IApplicationPresenter {

    private ApplicationPresenter(){}

    public static ApplicationPresenter getInstance(){
        return ApplicationPresenterInstance.APPLICATION_PRESENTER;
    }

    private static class ApplicationPresenterInstance {
        private static final ApplicationPresenter APPLICATION_PRESENTER = new ApplicationPresenter();
    }

    @Override
    public void sendMessageToTransportLayer(String message, SimpleProtocolType type) {
        ITransportPresenter iTransportPresenter;
        iTransportPresenter = TransportPresenter.getInstance();
        iTransportPresenter.getMessageFromApplication(message, type);
    }

    @Override
    public void sendMessageToUser(String message) {
        //no need to send message to user currently
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
        System.out.println("Application");
        System.out.println(message);
    }
}
