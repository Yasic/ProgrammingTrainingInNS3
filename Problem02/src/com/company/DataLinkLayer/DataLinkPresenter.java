package com.company.DataLinkLayer;

import com.company.NetworkLayer.INetworkPresenter;
import com.company.NetworkLayer.NetworkPresenter;

/**
 * Created by yasic on 16-6-7.
 */
public class DataLinkPresenter implements IDataLinkPresenter {
    /*private byte[] data;
    private INetworkPresenter iNetworkPresenter;*/
    @Override
    public boolean getMessageFromNetwork(byte[] data) {
        //System.out.println("Datalink Presenter");
        //DataTranslationUtil.printStringFromByteAray(data);
        sendMessageToNetwork(data);
        return true;
    }

    @Override
    public boolean getMessageFromPHY(byte[] data) {
        sendMessageToNetwork(data);
        return false;
    }

    @Override
    public boolean sendMessageToNetwork(byte[] data) {
        INetworkPresenter iNetworkPresenter = new NetworkPresenter();
        iNetworkPresenter.getMessageFromDataLink(data);
        return false;
    }

    @Override
    public boolean sendMessageToPHY(byte[] data) {
        return false;
    }

    /*protected byte[] serialize(SimpleProtocolType simpleProtocolType){
        if (data == null || data.length == 0){
            System.out.println("Data is empty!");
            return null;
        }
        EthernetFrame ethernetFrame = new EthernetFrame.Builder()
                .setDestinationAddress(RelativeParameterUtil.DESTINATIONMACADDRESS)
                .setSourceAddress(RelativeParameterUtil.SOURCEMACADDRESS)
                .setType((byte) 0)
                .setNetworkData(this.data)
                .setCheckSum((byte) 0)
                .build();
        return ethernetFrame.serialize();
    }*/
}
