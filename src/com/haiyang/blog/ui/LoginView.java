package com.haiyang.blog.ui;



import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.HttpContext;
import org.kymjs.kjframe.http.HttpConfig;

import com.haiyang.blog.AppConfig;
import com.haiyang.blog.AppContext;
import com.haiyang.blog.R;
import com.haiyang.blog.api.ApiHttpClient;
import com.haiyang.blog.api.remote.BlogPluApi;
import com.haiyang.blog.util.StringUtils;
import com.haiyang.blog.util.TDevice;
import com.haiyang.blog.util.TLog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Toast;
/**
 * Scroller应用
 * @author Ra
 * blog : http://blog.csdn.net/vipzjyno1/
 */
public class LoginView extends RelativeLayout 
								{
	/** Scroller 拖动类 */
	private Scroller mScroller;
	/** 屏幕高度  */
	private int mScreenHeigh = 0;
	/** 屏幕宽度  */
	private int mScreenWidth = 0;
	/** 点击时候Y的坐标*/
	private int downY = 0;
	/** 拖动时候Y的坐标*/
	private int moveY = 0;
	/** 拖动时候Y的方向距离*/
	private int scrollY = 0;
	/** 松开时候Y的坐标*/
	private int upY = 0;
	/** 是否在移动*/
	private Boolean isMoving = false;
	/** 布局的高度*/
	private int viewHeight = 0;
	/** 是否打开*/	
	public boolean isShow = false;
	/** 是否可以拖动*/	
	public boolean mEnabled = true;
	/** 点击外面是否关闭该界面*/	
	public boolean mOutsideTouchable = true;
	/** 上升动画时间 */
	private int mDuration = 800;
	private final static String TAG = "LoginView";
	private  EditText etUsername;
	private  EditText etPassword;
	private String mUserName;
    private String mPassword;
    private Button btnLogin;

 	public LoginView(Context context) {
		super(context);
		init(context);
	}

	public LoginView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LoginView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
		setFocusable(true);
	

		mScroller = new Scroller(context);
		mScreenHeigh = TDevice.getWindowHeigh(context);
		mScreenWidth = TDevice.getWindowWidth(context);
		// 背景设置成透明
		this.setBackgroundColor(Color.argb(0, 0, 0, 0));
		final View view = LayoutInflater.from(context).inflate(R.layout.view_login,null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);// 如果不给他设这个，它的布局的MATCH_PARENT就不知道该是多少
		addView(view, params);// ViewGroup的大小，
		// 背景设置成透明
		this.setBackgroundColor(Color.argb(0, 0, 0, 0));
		view.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				viewHeight = view.getHeight();
			}
		});
		LoginView.this.scrollTo(0, mScreenHeigh);
		ImageView btn_close = (ImageView)view.findViewById(R.id.btn_close);
		btn_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				dismiss();
			}
		});
		etUsername=(EditText)findViewById(R.id.et_username);
		etPassword=(EditText)findViewById(R.id.et_password);
		btnLogin=(Button)findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				handleLogin();
			}
		});
	
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(!mEnabled){
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) event.getY();
			Log.d(TAG, "downY = " + downY);
			//如果完全显示的时候，让布局得到触摸监听，如果不显示，触摸事件不拦截，向下传递
			if(isShow){
				return true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			moveY = (int) event.getY();
			scrollY = moveY - downY;
			//向下滑动
			if (scrollY > 0) {
				if(isShow){
					scrollTo(0, -Math.abs(scrollY));
				}
			}else{
				if(mScreenHeigh - this.getTop() <= viewHeight && !isShow){
					scrollTo(0, Math.abs(viewHeight - scrollY));
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			upY = (int) event.getY();
			if(isShow){
				if( this.getScrollY() <= -(viewHeight /2)){
					startMoveAnim(this.getScrollY(),-(viewHeight - this.getScrollY()), mDuration);
					isShow = false;
					Log.d("isShow", "false");
				} else {
					startMoveAnim(this.getScrollY(), -this.getScrollY(), mDuration);
					isShow = true;
					Log.d("isShow", "true");
				}
			}
			Log.d("this.getScrollY()", ""+this.getScrollY());
			changed();
			break;
		case MotionEvent.ACTION_OUTSIDE:
			Log.d(TAG, "ACTION_OUTSIDE");
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * 拖动动画
	 * @param startY  
	 * @param dy  移动到某点的Y坐标距离
	 * @param duration 时间
	 */
	public void startMoveAnim(int startY, int dy, int duration) {
		isMoving = true;
		mScroller.startScroll(0, startY, 0, dy, duration);
		invalidate();//通知UI线程的更新
	}
	
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			// 更新界面
			postInvalidate();
			isMoving = true;
		} else {
			isMoving = false;
		}
		super.computeScroll();
	}
	
	/** 开打界面 */
	public void show(){
		if(!isShow && !isMoving){
			LoginView.this.startMoveAnim(-viewHeight,   viewHeight, mDuration);
			isShow = true;
			Log.d("isShow", "true");
			changed();
		}
	}
	
	/** 关闭界面 */
	public void dismiss(){
		if(isShow && !isMoving){
			LoginView.this.startMoveAnim(0, -viewHeight, mDuration);
			isShow = false;
			Log.d("isShow", "false");
			changed();
		}
	}
	
	/** 是否打开 */
	public boolean isShow(){
		return isShow;
	}
	
	/** 获取是否可以拖动*/
	public boolean isSlidingEnabled() {
		return mEnabled;
	}
	
	/** 设置是否可以拖动*/
	public void setSlidingEnabled(boolean enabled) {
		mEnabled = enabled;
	}
	
	/**
	 * 设置监听接口，实现遮罩层效果
	 */
	public void setOnStatusListener(onStatusListener listener){
		this.statusListener = listener;
	}
	
    public void setOutsideTouchable(boolean touchable) {
        mOutsideTouchable = touchable;
    }
	/**
	 * 显示状态发生改变时候执行回调借口
	 */
	public void changed(){
		if(statusListener != null){
			if(isShow){
				statusListener.onShow();
			}else{
				statusListener.onDismiss();
			}
		}
	}
	
	/** 监听接口*/
	public onStatusListener statusListener;
	
	/**
	 * 监听接口，来在主界面监听界面变化状态
	 */
	public interface onStatusListener{
		/**  开打状态  */
		public void onShow();
		/**  关闭状态  */
		public void onDismiss();
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		int id = v.getId();
//		TLog.log(TAG, "click");
//        switch (id) {
//        case R.id.btn_login:
//        	TLog.log(TAG, "button");
//            handleLogin();
//            break;
//        default:
//            break;
//        }
//		
//	}
	 public void handleLogin(){
		 
		 new Thread(){
				public void run() {
					if (!prepareForLogin()) {
			            return;
			        }

			        // if the data has ready
			        mUserName = etUsername.getText().toString();
			        mPassword = etPassword.getText().toString();
			    	TLog.log(TAG, "handlelogin");
//			        showWaitDialog(R.string.progress_login);
			        BlogPluApi.login(mUserName, mPassword, mHandler);
				}
			}.start();
		 
		 
		 
	 }
    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

    	 @Override
         public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
    		 TLog.log(TAG,"yes");
    		 dismiss();
             try {
                 AsyncHttpClient client = ApiHttpClient.getHttpClient();
                 HttpContext httpContext = client.getHttpContext();
                 CookieStore cookies = (CookieStore) httpContext
                         .getAttribute(ClientContext.COOKIE_STORE);
                 if (cookies != null) {
                     String tmpcookies = "";
                     for (Cookie c : cookies.getCookies()) {
                         TLog.log(TAG,
                                 "cookie:" + c.getName() + " " + c.getValue());
                         tmpcookies += (c.getName() + "=" + c.getValue()) + ";";
                     }
                     TLog.log(TAG, "cookies:" + tmpcookies);
                     AppContext.getInstance().setProperty(AppConfig.CONF_COOKIE,
                             tmpcookies);
                     ApiHttpClient.setCookie(ApiHttpClient.getCookie(AppContext
                             .getInstance()));
                     HttpConfig.sCookie = tmpcookies;
                 }
//                 LoginUserBean user = XmlUtils.toBean(LoginUserBean.class,
//                         new ByteArrayInputStream(arg2));
//                 Result res = user.getResult();
//                 if (res.OK()) {
//                     // 保存登录信息
//                     user.getUser().setAccount(mUserName);
//                     user.getUser().setPwd(mPassword);
//                     user.getUser().setRememberMe(true);
//                     AppContext.getInstance().saveUserInfo(user.getUser());
//                     hideWaitDialog();
//                     handleLoginSuccess();
//                 } else {
//                     AppContext.getInstance().cleanLoginInfo();
//                     hideWaitDialog();
//                     AppContext.showToast(res.getErrorMessage());
//                 }
             } catch (Exception e) {
                 e.printStackTrace();
                 onFailure(arg0, arg1, arg2, e);
             }
         }

         @Override
         public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                 Throwable arg3) {
        	 System.out.println("aaaaaaaaaaaaaaa");
//        	 TLog("fail",);
//             hideWaitDialog();
//             AppContext.showToast(R.string.tip_login_error_for_network);
         }
     };

    
    private void handleLoginSuccess() { }

    private boolean prepareForLogin() {
//        if (!TDevice.hasInternet()) {
////            AppContext.showToastShort(R.string.tip_no_internet);
//            return false;
//        }
        String uName = etUsername.getText().toString();
        if (StringUtils.isEmpty(uName)) {
//            AppContext.showToastShort(R.string.tip_please_input_username);
        	
            etUsername.requestFocus();
            return false;
        }
        // 去除邮箱正确性检测
        // if (!StringUtils.isEmail(uName)) {
        // AppContext.showToastShort(R.string.tip_illegal_email);
        // mEtUserName.requestFocus();
        // return false;
        // }
        String pwd = etPassword.getText().toString();
        if (StringUtils.isEmpty(pwd)) {
        	
//            AppContext.showToastShort(R.string.tip_please_input_password);
//        	Toast.makeText(c, R.string.tip_please_input_password,
//        			Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return false;
        }
        return true;
    }



}
