/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
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

package com.itrycn.myeasywol.adapter.entity;

/**
 * 新闻信息
 *
 * @author xuexiang
 * @since 2019/4/7 下午12:07
 */
public class NewInfo {

    /**
     * 用户名
     */
    private String UserName = "xuexiangjys";
    /**
     * 标签
     */
    private String Tag;
    /**
     * 标题
     */
    private String Title;
    /**
     * 摘要
     */
    private String Summary;

    /**
     * 图片
     */
    private String ImageUrl;
    /**
     * 点赞数
     */
    private int Praise;
    /**
     * 评论数
     */
    private int Comment;
    /**
     * 阅读量
     */
    private int Read;
    /**
     * 新闻的详情地址
     */
    private String DetailUrl;


    public NewInfo() {

    }

    public NewInfo(String userName, String tag, String title, String summary, String imageUrl, int praise, int comment, int read, String detailUrl) {
        UserName = userName;
        Tag = tag;
        Title = title;
        Summary = summary;
        ImageUrl = imageUrl;
        Praise = praise;
        Comment = comment;
        Read = read;
        DetailUrl = detailUrl;
    }


    public NewInfo(String tag, String title, String summary, String imageUrl, String detailUrl) {
        Tag = tag;
        Title = title;
        Summary = summary;
        ImageUrl = imageUrl;
        DetailUrl = detailUrl;
    }


    public NewInfo(String tag, String title) {
        Tag = tag;
        Title = title;

        Praise = (int) (Math.random() * 100 + 5);
        Comment = (int) (Math.random() * 50 + 5);
        Read = (int) (Math.random() * 500 + 50);
    }



    public String getUserName() {
        return UserName;
    }

    public NewInfo setUserName(String userName) {
        UserName = userName;
        return this;
    }

    public String getTag() {
        return Tag;
    }

    public NewInfo setTag(String tag) {
        Tag = tag;
        return this;
    }

    public String getTitle() {
        return Title;
    }

    public NewInfo setTitle(String title) {
        Title = title;
        return this;
    }

    public String getSummary() {
        return Summary;
    }

    public NewInfo setSummary(String summary) {
        Summary = summary;
        return this;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public NewInfo setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
        return this;
    }

    public int getPraise() {
        return Praise;
    }

    public NewInfo setPraise(int praise) {
        Praise = praise;
        return this;
    }

    public int getComment() {
        return Comment;
    }

    public NewInfo setComment(int comment) {
        Comment = comment;
        return this;
    }

    public int getRead() {
        return Read;
    }

    public NewInfo setRead(int read) {
        Read = read;
        return this;
    }

    public String getDetailUrl() {
        return DetailUrl;
    }

    public NewInfo setDetailUrl(String detailUrl) {
        DetailUrl = detailUrl;
        return this;
    }

    @Override
    public String toString() {
        return "NewInfo{" +
                "UserName='" + UserName + '\'' +
                ", Tag='" + Tag + '\'' +
                ", Title='" + Title + '\'' +
                ", Summary='" + Summary + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", Praise=" + Praise +
                ", Comment=" + Comment +
                ", Read=" + Read +
                ", DetailUrl='" + DetailUrl + '\'' +
                '}';
    }
}
