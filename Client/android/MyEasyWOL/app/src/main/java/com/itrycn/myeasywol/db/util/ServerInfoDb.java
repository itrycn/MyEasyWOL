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

package com.itrycn.myeasywol.db.util;

import android.content.Context;

import com.itrycn.myeasywol.db.DBHelper;
import com.itrycn.myeasywol.db.dao.ServerInfoDao;
import com.itrycn.myeasywol.db.entity.ServerInfo;

import java.util.List;

public class ServerInfoDb {
    /**
     * 添加服务器信息
     *
     * @param context
     * @param pcInfo
     * @return
     */
    public static boolean addServerInfo(Context context, ServerInfo pcInfo) {
        try {
            DBHelper.getInstance(context).getDaoSession().getServerInfoDao().insert(pcInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 批量添加服务器数据
     *
     * @param context
     * @param serverInfos
     * @return
     */
    public static boolean addServerInfos(Context context, List<ServerInfo> serverInfos) {
        try {
            DBHelper.getInstance(context).getDaoSession().getServerInfoDao().insertInTx(serverInfos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 更新服务器数据
     */
    public static boolean updateServerInfo(Context context, ServerInfo info) {
        try {

            String sql = "UPDATE ";
            sql += ServerInfoDao.TABLENAME;
            sql += " SET " + ServerInfoDao.Properties.Name.columnName + " =?,"+
                    ServerInfoDao.Properties.Url.columnName+"=?,"+
                ServerInfoDao.Properties.SuccessMark.columnName+"=?";
            sql += " where " + ServerInfoDao.Properties.Id.columnName + "="+info.getId();
            String args[] = {info.getName(),info.getUrl(),info.getSuccessMark()};
            DBHelper.getInstance(context).getWritableDatabase().execSQL(sql, args);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 删除服务器信息
     *
     * @param context
     */
    public static boolean deleteServerInfo(Context context,Long id) {
        try {
            String sql = "DELETE FROM ";
            sql += ServerInfoDao.TABLENAME;
            sql += " where " + ServerInfoDao.Properties.Id.columnName + "="+id;
            DBHelper.getInstance(context).getWritableDatabase().execSQL(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
