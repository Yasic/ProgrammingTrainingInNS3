package ApplicationLayer;

import Utils.SimpleProtocolType;

/**
 * Created by yasic on 16-6-7.
 */
public interface IApplicationPresenter {
    void sendMessageToTransportLayer(String message, SimpleProtocolType type);
}
