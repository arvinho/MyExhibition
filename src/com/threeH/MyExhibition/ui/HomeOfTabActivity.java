package com.threeH.MyExhibition.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.threeH.MyExhibition.R;
import com.threeH.MyExhibition.service.ClientController;
import com.threeH.MyExhibition.tools.ByteUtil;
import com.threeH.MyExhibition.tools.IntentIntegrator;
import com.threeH.MyExhibition.tools.IntentResult;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: pjq
 * Date: 13-6-19
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
public class HomeOfTabActivity extends TabActivity implements ActivityInterface{
    private TabHost tabhost;
    private RadioGroup radioGroup;
    private Button buttonSearch;
    public static final String TAB_SIGNUP = "tabSingup";
    public static final String TAB_NO_SIGNUP = "tabNoSingup";
    private ClientController mController;
    private EditText editText;
    private String name;
    private String strCurrentTab;
    private ImageView mImgViewScanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exhibitionlist_page);
        initdata();
        findView();
        addAction();
    }

    @Override
    public void findView() {
        radioGroup = (RadioGroup) this.findViewById(R.id.homeoftab_radiogroup);
        buttonSearch = (Button) this.findViewById(R.id.search_btn);
        editText = (EditText) this.findViewById(R.id.titlebar_et);
        mImgViewScanner = (ImageView) this.findViewById(R.id.exhibitionlist_page_iv_scanner);
    }

    @Override
    public void initdata() {
        tabhost = this.getTabHost();
        mController = ClientController.getController(this);
        strCurrentTab = getIntent().getStringExtra("currentTab");
    }

    @Override
    public void addAction() {
        tabhost.addTab(tabhost.newTabSpec(TAB_SIGNUP).setIndicator(TAB_SIGNUP)
                .setContent(new Intent(this,SignupExhiListActivity.class)));
        tabhost.addTab(tabhost.newTabSpec(TAB_NO_SIGNUP).setIndicator(TAB_NO_SIGNUP)
                .setContent(new Intent(this,NoSignupExhiListActivity.class)));
        if(null != strCurrentTab && !"".equals(strCurrentTab)){
            tabhost.setCurrentTabByTag(TAB_SIGNUP);
            radioGroup.setBackgroundResource(R.drawable.homepage_titlebar_background_signup);
        }else{
            tabhost.setCurrentTabByTag(TAB_NO_SIGNUP);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.exhibitionlsit_radiobutton_singup:
                        tabhost.setCurrentTabByTag(TAB_SIGNUP);
                        radioGroup.setBackgroundResource(R.drawable.homepage_titlebar_background_signup);
                        break;
                    case R.id.exhibitionlsit_radiobutton_no_singup:
                        tabhost.setCurrentTabByTag(TAB_NO_SIGNUP);
                        radioGroup.setBackgroundResource(R.drawable.homepage_titlebar_background_exhibition);
                        break;
                }
            }
        });
        mImgViewScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*IntentIntegrator integrator = new IntentIntegrator(HomeOfTabActivity.this);
                integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);*/
                Intent intent = new Intent(HomeOfTabActivity.this,
                                           com.google.zxing.client.android.CaptureActivity.class);
                startActivityForResult(intent,1);
            }
        });
         /*buttonSearch.setOnClickListener(new View.OnClickListener() {
         @Override
            public void onClick(View v) {
                        //v.setBackgroundResource(R.drawable.search_focus);
                        try {
                            name = editText.getText().toString();
                            String str = mController.getService().UnErollExList("pjqAndroid",-1,-1,name);
                           UnEnrollExhibition allExhibitionData = new Gson().fromJson(str,UnEnrollExhibition.class);
                            List<HashMap<String,String>> data = Tool.makeAllExhibitionListAdapterData(allExhibitionData);
                            HomePageEnrollListAdapter adapter = new HomePageEnrollListAdapter(HomeOfTabActivity.this,data);
                            NoSignupExhiListActivity.listView.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            String result = data.getStringExtra("result");
            if(result != null && result.startsWith("MEK://")){
                NoSignupExhiListActivity.mStrExKey = decodeExhibitionKey(result.substring(6));
                tabhost.setCurrentTabByTag(TAB_NO_SIGNUP);
            }
        }
    }

    private String decodeExhibitionKey(String qrcode){
        String exKey = "";
        ByteBuffer buffer = ByteBuffer.allocate(200);
        buffer.put(ByteUtil.ascii2byte(qrcode));
        buffer.flip();
        short size = buffer.getShort();
        byte[] bs = new byte[size];
        buffer.get(bs);
        try {
            exKey = new String(bs,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return exKey;
    }
}
