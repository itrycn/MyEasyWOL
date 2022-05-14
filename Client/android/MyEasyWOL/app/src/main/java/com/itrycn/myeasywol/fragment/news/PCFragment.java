/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.itrycn.myeasywol.adapter.base.delegate.BaseDelegateAdapter;
import com.itrycn.myeasywol.db.DBHelper;
import com.itrycn.myeasywol.db.dao.PCInfoDao;
import com.itrycn.myeasywol.db.entity.PCInfo;
import com.itrycn.myeasywol.db.entity.ServerInfo;
import com.itrycn.myeasywol.fragment.profile.MsgCode;
import com.itrycn.myeasywol.utils.MyBroadcastReceiver;
import com.itrycn.myeasywol.utils.RandomUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.itrycn.myeasywol.R;
import com.itrycn.myeasywol.adapter.base.broccoli.BroccoliSimpleDelegateAdapter;
import com.itrycn.myeasywol.adapter.base.delegate.SimpleDelegateAdapter;
import com.itrycn.myeasywol.adapter.base.delegate.SingleDelegateAdapter;
import com.itrycn.myeasywol.adapter.entity.NewInfo;
import com.itrycn.myeasywol.core.BaseFragment;
import com.itrycn.myeasywol.utils.Utils;
import com.itrycn.myeasywol.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import butterknife.BindView;
import me.samlss.broccoli.Broccoli;

/**
 * 首页动态
 *
 * @author xuexiang
 * @since 2019-10-30 00:15
 */
@Page(anim = CoreAnim.none)
public class PCFragment extends BaseFragment {
    /**
     * 广播
     */
    private MyBroadcastReceiver mBroadcastReceiver;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private SimpleDelegateAdapter<PCInfo> commonAdapter;
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
        return R.layout.fragment_news;
    }
    @Override
    protected void handleWorkerMessage(Message msg) {
    }
    @Override
    protected void handleUIMessage(Message msg) {
    }
    private static List<PCInfo> getPCList(Context context) {
        try {
            //按创建时间倒序
            List<PCInfo> audioInfos = DBHelper.getInstance(context).getDaoSession().getPCInfoDao().queryBuilder().list();
            return audioInfos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PCInfo>();
    }
    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getContext());
        recyclerView.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        mBroadcastReceiver = new MyBroadcastReceiver();
        //轮播条
//        SingleDelegateAdapter bannerAdapter = new SingleDelegateAdapter(R.layout.include_head_view_banner) {
//            @Override
//            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
//                SimpleImageBanner banner = holder.findViewById(R.id.sib_simple_usage);
//                banner.setSource(DemoDataProvider.getBannerList())
//                        .setOnItemClickListener((view, item, position1) -> XToastUtils.toast("headBanner position--->" + position1)).startScroll();
//            }
//        };
        //九宫格菜单
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setPadding(0, 16, 0, 0);
        gridLayoutHelper.setVGap(10);
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setAutoExpand(false);
        commonAdapter = new SimpleDelegateAdapter<PCInfo>(R.layout.adapter_common_grid_item, gridLayoutHelper,getPCList(getContext())) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, PCInfo item) {
                if (item != null) {
                    RadiusImageView imageView = holder.findViewById(R.id.riv_item);
                    imageView.setCircle(true);
                    Drawable[] draw=ResUtils.getDrawableArray(getContext(),  R.array.grid_icons_entry);
                    ImageLoader.get().loadImage(imageView,draw[RandomUtils.getRandom(0,draw.length)]);
                    holder.text(R.id.tv_title, item.getName().toString().substring(0, 1));
                    holder.text(R.id.tv_sub_title, item.getName());
                    holder.click(R.id.ll_container,new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //打开排行页面
                                    Bundle bundle = new Bundle();
                                    bundle.putLong(MsgCode.ACTION_DATA_KEY, item.getId());
                                    MyBroadcastReceiver.sendReceiver(getContext().getApplicationContext(), MsgCode.ACTION_CODE_WAKE_PC,MyBroadcastReceiver.ACTION_BUNDLEKEY,bundle);
                                    //XToastUtils.toast("点击了：" + item.getMac());
                                }
                            });

                }
            }
        };
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
//        delegateAdapter.addAdapter(bannerAdapter);
        delegateAdapter.addAdapter(commonAdapter);

        recyclerView.setAdapter(delegateAdapter);
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
                    case MsgCode.ACTION_CODE_UPDATE_PCLIST:
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
    @Override
    protected void initListeners() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            // TODO: 2020-02-25 这里只是模拟了网络请求
            refreshLayout.getLayout().post(() -> {
                refresh();
                refreshLayout.finishRefresh();
            });
        });
        //refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果
    }
    private void refresh() {
//        refreshLayout.setEnableRefresh(true);
        loadData();
    }

    private void loadData() {
        new Handler().post(() -> {
            commonAdapter.refresh(getPCList(getContext()));
//            if (refreshLayout != null) {
//                refreshLayout.setEnableRefresh(false);
//            }
        });
    }
}
