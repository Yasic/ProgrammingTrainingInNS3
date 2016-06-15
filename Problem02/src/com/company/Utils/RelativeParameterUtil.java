package com.company.Utils;

/**
 * Created by yasic on 16-6-8.
 */
public class RelativeParameterUtil {
    public static final short SOURCEPROT = (short) 0x8000;
    public static final short DESTINATIONPROT = (short) 0x8080;

    public static final byte DATAOFFSET = 0x5;
    public static final byte IPVIRSION = 4;

    public static final byte DESTINATIONMACADDRESS = 0;
    public static final byte SOURCEMACADDRESS = 0;

    public static final int SOURCEIPADDRESS = 192 * 256 * 256 * 256 + 168 * 256 * 256 + 1 * 256 + 1 * 1;
    public static final int DESTINATIONIPADDRESS = 192 * 256 * 256 * 256 + 168 * 256 * 256 + 1 * 256 + 2 * 1;

    public static final String ROWDATAFILENAME = "rawData";
    public static final String STRINGINPUTFILENAME = "stringInput";

    public static final int TCPPROTOCOLNUM = 6;
    public static final int UDPPROTOCOLNUM = 17;

    public static final int MAXSIZEOFTCPMESSAGE = 1400;
    public static final int MAXSIZEOFIPPACKET = 1400;
}
