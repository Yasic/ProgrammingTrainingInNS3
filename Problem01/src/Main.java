import ApplicationLayer.ApplicationPresenter;
import Utils.SimpleProtocolType;

public class Main {

    public static void main(String[] args) {
        ApplicationPresenter applicationPresenter = new ApplicationPresenter();
        String s = "";
        for (int i = 0; i < 1000; i++){
            s += "hello world ";
        }
        applicationPresenter.sendMessageToTransportLayer(
                s,
                SimpleProtocolType.UDP);
    }
}
