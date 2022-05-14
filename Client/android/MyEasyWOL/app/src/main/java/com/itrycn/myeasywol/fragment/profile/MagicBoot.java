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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MagicBoot {
    /**
     * 发送开机魔术包
     */
    public static boolean SendMagic(String macAddress,String destIP)
    {
        return SendMagic(macAddress,destIP,20105);
    }
    /**
     * 发送开机魔术包
     */
    public static boolean SendMagic(String macAddress,String destIP,int port)
    {
        try {
            if(macAddress==null || destIP==null){return  false;}
            // String destIP = "255.255.255.255";// 广播地址
            // 检测 mac 地址,并将其转换为二进制
            byte[] destMac = getMacBytes(macAddress);
            if (destMac == null)
                return false;
            InetAddress destHost = InetAddress.getByName(destIP);
            // construct packet data
            byte[] magicBytes = new byte[102];
            // 将数据包的前6位放入0xFF即 "FF"的二进制
            for (int i = 0; i < 6; i++)
                magicBytes[i] = (byte) 0xFF;
            // 从第7个位置开始把mac地址放入16次
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < destMac.length; j++) {
                    magicBytes[6 + destMac.length * i + j] = destMac[j];
                }
            }
            // create packet
            DatagramPacket dp = null;
            dp = new DatagramPacket(magicBytes, magicBytes.length, destHost, port);
            DatagramSocket ds = new DatagramSocket();
            ds.send(dp);
            ds.close();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }
}
