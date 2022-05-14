/*
 * Copyright (C) 2021 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.itrycn.myeasywol.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

public class MyBroadcastReceiver {
    /**
     * audio的receiver的action
     */
    private static final String RECEIVER_ACTION = "com.itrycn.myeasywol.receiver.action";
    /**
     * code key
     */
    public static final String ACTION_CODE_KEY = "com.itrycn.myeasywol.action.code.key";
    /**
     * bundle key
     */
    public static final String ACTION_BUNDLEKEY = "com.itrycn.myeasywol.receiver.action.bundle.key";
    /**
     * 动态广播自定义权限
     */
    public final static String RECEIVER_PERMISSION = "com.itrycn.myeasywol.permissions.RECEIVER";
    private android.content.BroadcastReceiver mBroadcastReceiver;
    private IntentFilter mIntentFilter;
    private MyReceiverListener mReceiverListener;

    public MyBroadcastReceiver() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(RECEIVER_ACTION);
    }
    /**
     * 注册广播
     *
     * @param context
     */
    public void registerReceiver(Context context) {

        mBroadcastReceiver = new android.content.BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (mReceiverListener != null) {
                    int code = intent.getIntExtra(ACTION_CODE_KEY, -1);
                    if (code != -1) {
                        mReceiverListener.onReceive(context, intent, code);
                    }
                }
            }
        };
        context.registerReceiver(mBroadcastReceiver, mIntentFilter, RECEIVER_PERMISSION, null);
    }

    /**
     * 发广播
     *
     * @param context
     * @param code
     * @param bundleKey
     * @param bundleValue
     */
    public static void sendReceiver(Context context, int code, String bundleKey, Bundle bundleValue) {
        Intent intent = new Intent(RECEIVER_ACTION);
        intent.putExtra(ACTION_CODE_KEY, code);
        if (!TextUtils.isEmpty(bundleKey) && bundleValue != null) {
            intent.putExtra(bundleKey, bundleValue);
        }
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(intent,RECEIVER_PERMISSION);
    }
    /**
     * 发广播
     *
     * @param context
     * @param code
     */
    public static void sendReceiver(Context context, int code) {
        sendReceiver(context, code, null, null);
    }
    /**
     * 取消注册广播
     */
    public void unregisterReceiver(Context context) {
        if (mBroadcastReceiver != null) {
            context.unregisterReceiver(mBroadcastReceiver);
        }
    }

    public interface MyReceiverListener {
        void onReceive(Context context, Intent intent, int code);
    }

    public void setReceiverListener(MyReceiverListener ReceiverListener) {
        this.mReceiverListener = ReceiverListener;
    }
}
