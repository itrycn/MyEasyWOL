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

import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

import com.just.agentweb.core.client.MiddlewareWebClientBase;
import com.itrycn.myeasywol.R;
import com.xuexiang.xui.utils.ResUtils;

import static com.itrycn.myeasywol.core.webview.WebViewInterceptDialog.APP_LINK_HOST;

/**
 * 【网络请求、加载】
 * WebClient（WebViewClient 这个类主要帮助WebView处理各种通知、url加载，请求时间的）中间件
 * <p>
 * <p>
 * 方法的执行顺序，例如下面用了7个中间件一个 WebViewClient
 * <p>
 * .useMiddlewareWebClient(getMiddlewareWebClient())  // 1
 * .useMiddlewareWebClient(getMiddlewareWebClient())  // 2
 * .useMiddlewareWebClient(getMiddlewareWebClient())  // 3
 * .useMiddlewareWebClient(getMiddlewareWebClient())  // 4
 * .useMiddlewareWebClient(getMiddlewareWebClient())  // 5
 * .useMiddlewareWebClient(getMiddlewareWebClient())  // 6
 * .useMiddlewareWebClient(getMiddlewareWebClient())  // 7
 * DefaultWebClient                                  // 8
 * .setWebViewClient(mWebViewClient)                 // 9
 * <p>
 * <p>
 * 典型的洋葱模型
 * 对象内部的方法执行顺序: 1->2->3->4->5->6->7->8->9->8->7->6->5->4->3->2->1
 * <p>
 * <p>
 * 中断中间件的执行， 删除super.methodName(...) 这行即可
 * <p>
 * 这里主要是做去广告的工作
 */
public class MiddlewareWebViewClient extends MiddlewareWebClientBase {

    public MiddlewareWebViewClient() {
    }

    private static int count = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.i("Info", "MiddlewareWebViewClient -- >  shouldOverrideUrlLoading:" + request.getUrl().toString() + "  c:" + (count++));
        if (shouldOverrideUrlLoadingByApp(view, request.getUrl().toString())) {
            return true;
        }
        return super.shouldOverrideUrlLoading(view, request);

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i("Info", "MiddlewareWebViewClient -- >  shouldOverrideUrlLoading:" + url + "  c:" + (count++));
        if (shouldOverrideUrlLoadingByApp(view, url)) {
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        url = url.toLowerCase();
        if (!hasAdUrl(url)) {
            //正常加载
            return super.shouldInterceptRequest(view, url);
        } else {
            //含有广告资源屏蔽请求
            return new WebResourceResponse(null, null, null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString().toLowerCase();
        if (!hasAdUrl(url)) {
            //正常加载
            return super.shouldInterceptRequest(view, request);
        } else {
            //含有广告资源屏蔽请求
            return new WebResourceResponse(null, null, null);
        }
    }

    /**
     * 判断是否存在广告的链接
     *
     * @param url
     * @return
     */
    private static boolean hasAdUrl(String url) {
        return false;
    }


    /**
     * 根据url的scheme处理跳转第三方app的业务,true代表拦截，false代表不拦截
     */
    private boolean shouldOverrideUrlLoadingByApp(WebView webView, final String url) {
        if (url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp")) {
            //不拦截http, https, ftp的请求
            Uri uri = Uri.parse(url);
            if (uri != null && !(APP_LINK_HOST.equals(uri.getHost())
                    //防止xui官网被拦截
                    && url.contains("xpage"))) {
                return false;
            }
        }

        WebViewInterceptDialog.show(url);
        return true;
    }

}
