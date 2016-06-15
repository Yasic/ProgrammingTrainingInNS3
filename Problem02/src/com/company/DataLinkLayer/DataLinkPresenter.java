package com.company.DataLinkLayer;

import com.company.NetworkLayer.INetworkPresenter;
import com.company.NetworkLayer.NetworkPresenter;
import com.company.Utils.DataTranslationUtil;
import com.company.Utils.RelativeParameterUtil;

/**
 * Created by yasic on 16-6-7.
 * just an empty layer!!
 */
public class DataLinkPresenter implements IDataLinkPresenter {
    private static boolean isBegin = true;

    private DataLinkPresenter(){}

    public static DataLinkPresenter getInstance(){
        return DataLinkPresenterInstance.DATA_LINK_PRESENTER;
    }

    private static class DataLinkPresenterInstance{
        private static final DataLinkPresenter DATA_LINK_PRESENTER = new DataLinkPresenter();
    }


    @Override
    public boolean getMessageFromNetwork(byte[] data) {
        /**
         * write the byte array into file
         */
        String result = DataTranslationUtil.byteArray2String(data);
        DataTranslationUtil.writeIntoFile(RelativeParameterUtil.ROWDATAFILENAME, result, isBegin);
        if (isBegin){isBegin = false;}
        return true;
    }

    @Override
    public boolean getMessageFromPHY(byte[] data) {
        sendMessageToNetwork(data);
        return false;
    }

    @Override
    public boolean sendMessageToNetwork(byte[] data) {
        INetworkPresenter iNetworkPresenter = NetworkPresenter.getInstance();
        iNetworkPresenter.getMessageFromDataLink(data);
        return false;
    }

    @Override
    public boolean sendMessageToPHY(byte[] data) {
        return false;
    }
}
