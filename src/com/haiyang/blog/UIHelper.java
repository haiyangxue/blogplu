package com.haiyang.blog;

import java.net.URLDecoder;



import org.json.JSONException;
import org.json.JSONObject;

import com.haiyang.blog.ui.LoginView;
import com.haiyang.blog.util.TDevice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ZoomButtonsController;


/**
 * 界面帮助类
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年10月10日 下午3:33:36
 * 
 */
public class UIHelper {

    /** 全局web样式 */
    // 链接样式文件，代码块高亮的处理
    public final static String linkCss = "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/client.js\"></script>"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>"
            + "<script type=\"text/javascript\">function showImagePreview(var url){window.location.url= url;}</script>";
    public final static String WEB_STYLE = linkCss
            + "<style>* {font-size:16px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} "
            + "img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} "
            + "pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;overflow: auto;} "
            + "a.tag {font-size:15px;text-decoration:none;background-color:#cfc;color:#060;border-bottom:1px solid #B1D3EB;border-right:1px solid #B1D3EB;color:#3E6D8E;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;position:relative}</style>";

    public static final String WEB_LOAD_IMAGES = "<script type=\"text/javascript\"> var allImgUrls = getAllImgSrc(document.body.innerHTML);</script>";

    private static final String SHOWIMAGE = "ima-api:action=showImage&data=";

    /**
     * 显示登录界面
     * 
     * @param context
     */
    public static void showLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginView.class);
        context.startActivity(intent);
    }


