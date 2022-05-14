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
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.itrycn.myeasywol.R;
import com.itrycn.myeasywol.adapter.base.delegate.ServerRecyclerAdapter;
import com.itrycn.myeasywol.core.BaseFragment;
import com.itrycn.myeasywol.db.DBHelper;
import com.itrycn.myeasywol.db.entity.ServerInfo;
import com.itrycn.myeasywol.db.util.PCInfoDb;
import com.itrycn.myeasywol.db.util.ServerInfoDb;
import com.itrycn.myeasywol.fragment.profile.MsgCode;
import com.itrycn.myeasywol.utils.MyBroadcastReceiver;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

import butterknife.BindView;

@Page(name = "服务器列表")
public class ServerViewFragment extends BaseFragment {
    /**
     * 广播
     */
    private MyBroadcastReceiver mBroadcastReceiver;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    SwipeRecyclerView recyclerView;

    private ServerRecyclerAdapter ServerAdapter;
    @Override
    protected void handleWorkerMessage(Message msg) {
    }
    @Override
    protected void handleUIMessage(Message msg) {
    }
    /**
     * @return 返回为 null意为不需要导航栏
     */
    @Override
    protected TitleBar initTitle() {
        return null;
    }
    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_server;
    }
    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        WidgetUtils.initRecyclerView(recyclerView);

        //必须在setAdapter之前调用
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        //必须在setAdapter之前调用
        recyclerView.setOnItemMenuClickListener(mMenuItemClickListener);
        recyclerView.setOnItemClickListener(ItemClickListener);
        //List<ServerInfo> audioInfos = DBHelper.getInstance(getContext()).getDaoSession().getServerInfoDao().queryBuilder().list();
        ServerAdapter = new ServerRecyclerAdapter();
        recyclerView.setAdapter(ServerAdapter);

        refreshLayout.setColorSchemeColors(0xff0099cc, 0xffff4444, 0xff669900, 0xffaa66cc, 0xffff8800);
        mBroadcastReceiver = new MyBroadcastReceiver();
        mBroadcastReceiver.setReceiverListener(new MyBroadcastReceiver.MyReceiverListener() {
            @Override
            public void onReceive(Context context, final Intent intent, final int code) {
                mUIHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        handleAudioBroadcastReceiver(intent, code);
                    }
                });
            }

            private void handleAudioBroadcastReceiver(Intent intent, int code) {
                switch (code) {
                    case MsgCode.ACTION_CODE_UPDATE_SERVERLIST:
                        refresh();
                       break;
                }
            }
        });
        mBroadcastReceiver.registerReceiver(getContext().getApplicationContext());
    }
    @Override
    public void onDestroyView() {
        if (mBroadcastReceiver != null) {
            mBroadcastReceiver.unregisterReceiver(getContext());
        }
        super.onDestroyView();
    }
    private OnItemClickListener ItemClickListener = (View itemView, int position) -> {
        Bundle bundle = new Bundle();
        bundle.putLong(MsgCode.ACTION_DATA_KEY, ServerAdapter.getItem(position).getId());
        MyBroadcastReceiver.sendReceiver(getContext().getApplicationContext(), MsgCode.ACTION_CODE_EDIT_SERVER,MyBroadcastReceiver.ACTION_BUNDLEKEY,bundle);
    };
    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = (swipeLeftMenu, swipeRightMenu, position) -> {
        int width = getResources().getDimensionPixelSize(R.dimen.dp_70);

        // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
        // 2. 指定具体的高，比如80;
        // 3. WRAP_CONTENT，自身高度，不推荐;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;


        // 添加右侧的，如果不添加，则右侧不会出现菜单。
        {
            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext()).setBackground(R.drawable.menu_selector_red)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

            SwipeMenuItem addItem = new SwipeMenuItem(getContext()).setBackground(R.drawable.menu_selector_green)
                    .setText("编辑")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
        }
    };
    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private OnItemMenuClickListener mMenuItemClickListener = (menuBridge, position) -> {
        menuBridge.closeMenu();

        int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
        int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

        if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
            if(menuPosition==0)//删除
            {
                MaterialDialog.Builder materialDialog= new MaterialDialog.Builder(getContext());
                materialDialog.content("是否确认要删除该服务器？")
                        .positiveText(R.string.lab_yes)
                        .negativeText(R.string.lab_no)
                        .onPositive((dialog, which) -> {
                            Long ServerId=ServerAdapter.getItem(position).getId();
                            int count= PCInfoDb.getPCCountByServer(getContext(),ServerId);
                            if(count==0) {
                                ServerInfoDb.deleteServerInfo(getContext(), ServerId);
                                ServerAdapter.getListData().remove(position);
                                ServerAdapter.notifyDataSetChanged();
                            }
                            else
                            {
                                new MaterialDialog.Builder(getContext())
                                        .iconRes(R.drawable.icon_tip)
                                        .title("提示")
                                        .content("当前中转服务器下还有设备,请先删除设备后再删除中转服务器。")
                                        .positiveText("确定")
                                        .show();
                            }
                        }).show();

            }
            else if(menuPosition==1)//编辑
            {
                Bundle bundle = new Bundle();
                bundle.putLong(MsgCode.ACTION_DATA_KEY, ServerAdapter.getItem(position).getId());
                MyBroadcastReceiver.sendReceiver(getContext().getApplicationContext(), MsgCode.ACTION_CODE_EDIT_SERVER,MyBroadcastReceiver.ACTION_BUNDLEKEY,bundle);
            }
            //XToastUtils.toast("list第" + position + "; 右侧菜单第" + menuPosition+"-->"+ServerAdapter.getItem(position).getName());
        } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
            //XToastUtils.toast("list第" + position + "; 左侧菜单第" + menuPosition);
        }
    };
    @Override
    protected void initListeners() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(this::loadData);
        refresh(); //第一次进入触发自动刷新，演示效果
    }
    private void refresh() {
        refreshLayout.setRefreshing(true);
        loadData();
    }

    private void loadData() {
        new Handler().postDelayed(() -> {
            List<ServerInfo> audioInfos = DBHelper.getInstance(getContext()).getDaoSession().getServerInfoDao().queryBuilder().list();
            ServerAdapter.refresh(audioInfos);
            if (refreshLayout != null) {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
