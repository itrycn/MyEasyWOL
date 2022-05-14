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
import android.database.sqlite.SQLiteDatabase;

import com.itrycn.myeasywol.db.dao.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * 更新
 */
public class UpdateOpenHelper extends DaoMaster.OpenHelper {

    public UpdateOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 数据库升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            //操作数据库的更新 有几个表升级都可以传入到下面
            //MigrationHelper.getInstance().migrate(db, AudioInfoDao.class);
        }
    }
}
