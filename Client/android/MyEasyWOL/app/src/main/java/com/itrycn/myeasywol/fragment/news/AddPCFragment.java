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
import android.widget.Spinner;
import android.widget.TextView;

import com.itrycn.myeasywol.R;
import com.itrycn.myeasywol.core.BaseFragment;
import com.itrycn.myeasywol.db.DBHelper;
import com.itrycn.myeasywol.db.entity.PCInfo;
import com.itrycn.myeasywol.db.entity.ServerInfo;
import com.itrycn.myeasywol.db.util.PCInfoDb;
import com.itrycn.myeasywol.fragment.profile.MsgCode;
import com.itrycn.myeasywol.utils.MyBroadcastReceiver;
import com.itrycn.myeasywol.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Page(name = "设备管理")
public class AddPCFragment extends BaseFragment {
    @BindView(R.id.et_name)
    MaterialEditText etName;
    @BindView(R.id.et_mac)
    MaterialEditText etMac;
    @BindView(R.id.et_ip)
    MaterialEditText etIP;
    @BindView(R.id.et_port)
    MaterialEditText etPort;
    @BindView(R.id.spinner_server)
    Spinner spinnerServer;
    @BindView(R.id.frag_name)
    TextView frag_name;
    Boolean isAdd=true;
    Long SelectedId=0L;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_pcinfo;
    }
    @Override
    protected void handleWorkerMessage(Message msg) {
    }
    @Override
    protected void handleUIMessage(Message msg) {
    }
    @Override
    protected void initViews() {
        List<ServerInfo> list =new ArrayList<ServerInfo>();
        ServerInfo info= new ServerInfo();
        info.setName(getString(R.string.sever_direct));
        info.setUrl("direct");
        info.setId(-1L);
        list.add(info);
        list.addAll(DBHelper.getInstance(getContext()).getDaoSession().getServerInfoDao().queryBuilder().list());
        WidgetUtils.initSpinnerStyle(spinnerServer);
        ServerAdapter _MyAdapter=new ServerAdapter(getContext(), list);
        spinnerServer.setAdapter(_MyAdapter);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Long id= bundle.getLong(MsgCode.ACTION_DATA_KEY,-1);
            if(id>=0) {
                List<PCInfo> list_pc = DBHelper.getInstance(getContext()).getDaoSession().getPCInfoDao().queryBuilder().where(new WhereCondition.StringCondition("_id=" + id)).list();
                if (list_pc.size() == 1) {
                    etName.setText(list_pc.get(0).getName());
                    etMac.setText(list_pc.get(0).getMac());
                    etIP.setText(list_pc.get(0).getIp());
                    if(list_pc.get(0).getPort()>0) {
                        etPort.setText(String.valueOf(list_pc.get(0).getPort()));
                    }
                    SelectedId = list_pc.get(0).getId();
                    isAdd = false;
                    frag_name.setText("修改设备");
                    Long ServerId = list_pc.get(0).getServerID();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId() == ServerId) {
                            spinnerServer.setSelection(i);
                            break;
                        }
                    }
                }
            }
        }    }
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
                String Mac=etMac.getEditValue();
                if(Mac.length()!=17 && Mac.length()!=12)
                {
                    XToastUtils.warning("请输入正确的MAC");
                    return;
                }
                ServerInfo ss=(ServerInfo)spinnerServer.getSelectedItem();
                PCInfo info=new PCInfo();
                info.setName(Name);
                info.setMac(Mac);
                info.setIp(etIP.getEditValue());
                if(etPort.getEditValue().length()>0) {
                    info.setPort(Integer.parseInt(etPort.getEditValue()));
                }
                else
                {
                    info.setPort(-1);
                }
                info.setServerID(ss.getId());
                if(isAdd) {
                    PCInfoDb.addPCInfo(getContext(),info);
                }
                else
                {
                    info.setId(SelectedId);
                    PCInfoDb.updatePCInfo(getContext(),info);
                }
                popToBack();
                MyBroadcastReceiver.sendReceiver(getContext().getApplicationContext(), MsgCode.ACTION_CODE_UPDATE_PCLIST);
                //确定按钮
                break;
            default:
                break;
        }
    }
}
