package NetworkLayer;

import DataLinkLayer.DataLinkPresenter;
import DataLinkLayer.IDataLinkPresenter;
import JavaBean.IPv4Packet;
import Utils.BasePresenter;
import Utils.DataTranslationUtil;
import Utils.RelativeParameterUtil;
import Utils.SimpleProtocolType;

import java.util.List;

/**
 * Created by yasic on 16-6-7.
 */
public class NetworkPresenter extends BasePresenter implements INetworkPresenter {
    private byte[] data;
    private IDataLinkPresenter iDataLinkPresenter;
    @Override
    public boolean getMessageFromTransport(byte[] data, SimpleProtocolType simpleProtocolType) {
        this.data = data;
        iDataLinkPresenter = new DataLinkPresenter();
        byte[] resultData;
        boolean isSuccessful = true;
        if (data.length > 1400){
            List<byte[]> bytes = DataTranslationUtil.splitByteArray(data, 1400);
            for (int i = 0; i < bytes.size(); i++){
                if (i == bytes.size() - 1){
                    resultData = serialize(bytes.get(i), simpleProtocolType, i, true, true);
                }
                else {
                    resultData = serialize(bytes.get(i), simpleProtocolType, i, true, false);
                }
                System.out.println("IPPaket");
                DataTranslationUtil.printStringFromByteAray(resultData);
                if (resultData == null){
                    return false;
                }
                isSuccessful &= iDataLinkPresenter.getMessageFromNetwork(resultData, simpleProtocolType);
            }
            return isSuccessful;
        }
        resultData = serialize(this.data, simpleProtocolType, 0, false, false);
        System.out.println("IPPaket");
        DataTranslationUtil.printStringFromByteAray(resultData);
        if (resultData == null){
            return false;
        }
        isSuccessful = iDataLinkPresenter.getMessageFromNetwork(resultData, simpleProtocolType);
        return isSuccessful;
    }

    private byte[] serialize(byte[] targetData, SimpleProtocolType simpleProtocolType, int sequence, boolean isSplit, boolean isEnd){
        if (targetData == null || targetData.length == 0){
            System.out.println("Data is empty!");
            return null;
        }
        IPv4Packet iPv4Packet;
        short totalLength = (short) (5 * 4 + targetData.length);
        switch (simpleProtocolType){
            case TCP:
                if (isSplit){
                    if (!isEnd){
                        iPv4Packet = new IPv4Packet.Builder()
                                .setVirsion(RelativeParameterUtil.IPVIRSION)
                                .setHLEN((byte) 5)
                                .setDS((byte) 0)
                                .setTotalLength(totalLength)
                                .setIdentification((short) 0)
                                .setFlags((byte) (1))
                                .setFragmentOffset((short) sequence)
                                .setTtl((byte) 13)
                                .setProtocol((byte) 6)
                                .setHeaderCheckSum((short) 0)
                                .setSourceAddress(0)
                                .setDestinationAddress(0)
                                .setTransprotData(targetData)
                                .setOptions(null)
                                .build();
                        return iPv4Packet.serialize();
                    }
                    else {
                        iPv4Packet = new IPv4Packet.Builder()
                                .setVirsion(RelativeParameterUtil.IPVIRSION)
                                .setHLEN((byte) 5)
                                .setDS((byte) 0)
                                .setTotalLength(totalLength)
                                .setIdentification((short) 0)
                                .setFlags((byte) (0))
                                .setFragmentOffset((short) sequence)
                                .setTtl((byte) 13)
                                .setProtocol((byte) 6)
                                .setHeaderCheckSum((short) 0)
                                .setSourceAddress(0)
                                .setDestinationAddress(0)
                                .setTransprotData(targetData)
                                .setOptions(null)
                                .build();
                        return iPv4Packet.serialize();
                    }
                }
                else {
                    iPv4Packet = new IPv4Packet.Builder()
                            .setVirsion(RelativeParameterUtil.IPVIRSION)
                            .setHLEN((byte) 5)
                            .setDS((byte) 0)
                            .setTotalLength(totalLength)
                            .setIdentification((short) 0)
                            .setFlags((byte) (1 >> 1))
                            .setFragmentOffset((short) sequence)
                            .setTtl((byte) 13)
                            .setProtocol((byte) 6)
                            .setHeaderCheckSum((short) 0)
                            .setSourceAddress(0)
                            .setDestinationAddress(0)
                            .setTransprotData(targetData)
                            .setOptions(null)
                            .build();
                    return iPv4Packet.serialize();
                }
            case UDP:
                if (isSplit){
                    if (!isEnd){
                        iPv4Packet = new IPv4Packet.Builder()
                                .setVirsion(RelativeParameterUtil.IPVIRSION)
                                .setHLEN((byte) 5)
                                .setDS((byte) 0)
                                .setTotalLength(totalLength)
                                .setIdentification((short) 0)
                                .setFlags((byte) 0)
                                .setFragmentOffset((short) sequence)
                                .setTtl((byte) 13)
                                .setProtocol((byte) 17)
                                .setHeaderCheckSum((short) 0)
                                .setSourceAddress(0)
                                .setDestinationAddress(0)
                                .setTransprotData(targetData)
                                .setOptions(null)
                                .build();
                        return iPv4Packet.serialize();
                    }
                    else {
                        iPv4Packet = new IPv4Packet.Builder()
                                .setVirsion(RelativeParameterUtil.IPVIRSION)
                                .setHLEN((byte) 5)
                                .setDS((byte) 0)
                                .setTotalLength(totalLength)
                                .setIdentification((short) 0)
                                .setFlags((byte) 1)
                                .setFragmentOffset((short) sequence)
                                .setTtl((byte) 13)
                                .setProtocol((byte) 17)
                                .setHeaderCheckSum((short) 0)
                                .setSourceAddress(0)
                                .setDestinationAddress(0)
                                .setTransprotData(targetData)
                                .setOptions(null)
                                .build();
                        return iPv4Packet.serialize();
                    }
                }
                else {
                    iPv4Packet = new IPv4Packet.Builder()
                            .setVirsion(RelativeParameterUtil.IPVIRSION)
                            .setHLEN((byte) 5)
                            .setDS((byte) 0)
                            .setTotalLength(totalLength)
                            .setIdentification((short) 0)
                            .setFlags((byte) (1>>1))
                            .setFragmentOffset((short) sequence)
                            .setTtl((byte) 13)
                            .setProtocol((byte) 17)
                            .setHeaderCheckSum((short) 0)
                            .setSourceAddress(0)
                            .setDestinationAddress(0)
                            .setTransprotData(targetData)
                            .setOptions(null)
                            .build();
                    return iPv4Packet.serialize();
                }
            default:
                System.out.println("Protocol Type is Wrong!");
                break;
        }
        return null;
    }
}
