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
import android.database.Cursor;

import com.itrycn.myeasywol.db.DBHelper;
import com.itrycn.myeasywol.db.entity.PCInfo;
import com.itrycn.myeasywol.db.dao.PCInfoDao;

import java.util.List;

public class PCInfoDb {
    /**
     * 添加PC信息
     *
     * @param context
     * @param pcInfo
     * @return
     */
    public static boolean addPCInfo(Context context, PCInfo pcInfo) {
        try {
            DBHelper.getInstance(context).getDaoSession().getPCInfoDao().insert(pcInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 批量添加PC数据
     *
     * @param context
     * @param pcInfos
     * @return
     */
    public static boolean addPCInfos(Context context, List<PCInfo> pcInfos) {
        try {
            DBHelper.getInstance(context).getDaoSession().getPCInfoDao().insertInTx(pcInfos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 更新PC信息
     */
    public static boolean updatePCInfo(Context context, PCInfo info) {
        try {

            String sql = "UPDATE ";
            sql += PCInfoDao.TABLENAME;
            sql += " SET " + PCInfoDao.Properties.Name.columnName + " =?,"+
                    PCInfoDao.Properties.Ip.columnName+"=?,"+
                    PCInfoDao.Properties.Port.columnName+"="+info.getPort()+","+
                    PCInfoDao.Properties.Mac.columnName+"=?,"+
                    PCInfoDao.Properties.ServerID.columnName+"="+info.getServerID();
            sql += " where " + PCInfoDao.Properties.Id.columnName + "="+info.getId();

            String args[] = {info.getName(),info.getIp(), info.getMac()};
            DBHelper.getInstance(context).getWritableDatabase().execSQL(sql, args);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 删除PC信息
     *
     * @param context
     */
    public static boolean deletePCInfo(Context context,Long id) {
        try {
            String sql = "DELETE FROM ";
            sql += PCInfoDao.TABLENAME;
            sql += " where " + PCInfoDao.Properties.Id.columnName + "="+id;
            DBHelper.getInstance(context).getWritableDatabase().execSQL(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 获取指定服务器下的PC数量
     *
     * @param context
     * @return
     */
    public static int getPCCountByServer(Context context,Long ServerId) {
        Cursor cursor = null;
        int count = 0;
        try {
            //String args[] = {};
            String sql = "select count(*) from " + PCInfoDao.TABLENAME + " WHERE " + PCInfoDao.Properties.ServerID.columnName + "="+ServerId;
            cursor = DBHelper.getInstance(context).getWritableDatabase().rawQuery(sql, null);
            cursor.moveToFirst();
            count = cursor.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count;
    }
}
