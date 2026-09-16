package com.style.myevent;

/**
 * Created by xiajun on 2017/7/19.
 */

public interface EventReceiver {
    void onMainThreadEvent(String code, Object data);
}
