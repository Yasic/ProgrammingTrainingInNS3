package Utils;

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
}
