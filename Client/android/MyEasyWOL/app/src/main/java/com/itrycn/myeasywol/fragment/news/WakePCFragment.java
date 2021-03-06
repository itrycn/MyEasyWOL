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
import android.os.Bundle;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;

import com.itrycn.myeasywol.R;
import com.itrycn.myeasywol.core.BaseFragment;
import com.itrycn.myeasywol.db.DBHelper;
import com.itrycn.myeasywol.db.entity.PCInfo;
import com.itrycn.myeasywol.db.entity.ServerInfo;
import com.itrycn.myeasywol.db.util.PCInfoDb;
import com.itrycn.myeasywol.fragment.profile.MagicBoot;
import com.itrycn.myeasywol.fragment.profile.MsgCode;
import com.itrycn.myeasywol.utils.MyBroadcastReceiver;
import com.itrycn.myeasywol.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import org.greenrobot.greendao.query.WhereCondition;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

@Page(name = "????????????")
public class WakePCFragment extends BaseFragment {
    private PopupMenu mPopupMenu;
    @BindView(R.id.iv_back)
    ImageView mBackImageView;
    @BindView(R.id.toolbar_title)
    TextView mTitleTextView;
    @BindView(R.id.iv_more)
    ImageView mMoreImageView;
    /**
     * ????????????
     */
    private final int Wake_Successs = 1;
    /**
     * ????????????
     */
    private final int Wake_Failure = -1;
    /**
     * ????????????????????????????????????
     */
    private final int Wake_UnfoundServer = -2;
    /**
     * ???????????????????????????
     */
    private final int Wake_Error = -3;
    /**
     * ??????
     */
    private MyBroadcastReceiver mBroadcastReceiver;
    PCInfo pcitem;
    @BindView(R.id.lblpcname)
    TextView lblpcname;
    @BindView(R.id.lblservername)
    TextView lblservername;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wake_pc;
    }
    @Override
    protected void handleWorkerMessage(Message msg) {
    }
    /**
     * @return ????????? null????????????????????????
     */
    @Override
    protected TitleBar initTitle() {
        return null;
    }
    @Override
    protected void handleUIMessage(Message msg) {
        switch (msg.what) {
            case Wake_Successs:
                XToastUtils.success("????????????????????????????????????");
                break;
            case Wake_Failure:
                XToastUtils.error( "????????????????????????????????????\r\n????????????");
                break;
            case Wake_UnfoundServer:
                XToastUtils.warning("?????????????????????,?????????????????????");
                break;
            case Wake_Error:
                XToastUtils.error("????????????,???????????????");
                break;
        }
    }
    @Override
    protected void initViews() {
        mTitleTextView.setText("????????????");
        Bundle bundle = getArguments();
        if (bundle != null) {
            Long id= bundle.getLong(MsgCode.ACTION_DATA_KEY);
            List<PCInfo> list= DBHelper.getInstance(getContext()).getDaoSession().getPCInfoDao().queryBuilder().where(new WhereCondition.StringCondition("_id="+id)).list();
            if(list.size()==1)
            {
                pcitem=list.get(0);
                lblpcname.setText(pcitem.getName());
                if(pcitem.getServerID()==-1)
                {
                    lblservername.setText(R.string.sever_direct);
                }
                else {
                    List<ServerInfo> list_server = DBHelper.getInstance(getContext()).getDaoSession().getServerInfoDao().queryBuilder().where(new WhereCondition.StringCondition("_id=" + pcitem.getServerID())).list();
                    if (list_server.size() == 1) {
                        lblservername.setText(list_server.get(0).getName());
                    }
                }
            }
        }
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
                        Bundle bundle = getArguments();
                        if (bundle != null) {
                            Long id= bundle.getLong(MsgCode.ACTION_DATA_KEY);
                            List<PCInfo> list= DBHelper.getInstance(getContext()).getDaoSession().getPCInfoDao().queryBuilder().where(new WhereCondition.StringCondition("_id="+id)).list();
                            if(list.size()==1)
                            {
                                pcitem=list.get(0);
                                lblpcname.setText(pcitem.getName());
                                if(pcitem.getServerID()==-1)
                                {
                                    lblservername.setText(R.string.sever_direct);
                                }
                                else {
                                    List<ServerInfo> list_server = DBHelper.getInstance(getContext()).getDaoSession().getServerInfoDao().queryBuilder().where(new WhereCondition.StringCondition("_id=" + pcitem.getServerID())).list();
                                    if (list_server.size() == 1) {
                                        lblservername.setText(list_server.get(0).getName());
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        });
        mBroadcastReceiver.registerReceiver(getContext().getApplicationContext());
    }
    @Override
    public void onDestroy() {
        if (mBroadcastReceiver != null) {
            mBroadcastReceiver.unregisterReceiver(getContext().getApplicationContext());
        }
        super.onDestroy();
    }
    /**
     * ??????????????????
     *
     * @param view ??????????????????View??????
     */
    private void showPoPup(View view) {
        if (mPopupMenu == null) {
            mPopupMenu = new PopupMenu(getContext(), view);
            mPopupMenu.inflate(R.menu.menu_toolbar_edit);
            mPopupMenu.setOnMenuItemClickListener(mOnMenuItemClickListener);
        }
        mPopupMenu.show();
    }
    /**
     * ????????????
     */
    private PopupMenu.OnMenuItemClickListener mOnMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.edit:
                    Bundle bundle = new Bundle();
                    bundle.putLong(MsgCode.ACTION_DATA_KEY, pcitem.getId());
                    MyBroadcastReceiver.sendReceiver(getContext().getApplicationContext(), MsgCode.ACTION_CODE_EDIT_PC,MyBroadcastReceiver.ACTION_BUNDLEKEY,bundle);
                    return true;
                case R.id.delete:
                    MaterialDialog.Builder materialDialog= new MaterialDialog.Builder(getContext());
                    materialDialog.content("?????????????????????????????????")
                            .positiveText(R.string.lab_yes)
                            .negativeText(R.string.lab_no)
                            .onPositive((dialog, which) -> {
                                PCInfoDb.deletePCInfo(getContext(),pcitem.getId());
                                MyBroadcastReceiver.sendReceiver(getContext().getApplicationContext(), MsgCode.ACTION_CODE_UPDATE_PCLIST);
                                popToBack();
                            }).show();
                    return true;
                default:
                    return false;
            }

        }
    };
    @SingleClick
    @OnClick({R.id.btn_wake,R.id.iv_back,R.id.iv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_wake:
                if(pcitem!=null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Long ServerID=pcitem.getServerID();
                            if(ServerID==-1)//????????????
                            {
                                new Thread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        int port=pcitem.getPort();
                                        if(port<=0){port=20201;}
                                        try {
                                            boolean result = MagicBoot.SendMagic(pcitem.getMac(), pcitem.getIp(), port);
                                            if (result) {
                                                mUIHandler.sendEmptyMessage(Wake_Successs);
                                            } else {
                                                mUIHandler.sendEmptyMessage(Wake_Failure);
                                            }
                                        }
                                        catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            mUIHandler.sendEmptyMessage(Wake_Error);
                                            e.printStackTrace();
                                        }
                                    }
                                }).start(); //??????????????????????????????????????????????????????

                                //mUIHandler.sendEmptyMessage(Wake_UnfoundServer);
                            }
                            else {
                                new Thread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        int port=pcitem.getPort();
                                        if(port<=0){port=20201;}
                                        List<ServerInfo> list = DBHelper.getInstance(getContext()).getDaoSession().getServerInfoDao().queryBuilder().where(new WhereCondition.StringCondition("_id=" + ServerID)).list();
                                        if (list.size() == 1) {
                                            SendMagic(pcitem.getMac(), pcitem.getIp(), list.get(0).getUrl(),port,list.get(0).getSuccessMark());
                                        }
                                        else
                                        {
                                            mUIHandler.sendEmptyMessage(Wake_UnfoundServer);
                                        }
                                    }
                                }).start(); //??????????????????????????????????????????????????????
                            }
                        }
                    }).start(); //??????????????????????????????????????????????????????
                }
                //????????????
                break;
            case R.id.iv_back:
                popToBack();
                break;
            case R.id.iv_more:
                showPoPup(view);
                break;
            default:
                break;
        }
    }
    private void SendMagic(String Mac,String IP,String Url,int Port,String SuccessMark)
    {
        String result;
        String _Url=Url;
        if(_Url.contains("{mac}")) {
            _Url = _Url.replace("{mac}", Mac);
            _Url = _Url.replace("{ip}", IP);
            _Url = _Url.replace("{port}", String.valueOf(Port));
            result = sendGetMessage(_Url,"utf-8");
        }
        else {
            Map<String, String> params = new HashMap<String, String>();
            params.put("a", "magic");
            params.put("mac", Mac);
            params.put("ip", IP);
            params.put("port", String.valueOf(Port));
            result = sendPostMessage(_Url, params, "utf-8");
        }
        if(result.equals("ok") || result.contains("success") || result.contains("??????") || (SuccessMark!=null && !SuccessMark.equals("") && result.contains(SuccessMark)))
        {
            mUIHandler.sendEmptyMessage(Wake_Successs);
        }
        else
        {
            mUIHandler.sendEmptyMessage(Wake_Failure);
        }
    }

    /**
     * @param params ?????????url?????????
     * @param encode ????????????
     * @return
     */
    public static String sendPostMessage(String url, Map<String, String> params, String encode){
        StringBuffer buffer = new StringBuffer();
        try {//????????????????????????????????????
            URL uri = new URL(url);
            if(params != null&&!params.isEmpty()){
                //?????????
                //Map.Entry ???Map?????????????????????????????????????????????????????????????????????Key???Value???
                for(Map.Entry<String, String> entry : params.entrySet()){
                    buffer.append(entry.getKey()).append("=").
                            append(URLEncoder.encode(entry.getValue(),encode)).
                            append("&");
                }
                //            System.out.println(buffer.toString());
                //????????????????????????&???????????????;??????????????????
                buffer.deleteCharAt(buffer.length()-1);
            }
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);//??????????????????????????????
            connection.setDoOutput(true);//???????????????????????????

            connection.setRequestMethod("POST");
            //??????????????????
            connection.setUseCaches(false);
            //?????????????????????????????????????????????
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            byte[] mydata = buffer.toString().getBytes();
            connection.setRequestProperty("Content-Length", String.valueOf(mydata.length));
            connection.connect();   //????????????????????????????????????????????????

            //??????????????????????????????????????????
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(mydata,0,mydata.length);
            //??????????????????????????????????????????
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                return changeInputeStream(connection.getInputStream(),encode);

            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
    public static String sendGetMessage(String url, String encode)
    {
        //1. URL
        try
        {
        URL uri = new URL(url);
        //2. HttpURLConnection
        HttpURLConnection conn=(HttpURLConnection)uri.openConnection();
        //3. set(GET)
        conn.setRequestMethod("GET");
        //4. getInputStream
        InputStream is = conn.getInputStream();
        //5. ??????is?????????responseText????????????????????????
        int responseCode = conn.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK) {
            return changeInputeStream(conn.getInputStream(), encode);
        }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  "";
    }
    /**
     * ????????????????????????????????????
     * @param inputStream
     * @param encode
     * @return
     */
    private static String changeInputeStream(InputStream inputStream, String encode) {
        //??????????????????????????????????????????
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if(inputStream != null){
            try {
                while((len = inputStream.read(data))!=-1){
                    data.toString();
                    outputStream.write(data, 0, len);
                }
                //result???????????????????????????doPost????????????
                result = new String(outputStream.toByteArray(),encode);
                outputStream.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }
}
