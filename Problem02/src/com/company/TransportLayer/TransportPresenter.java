package com.company.TransportLayer;

import com.company.JavaBean.TCPMessage;
import com.company.JavaBean.UDPMessage;
import com.company.NetworkLayer.INetworkPresenter;
import com.company.NetworkLayer.NetworkPresenter;
import com.company.Utils.DataTranslationUtil;
import com.company.Utils.RelativeParameterUtil;
import com.company.Utils.SimpleProtocolType;

import java.util.Arrays;
import java.util.List;

import static com.company.Utils.SimpleProtocolType.TCP;
import static com.company.Utils.SimpleProtocolType.UDP;

/**
 * Created by yasic on 16-6-7.
 */
public class TransportPresenter implements ITransportPresenter {
    private int randomSequence = (int) ((2147483647) * Math.random() + 1);
    private static int SEQUENCE = 0;
    private static byte[] TOTALDATA = new byte[0];

    @Override
    public boolean getMessageFromApplication(String string, SimpleProtocolType simpleProtocolType) {
        byte[] data = string.getBytes();
        boolean isSuccessful = true;
        if (simpleProtocolType == TCP){
            if (data.length > 1046){
                List<byte[]> bytes = DataTranslationUtil.splitByteArray(data, 1046);
                for (int i = 0; i < bytes.size(); i++){
                    if (i == bytes.size() - 1){
                        byte[] tempData = tcpSplitSerialize(randomSequence + i, bytes.get(i), true);
                        isSuccessful &= sendMessageToNetwork(tempData, simpleProtocolType);
                        continue;
                    }
                    byte[] tempData = tcpSplitSerialize(randomSequence + i, bytes.get(i), false);
                    isSuccessful &= sendMessageToNetwork(tempData, simpleProtocolType);
                }
                return isSuccessful;
            }
            else {
                byte[] resultData = tcpSplitSerialize(randomSequence, data, true);
                //DataTranslationUtil.printStringFromByteAray(resultData);
                isSuccessful = sendMessageToNetwork(resultData, simpleProtocolType);
                return isSuccessful;
            }
        }
        else if (simpleProtocolType == UDP){
            byte[] resultData = udpSerialize(data);
            isSuccessful = sendMessageToNetwork(resultData, simpleProtocolType);
            return isSuccessful;
        }
        return false;
    }

    @Override
    public boolean getMessageFromNetwork(byte[] data, SimpleProtocolType simpleProtocolType) {
        System.out.println("getMessageFromNetwork");
        switch (simpleProtocolType){
            case TCP:
                tcpDeserialize(data);
                break;
            case UDP:
                udpDeserialize(data);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean sendMessageToApplication(String data) {
        return false;
    }

    @Override
    public boolean sendMessageToNetwork(byte[] data, SimpleProtocolType simpleProtocolType) {
        INetworkPresenter iNetworkPresenter = new NetworkPresenter();
        boolean isSuccessful = true;
        isSuccessful &= iNetworkPresenter.getMessageFromTransport(data, simpleProtocolType);
        return isSuccessful;
    }


    private byte[] udpSerialize(byte[] targetData){
        if (targetData == null || targetData.length == 0){
            System.out.println("Data is empty!");
            return null;
        }
        UDPMessage udpMessage = new UDPMessage.Builder()
                .setSourcePort(RelativeParameterUtil.SOURCEPROT)
                .setDestinationPort(RelativeParameterUtil.DESTINATIONPROT)
                .setLength((short) (2 * 4 + targetData.length))
                .setCheckSum((short) 0)
                .setApplicationData(targetData)
                .build();
        return udpMessage.serialize();
    }

    private void udpDeserialize(byte[] data){
        String resultString = "";
        data = Arrays.copyOfRange(data, 8 ,data.length - 1);
        DataTranslationUtil.printStringFromByteAray(data);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < data.length; i ++){
            stringBuffer.append((char)data[i]);
        }
        System.out.println(stringBuffer.toString());
    }

    private byte[] tcpSplitSerialize(int sequence, byte[] targetData, boolean isEnd){
        byte FIN = 0;
        if (isEnd){
            FIN = 1;
        }
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
                .setFIN(FIN)
                .setWindowSize(Short.MAX_VALUE)
                .setCheckSum(getCheckSum())
                .setUrgentPointer((short) 0)
                .setApplicationData(targetData)
                .build();
        return tcpMessage.serialize();
    }

    private void tcpDeserialize(byte[] data){
        //System.out.println(data[13] & 0xf1);
        int FIN = (data[13] & 0xf1);
        if (FIN == 1){
            data = Arrays.copyOfRange(data, 20, data.length - 1);
            TOTALDATA = DataTranslationUtil.spliceByte(data, TOTALDATA);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < TOTALDATA.length; i ++){
                stringBuffer.append((char)TOTALDATA[i]);
            }
            System.out.println(stringBuffer.toString());
            return;
        }
        DataTranslationUtil.printStringFromByteAray(data);
        int sequence = ((data[4] >> 4) + (data[4] & 0x0F) + (data[5] >> 4) + (data[5] & 0x0F) + (data[6] >> 4) + (data[6] & 0x0F) + (data[7] >> 4) + (data[7] & 0x0F));
        if (sequence > SEQUENCE){
            data = Arrays.copyOfRange(data, 20, data.length - 1);
            TOTALDATA = DataTranslationUtil.spliceByte(data, TOTALDATA);
        }
    }

    private short getCheckSum(){
        return 0;
    }
}
