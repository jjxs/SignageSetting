package com.example.administrator.signagesetting;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.administrator.signagesetting.XmlUtil.PullService;
import com.example.administrator.signagesetting.bean.Properties;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_apply;
    private static String fileName = "setting.xml";
    private EditText edit_serverURL, edit_ScheduleDownload, edit_AppRootPath, edit_USBRootPath, edit_IntervalToGetSchedule, edit_InterToNotifyStatus;
    Properties properties;
    private PullService pullService;
    private static final int Mypermission_EAD_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //检查权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Mypermission_EAD_EXTERNAL_STORAGE);

        } else {
            initView();
        }

    }

    /**
     * 初始化页面
     */
    private void initView() {
        btn_apply = (Button) findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(this);
        edit_serverURL = (EditText) findViewById(R.id.edit_ServerURL);
        edit_AppRootPath = (EditText) findViewById(R.id.edit_AppRootPath);
        edit_InterToNotifyStatus = (EditText) findViewById(R.id.edit_InterToNotifyStatus);
        edit_IntervalToGetSchedule = (EditText) findViewById(R.id.edit_IntervalToGetSchedule);
        edit_USBRootPath = (EditText) findViewById(R.id.edit_USBRootPath);
        edit_ScheduleDownload = (EditText) findViewById(R.id.edit_ScheduleDownload);
        pullService = new PullService();

        //获取文件夹路径
        File dirFile = new File(Environment.getExternalStorageDirectory().toString() + "/signage");
        if (!dirFile.exists() && !dirFile.isDirectory()) {
            dirFile.mkdir();
        }
        File xmlFile = new File(dirFile.getPath() + "/" + fileName);
        if (!xmlFile.exists()) {
            try {
                xmlFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //获取xml中数据
        properties = pullService.getProperties(xmlFile);
        //把数据设置到输入框中
        setEditText(properties);

        edit_serverURL.addTextChangedListener(textWatcher);
        edit_AppRootPath.addTextChangedListener(textWatcher);
        edit_InterToNotifyStatus.addTextChangedListener(textWatcher);
        edit_IntervalToGetSchedule.addTextChangedListener(textWatcher);
        edit_USBRootPath.addTextChangedListener(textWatcher);
        edit_ScheduleDownload.addTextChangedListener(textWatcher);
        btn_apply.setBackgroundColor(Color.parseColor("#333333d6"));

        //关闭 serverURL输入框其他输入框
//        setEditTextClose();
    }

    /**
     * 设置除了 serverURL输入框其他输入框都关闭
     */
    private void setEditTextClose() {
        edit_AppRootPath.setFocusable(false);
        edit_AppRootPath.setEnabled(false);

        edit_InterToNotifyStatus.setFocusable(false);
        edit_InterToNotifyStatus.setEnabled(false);

        edit_IntervalToGetSchedule.setFocusable(false);
        edit_IntervalToGetSchedule.setEnabled(false);

        edit_USBRootPath.setFocusable(false);
        edit_USBRootPath.setEnabled(false);

        edit_ScheduleDownload.setFocusable(false);
        edit_ScheduleDownload.setEnabled(false);

    }

    /**
     * 返回输入框内容
     *
     * @param properties
     */
    private void setEditText(Properties properties) {
        edit_serverURL.setText(properties.getServer_url());
        edit_ScheduleDownload.setText(properties.getSchedule_download());
        edit_AppRootPath.setText(properties.getApplication_root());
        edit_USBRootPath.setText(properties.getUsb_root());
        edit_IntervalToGetSchedule.setText(properties.getGet_schedule());
        edit_InterToNotifyStatus.setText(properties.getNotify_status());
    }

    /**
     * 保存文件
     */
    public void apply() {
        getPropertiesList();
        pullService.writeXML(properties, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_apply:

                if (btn_apply.isEnabled()) {
                    btn_apply.setBackgroundColor(Color.parseColor("#333333d6"));
                    //让输入框失去焦点
                    btn_apply.setEnabled(false);
                    btn_apply.requestFocus();
                    btn_apply.requestFocusFromTouch();
                    apply();
                }
                break;
        }
    }

    /**
     * 获取输入框中的数据并添加到properties类
     *
     * @return
     */
    public void getPropertiesList() {
        properties = new Properties();
        properties.setServer_url(edit_serverURL.getText().toString());
        properties.setApplication_root(edit_AppRootPath.getText().toString());
        properties.setUsb_root(edit_USBRootPath.getText().toString());
        properties.setGet_schedule(edit_IntervalToGetSchedule.getText().toString());
        properties.setNotify_status(edit_InterToNotifyStatus.getText().toString());
        properties.setSchedule_download(edit_ScheduleDownload.getText().toString());
    }


    /**
     * 输入框内容的监听
     */
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {







            if (TextUtils.equals(properties.getServer_url(),edit_serverURL.getText().toString()) &&
                    TextUtils.equals(properties.getApplication_root(),edit_AppRootPath.getText().toString())&&
                    TextUtils.equals(properties.getGet_schedule(),edit_IntervalToGetSchedule.getText().toString())&&
                    TextUtils.equals(properties.getNotify_status(),edit_InterToNotifyStatus.getText().toString())&&
                    TextUtils.equals(properties.getSchedule_download(),edit_ScheduleDownload.getText().toString())&&
                    TextUtils.equals(properties.getUsb_root(),edit_USBRootPath.getText().toString())
            ){
                btn_apply.setBackgroundColor(Color.parseColor("#333333d6"));
                    btn_apply.setEnabled(false);
            }else{
                btn_apply.setBackgroundColor(Color.parseColor("#d86917"));
                   btn_apply.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    /**
     * 申请权限的回调方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Mypermission_EAD_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                initView();
            } else {
                Toast.makeText(this, "请您添加权限", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}
