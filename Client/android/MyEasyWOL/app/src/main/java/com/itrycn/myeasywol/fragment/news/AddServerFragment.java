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

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.itrycn.myeasywol.R;
import com.itrycn.myeasywol.core.BaseFragment;
import com.itrycn.myeasywol.db.DBHelper;
import com.itrycn.myeasywol.db.entity.ServerInfo;
import com.itrycn.myeasywol.db.util.ServerInfoDb;
import com.itrycn.myeasywol.fragment.profile.MsgCode;
import com.itrycn.myeasywol.utils.MyBroadcastReceiver;
import com.itrycn.myeasywol.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Page(name = "服务器管理")
public class AddServerFragment extends BaseFragment {
    @BindView(R.id.et_name)
    MaterialEditText etName;
    @BindView(R.id.et_url)
    MaterialEditText etUrl;
    @BindView(R.id.et_successmark)
    MaterialEditText etSuccessMark;
    @BindView(R.id.frag_name)
    TextView frag_name;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_serverinfo;
    }
    Boolean isAdd=true;
    Long SelectedId=0L;
    @Override
    protected void initViews() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Long id= bundle.getLong(MsgCode.ACTION_DATA_KEY,-1);
            if(id>=0) {
                List<ServerInfo> list = DBHelper.getInstance(getContext()).getDaoSession().getServerInfoDao().queryBuilder().where(new WhereCondition.StringCondition("_id=" + id)).list();
                if (list.size() == 1) {
                    etName.setText(list.get(0).getName());
                    etUrl.setText(list.get(0).getUrl());
                    etSuccessMark.setText(list.get(0).getSuccessMark());
                    SelectedId = list.get(0).getId();
                    isAdd = false;
                    frag_name.setText("修改服务器");
                }
            }
        }
    }
    @Override
    protected void handleWorkerMessage(Message msg) {
    }
    @Override
    protected void handleUIMessage(Message msg) {
    }
    @SingleClick
    @OnClick({R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                String Name=etName.getEditValue();
                if(Name.length()<1)
                {
                    XToastUtils.warning("请输入名称");
                    return;
                }
                String Url=etUrl.getEditValue();
                if(Url.length()<5)
                {
                    XToastUtils.warning("请输入正确的Url");
                    return;
                }
                ServerInfo info=new ServerInfo();
                info.setName(Name);
                info.setUrl(Url);
                info.setSuccessMark(etSuccessMark.getEditValue());
                if(isAdd) {
                    ServerInfoDb.addServerInfo(getContext(), info);
                }
                else
                {
                    info.setId(SelectedId);
                    ServerInfoDb.updateServerInfo(getContext(), info);
                }
                popToBack();
                MyBroadcastReceiver.sendReceiver(getContext().getApplicationContext(), MsgCode.ACTION_CODE_UPDATE_SERVERLIST);
                //确定按钮
                break;
            default:
                break;
        }
    }
}
