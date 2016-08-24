package com.example.netframe.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.netframe.R;
import com.example.netframe.adapter.SennicListAdapter;
import com.example.netframe.bean.Sennic;
import com.example.netframe.service.IMasterService;
import com.example.netframe.service.MasterServiceClient;
import com.example.netframe.utils.IDynamicLoadingHandler;
import com.example.netframe.utils.PtrLoadingHelper;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import in.srain.cube.views.ptr.PtrFrameLayout;

@EActivity
public class MainActivity extends AppCompatActivity implements IDynamicLoadingHandler{
    @Bean
    MasterServiceClient service;
    private final static int GET_SENNIC=0;
    @ViewById(R.id.list)
    RecyclerView recyclerView;
    @ViewById(R.id.manage_list_ptr)
    PtrFrameLayout ptrFrameLayout;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    private PtrLoadingHelper ptrLoadingHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       init();

    }

    public void init(){
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(ptrLoadingHelper==null){
            ptrLoadingHelper=new PtrLoadingHelper(this);
        }
        ptrLoadingHelper.setPtrFrameLayout(ptrFrameLayout);
    }
    @Background
    public void getSennic(){
        try{
            Sennic sennic=service.getmRestService().getSennic();
            if(sennic!=null&&sennic.getCode()==200){
                onSuccess(GET_SENNIC,sennic);
                return;
            }
        }
        catch (Exception ex){

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        ptrLoadingHelper.setContentLoaded(false);

    }

    @UiThread
    public <T> void onSuccess(int flag,T value){
        SennicListAdapter adapter=new SennicListAdapter(this,((Sennic)value).getData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ptrLoadingHelper.setContentLoaded(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startLoading() {
        getSennic();
    }
}
