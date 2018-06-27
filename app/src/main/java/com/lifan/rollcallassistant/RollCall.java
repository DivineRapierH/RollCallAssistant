package com.lifan.rollcallassistant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import org.apache.poi.*;

import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class RollCall extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public static final String EXTRA_MESSAGE = "com.lifan.rollcallassistant.MESSAGE";

    private BottomNavigationView navigation;
    private ViewPager viewPager;

    private FragmentRollcall fragmentRollcall = new FragmentRollcall();
    private FragmentPickup fragmentPickup = new FragmentPickup();
    private FragmentResult fragmentResult = new FragmentResult();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_call);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //添加viewPager事件监听
        viewPager.addOnPageChangeListener(this);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return fragmentRollcall;
                    case 1:
                        return fragmentPickup;
                    case 2:
                        return fragmentResult;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }

    public void importExcel(View view) {
//        Intent intent = new Intent(this, ImportExcel.class);
//        String message = "test message";
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/*");//设置类型
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);


    }

    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 1) {
//                Uri uri = data.getData();
//                Toast.makeText(this, "文件路径："+uri.getPath().toString(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            LogUtil.e(TAG, "选择的文件Uri = " + data.toString());
            //通过Uri获取真实路径
            final String excelPath = getRealFilePath(this, data.getData());
            LogUtil.e(TAG, "excelPath = " + excelPath);//    /storage/emulated/0/test.xls
            if (excelPath.contains(".xls") || excelPath.contains(".xlsx")) {
                showSnack("正在加载Excel中...");
                //载入excel
                readExcel(excelPath);
            } else {
                showSnack("此文件不是excel格式");
            }
        }
    }


    public void onClick(View v){
        Intent intent = new Intent(this, ImportExcel.class);
        String message = "test message by interface";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //点击BottomNavigationView的Item项，切换ViewPager页面
            //menu/navigation.xml里加的android:orderInCategory属性就是下面item.getOrder()取的值
            viewPager.setCurrentItem(item.getOrder());
            return true;
        }

    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        return;
    }

    @Override
    public void onPageSelected(int position) {
        //页面滑动的时候，改变BottomNavigationView的Item高亮
        navigation.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        return;
    }



    /*private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_rollcall:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.menu_pickup:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.menu_result:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_call);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
*/
}
