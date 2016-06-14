package DataLinkLayer;

import JavaBean.EthernetFrame;
import Utils.DataTranslationUtil;
import Utils.RelativeParameterUtil;
import Utils.SimpleProtocolType;

/**
 * Created by yasic on 16-6-7.
 */
public class DataLinkPresenter implements IDataLinkPresenter {
    private byte[] data;
    @Override
    public boolean getMessageFromNetwork(byte[] data, SimpleProtocolType simpleProtocolType) {
        this.data = data;
        DataTranslationUtil.printStringFromByteAray(data);
        //byte[] resultData = serialize(simpleProtocolType);
        return true;
    }

    protected byte[] serialize(SimpleProtocolType simpleProtocolType){
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
    }
}
