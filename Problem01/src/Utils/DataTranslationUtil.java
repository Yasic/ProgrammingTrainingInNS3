package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasic on 16-6-9.
 */
public class DataTranslationUtil {
    public static void printStringFromByteAray(byte[] bytes){
        String result = "";
        for(int i = 0; i < bytes.length; i++){
            String temp = Integer.toHexString(bytes[i] & 0xFF);
            if(temp.length() == 1){
                temp = "0" + temp;
            }
            result = result + " "+ temp;
        }
        System.out.println(result);
    }

    public static List<byte[]> splitByteArray(byte[] targetData, int MAXLEN){
        List<byte[]> bytes = new ArrayList<>();
        int temp = 0;
        int index = 0;
        while ((temp + MAXLEN) <= targetData.length){
            byte[] tempByte = new byte[MAXLEN];
            for (int i = 0; i < MAXLEN; i++){
                tempByte[i] = targetData[index++];
            }
            //DataTranslationUtil.printStringFromByteAray(tempByte);
            //System.out.println("\n");
            bytes.add(tempByte);
            temp += MAXLEN;
        }
        byte[] tempByte = new byte[targetData.length - temp];
        for (int i = 0; i < targetData.length - temp; i++){
            tempByte[i] = targetData[index++];
        }
        //DataTranslationUtil.printStringFromByteAray(tempByte);
        System.out.println("\n");
        bytes.add(tempByte);
        return bytes;
    }
}
