package com.company.JavaBean;

/**
 * Created by yasic on 16-6-8.
 */
public class EthernetFrame extends BaseJavaBean {
    private byte destinationAddress;
    private byte sourceAddress;
    private byte type;
    private byte[] NetworkData;
    private byte checkSum;

    private EthernetFrame() {
    }

    @Override
    public byte[] serialize() {
        return null;
    }

    public static class Builder{
        private EthernetFrame ethernetFrame = new EthernetFrame();

        public Builder setDestinationAddress(byte destinationAddress) {
            ethernetFrame.destinationAddress = destinationAddress;
            return this;
        }

        public Builder setSourceAddress(byte sourceAddress) {
            ethernetFrame.sourceAddress = sourceAddress;
            return this;
        }

        public Builder setType(byte type) {
            ethernetFrame.type = type;
            return this;
        }

        public Builder setNetworkData(byte[] networkData) {
            ethernetFrame.NetworkData = networkData;
            return this;
        }

        public Builder setCheckSum(byte checkSum) {
            ethernetFrame.checkSum = checkSum;
            return this;
        }

        public EthernetFrame build(){
            return ethernetFrame;
        }
    }

}
