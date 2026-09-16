package com.style.myevent;

import android.os.Handler;
import android.os.Looper;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import example.queue.ReceiveEventTask;

public class MyEventManager {
    private static final String TAG = "EventManager";
    private static MyEventManager instance;
    /**
     * 有链表结构：查询慢，插入删除快。
     * 无链表结构：查询快，插入删除慢。
     * 这里需要查询快，所以使用无链表结构集合。
     */
    private final HashMap<Object, HashSet<String>> subscriberMap = new HashMap<>();

    /**
     * ui handler
     */
    private final Handler mUIHandler = new Handler(Looper.getMainLooper());

    public static MyEventManager getInstance() {
        synchronized (MyEventManager.class) {
            if (instance == null)
                instance = new MyEventManager();
        }
        return instance;
    }

    /**
     * 订阅事件
     *
     * @param subscriber
     * @param codes
     */
    public synchronized void register(Object subscriber, String... codes) {
        if (subscriber == null)
            return;
        if (!subscriberMap.containsKey(subscriber)) {
            HashSet<String> event = new HashSet<>();
            Collections.addAll(event, codes);
            subscriberMap.put(subscriber, event);
        } else {
            Collections.addAll(Objects.requireNonNull(subscriberMap.get(subscriber)), codes);
        }
    }

    /**
     * 取消订阅
     *
     * @param subscriber
     */
    public synchronized void unRegister(Object subscriber) {
        subscriberMap.remove(subscriber);
    }

    /**
     * 接收事件
     *
     * @param code
     * @param data
     */
    public void post(final String code, final Object data) {
        ThreadPoolUtil.execute(new ReceiveEventTask(code, data, subscriberMap, mUIHandler));
    }
}
