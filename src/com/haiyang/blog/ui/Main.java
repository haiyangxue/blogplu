package com.haiyang.blog.ui;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;



import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.HttpContext;
import org.kymjs.kjframe.http.HttpConfig;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.View.OnClickListener;

import com.haiyang.blog.R;
import com.haiyang.blog.api.ApiHttpClient;
import com.haiyang.blog.fragment.ChatFragment;
import com.haiyang.blog.fragment.ContactsFragment;
import com.haiyang.blog.fragment.FoundFragment;
import com.haiyang.blog.ui.LoginView.onStatusListener;
import com.haiyang.blog.util.TLog;
import com.haiyang.blog.widget.PagerSlidingTabStrip;
import com.haiyang.blog.widget.NavigationDrawerFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Main extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * 聊天界面的Fragment
	 */
	private ChatFragment chatFragment;

	/**
	 * 发现界面的Fragment
	 */
	private FoundFragment foundFragment;

	/**
	 * 通讯录界面的Fragment
	 */
	private ContactsFragment contactsFragment;

	/**
	 * 登录界面的View
	 */
	private static LoginView mLoginView;

	/**
	 * 登录时的阴影覆盖
	 */
	private View view_mask;
	
	/**
	 * PagerSlidingTabStrip的实例
	 */
	private PagerSlidingTabStrip tabs;

	/**
	 * 获取当前屏幕的密度
	 */
	private DisplayMetrics dm;

	/**
	 * 左侧划出抽屉内部fragment
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * 存放上次显示在action bar中的title {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	private Fragment currentFragment;
	private Fragment lastFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setOverflowShowingAlways();
		dm = getResources().getDisplayMetrics();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		tabs.setViewPager(pager);
		setTabsValue();
		
		view_mask = (View)findViewById(R.id.view_mask);
		mLoginView = (LoginView)findViewById(R.id.mLoginView);
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// 设置抽屉
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	/**
	 * 对PagerSlidingTabStrip的各项属性进行赋值。
	 */
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(Color.parseColor("#45c01a"));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "聊天", "发现", "通讯录" };

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (chatFragment == null) {
					chatFragment = new ChatFragment();
				}
				return chatFragment;
			case 1:
				if (foundFragment == null) {
					foundFragment = new FoundFragment();
				}
				return foundFragment;
			case 2:
				if (contactsFragment == null) {
					contactsFragment = new ContactsFragment();
				}
				return contactsFragment;
			default:
				return null;
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(String title) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		currentFragment = fragmentManager.findFragmentByTag(title);
		if (currentFragment == null) {
			currentFragment = ContentFragment.newInstance(title);
			ft.add(R.id.tabs, currentFragment, title);
		}
		if (lastFragment != null) {
			ft.hide(lastFragment);
		}
		if (currentFragment.isDetached()) {
			ft.attach(currentFragment);
		}
		ft.show(currentFragment);
		lastFragment = currentFragment;
		ft.commit();
		onSectionAttached(title);
	}

	public void onSectionAttached(String title) {
		mTitle = title;
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		//设置遮罩阴影层点击消失该界面
    	view_mask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mLoginView.isShow()){
					mLoginView.dismiss();
				}
			}
		});
    	//设置登录界面状态监听
    	mLoginView.setOnStatusListener(new onStatusListener() {
			
			@Override
			public void onShow() {
				//显示
				view_mask.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onDismiss() {
				//隐藏
				view_mask.setVisibility(View.GONE);
			}
		});
    	
    	
		if (id == R.id.action_plus) {
			if(mLoginView.isShow()){
				mLoginView.dismiss();
			}else{
				mLoginView.show();
			}
			TLog.log("item", "plus");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 内容fragment
	 */
	public static class ContentFragment extends Fragment {

		private static final String ARG_SECTION_TITLE = "section_title";

		/**
		 * 返回根据title参数创建的fragment
		 */
		public static ContentFragment newInstance(String title) {
			ContentFragment fragment = new ContentFragment();
			Bundle args = new Bundle();
			args.putString(ARG_SECTION_TITLE, title);
			fragment.setArguments(args);
			return fragment;
		}

		public ContentFragment() {
		}
//		//设置登录界面状态监听
//    	mLoginView.setOnStatusListener(new onStatusListener() {
//			
//			@Override
//			public void onShow() {
//				//显示
//				view_mask.setVisibility(View.VISIBLE);
//			}
//			
//			@Override
//			public void onDismiss() {
//				//隐藏
//				view_mask.setVisibility(View.GONE);
//			}
//		});
		// @Override
		// public View onCreateView(LayoutInflater inflater, ViewGroup
		// container,
		// Bundle savedInstanceState) {
		// View rootView = inflater.inflate(R.layout.fragment_main, container,
		// false);
		// TextView textView = (TextView) rootView
		// .findViewById(R.id.section_label);
		// textView.setText(getArguments().getString(ARG_SECTION_TITLE));
		// return rootView;
		// }
	}
	
	
}