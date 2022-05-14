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

package com.itrycn.myeasywol.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ServerInfo {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String Url;
    private String SuccessMark;
    @Generated(hash = 620916738)
    public ServerInfo(Long id, String name, String Url, String SuccessMark) {
        this.id = id;
        this.name = name;
        this.Url = Url;
        this.SuccessMark = SuccessMark;
    }
    @Generated(hash = 1634164213)
    public ServerInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return this.Url;
    }
    public void setUrl(String Url) {
        this.Url = Url;
    }
    public String getSuccessMark() {
        return this.SuccessMark;
    }
    public void setSuccessMark(String SuccessMark) {
        this.SuccessMark = SuccessMark;
    }
}
