package ApplicationLayer;

import TransportLayer.ITransportPresenter;
import TransportLayer.TransportPresenter;
import Utils.SimpleProtocolType;

/**
 * Created by yasic on 16-6-7.
 */
public class ApplicationPresenter implements IApplicationPresenter {
    private ITransportPresenter iTransportPresenter;

    @Override
    public void sendMessageToTransportLayer(String message, SimpleProtocolType type) {
        iTransportPresenter = new TransportPresenter();
        iTransportPresenter.getMessageFromApplication(message, type);
    }
}
