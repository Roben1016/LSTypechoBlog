package com.roshine.lstypechoblog.mvp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.event.ArticleDetailReceivedEvent;
import com.roshine.lstypechoblog.event.PreBlogSelectedEvent;
import com.roshine.lstypechoblog.mvp.bean.ArticleDetailBean;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.presenter.EditBlogPresenter;
import com.roshine.lstypechoblog.mvp.view.activity.base.MvpBaseActivity;
import com.roshine.lstypechoblog.mvp.view.fragment.EditBlogFragment;
import com.roshine.lstypechoblog.mvp.view.fragment.PreBlogFragment;
import com.roshine.lstypechoblog.utils.DisplayUtil;
import com.roshine.lstypechoblog.utils.LogUtil;
import com.roshine.lstypechoblog.utils.ThemeColorUtil;
import com.roshine.lstypechoblog.widget.markdown.ExpandableLinearLayout;
import com.roshine.lstypechoblog.widget.markdown.TabIconView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Method;

import butterknife.BindView;

/**
 * @author Roshine
 * @date 2017/9/8 21:48
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class EditBlogActivity extends MvpBaseActivity<ContractUtil.IEditBlogView, EditBlogPresenter> implements ContractUtil.IEditBlogView, View.OnClickListener {
    private static final int SYSTEM_GALLERY = 111;
    @BindView(R.id.id_toolbar)
    Toolbar toolbar;
    @BindView(R.id.id_appbar)
    AppBarLayout appbar;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.tabIconView)
    TabIconView mTabIconView;
    @BindView(R.id.action_other_operate)
    ExpandableLinearLayout mExpandLayout;
    private String toolTitle = "";
    private MenuItem mActionOtherOperate;

    private EditBlogPresenter presenter;
    private static final String POST_ID = "postId";

    private String postId = "";
    private EditBlogFragment editFragment;
    private PreBlogFragment preFragment;
    private String enterType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_blog;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        toolbar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        mExpandLayout.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        initActionBar(toolbar);
        initAppBarLayout(appbar);
        initViewPager();
        initTab();
    }

    private void initViewPager() {
        editFragment = EditBlogFragment.newInstance();
        preFragment = PreBlogFragment.newInstance();
        viewPager.setAdapter(new EditFragmentAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //更新标题
//                if (position == 0)
//                    getToolbar().setTitle("");
//                else if (mName != null)
//                    getToolbar().setTitle(mName);
//
//                //刷新渲染数据
                if (position == 1) {
                    EventBus.getDefault().post(new PreBlogSelectedEvent(true));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initTab() {
        mTabIconView.addTab(R.drawable.ic_shortcut_format_list_bulleted, R.id.id_shortcut_list_bulleted, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_list_numbers, R.id.id_shortcut_format_numbers, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_insert_link, R.id.id_shortcut_insert_link, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_insert_photo, R.id.id_shortcut_insert_photo, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_console, R.id.id_shortcut_console, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_bold, R.id.id_shortcut_format_bold, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_italic, R.id.id_shortcut_format_italic, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_1, R.id.id_shortcut_format_header_1, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_2, R.id.id_shortcut_format_header_2, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_3, R.id.id_shortcut_format_header_3, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_quote, R.id.id_shortcut_format_quote, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_xml, R.id.id_shortcut_xml, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_minus, R.id.id_shortcut_minus, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_strikethrough, R.id.id_shortcut_format_strikethrough, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_grid, R.id.id_shortcut_grid, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_4, R.id.id_shortcut_format_header_4, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_5, R.id.id_shortcut_format_header_5, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_6, R.id.id_shortcut_format_header_6, this);
    }

    private void initActionBar(Toolbar toolbar) {
//        if (!Check.isEmpty(getSubtitleString())) {
//            toolbar.setSubtitle("好的");
//        }
//        if (getTitleString() != null) {
        toolbar.setTitle(toolTitle);
//        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initAppBarLayout(AppBarLayout appBar) {
        if (appBar == null) return;
        if (Build.VERSION.SDK_INT >= 21) {
            appBar.setElevation(DisplayUtil.dp2px(this, 3));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor_act, menu);
        mActionOtherOperate = menu.findItem(R.id.action_other_operate);
        if (mExpandLayout.isExpanded())
            //展开，设置向上箭头
            mActionOtherOperate.setIcon(R.drawable.ic_arrow_up);
        else
            mActionOtherOperate.setIcon(R.drawable.ic_add_white_24dp);
        setOverflowIconVisible(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setOverflowIconVisible(Menu menu) {
        try {
            Class clazz = Class.forName("android.support.v7.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);
            m.invoke(menu, true);
        } catch (Exception e) {
            LogUtil.showD("OverflowIconVisible", e.getMessage());
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_other_operate://展开和收缩
                if (!mExpandLayout.isExpanded()) {
                    //没有展开，但是接下来就是展开，设置向上箭头
                    mActionOtherOperate.setIcon(R.drawable.ic_arrow_up);
                } else {
                    mActionOtherOperate.setIcon(R.drawable.ic_add_white_24dp);
                }
                mExpandLayout.toggle();
                return true;
            case R.id.action_preview://预览
                viewPager.setCurrentItem(1, true);
                return true;
            case R.id.action_edit://编辑
                viewPager.setCurrentItem(0, true);
                return true;
            case R.id.action_helper:
                toast("帮助");
//                CommonMarkdownActivity.startHelper(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!mExpandLayout.isExpanded())
                //没有展开，但是接下来就是展开，设置向上箭头
                mActionOtherOperate.setIcon(R.drawable.ic_arrow_up);
            else
                mActionOtherOperate.setIcon(R.drawable.ic_add_white_24dp);
            mExpandLayout.toggle();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.hasExtra(POST_ID)) {
            postId = intent.getStringExtra(POST_ID);
        }
        if(intent.hasExtra("enterType")){
            enterType = intent.getStringExtra("enterType");
        }
        loadDatas();
    }

    private void loadDatas() {
        if(MainActivity.ENTER_TYPE_MAIN.equals(enterType)){
            return;
        }
        showProgress();
        presenter.getArticleDetail(this, postId);
    }

    @Override
    public EditBlogPresenter getPresenter() {
        presenter = new EditBlogPresenter();
        return presenter;
    }

    @Nullable
    @Override
    public void loadSuccess(ArticleDetailBean datas) {
        hideProgress();
        if (datas != null) {
            String title = datas.getTitle();
            String mt_text_more = datas.getMt_text_more();
            String description = datas.getDescription();
            EventBus.getDefault().post(new ArticleDetailReceivedEvent(datas));
//            tvTitle.setText(toolTitle);
//            markdownView.parseMarkdown(description + mt_text_more, true);
        }
    }

    @Override
    public void loadFail(String message) {
        hideProgress();
        toast(message);
    }

    @Override
    public void onClick(View v) {
        if (R.id.id_shortcut_insert_photo == v.getId()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);// Pick an item fromthe
            intent.setType("image/*");// 从所有图片中进行选择
            startActivityForResult(intent, SYSTEM_GALLERY);
            return;
        } else if (R.id.id_shortcut_insert_link == v.getId()) {
            //插入链接
            insertLink();
            return;
        } else if (R.id.id_shortcut_grid == v.getId()) {
            //插入表格
            insertTable();
            return;
        }
        //点击事件分发
        editFragment.getPerformEditable().onClick(v);
    }


    private class EditFragmentAdapter extends FragmentPagerAdapter {

        public EditFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return editFragment;
            }
            return preFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == SYSTEM_GALLERY) {
            Uri uri = data.getData();
            String[] pojo = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.managedQuery(uri, pojo, null, null, null);
            if (cursor != null) {
//                    ContentResolver cr = this.getContentResolver();
                int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(colunm_index);
                //以上代码获取图片路径
                Uri.fromFile(new File(path));//Uri.decode(imageUri.toString())
                editFragment.getPerformEditable().perform(R.id.id_shortcut_insert_photo, Uri.fromFile(new File(path)));
            } else {
                toast(getResources().getString(R.string.image_edit_error));
            }
        }

    }


    /**
     * 插入表格
     */
    private void insertTable() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.view_common_input_table_view, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.insert_table_text))
                .setView(rootView)
                .show();
        TextInputLayout rowNumberHint = (TextInputLayout) rootView.findViewById(R.id.rowNumberHint);
        TextInputLayout columnNumberHint = (TextInputLayout) rootView.findViewById(R.id.columnNumberHint);
        EditText rowNumber = (EditText) rootView.findViewById(R.id.rowNumber);
        EditText columnNumber = (EditText) rootView.findViewById(R.id.columnNumber);
        TextView tvSure = (TextView) rootView.findViewById(R.id.sure);
        TextView tvCancel = (TextView) rootView.findViewById(R.id.cancel);
        tvSure.setTextColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        tvCancel.setTextColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        tvSure.setOnClickListener(v -> {
            String rowNumberStr = rowNumber.getText().toString().trim();
            String columnNumberStr = columnNumber.getText().toString().trim();

            if (TextUtils.isEmpty(rowNumberStr)) {
                rowNumberHint.setError("不能为空");
                return;
            }
            if (TextUtils.isEmpty(columnNumberStr)) {
                columnNumberHint.setError("不能为空");
                return;
            }


            if (rowNumberHint.isErrorEnabled())
                rowNumberHint.setErrorEnabled(false);
            if (columnNumberHint.isErrorEnabled())
                columnNumberHint.setErrorEnabled(false);

            editFragment.getPerformEditable().perform(R.id.id_shortcut_grid, Integer.parseInt(rowNumberStr), Integer.parseInt(columnNumberStr));
            dialog.dismiss();
        });

        tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    /**
     * 插入链接
     */
    private void insertLink() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.view_common_input_link_view, null);

        AlertDialog dialog = new AlertDialog.Builder(this, R.style.AlertDialogThemeV7)
                .setTitle("插入链接")
                .setView(rootView)
                .show();

        TextInputLayout titleHint = (TextInputLayout) rootView.findViewById(R.id.inputNameHint);
        TextInputLayout linkHint = (TextInputLayout) rootView.findViewById(R.id.inputHint);
        EditText title = (EditText) rootView.findViewById(R.id.name);
        EditText link = (EditText) rootView.findViewById(R.id.text);

        TextView tvSure = (TextView) rootView.findViewById(R.id.sure);
        TextView tvCancel = (TextView) rootView.findViewById(R.id.cancel);
        tvSure.setTextColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        tvCancel.setTextColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        tvSure.setOnClickListener(v -> {
            String titleStr = title.getText().toString().trim();
            String linkStr = link.getText().toString().trim();

            if (TextUtils.isEmpty(titleStr)) {
                titleHint.setError("不能为空");
                return;
            }
            if (TextUtils.isEmpty(linkStr)) {
                linkHint.setError("不能为空");
                return;
            }

            if (titleHint.isErrorEnabled())
                titleHint.setErrorEnabled(false);
            if (linkHint.isErrorEnabled())
                linkHint.setErrorEnabled(false);

            editFragment.getPerformEditable().perform(R.id.id_shortcut_insert_link, titleStr, linkStr);
            dialog.dismiss();
        });

        tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}
