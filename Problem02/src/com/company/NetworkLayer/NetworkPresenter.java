package com.company.NetworkLayer;

import com.company.DataLinkLayer.DataLinkPresenter;
import com.company.DataLinkLayer.IDataLinkPresenter;
import com.company.JavaBean.IPv4Packet;
import com.company.TransportLayer.ITransportPresenter;
import com.company.TransportLayer.TransportPresenter;
import com.company.Utils.DataTranslationUtil;
import com.company.Utils.RelativeParameterUtil;
import com.company.Utils.SimpleProtocolType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yasic on 16-6-7.
 */
public class NetworkPresenter implements INetworkPresenter {
    private static byte[] TOTALDATA = new byte[0];

    @Override
    public boolean getMessageFromTransport(byte[] data, SimpleProtocolType simpleProtocolType) {
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
                isSuccessful &= sendMessageToDataLink(resultData);
            }
            return isSuccessful;
        }
        resultData = serialize(data, simpleProtocolType, 0, false, false);
        System.out.println("IPPaket");
        DataTranslationUtil.printStringFromByteAray(resultData);
        if (resultData == null){
            return false;
        }
        isSuccessful = sendMessageToDataLink(resultData);
        return isSuccessful;
    }

    @Override
    public boolean getMessageFromDataLink(byte[] data) {
        deSerialize(data);
        return false;
    }

    @Override
    public boolean sendMessageToTransport(byte[] data, SimpleProtocolType simpleProtocolType) {
        ITransportPresenter iTransportPresenter = new TransportPresenter();
        iTransportPresenter.getMessageFromNetwork(data, simpleProtocolType);
        return false;
    }

    @Override
    public boolean sendMessageToDataLink(byte[] data) {
        IDataLinkPresenter iDataLinkPresenter = new DataLinkPresenter();
        return iDataLinkPresenter.getMessageFromNetwork(data);
    }

    private byte[] serialize(byte[] targetData, SimpleProtocolType simpleProtocolType, int sequence, boolean isSplit, boolean isEnd){
        if (targetData == null || targetData.length == 0){
            System.out.println("Data is empty!");
            return null;
        }
        IPv4Packet iPv4Packet;
        short totalLength = (short) (5 * 4 + targetData.length);
        byte protocolType = 0;
        switch (simpleProtocolType){
            case TCP:
                protocolType = 6;
                break;
            case UDP:
                protocolType = 17;
                break;
            default:
                System.out.println("Protocol Type is Wrong!");
                break;
        }
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
                        .setProtocol(protocolType)
                        .setHeaderCheckSum((short) 0)
                        .setSourceAddress(RelativeParameterUtil.SOURCEIPADDRESS)
                        .setDestinationAddress(RelativeParameterUtil.DESTINATIONIPADDRESS)
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
                        .setProtocol(protocolType)
                        .setHeaderCheckSum((short) 0)
                        .setSourceAddress(RelativeParameterUtil.SOURCEIPADDRESS)
                        .setDestinationAddress(RelativeParameterUtil.DESTINATIONIPADDRESS)
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
                    .setFlags((byte) (2))
                    .setFragmentOffset((short) sequence)
                    .setTtl((byte) 13)
                    .setProtocol(protocolType)
                    .setHeaderCheckSum((short) 0)
                    .setSourceAddress(RelativeParameterUtil.SOURCEIPADDRESS)
                    .setDestinationAddress(RelativeParameterUtil.DESTINATIONIPADDRESS)
                    .setTransprotData(targetData)
                    .setOptions(null)
                    .build();
            return iPv4Packet.serialize();
        }
    }

    private void deSerialize(byte[] data){
        boolean isSplit = false;
        boolean isEnd = false;
        byte flag = Byte.parseByte(Integer.toHexString((data[6] >> 5) & 0xFF));
        byte protocolType = Byte.parseByte(Integer.toHexString(data[9] & 0xFF));
        switch (flag){
            case 0:
                isSplit = true;
                isEnd = true;
                break;
            case 1:
                isSplit = true;
                isEnd = false;
                break;
            case 2:
                isSplit = false;
                break;
            default:
                System.out.println("Error during deserialize in Network layer");
                break;
        }
        if (isSplit){
            if (!isEnd){
                data = Arrays.copyOfRange(data, 20, data.length - 1);
                TOTALDATA = DataTranslationUtil.spliceByte(data, TOTALDATA);
                return;
            }
            if (isEnd){
                data = Arrays.copyOfRange(data, 20, data.length - 1);
                TOTALDATA = DataTranslationUtil.spliceByte(data, TOTALDATA);
            }
        }
        else {
            TOTALDATA = new byte[0];
            data = Arrays.copyOfRange(data, 20, data.length - 1);
            TOTALDATA = DataTranslationUtil.spliceByte(data, TOTALDATA);
        }
        DataTranslationUtil.printStringFromByteAray(TOTALDATA);
        switch (protocolType){
            case 11:
                sendMessageToTransport(TOTALDATA, SimpleProtocolType.UDP);
                break;
            case 6:
                sendMessageToTransport(TOTALDATA, SimpleProtocolType.TCP);
                break;
            default:
                System.out.println("Error during deserialize in Network layer");
                break;
        }
    }

}
