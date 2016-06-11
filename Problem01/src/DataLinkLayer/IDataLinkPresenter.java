package DataLinkLayer;

import Utils.SimpleProtocolType;

/**
 * Created by yasic on 16-6-7.
 */
public interface IDataLinkPresenter {
    boolean getMessageFromNetwork(byte[] data, SimpleProtocolType simpleProtocolType);
}
