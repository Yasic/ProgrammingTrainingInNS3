package com.company.JavaBean;

import java.nio.ByteBuffer;

/**
 * Created by yasic on 16-6-7.
 */
public class TCPMessage extends BaseJavaBean {
    public short sourcePort;
    private short destinationPort;
    private int sequenceNumber;
    private int acknowledgmentNumber;
    private byte dataOffset;
    private static final byte reserved = 0x0;
    private byte NS;
    private byte CWR;
    private byte ECE;
    private byte URG;
    private byte ACK;
    private byte PSH;
    private byte RST;
    private byte SYN;
    private byte FIN;
    private short windowSize;
    private short checkSum;
    private short urgentPointer;
    private byte[] options = new byte[0];
    private byte[] applicationData;

    private TCPMessage() {
    }

    @Override
    public byte[] serialize() {
        int totalLength = this.dataOffset * 4 + this.applicationData.length;
        byte[] bytes = new byte[totalLength];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.putShort(this.sourcePort);
        byteBuffer.putShort(this.destinationPort);
        byteBuffer.putInt(this.sequenceNumber);
        byteBuffer.putInt(this.acknowledgmentNumber);
        byteBuffer.putShort((short) ((this.dataOffset << 12)
                + (this.NS << 8) + (this.CWR << 7) + (this.ECE << 6)
                + (this.URG << 5) + (this.ACK << 4) + (this.PSH << 3)
                + (this.RST << 2) + (this.SYN << 1) + (this.FIN)));
        byteBuffer.putShort(this.windowSize);
        byteBuffer.putShort(this.checkSum);
        byteBuffer.putShort(this.urgentPointer);
        byteBuffer.put(this.applicationData);
        return byteBuffer.array();
    }

    public static class Builder {
        private TCPMessage tcpMessage = new TCPMessage();

        public Builder() {
        }

        public Builder setSourcePort(short sourcePort) {
            tcpMessage.sourcePort = sourcePort;
            return this;
        }

        public Builder setDestinationPort(short destinationPort) {
            tcpMessage.destinationPort = destinationPort;
            return this;
        }

        public Builder setSequenceNumber(int sequenceNumber) {
            tcpMessage.sequenceNumber = sequenceNumber;
            return this;
        }

        public Builder setAcknowledgmentNumber(int acknowledgmentNumber) {
            tcpMessage.acknowledgmentNumber = acknowledgmentNumber;
            return this;
        }

        public Builder setDataOffset(byte dataOffset) {
            tcpMessage.dataOffset = dataOffset;
            return this;
        }

        public Builder setNS(byte NS) {
            tcpMessage.NS = NS;
            return this;
        }

        public Builder setCWR(byte CWR) {
            tcpMessage.CWR = CWR;
            return this;
        }

        public Builder setECE(byte ECE) {
            tcpMessage.ECE = ECE;
            return this;
        }

        public Builder setURG(byte URG) {
            tcpMessage.URG = URG;
            return this;
        }

        public Builder setACK(byte ACK) {
            tcpMessage.ACK = ACK;
            return this;
        }

        public Builder setPSH(byte PSH) {
            tcpMessage.PSH = PSH;
            return this;
        }

        public Builder setRST(byte RST) {
            tcpMessage.RST = RST;
            return this;
        }

        public Builder setSYN(byte SYN) {
            tcpMessage.SYN = SYN;
            return this;
        }

        public Builder setFIN(byte FIN) {
            tcpMessage.FIN = FIN;
            return this;
        }

        public Builder setWindowSize(short windowSize) {
            tcpMessage.windowSize = windowSize;
            return this;
        }

        public Builder setCheckSum(short checkSum) {
            tcpMessage.checkSum = checkSum;
            return this;
        }

        public Builder setUrgentPointer(short urgentPointer) {
            tcpMessage.urgentPointer = urgentPointer;
            return this;
        }

        public Builder setOptions(byte[] options) {
            tcpMessage.options = options;
            return this;
        }

        public Builder setApplicationData(byte[] applicationData) {
            tcpMessage.applicationData = applicationData;
            return this;
        }

        public TCPMessage build() {
            return tcpMessage;
        }
    }
}
