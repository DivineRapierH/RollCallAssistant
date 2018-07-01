package com.lifan.rollcallassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;

import org.apache.commons.codec.binary.Base64;

import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Locale;

public class RollCall extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public static final String EXTRA_MESSAGE = "com.lifan.rollcallassistant.MESSAGE";

    private BottomNavigationView navigation;
    private ViewPager viewPager;

    private FragmentRollcall fragmentRollcall = new FragmentRollcall();
    private FragmentPickup fragmentPickup = new FragmentPickup();
    private FragmentResult fragmentResult = new FragmentResult();

    ClassInfo SavedClass = new ClassInfo();

    private int StudentPointer = 0;
    private int PickPointer;

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
        //跳转到导入Excel的Activity
        Intent intent = new Intent(this, ImportExcel.class);
        String message = "test message";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void refreshResult(View view){
        //刷新result界面显示学生信息

        //读取本地文件获得学生信息
        SavedClass = readClass();
        fragmentResult.set();

        Toast.makeText(getApplicationContext(), "Successfully refreshed!", Toast.LENGTH_SHORT).show();
    }


    private TextToSpeech mTts ;

    public void startRollCall(View v){
        StudentPointer = 0;
        fragmentRollcall.setName(StudentPointer);
        mTts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTts.setLanguage(Locale.US);
                    mTts.speak(SavedClass.Students[StudentPointer].getStuName(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

    }

    public void previousStudent(View v){
        if(StudentPointer == 0)
            return;
        StudentPointer--;
        fragmentRollcall.setName(StudentPointer);
        mTts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTts.setLanguage(Locale.US);
                    mTts.speak(SavedClass.Students[StudentPointer].getStuName(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    public void nextStudent(View v){
        if(StudentPointer == 4)
            return;
        StudentPointer++;
        fragmentRollcall.setName(StudentPointer);
        mTts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTts.setLanguage(Locale.US);
                    mTts.speak(SavedClass.Students[StudentPointer].getStuName(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    public void stuAttended(View v){
        if(StudentPointer == 4) {
            Toast.makeText(getApplicationContext(), "Roll call finished!", Toast.LENGTH_SHORT).show();
            return;
        }
        StudentPointer++;
        fragmentRollcall.setName(StudentPointer);
        mTts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTts.setLanguage(Locale.US);
                    mTts.speak(SavedClass.Students[StudentPointer].getStuName(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    public void stuAbsent(View v){
        SavedClass.Students[StudentPointer].addAbsence();
        saveClass(SavedClass);
        if(StudentPointer == 4) {
            Toast.makeText(getApplicationContext(), "Roll call finished!", Toast.LENGTH_SHORT).show();
            return;
        }
        StudentPointer++;
        fragmentRollcall.setName(StudentPointer);
        mTts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTts.setLanguage(Locale.US);
                    mTts.speak(SavedClass.Students[StudentPointer].getStuName(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    public void pickStudent(View v){
        final int pickPointer = (int)System.currentTimeMillis() % 5;
        PickPointer = pickPointer;
        //Toast.makeText(getApplicationContext(), "Pointer:"+pickPointer, Toast.LENGTH_SHORT).show();
        fragmentPickup.setName(pickPointer);
        mTts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTts.setLanguage(Locale.US);
                    mTts.speak(SavedClass.Students[pickPointer].getStuName(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    public void score1(View v){
        SavedClass.Students[PickPointer].addAnswer(1);
        Toast.makeText(getApplicationContext(), "Successfully marked!", Toast.LENGTH_SHORT).show();
        saveClass(SavedClass);
    }

    public void score2(View v){
        SavedClass.Students[PickPointer].addAnswer(2);
        Toast.makeText(getApplicationContext(), "Successfully marked!", Toast.LENGTH_SHORT).show();
        saveClass(SavedClass);
    }

    public void score3(View v){
        SavedClass.Students[PickPointer].addAnswer(3);
        Toast.makeText(getApplicationContext(), "Successfully marked!", Toast.LENGTH_SHORT).show();
        saveClass(SavedClass);
    }

    public void score4(View v){
        SavedClass.Students[PickPointer].addAnswer(4);
        Toast.makeText(getApplicationContext(), "Successfully marked!", Toast.LENGTH_SHORT).show();
        saveClass(SavedClass);
    }

    public void score5(View v){
        SavedClass.Students[PickPointer].addAnswer(5);
        Toast.makeText(getApplicationContext(), "Successfully marked!", Toast.LENGTH_SHORT).show();
        saveClass(SavedClass);
    }

    public void saveClass(ClassInfo class_1){
        SharedPreferences preferences = getSharedPreferences("base64",MODE_PRIVATE);
        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            //创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            //将对象写入字节流
            oos.writeObject(class_1);
            //将字节流编码成base64的字符串
            String class_1_Base64 = new String(Base64.encodeBase64(baos.toByteArray()));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("class_1",class_1_Base64);
            editor.commit();
        }catch (IOException e){
            Toast.makeText(getApplicationContext(), "IO Failure", Toast.LENGTH_SHORT).show();
        }
        Log.i("OK","Successfully saved");
    }

    public ClassInfo readClass(){
        ClassInfo class_1 = new ClassInfo();
        SharedPreferences preferences = getSharedPreferences("base64",MODE_PRIVATE);
        String classBase64 = preferences.getString("class_1","");

        //读取字节
        byte[] base64 = Base64.decodeBase64(classBase64.getBytes());
        //封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try{
            //再次封装
            ObjectInputStream ois = new ObjectInputStream(bais);
            try{
                //读取对象
                class_1 = (ClassInfo) ois.readObject();
            }catch (ClassNotFoundException e){
                Toast.makeText(getApplicationContext(), "ClassNotFoundException", Toast.LENGTH_SHORT).show();
            }
        }catch (StreamCorruptedException e){
            Toast.makeText(getApplicationContext(), "StreamCorruptedException", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(getApplicationContext(), "IOException", Toast.LENGTH_SHORT).show();
        }
        return class_1;
    }

    //↓切换fragment用↓
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

    }

    @Override
    public void onPageSelected(int position) {
        //页面滑动的时候，改变BottomNavigationView的Item高亮
        navigation.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onPause(){
        if(mTts !=null){
            mTts.stop();
            mTts.shutdown();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        saveClass(SavedClass);
    }

}
