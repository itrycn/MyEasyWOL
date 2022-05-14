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

package com.itrycn.myeasywol.fragment.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itrycn.myeasywol.R;
import com.itrycn.myeasywol.db.entity.ServerInfo;

import java.util.List;

public class ServerAdapter extends BaseAdapter {
    private List<ServerInfo> mList;
    private Context mContext;
    public ServerAdapter(Context pContext, List<ServerInfo> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * 下面是重要代码
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.xui_layout_spinner_selected_item, null);
        if(convertView!=null)
        {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.spinner_item);
            _TextView1.setText(mList.get(position).getName());
        }
        return convertView;
    }
}
