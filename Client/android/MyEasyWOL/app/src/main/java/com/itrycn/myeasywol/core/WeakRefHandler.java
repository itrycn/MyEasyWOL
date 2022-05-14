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

package com.itrycn.myeasywol.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by zhangliangming on 2018-08-23.
 */
public class WeakRefHandler<T> extends Handler {
    private WeakReference<T> mWeakReference;
    private Callback mCallback;

    public WeakRefHandler(Looper looper, T t, Callback callback) {
        super(looper);
        mCallback = callback;
        mWeakReference = new WeakReference<>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        if (isAlive() && mCallback != null) {
            mCallback.handleMessage(msg);
        }
    }

    /**
     * 是否还存活
     *
     * @return
     */
    public boolean isAlive() {
        T t = mWeakReference.get();
        return t != null;
    }
}
