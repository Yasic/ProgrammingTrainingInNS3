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
    /**
     * the total byte array constituted by all byte arrays comes from data link layer
     */
    private static byte[] TOTALDATA = new byte[0];

    private NetworkPresenter(){}

    public static NetworkPresenter getInstance(){
        return NetworkPresenterInstance.NETWORK_PRESENTER;
    }

    private static class NetworkPresenterInstance{
        private static final NetworkPresenter NETWORK_PRESENTER = new NetworkPresenter();
    }

    @Override
    public boolean getMessageFromTransport(byte[] data, SimpleProtocolType simpleProtocolType) {
        byte[] resultData;
        boolean isSuccessful = true;
        if (data.length > RelativeParameterUtil.MAXSIZEOFIPPACKET){
            List<byte[]> bytes = DataTranslationUtil.splitByteArray(data, RelativeParameterUtil.MAXSIZEOFIPPACKET);
            for (int i = 0; i < bytes.size(); i++){
                if (i == bytes.size() - 1){
                    resultData = serialize(bytes.get(i), simpleProtocolType, i, true, true);
                }
                else {
                    resultData = serialize(bytes.get(i), simpleProtocolType, i, true, false);
                }
                if (resultData == null){
                    return false;
                }
                isSuccessful &= sendMessageToDataLink(resultData);
            }
            return isSuccessful;
        }
        resultData = serialize(data, simpleProtocolType, 0, false, false);
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
        ITransportPresenter iTransportPresenter = TransportPresenter.getInstance();
        iTransportPresenter.getMessageFromNetwork(data, simpleProtocolType);
        return false;
    }

    @Override
    public boolean sendMessageToDataLink(byte[] data) {
        IDataLinkPresenter iDataLinkPresenter = DataLinkPresenter.getInstance();
        return iDataLinkPresenter.getMessageFromNetwork(data);
    }

    /**
     * serialize the target byte array to IPv4 Packet
     * @param targetData target data which comes from transport layer
     * @param simpleProtocolType the type of the protocol that need to be packaged
     * @param sequence the sequence number of the target byte array
     * @param isSplit if the target byte array has been split by network layer
     * @param isEnd if the target byte array is the end of the byte arrays
     * @return the byte array that has been serialized
     */
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
                protocolType = RelativeParameterUtil.TCPPROTOCOLNUM;
                break;
            case UDP:
                protocolType = RelativeParameterUtil.UDPPROTOCOLNUM;
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

    /**
     * deserialize the target byte array which comes from the data link layer
     * @param data the target byte array
     */
    private void deSerialize(byte[] data){
        System.out.println("IP Deserialize");
        boolean isSplit = false;
        boolean isEnd = false;
        byte flag = Byte.parseByte(Integer.toHexString((data[6] >> 5) & 0xFF));
        byte protocolType = Byte.parseByte(Integer.toHexString(data[9] & 0xFF));

        /**
         * set the value of isSplit and isEnd
         */
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
                data = Arrays.copyOfRange(data, 20, data.length);
                TOTALDATA = DataTranslationUtil.spliceByte(data, TOTALDATA);
                DataTranslationUtil.printStringFromByteAray(TOTALDATA);
                return;
            }
            if (isEnd){
                data = Arrays.copyOfRange(data, 20, data.length);
                TOTALDATA = DataTranslationUtil.spliceByte(data, TOTALDATA);
                DataTranslationUtil.printStringFromByteAray(TOTALDATA);
            }
        }
        else {
            TOTALDATA = new byte[0];
            data = Arrays.copyOfRange(data, 20, data.length);
            TOTALDATA = DataTranslationUtil.spliceByte(data, TOTALDATA);
            DataTranslationUtil.printStringFromByteAray(TOTALDATA);
        }



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
