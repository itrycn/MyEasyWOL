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

package com.itrycn.myeasywol.core.webview;

import android.view.KeyEvent;

import com.just.agentweb.core.AgentWeb;
import com.itrycn.myeasywol.core.BaseFragment;

/**
 * 基础web
 *
 * @author xuexiang
 * @since 2019/5/28 10:22
 */
public abstract class BaseWebViewFragment extends BaseFragment {

    protected AgentWeb mAgentWeb;

    //===================生命周期管理===========================//
    @Override
    public void onResume() {
        if (mAgentWeb != null) {
            //恢复
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mAgentWeb != null) {
            //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event);
    }

    @Override
    public void onDestroyView() {
        if (mAgentWeb != null) {
            mAgentWeb.destroy();
        }
        super.onDestroyView();
    }
}
