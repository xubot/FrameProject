package com.example.frame.frameproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.frame.frameproject.R;
import com.example.frame.frameproject.aop.DebugLog;
import com.example.frame.frameproject.aop.SingleClick;
import com.example.frame.frameproject.common.MyActivity;
import com.example.frame.frameproject.helper.InputTextHelper;
import com.example.frame.frameproject.http.model.HttpData;
import com.example.frame.frameproject.http.request.GetCodeApi;
import com.example.frame.frameproject.http.request.PhoneApi;
import com.example.frame.frameproject.other.IntentKey;
import com.example.frame.frameproject.widget.view.CountdownView;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/04/20
 *    desc   : 设置手机号
 */
public final class PhoneResetActivity extends MyActivity {

    @DebugLog
    public static void start(Context context, String code) {
        Intent intent = new Intent(context, PhoneResetActivity.class);
        intent.putExtra(IntentKey.CODE, code);
        context.startActivity(intent);
    }

    private EditText mPhoneView;
    private EditText mCodeView;
    private CountdownView mCountdownView;
    private Button mCommitView;

    /** 验证码 */
    private String mVerifyCode;

    @Override
    protected int getLayoutId() {
        return R.layout.phone_reset_activity;
    }

    @Override
    protected void initView() {
        mPhoneView = findViewById(R.id.et_phone_reset_phone);
        mCodeView = findViewById(R.id.et_phone_reset_code);
        mCountdownView = findViewById(R.id.cv_phone_reset_countdown);
        mCommitView = findViewById(R.id.btn_phone_reset_commit);
        setOnClickListener(mCountdownView, mCommitView);

        InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mCodeView)
                .setMain(mCommitView)
                .build();
    }

    @Override
    protected void initData() {
        mVerifyCode = getString(IntentKey.CODE);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        if (v == mCountdownView) {

            if (mPhoneView.getText().toString().length() != 11) {
                toast(R.string.common_phone_input_error);
                return;
            }

            if (true) {
                toast(R.string.common_code_send_hint);
                mCountdownView.start();
                return;
            }

            // 获取验证码
            EasyHttp.post(this)
                    .api(new GetCodeApi()
                            .setPhone(mPhoneView.getText().toString()))
                    .request(new HttpCallback<HttpData<Void>>(this) {

                        @Override
                        public void onSucceed(HttpData<Void> data) {
                            toast(R.string.common_code_send_hint);
                            mCountdownView.start();
                        }
                    });
        } else if (v == mCommitView) {

            if (mPhoneView.getText().toString().length() != 11) {
                toast(R.string.common_phone_input_error);
                return;
            }

            if (mCodeView.getText().toString().length() != getResources().getInteger(R.integer.sms_code_length)) {
                ToastUtils.show(R.string.common_code_error_hint);
                return;
            }

            if (true) {
                toast(R.string.phone_reset_commit_succeed);
                finish();
                return;
            }

            // 更换手机号
            EasyHttp.post(this)
                    .api(new PhoneApi()
                            .setPreCode(mVerifyCode)
                            .setPhone(mPhoneView.getText().toString())
                            .setCode(mCodeView.getText().toString()))
                    .request(new HttpCallback<HttpData<Void>>(this) {

                        @Override
                        public void onSucceed(HttpData<Void> data) {
                            toast(R.string.phone_reset_commit_succeed);
                            finish();
                        }
                    });
        }
    }
}