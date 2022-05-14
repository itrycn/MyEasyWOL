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

package com.itrycn.myeasywol.fragment.profile;

import android.content.Context;

import com.itrycn.myeasywol.utils.MyBroadcastReceiver;

public class SendMsg {
    /**
     * 发null广播
     *
     * @param context
     */
    public static void sendUpdateServer(Context context) {
       MyBroadcastReceiver.sendReceiver(context, MsgCode.ACTION_CODE_UPDATE_SERVERLIST);
    }
}
