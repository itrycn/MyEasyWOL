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

package com.itrycn.myeasywol.db;

import android.content.Context;

import com.itrycn.myeasywol.db.dao.DaoMaster;
import com.itrycn.myeasywol.db.dao.DaoSession;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

public class DBHelper {
    private final String DB_NAME = "happyplayer.db";
    private static DBHelper _DBHelper;
    private static UpdateOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;
    private static String mPassword;

    private DBHelper(Context context) {
        init(context);
    }

    private void init(Context context) {
        mPassword = "itrycn2021";
        // 初始化数据库信息
        mDevOpenHelper = new UpdateOpenHelper(context, DB_NAME, null);
        getDaoMaster();
        getDaoSession();
    }

    public static DBHelper getInstance(Context context) {
        if (null == _DBHelper) {
            synchronized (DBHelper.class) {
                if (null == _DBHelper) {
                    _DBHelper = new DBHelper(context);
                }
            }
        }
        return _DBHelper;
    }

    /**
     * 获取可写数据库
     *
     * @return
     */
    public Database getWritableDatabase() {
        return mDevOpenHelper.getEncryptedWritableDb(mPassword);
//        return mDevOpenHelper.getWritableDb();
    }

    /**
     * 获取DaoMaster
     *
     * @return
     */
    private DaoMaster getDaoMaster() {
        if (null == mDaoMaster) {
            synchronized (DBHelper.class) {
                if (null == mDaoMaster) {
                    mDaoMaster = new DaoMaster(getWritableDatabase());
                }
            }
        }
        return mDaoMaster;
    }

    /**
     * 获取DaoSession
     *
     * @return
     */
    public DaoSession getDaoSession() {
        if (null == mDaoSession) {
            synchronized (DBHelper.class) {
                mDaoSession = getDaoMaster().newSession(IdentityScopeType.None);
            }
        }

        return mDaoSession;
    }

}
