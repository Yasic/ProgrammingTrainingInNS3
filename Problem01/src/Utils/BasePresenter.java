package Utils;

import JavaBean.BaseJavaBean;

/**
 * Created by yasic on 16-6-7.
 */
public class BasePresenter<T extends BaseJavaBean> {
    private T BJB;

    protected T deSerialize(){
        return BJB;
    }
}
