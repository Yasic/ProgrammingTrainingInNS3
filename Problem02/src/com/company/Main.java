package com.company;

import com.company.ApplicationLayer.ApplicationPresenter;
import com.company.Utils.SimpleProtocolType;

public class Main {

    public static void main(String[] args) {
        ApplicationPresenter applicationPresenter = new ApplicationPresenter();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 130; i++){
            stringBuffer.append("hello world" + i + " ");
        }
        applicationPresenter.getMessageFromUser(stringBuffer.toString(), SimpleProtocolType.TCP);
    }
}