//
//    /**
//     * 显示新闻详情
//     * 
//     * @param context
//     * @param newsId
//     */
//    public static void showNewsDetail(Context context, int newsId,
//            int commentCount) {
//        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra("news_id", newsId);
//        intent.putExtra("comment_count", commentCount);
//        intent.putExtra(DetailActivity.BUNDLE_KEY_DISPLAY_TYPE,
//                DetailActivity.DISPLAY_NEWS);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 显示博客详情
//     * 
//     * @param context
//     * @param blogId
//     */
//    public static void showBlogDetail(Context context, int blogId, int count) {
//        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra("blog_id", blogId);
//        intent.putExtra("comment_count", count);
//        intent.putExtra(DetailActivity.BUNDLE_KEY_DISPLAY_TYPE,
//                DetailActivity.DISPLAY_BLOG);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 显示帖子详情
//     * 
//     * @param context
//     * @param postId
//     */
//    public static void showPostDetail(Context context, int postId, int count) {
//        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra("post_id", postId);
//        intent.putExtra("comment_count", count);
//        intent.putExtra(DetailActivity.BUNDLE_KEY_DISPLAY_TYPE,
//                DetailActivity.DISPLAY_POST);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 显示活动详情
//     * 
//     * @param context
//     * @param eventId
//     */
//    public static void showEventDetail(Context context, int eventId) {
//        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra("post_id", eventId);
//        intent.putExtra(DetailActivity.BUNDLE_KEY_DISPLAY_TYPE,
//                DetailActivity.DISPLAY_EVENT);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 显示相关Tag帖子列表
//     * 
//     * @param context
//     * @param tag
//     */
//    public static void showPostListByTag(Context context, String tag) {
//        Bundle args = new Bundle();
//        args.putString(QuestionTagFragment.BUNDLE_KEY_TAG, tag);
//        showSimpleBack(context, SimpleBackPage.QUESTION_TAG, args);
//    }
//
//    /**
//     * 显示动弹详情
//     * 
//     * @param context
//     * @param id
//     */
//    public static void showTweetDetail(Context context, Tweet tweet, int tweetid) {
//        Intent intent = new Intent(context, DetailActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("tweet_id", tweetid);
//        bundle.putInt(DetailActivity.BUNDLE_KEY_DISPLAY_TYPE,
//                DetailActivity.DISPLAY_TWEET);
//        if (tweet != null) {
//            bundle.putParcelable("tweet", tweet);
//        }
//        intent.putExtras(bundle);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 显示软件详情
//     * 
//     * @param context
//     * @param ident
//     */
//    public static void showSoftwareDetail(Context context, String ident) {
//        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra("ident", ident);
//        intent.putExtra(DetailActivity.BUNDLE_KEY_DISPLAY_TYPE,
//                DetailActivity.DISPLAY_SOFTWARE);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 新闻超链接点击跳转
//     * 
//     * @param context
//     * @param newsId
//     * @param newsType
//     * @param objId
//     */
//    public static void showNewsRedirect(Context context, News news) {
//        String url = news.getUrl();
//        // 如果是活动则直接跳转活动详情页面
//        String eventUrl = news.getNewType().getEventUrl();
//        if (!StringUtils.isEmpty(eventUrl)) {
//            showEventDetail(context,
//                    StringUtils.toInt(news.getNewType().getAttachment()));
//            return;
//        }
//        // url为空-旧方法
//        if (StringUtils.isEmpty(url)) {
//            int newsId = news.getId();
//            int newsType = news.getNewType().getType();
//            String objId = news.getNewType().getAttachment();
//            switch (newsType) {
//            case News.NEWSTYPE_NEWS:
//                showNewsDetail(context, newsId, news.getCommentCount());
//                break;
//            case News.NEWSTYPE_SOFTWARE:
//                showSoftwareDetail(context, objId);
//                break;
//            case News.NEWSTYPE_POST:
//                showPostDetail(context, StringUtils.toInt(objId),
//                        news.getCommentCount());
//                break;
//            case News.NEWSTYPE_BLOG:
//                showBlogDetail(context, StringUtils.toInt(objId),
//                        news.getCommentCount());
//                break;
//            default:
//                break;
//            }
//        } else {
//            showUrlRedirect(context, url);
//        }
//    }
//
//    /**
//     * 动态点击跳转到相关新闻、帖子等
//     * 
//     * @param context
//     * @param id
//     * @param catalog
//     *            0其他 1新闻 2帖子 3动弹 4博客
//     */
//    public static void showActiveRedirect(Context context, Active active) {
//        String url = active.getUrl();
//        // url为空-旧方法
//        if (StringUtils.isEmpty(url)) {
//            int id = active.getObjectId();
//            int catalog = active.getCatalog();
//            switch (catalog) {
//            case Active.CATALOG_OTHER:
//                // 其他-无跳转
//                break;
//            case Active.CATALOG_NEWS:
//                showNewsDetail(context, id, active.getCommentCount());
//                break;
//            case Active.CATALOG_POST:
//                showPostDetail(context, id, active.getCommentCount());
//                break;
//            case Active.CATALOG_TWEET:
//                showTweetDetail(context, null, id);
//                break;
//            case Active.CATALOG_BLOG:
//                showBlogDetail(context, id, active.getCommentCount());
//                break;
//            default:
//                break;
//            }
//        } else {
//            showUrlRedirect(context, url);
//        }
//    }
//
//    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
//    public static void initWebView(WebView webView) {
//        WebSettings settings = webView.getSettings();
//        settings.setDefaultFontSize(15);
//        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        int sysVersion = Build.VERSION.SDK_INT;
//        if (sysVersion >= 11) {
//            settings.setDisplayZoomControls(false);
//        } else {
//            ZoomButtonsController zbc = new ZoomButtonsController(webView);
//            zbc.getZoomControls().setVisibility(View.GONE);
//        }
//        webView.setWebViewClient(UIHelper.getWebViewClient());
//    }
//
//    /**
//     * 添加网页的点击图片展示支持
//     */
//    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
//    @JavascriptInterface
//    public static void addWebImageShow(final Context cxt, WebView wv) {
//        wv.getSettings().setJavaScriptEnabled(true);
//        wv.addJavascriptInterface(new OnWebViewImageListener() {
//            @Override
//            @JavascriptInterface
//            public void showImagePreview(String bigImageUrl) {
//                if (bigImageUrl != null && !StringUtils.isEmpty(bigImageUrl)) {
//                    UIHelper.showImagePreview(cxt, new String[] { bigImageUrl });
//                }
//            }
//        }, "mWebViewImageListener");
//    }
//
//    /**
//     * 获取webviewClient对象
//     * 
//     * @return
//     */
//    public static WebViewClient getWebViewClient() {
//
//        return new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                showUrlRedirect(view.getContext(), url);
//                return true;
//            }
//        };
//    }
//
//    public static String setHtmlCotentSupportImagePreview(String body) {
//        // 读取用户设置：是否加载文章图片--默认有wifi下始终加载图片
//        if (AppContext.get(AppConfig.KEY_LOAD_IMAGE, true)
//                || TDevice.isWifiOpen()) {
//            // 过滤掉 img标签的width,height属性
//            body = body.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
//            body = body.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");
//            // 添加点击图片放大支持
//            // 添加点击图片放大支持
//            body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
//                    "$1$2\" onClick=\"showImagePreview('$2')\"");
//        } else {
//            // 过滤掉 img标签
//            body = body.replaceAll("<\\s*img\\s+([^>]*)\\s*>", "");
//        }
//        return body;
//    }
//
//    /**
//     * 摇一摇点击跳转
//     * 
//     * @param obj
//     */
//    public static void showUrlShake(Context context, ShakeObject obj) {
//        if (StringUtils.isEmpty(obj.getUrl())) {
//            if (ShakeObject.RANDOMTYPE_NEWS.equals(obj.getRandomtype())) {
//                UIHelper.showNewsDetail(context,
//                        StringUtils.toInt(obj.getId()),
//                        StringUtils.toInt(obj.getCommentCount()));
//            }
//        } else {
//            if (!StringUtils.isEmpty(obj.getUrl())) {
//                UIHelper.showUrlRedirect(context, obj.getUrl());
//            }
//        }
//    }
//
//    /**
//     * url跳转
//     * 
//     * @param context
//     * @param url
//     */
//    public static void showUrlRedirect(Context context, String url) {
//        if (url == null)
//            return;
//        if (url.contains("city.oschina.net/")) {
//            int id = StringUtils.toInt(url.substring(url.lastIndexOf('/') + 1));
//            UIHelper.showEventDetail(context, id);
//            return;
//        }
//
//        if (url.startsWith(SHOWIMAGE)) {
//            String realUrl = url.substring(SHOWIMAGE.length());
//            try {
//                JSONObject json = new JSONObject(realUrl);
//                int idx = json.optInt("index");
//                String[] urls = json.getString("urls").split(",");
//                showImagePreview(context, idx, urls);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return;
//        }
//        URLsUtils urls = URLsUtils.parseURL(url);
//        if (urls != null) {
//            showLinkRedirect(context, urls.getObjType(), urls.getObjId(),
//                    urls.getObjKey());
//        } else {
//            openBrowser(context, url);
//        }
//    }
//
//    public static void showLinkRedirect(Context context, int objType,
//            int objId, String objKey) {
//        switch (objType) {
//        case URLsUtils.URL_OBJ_TYPE_NEWS:
//            showNewsDetail(context, objId, -1);
//            break;
//        case URLsUtils.URL_OBJ_TYPE_QUESTION:
//            showPostDetail(context, objId, 0);
//            break;
//        case URLsUtils.URL_OBJ_TYPE_QUESTION_TAG:
//            showPostListByTag(context, objKey);
//            break;
//        case URLsUtils.URL_OBJ_TYPE_SOFTWARE:
//            showSoftwareDetail(context, objKey);
//            break;
//        case URLsUtils.URL_OBJ_TYPE_ZONE:
//            showUserCenter(context, objId, objKey);
//            break;
//        case URLsUtils.URL_OBJ_TYPE_TWEET:
//            showTweetDetail(context, null, objId);
//            break;
//        case URLsUtils.URL_OBJ_TYPE_BLOG:
//            showBlogDetail(context, objId, 0);
//            break;
//        case URLsUtils.URL_OBJ_TYPE_OTHER:
//            openBrowser(context, objKey);
//            break;
//        case URLsUtils.URL_OBJ_TYPE_TEAM:
//            openSysBrowser(context, objKey);
//            break;
//        case URLsUtils.URL_OBJ_TYPE_GIT:
//            openSysBrowser(context, objKey);
//            break;
//        }
//    }
//
//    /**
//     * 打开内置浏览器
//     * 
//     * @param context
//     * @param url
//     */
//    public static void openBrowser(Context context, String url) {
//
//        if (StringUtils.isImgUrl(url)) {
//            ImagePreviewActivity.showImagePrivew(context, 0,
//                    new String[] { url });
//            return;
//        }
//
//        if (url.startsWith("http://www.oschina.net/tweet-topic/")) {
//            Bundle bundle = new Bundle();
//            int i = url.lastIndexOf("/");
//            if (i != -1) {
//                bundle.putString("topic",
//                        URLDecoder.decode(url.substring(i + 1)));
//            }
//            UIHelper.showSimpleBack(context, SimpleBackPage.TWEET_TOPIC_LIST,
//                    bundle);
//            return;
//        }
//        try {
//            // 启用外部浏览器
//            // Uri uri = Uri.parse(url);
//            // Intent it = new Intent(Intent.ACTION_VIEW, uri);
//            // context.startActivity(it);
//            Bundle bundle = new Bundle();
//            bundle.putString(BrowserFragment.BROWSER_KEY, url);
//            showSimpleBack(context, SimpleBackPage.BROWSER, bundle);
//        } catch (Exception e) {
//            e.printStackTrace();
//            AppContext.showToastShort("无法浏览此网页");
//        }
//    }
//
//    /**
//     * 打开系统中的浏览器
//     * 
//     * @param context
//     * @param url
//     */
//    public static void openSysBrowser(Context context, String url) {
//        try {
//            Uri uri = Uri.parse(url);
//            Intent it = new Intent(Intent.ACTION_VIEW, uri);
//            context.startActivity(it);
//        } catch (Exception e) {
//            e.printStackTrace();
//            AppContext.showToastShort("无法浏览此网页");
//        }
//    }
//
//    @JavascriptInterface
//    public static void showImagePreview(Context context, String[] imageUrls) {
//        ImagePreviewActivity.showImagePrivew(context, 0, imageUrls);
//    }
//
//    @JavascriptInterface
//    public static void showImagePreview(Context context, int index,
//            String[] imageUrls) {
//        ImagePreviewActivity.showImagePrivew(context, index, imageUrls);
//    }
//
//    public static void showSimpleBackForResult(Fragment fragment,
//            int requestCode, SimpleBackPage page, Bundle args) {
//        Intent intent = new Intent(fragment.getActivity(),
//                SimpleBackActivity.class);
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
//        fragment.startActivityForResult(intent, requestCode);
//    }
//
//    public static void showSimpleBackForResult(Activity context,
//            int requestCode, SimpleBackPage page, Bundle args) {
//        Intent intent = new Intent(context, SimpleBackActivity.class);
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
//        context.startActivityForResult(intent, requestCode);
//    }
//
//    public static void showSimpleBackForResult(Activity context,
//            int requestCode, SimpleBackPage page) {
//        Intent intent = new Intent(context, SimpleBackActivity.class);
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
//        context.startActivityForResult(intent, requestCode);
//    }
//
//    public static void showSimpleBack(Context context, SimpleBackPage page) {
//        Intent intent = new Intent(context, SimpleBackActivity.class);
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
//        context.startActivity(intent);
//    }
//
//    public static void showSimpleBack(Context context, SimpleBackPage page,
//            Bundle args) {
//        Intent intent = new Intent(context, SimpleBackActivity.class);
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
//        context.startActivity(intent);
//    }
//
//    public static void showTweetActivity(Context context, SimpleBackPage page,
//            Bundle args) {
//        Intent intent = new Intent(context, TweetActivity.class);
//        intent.putExtra(TweetActivity.FROM_KEY, 1);
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
//        context.startActivity(intent);
//    }
//
//    public static void showComment(Context context, int id, int catalog) {
//        Bundle args = new Bundle();
//        args.putInt(CommentFrament.BUNDLE_KEY_ID, id);
//        args.putInt(CommentFrament.BUNDLE_KEY_CATALOG, catalog);
//        showSimpleBack(context, SimpleBackPage.COMMENT, args);
//    }
//
//    public static void showSoftWareTweets(Context context, int id) {
//        Bundle args = new Bundle();
//        args.putInt(SoftWareTweetsFrament.BUNDLE_KEY_ID, id);
//        showSimpleBack(context, SimpleBackPage.SOFTWARE_TWEETS, args);
//    }
//
//    public static void showBlogComment(Context context, int id, int ownerId) {
//        Bundle args = new Bundle();
//        args.putInt(CommentFrament.BUNDLE_KEY_ID, id);
//        args.putInt(CommentFrament.BUNDLE_KEY_OWNER_ID, ownerId);
//        args.putBoolean(CommentFrament.BUNDLE_KEY_BLOG, true);
//        showSimpleBack(context, SimpleBackPage.COMMENT, args);
//    }
//
//    public static SpannableString parseActiveAction(int objecttype,
//            int objectcatalog, String objecttitle) {
//        String title = "";
//        int start = 0;
//        int end = 0;
//        if (objecttype == 32 && objectcatalog == 0) {
//            title = "加入了开源中国";
//        } else if (objecttype == 1 && objectcatalog == 0) {
//            title = "添加了开源项目 " + objecttitle;
//        } else if (objecttype == 2 && objectcatalog == 1) {
//            title = "在讨论区提问：" + objecttitle;
//        } else if (objecttype == 2 && objectcatalog == 2) {
//            title = "发表了新话题：" + objecttitle;
//        } else if (objecttype == 3 && objectcatalog == 0) {
//            title = "发表了博客 " + objecttitle;
//        } else if (objecttype == 4 && objectcatalog == 0) {
//            title = "发表一篇新闻 " + objecttitle;
//        } else if (objecttype == 5 && objectcatalog == 0) {
//            title = "分享了一段代码 " + objecttitle;
//        } else if (objecttype == 6 && objectcatalog == 0) {
//            title = "发布了一个职位：" + objecttitle;
//        } else if (objecttype == 16 && objectcatalog == 0) {
//            title = "在新闻 " + objecttitle + " 发表评论";
//        } else if (objecttype == 17 && objectcatalog == 1) {
//            title = "回答了问题：" + objecttitle;
//        } else if (objecttype == 17 && objectcatalog == 2) {
//            title = "回复了话题：" + objecttitle;
//        } else if (objecttype == 17 && objectcatalog == 3) {
//            title = "在 " + objecttitle + " 对回帖发表评论";
//        } else if (objecttype == 18 && objectcatalog == 0) {
//            title = "在博客 " + objecttitle + " 发表评论";
//        } else if (objecttype == 19 && objectcatalog == 0) {
//            title = "在代码 " + objecttitle + " 发表评论";
//        } else if (objecttype == 20 && objectcatalog == 0) {
//            title = "在职位 " + objecttitle + " 发表评论";
//        } else if (objecttype == 101 && objectcatalog == 0) {
//            title = "回复了动态：" + objecttitle;
//        } else if (objecttype == 100) {
//            title = "更新了动态";
//        }
//        SpannableString sp = new SpannableString(title);
//        // 设置标题字体大小、高亮
//        if (!StringUtils.isEmpty(objecttitle)) {
//            start = title.indexOf(objecttitle);
//            if (objecttitle.length() > 0 && start > 0) {
//                end = start + objecttitle.length();
//                sp.setSpan(new AbsoluteSizeSpan(14, true), start, end,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                sp.setSpan(
//                        new ForegroundColorSpan(Color.parseColor("#0e5986")),
//                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }
//        return sp;
//    }
//
//    /**
//     * 组合动态的回复文本
//     * 
//     * @param name
//     * @param body
//     * @return
//     */
//    public static SpannableStringBuilder parseActiveReply(String name,
//            String body) {
//        Spanned span = Html.fromHtml(body.trim());
//        SpannableStringBuilder sp = new SpannableStringBuilder(name + "：");
//        sp.append(span);
//        // 设置用户名字体加粗、高亮
//        // sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
//        // name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#576B95")), 0,
//                name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        return sp;
//    }
//
    /**
     * 发送App异常崩溃报告
     * 
     * @param cont
     * @param crashReport
     */
    public static void sendAppCrashReport(final Context context,
            final String crashReport) {
//        CommonDialog dialog = new CommonDialog(context);
//
//        dialog.setTitle(R.string.app_error);
//        dialog.setMessage(R.string.app_error_message);
//        dialog.setPositiveButton(R.string.submit_report,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        // 发送异常报告
//                        TDevice.sendEmail(context, "OSCAndroid客户端耍脾气 - 症状诊断报告",
//                                crashReport, "apposchina@163.com");
//                        // 退出
//                        AppManager.getAppManager().AppExit(context);
//                    }
//                });
//        dialog.setNegativeButton(R.string.cancle,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        // 退出
//                        AppManager.getAppManager().AppExit(context);
//                    }
//                });
//        dialog.show();
    }
