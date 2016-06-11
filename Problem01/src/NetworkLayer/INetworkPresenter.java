package NetworkLayer;

import Utils.SimpleProtocolType;

/**
 * Created by yasic on 16-6-7.
 */
public interface INetworkPresenter {
    boolean getMessageFromTransport(byte[] type, SimpleProtocolType simpleProtocolType);
}
