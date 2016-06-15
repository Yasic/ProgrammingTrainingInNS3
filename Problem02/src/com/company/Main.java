package com.company;

import com.company.ApplicationLayer.ApplicationPresenter;
import com.company.DataLinkLayer.DataLinkPresenter;
import com.company.DataLinkLayer.IDataLinkPresenter;
import com.company.Utils.RelativeParameterUtil;
import com.company.Utils.SimpleProtocolType;

import java.io.*;

public class Main {

    /**
     * send message which is read from file "stringInput" to application layer
     */
    private static void sendMessage(){
        ApplicationPresenter applicationPresenter = ApplicationPresenter.getInstance();
        String input = null;
        String inputFileName = RelativeParameterUtil.STRINGINPUTFILENAME;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader in = new BufferedReader(new FileReader(inputFileName));
            input = in.readLine();
            while (input != null) {
                stringBuffer.append(input);
                stringBuffer.append("\n");
                input = in.readLine();
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        applicationPresenter.getMessageFromUser(stringBuffer.toString(), SimpleProtocolType.TCP);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * receive message from PHY layer by reading from file "rawData"
     */
    private static void receiveMessage(){
        IDataLinkPresenter iDataLinkPresenter = DataLinkPresenter.getInstance();
        File filename = new File(RelativeParameterUtil.ROWDATAFILENAME);
        String line = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            line = in.readLine();
            while (line != null) {
                String[] temp = line.split(" ");
                int i = 0;
                byte[] bytes = new byte[temp.length];
                while (i != temp.length){
                    bytes[i] = (byte) Integer.parseInt(temp[i], 16);
                    i++;
                }
                iDataLinkPresenter.getMessageFromPHY(bytes);
                line = in.readLine();
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendMessage();
        receiveMessage();
    }
}
