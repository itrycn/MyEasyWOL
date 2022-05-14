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
public class PCInfo {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String ip;
    private int port;
    private String mac;
    private Long serverID;
    @Generated(hash = 867088671)
    public PCInfo(Long id, String name, String ip, int port, String mac,
            Long serverID) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.mac = mac;
        this.serverID = serverID;
    }
    @Generated(hash = 932968637)
    public PCInfo() {
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
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getMac() {
        return this.mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public Long getServerID() {
        return this.serverID;
    }
    public void setServerID(Long serverID) {
        this.serverID = serverID;
    }
    public int getPort() {
        return this.port;
    }
    public void setPort(int port) {
        this.port = port;
    }
}
