package com.zkp.gank.module.main.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.zkp.gank.R;
import com.zkp.gank.base.activity.BaseActivity;
import com.zkp.gank.http.AppConfig;
import com.zkp.gank.module.main.fragment.about.AboutFragment;

import butterknife.BindView;

/**
 * @author: zkp
 * @project: Gank
 * @package: com.zkp.gank.module.main.about
 * @time: 2019/4/30 14:01
 * @description:
 */
public class ComponentActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    Fragment mTargetFragment;

    private String title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        int fragType = getIntent().getIntExtra("type_fragment", -1);
        switch (fragType) {
            case AppConfig.TYPE_ABOUT_US:
                //关于
                mTargetFragment = AboutFragment.newInstance();
                title = getString(R.string.about_us);
                break;
            default:
                break;
        }

        if (mTargetFragment == null) {
            finish();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, mTargetFragment)
                    .commit();

            mTitle.setText(title);
        }
    }

    @Override
    protected void initEventAndData() {
        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }
}