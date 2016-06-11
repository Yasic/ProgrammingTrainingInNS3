package TransportLayer;

import Utils.SimpleProtocolType;

/**
 * Created by yasic on 16-6-7.
 */
public interface ITransportPresenter {
    boolean getMessageFromApplication(String data, SimpleProtocolType simpleProtocolType);
}
