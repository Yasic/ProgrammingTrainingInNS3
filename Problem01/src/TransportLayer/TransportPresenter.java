package TransportLayer;

import JavaBean.TCPMessage;
import JavaBean.UDPMessage;
import NetworkLayer.INetworkPresenter;
import NetworkLayer.NetworkPresenter;
import Utils.DataTranslationUtil;
import Utils.RelativeParameterUtil;
import Utils.SimpleProtocolType;

import java.util.List;

import static Utils.SimpleProtocolType.TCP;

/**
 * Created by yasic on 16-6-7.
 */
public class TransportPresenter implements ITransportPresenter {
    private int randomSequence = (int) ((2147483647) * Math.random() + 1);
    private byte[] data;
    private INetworkPresenter iNetworkPresenter;

    @Override
    public boolean getMessageFromApplication(String string, SimpleProtocolType simpleProtocolType) {
        this.data = string.getBytes();
        iNetworkPresenter = new NetworkPresenter();
        boolean isSuccessful = true;
        if (simpleProtocolType == TCP){
            if (this.data.length > 1046){
                List<byte[]> bytes = DataTranslationUtil.splitByteArray(this.data, 1046);
                for (int i = 0; i < bytes.size(); i++){
                    byte[] tempData = tcpSplitSerialize(randomSequence + i, bytes.get(i));
                    isSuccessful &= iNetworkPresenter.getMessageFromTransport(tempData, simpleProtocolType);
                }
                return isSuccessful;
            }
            else {
                byte[] resultData = tcpSplitSerialize(randomSequence, this.data);
                //DataTranslationUtil.printStringFromByteAray(resultData);
                isSuccessful = iNetworkPresenter.getMessageFromTransport(resultData, simpleProtocolType);
                return isSuccessful;
            }
        }
        byte[] resultData = udpSerialize();
        //DataTranslationUtil.printStringFromByteAray(resultData);
        isSuccessful = iNetworkPresenter.getMessageFromTransport(resultData, simpleProtocolType);
        return isSuccessful;
    }


    protected byte[] udpSerialize(){
        if (this.data == null || this.data.length == 0){
            System.out.println("Data is empty!");
            return null;
        }
        UDPMessage udpMessage = new UDPMessage.Builder()
                .setSourcePort(RelativeParameterUtil.SOURCEPROT)
                .setDestinationPort(RelativeParameterUtil.DESTINATIONPROT)
                .setLength((short) (2 * 4 + this.data.length))
                .setCheckSum((short) 0)
                .setApplicationData(this.data)
                .build();
        return udpMessage.serialize();
    }

    private byte[] tcpSplitSerialize(int sequence, byte[] targetData){
        TCPMessage tcpMessage = new TCPMessage.Builder().setSourcePort(RelativeParameterUtil.SOURCEPROT)
                .setDestinationPort(RelativeParameterUtil.DESTINATIONPROT)
                .setSequenceNumber(sequence)
                .setAcknowledgmentNumber(0)
                .setDataOffset(RelativeParameterUtil.DATAOFFSET)
                .setNS((byte) 0)
                .setCWR((byte) 0)
                .setURG((byte) 0)
                .setACK((byte) 0)
                .setPSH((byte) 1)
                .setRST((byte) 0)
                .setSYN((byte) 0)
                .setFIN((byte) 0)
                .setWindowSize(Short.MAX_VALUE)
                .setCheckSum(getCheckSum())
                .setUrgentPointer((short) 0)
                .setApplicationData(targetData)
                .build();
        return tcpMessage.serialize();
    }

    private short getCheckSum(){
        return 0;
    }
}
