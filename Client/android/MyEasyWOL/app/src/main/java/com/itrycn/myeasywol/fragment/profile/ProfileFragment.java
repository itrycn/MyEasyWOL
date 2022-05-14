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

package com.itrycn.myeasywol.fragment.profile;

import android.os.Message;

import com.itrycn.myeasywol.R;
import com.itrycn.myeasywol.core.BaseFragment;
import com.itrycn.myeasywol.fragment.AboutFragment;
import com.itrycn.myeasywol.fragment.SettingsFragment;
import com.itrycn.myeasywol.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;

/**
 * @author xuexiang
 * @since 2019-10-30 00:18
 */
@Page(anim = CoreAnim.none)
public class ProfileFragment extends BaseFragment implements SuperTextView.OnSuperTextViewClickListener {
    @BindView(R.id.menu_settings)
    SuperTextView menuSettings;
    @BindView(R.id.menu_about)
    SuperTextView menuAbout;
    @BindView(R.id.menu_yijian)
    SuperTextView menu_yijian;
    @Override
    protected void handleUIMessage(Message msg) {

    }

    @Override
    protected void handleWorkerMessage(Message msg) {

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
        return R.layout.fragment_profile;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {
        menuSettings.setOnSuperTextViewClickListener(this);
        menuAbout.setOnSuperTextViewClickListener(this);
        menu_yijian.setOnSuperTextViewClickListener(this);
    }

    @SingleClick
    @Override
    public void onClick(SuperTextView view) {
        switch(view.getId()) {
            case R.id.menu_yijian:
                XToastUtils.toast("请通过邮箱:zilinsoft@qq.com进行反馈。");;
                break;
            case R.id.menu_settings:
                openNewPage(SettingsFragment.class);
                break;
            case R.id.menu_about:
                openNewPage(AboutFragment.class);
                break;
            default:
                break;
        }
    }
}
