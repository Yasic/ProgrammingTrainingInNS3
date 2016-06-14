package com.company.JavaBean;

import java.nio.ByteBuffer;

/**
 * Created by yasic on 16-6-8.
 */
public class UDPMessage extends BaseJavaBean {
    private short sourcePort;
    private short destinationPort;
    private short length;
    private short checkSum;
    private byte[] applicationData;

    private UDPMessage() {
    }

    @Override
    public byte[] serialize() {
        byte[] bytes = new byte[this.length];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putShort(this.sourcePort);
        byteBuffer.putShort(this.destinationPort);
        byteBuffer.putShort(this.length);
        byteBuffer.putShort(this.checkSum);
        byteBuffer.put(this.applicationData);
        return byteBuffer.array();
    }

    public static class Builder{
        private UDPMessage udpMessage = new UDPMessage();

        public Builder(){}

        public Builder setSourcePort(short sourcePort) {
            udpMessage.sourcePort = sourcePort;
            return this;
        }

        public Builder setDestinationPort(short destinationPort) {
            udpMessage.destinationPort = destinationPort;
            return this;
        }

        public Builder setLength(short length) {
            udpMessage.length = length;
            return this;
        }

        public Builder setCheckSum(short checkSum) {
            udpMessage.checkSum = checkSum;
            return this;
        }

        public Builder setApplicationData(byte[] applicationData) {
            udpMessage.applicationData = applicationData;
            return this;
        }

        public UDPMessage build(){
            return udpMessage;
        }
    }

}
