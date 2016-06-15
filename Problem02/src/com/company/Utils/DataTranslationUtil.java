package com.company.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasic on 16-6-9.
 */
public class DataTranslationUtil {

    /**
     * translate byte array to string
     */
    public static String byteArray2String(byte[] data){
        String result = "";
        for(int i = 0; i < data.length; i++){
            String temp = Integer.toHexString(data[i] & 0xFF);
            if(temp.length() == 1){
                temp = "0" + temp;
            }
            if (i == 0){
                result += temp;
            }
            else {
                result += " " + temp;
            }
        }
        return result;
    }

    /**
     * print byte array info to stander input stream
     * @param bytes origin byte array
     */
    public static void printStringFromByteAray(byte[] bytes){
        String result = byteArray2String(bytes);
        System.out.println(result);
    }

    /**
     * write string into file
     * @param fileName the target file name
     * @param resultData the orgin string
     * @param isBegin if this string is the begin of the string set
     */
    public static void writeIntoFile(String fileName, String resultData, boolean isBegin){
        File file = new File(fileName);
        try {
            file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, !isBegin));
            bufferedWriter.write(resultData);
            bufferedWriter.write("\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * split byte array to little segment
     * @param targetData the target byte array which need to be split
     * @param MAXLEN the max size of each segment
     * @return the list of all little segment
     */
    public static List<byte[]> splitByteArray(byte[] targetData, int MAXLEN){
        List<byte[]> bytes = new ArrayList<>();
        int temp = 0;
        int index = 0;
        while ((temp + MAXLEN) <= targetData.length){
            byte[] tempByte = new byte[MAXLEN];
            for (int i = 0; i < MAXLEN; i++){
                tempByte[i] = targetData[index++];
            }
            bytes.add(tempByte);
            temp += MAXLEN;
        }
        byte[] tempByte = new byte[targetData.length - temp];
        for (int i = 0; i < targetData.length - temp; i++){
            tempByte[i] = targetData[index++];
        }
        bytes.add(tempByte);
        return bytes;
    }

    /**
     * splice origin byte array to the end of the target byte array
     * @param origin origin byte array
     * @param target target byte array
     * @return the byte array that has been spliced
     */
    public static byte[] spliceByte(byte[] origin, byte[] target){
        byte[] tempByte = new byte[origin.length + target.length];
        int index = 0;
        for (int i = 0; i < target.length; i++){
            tempByte[index++] = target[i];
        }
        for (int i = 0; i < origin.length; i++){
            tempByte[index++] = origin[i];
        }
        return tempByte;
    }
}
