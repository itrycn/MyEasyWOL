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

package com.itrycn.myeasywol.adapter.base.delegate;

import com.itrycn.myeasywol.R;
import com.itrycn.myeasywol.db.entity.ServerInfo;
import com.scwang.smartrefresh.layout.adapter.SmartRecyclerAdapter;
import com.scwang.smartrefresh.layout.adapter.SmartViewHolder;

import java.util.Collection;

public class ServerRecyclerAdapter extends SmartRecyclerAdapter<ServerInfo> {
    public ServerRecyclerAdapter() {
        super(R.layout.adapter_item_simple_list_1);
    }

    public ServerRecyclerAdapter(Collection<ServerInfo> data) {
        super(data, R.layout.adapter_item_simple_list_1);
    }

    /**
     * 绑定布局控件
     *
     * @param holder
     * @param model
     * @param position
     */
    @Override
    protected void onBindViewHolder(SmartViewHolder holder, ServerInfo model, int position) {
        holder.text(R.id.tv_title, model.getName());
        //holder.text(android.R.id.text2,model.getName());
        //holder.textColorId(android.R.id.text2, R.color.xui_config_color_light_blue_gray);
    }
}