//
    public static void sendAppCrashReport(final Context context) {
//        CommonDialog dialog = new CommonDialog(context);
//        dialog.setTitle(R.string.app_error);
//        dialog.setMessage(R.string.app_error_message);
//        dialog.setNegativeButton(R.string.ok,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        System.exit(-1);
//                    }
//                });
//        dialog.show();
    }
//
//    /**
//     * 发送通知广播
//     * 
//     * @param context
//     * @param notice
//     */
//    public static void sendBroadCast(Context context, Notice notice) {
//        if (!((AppContext) context.getApplicationContext()).isLogin()
//                || notice == null)
//            return;
//        Intent intent = new Intent(Constants.INTENT_ACTION_NOTICE);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("notice_bean", notice);
//        intent.putExtras(bundle);
//        context.sendBroadcast(intent);
//    }
//
//    /**
//     * 发送通知广播
//     * 
//     * @param context
//     */
//    public static void sendBroadcastForNotice(Context context) {
//        Intent intent = new Intent(NoticeService.INTENT_ACTION_BROADCAST);
//        context.sendBroadcast(intent);
//    }
//
//    /**
//     * 显示用户中心页面
//     * 
//     * @param context
//     * @param uid
//     * @param hisuid
//     * @param hisname
//     */
//    public static void showUserCenter(Context context, int hisuid,
//            String hisname) {
//        if (hisuid == 0 && hisname.equalsIgnoreCase("匿名")) {
//            AppContext.showToast("提醒你，该用户为非会员");
//            return;
//        }
//        Bundle args = new Bundle();
//        args.putInt("his_id", hisuid);
//        args.putString("his_name", hisname);
//        showSimpleBack(context, SimpleBackPage.USER_CENTER, args);
//    }
//
//    /**
//     * 显示用户的博客列表
//     * 
//     * @param context
//     * @param uid
//     */
//    public static void showUserBlog(Context context, int uid) {
//        Bundle args = new Bundle();
//        args.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, uid);
//        showSimpleBack(context, SimpleBackPage.USER_BLOG, args);
//    }
//
//    /**
//     * 显示用户头像大图
//     * 
//     * @param context
//     * @param avatarUrl
//     */
//    public static void showUserAvatar(Context context, String avatarUrl) {
//        if (StringUtils.isEmpty(avatarUrl)) {
//            return;
//        }
//        String url = AvatarView.getLargeAvatar(avatarUrl);
//        ImagePreviewActivity.showImagePrivew(context, 0, new String[] { url });
//    }
//
//    /**
//     * 显示登陆用户的个人中心页面
//     * 
//     * @param context
//     */
//    public static void showMyInformation(Context context) {
//        showSimpleBack(context, SimpleBackPage.MY_INFORMATION);
//    }
//
//    /**
//     * 显示我的所有动态
//     * 
//     * @param context
//     */
//    public static void showMyActive(Context context) {
//        showSimpleBack(context, SimpleBackPage.MY_ACTIVE);
//    }
//
//    /**
//     * 显示扫一扫界面
//     * 
//     * @param context
//     */
//    public static void showScanActivity(Context context) {
//        Intent intent = new Intent(context, CaptureActivity.class);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 显示用户的消息中心
//     * 
//     * @param context
//     */
//    public static void showMyMes(Context context) {
//        showSimpleBack(context, SimpleBackPage.MY_MES);
//    }
//
//    /**
//     * 显示用户收藏界面
//     * 
//     * @param activity
//     */
//    public static void showUserFavorite(Context context, int uid) {
//
//        Bundle args = new Bundle();
//        args.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, uid);
//        showSimpleBack(context, SimpleBackPage.USER_FAVORITE);
//    }
//
//    /*
//     * 显示用户的关注/粉丝列表
//     * 
//     * @param context
//     */
//    public static void showFriends(Context context, int uid, int tabIdx) {
//        Bundle args = new Bundle();
//        args.putInt(FriendsViewPagerFragment.BUNDLE_KEY_TABIDX, tabIdx);
//        args.putInt(FriendsFragment.BUNDLE_KEY_UID, uid);
//        showSimpleBack(context, SimpleBackPage.MY_FRIENDS, args);
//    }
//
//    /**
//     * 显示留言对话页面
//     * 
//     * @param context
//     * @param catalog
//     * @param friendid
//     */
//    public static void showMessageDetail(Context context, int friendid,
//            String friendname) {
//        Bundle args = new Bundle();
//        args.putInt(MessageDetailFragment.BUNDLE_KEY_FID, friendid);
//        args.putString(MessageDetailFragment.BUNDLE_KEY_FNAME, friendname);
//        showSimpleBack(context, SimpleBackPage.MESSAGE_DETAIL, args);
//    }
//
//    /**
//     * 显示设置界面
//     * 
//     * @param context
//     */
//    public static void showSetting(Context context) {
//        showSimpleBack(context, SimpleBackPage.SETTING);
//    }
//
//    /**
//     * 显示通知设置界面
//     * 
//     * @param context
//     */
//    public static void showSettingNotification(Context context) {
//        showSimpleBack(context, SimpleBackPage.SETTING_NOTIFICATION);
//    }
//
//    /**
//     * 显示关于界面
//     * 
//     * @param context
//     */
//    public static void showAboutOSC(Context context) {
//        showSimpleBack(context, SimpleBackPage.ABOUT_OSC);
//    }
//
//    /**
//     * 清除app缓存
//     * 
//     * @param activity
//     */
//    public static void clearAppCache(Activity activity) {
//        final Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == 1) {
//                    AppContext.showToastShort("缓存清除成功");
//                } else {
//                    AppContext.showToastShort("缓存清除失败");
//                }
//            }
//        };
//        new Thread() {
//            @Override
//            public void run() {
//                Message msg = new Message();
//                try {
//                    AppContext.getInstance().clearAppCache();
//                    msg.what = 1;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    msg.what = -1;
//                }
//                handler.sendMessage(msg);
//            }
//        }.start();
//    }
//
//    public static void openDownLoadService(Context context, String downurl,
//            String tilte) {
//        final ICallbackResult callback = new ICallbackResult() {
//
//            @Override
//            public void OnBackResult(Object s) {}
//        };
//        ServiceConnection conn = new ServiceConnection() {
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {}
//
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                DownloadBinder binder = (DownloadBinder) service;
//                binder.addCallback(callback);
//                binder.start();
//
//            }
//        };
//        Intent intent = new Intent(context, DownloadService.class);
//        intent.putExtra(DownloadService.BUNDLE_KEY_DOWNLOAD_URL, downurl);
//        intent.putExtra(DownloadService.BUNDLE_KEY_TITLE, tilte);
//        context.startService(intent);
//        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
//    }
//
//    /**
//     * 发送广播告知评论发生变化
//     * 
//     * @param context
//     * @param isBlog
//     * @param id
//     * @param catalog
//     * @param operation
//     * @param replyComment
//     */
//    public static void sendBroadCastCommentChanged(Context context,
//            boolean isBlog, int id, int catalog, int operation,
//            Comment replyComment) {
//        Intent intent = new Intent(Constants.INTENT_ACTION_COMMENT_CHANGED);
//        Bundle args = new Bundle();
//        args.putInt(Comment.BUNDLE_KEY_ID, id);
//        args.putInt(Comment.BUNDLE_KEY_CATALOG, catalog);
//        args.putBoolean(Comment.BUNDLE_KEY_BLOG, isBlog);
//        args.putInt(Comment.BUNDLE_KEY_OPERATION, operation);
//        args.putParcelable(Comment.BUNDLE_KEY_COMMENT, replyComment);
//        intent.putExtras(args);
//        context.sendBroadcast(intent);
//    }
//
//    /**
//     * 显示活动地址地图信息
//     * 
//     * @param context
//     * @param page
//     */
//    public static void showEventLocation(Context context, String city,
//            String location) {
//        Intent intent = new Intent(context, EventLocationActivity.class);
//        intent.putExtra("city", city);
//        intent.putExtra("location", location);
//        context.startActivity(intent);
//    }
//
//    public static void showCreateNewIssue(Context context, Team team,
//            TeamProject project, TeamIssueCatalog catalog) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("team", team);
//        if (project != null) {
//            bundle.putSerializable("project", project);
//        }
//        if (catalog != null) {
//            bundle.putSerializable("catalog", catalog);
//        }
//        UIHelper.showSimpleBack(context, SimpleBackPage.TEAM_NEW_ISSUE, bundle);
//    }
//
//    /***
//     * 显示任务详情
//     * 
//     * @author 火蚁 2015-1-30 下午2:59:57
//     * 
//     * @return void
//     * @param context
//     * @param team
//     * @param issue
//     */
//    public static void showTeamIssueDetail(Context context, Team team,
//            TeamIssue issue, TeamIssueCatalog catalog) {
//        Intent intent = new Intent(context, DetailActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("teamid", team.getId());
//        bundle.putInt("issueid", issue.getId());
//        bundle.putSerializable("team", team);
//        bundle.putSerializable("issue", issue);
//        bundle.putSerializable("issue_catalog", catalog);
//        bundle.putInt(DetailActivity.BUNDLE_KEY_DISPLAY_TYPE,
//                DetailActivity.DISPLAY_TEAM_ISSUE_DETAIL);
//        intent.putExtras(bundle);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 显示讨论贴详情
//     * 
//     * @author 火蚁 2015-2-2 下午6:37:53
//     * 
//     * @return void
//     * @param context
//     * @param team
//     * @param discuss
//     */
//    public static void showTeamDiscussDetail(Context context, Team team,
//            TeamDiscuss discuss) {
//        Intent intent = new Intent(context, DetailActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("teamid", team.getId());
//        bundle.putInt("discussid", discuss.getId());
//        bundle.putInt(DetailActivity.BUNDLE_KEY_DISPLAY_TYPE,
//                DetailActivity.DISPLAY_TEAM_DISCUSS_DETAIL);
//        intent.putExtras(bundle);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 显示周报详情
//     * 
//     * @param context
//     * @param data
//     */
//    public static void showDiaryDetail(Context context, Bundle data) {
//        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra("diary", data);
//        intent.putExtra(DetailActivity.BUNDLE_KEY_DISPLAY_TYPE,
//                DetailActivity.DISPLAY_TEAM_DIARY);
//        context.startActivity(intent);
//    }
//
//    public static void showTeamMemberInfo(Context context, int teamId,
//            TeamMember teamMember) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(TeamMemberAdapter.TEAM_MEMBER_KEY, teamMember);
//        bundle.putInt(TeamMemberAdapter.TEAM_ID_KEY, teamId);
//        UIHelper.showSimpleBack(context, SimpleBackPage.TEAM_USER_INFO, bundle);
//    }
//
//    /***
//     * 显示团队动态详情
//     * 
//     * @author 火蚁 2015-3-13 下午5:34:50
//     * 
//     * @return void
//     * @param contex
//     * @param teamId
//     * @param active
//     */
//    public static void showTeamActiveDetail(Context contex, int teamId,
//            TeamActive active) {
//        Intent intent = new Intent(contex, DetailActivity.class);
//
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(TeamActiveFragment.DYNAMIC_FRAGMENT_KEY, active);
//        bundle.putInt(TeamActiveFragment.DYNAMIC_FRAGMENT_TEAM_KEY, teamId);
//        bundle.putInt(DetailActivity.BUNDLE_KEY_DISPLAY_TYPE,
//                DetailActivity.DISPLAY_TEAM_TWEET_DETAIL);
//        intent.putExtras(bundle);
//        contex.startActivity(intent);
//    }
}
