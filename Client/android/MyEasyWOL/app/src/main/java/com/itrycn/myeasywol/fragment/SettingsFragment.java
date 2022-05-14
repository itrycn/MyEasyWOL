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

package com.itrycn.myeasywol.fragment;

import android.os.Message;

import com.itrycn.myeasywol.R;
import com.itrycn.myeasywol.core.BaseFragment;
import com.itrycn.myeasywol.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;

/**
 * @author xuexiang
 * @since 2019-10-15 22:38
 */
@Page(name = "设置")
public class SettingsFragment extends BaseFragment implements SuperTextView.OnSuperTextViewClickListener {

    @BindView(R.id.menu_common)
    SuperTextView menuCommon;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_settings;
    }
    @Override
    protected void handleWorkerMessage(Message msg) {
    }
    @Override
    protected void handleUIMessage(Message msg) {
    }
    @Override
    protected void initViews() {
        menuCommon.setOnSuperTextViewClickListener(this);
    }

    @SingleClick
    @Override
    public void onClick(SuperTextView superTextView) {
        switch (superTextView.getId()) {
            case R.id.menu_common:
                XToastUtils.toast("暂无设置,无需在此处设置");
                break;
            default:
                break;
        }
    }
}
