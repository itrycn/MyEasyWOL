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

public class MsgCode {
    /**
     * 更新服务器列表
     */
    public static final int ACTION_CODE_UPDATE_SERVERLIST = 100;
    /**
     * 发送编辑服务器消息
     */
    public static final int ACTION_CODE_EDIT_SERVER = 101;
    /**
     * 获取服务器内容
     */
    public static final int ACTION_CODE_GET_SERVER = 102;
    /**
     * 发送唤醒消息
     */
    public static final int ACTION_CODE_WAKE_PC = 103;
    /**
     * 更新设备列表
     */
    public static final int ACTION_CODE_UPDATE_PCLIST = 104;
    /**
     * 发送编辑设备消息
     */
    public static final int ACTION_CODE_EDIT_PC = 105;
    /**
     * data key
     */
    public static final String ACTION_DATA_KEY = "com.itryn.myeasywol.receiver.action.data.key";
}
