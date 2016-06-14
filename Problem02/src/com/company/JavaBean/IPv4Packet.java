package com.company.JavaBean;

import java.nio.ByteBuffer;

/**
 * Created by yasic on 16-6-8.
 */
public class IPv4Packet extends BaseJavaBean {
    private byte virsion;
    private byte HLEN;
    private byte DS;
    private short totalLength;
    private short identification;
    private byte Flags;
    private short fragmentOffset;
    private byte ttl;
    private byte protocol;
    private short headerCheckSum;
    private int sourceAddress;
    private int destinationAddress;
    private byte[] options;
    private byte[] transprotData;

    private IPv4Packet() {
    }

    @Override
    public byte[] serialize() {
        byte[] bytes = new byte[this.totalLength];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putShort((short) ((this.virsion << 12) + (this.HLEN << 8) + this.DS));
        byteBuffer.putShort(this.totalLength);
        byteBuffer.putShort(this.identification);
        byteBuffer.putShort((short) ((Flags << 13) + this.fragmentOffset));
        byteBuffer.putShort((short) ((this.ttl << 8) + this.protocol));
        byteBuffer.putShort(this.headerCheckSum);
        byteBuffer.putInt(this.sourceAddress);
        byteBuffer.putInt(this.destinationAddress);
        byteBuffer.put(this.transprotData);
        return byteBuffer.array();
    }

    public static class Builder {
        IPv4Packet iPv4Packet = new IPv4Packet();

        public Builder setVirsion(byte virsion) {
            iPv4Packet.virsion = virsion;
            return this;
        }

        public Builder setHLEN(byte HLEN) {
            iPv4Packet.HLEN = HLEN;
            return this;
        }

        public Builder setDS(byte DS) {
            iPv4Packet.DS = DS;
            return this;
        }

        public Builder setTotalLength(short totalLength) {
            iPv4Packet.totalLength = totalLength;
            return this;
        }

        public Builder setIdentification(short identification) {
            iPv4Packet.identification = identification;
            return this;
        }

        public Builder setFlags(byte flags) {
            iPv4Packet.Flags = flags;
            return this;
        }

        public Builder setFragmentOffset(short fragmentOffset) {
            iPv4Packet.fragmentOffset = fragmentOffset;
            return this;
        }

        public Builder setTtl(byte ttl) {
            iPv4Packet.ttl = ttl;
            return this;
        }

        public Builder setProtocol(byte protocol) {
            iPv4Packet.protocol = protocol;
            return this;
        }

        public Builder setHeaderCheckSum(short headerCheckSum) {
            iPv4Packet.headerCheckSum = headerCheckSum;
            return this;
        }

        public Builder setSourceAddress(int sourceAddress) {
            iPv4Packet.sourceAddress = sourceAddress;
            return this;
        }

        public Builder setDestinationAddress(int destinationAddress) {
            iPv4Packet.destinationAddress = destinationAddress;
            return this;
        }

        public Builder setOptions(byte[] options) {
            iPv4Packet.options = options;
            return this;
        }

        public Builder setTransprotData(byte[] transprotData) {
            iPv4Packet.transprotData = transprotData;
            return this;
        }

        public IPv4Packet build() {
            return iPv4Packet;
        }
    }

}
