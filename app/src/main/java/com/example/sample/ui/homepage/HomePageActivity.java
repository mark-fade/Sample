package com.example.sample.ui.homepage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.base.MvpActivity;
import com.example.sample.ui.homepage.adapter.MItemTouchCallback;
import com.example.sample.ui.homepage.adapter.StudyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageActivity extends MvpActivity<BasePresenter, HomePageModel> {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    StudyAdapter studyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<StudyAdapter.StudyBean> dataList = mModel.getStudyList();
        studyAdapter = new StudyAdapter(this, dataList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MItemTouchCallback(studyAdapter, dataList));
        itemTouchHelper.attachToRecyclerView(recyclerview);
        recyclerview.setAdapter(studyAdapter);
    }

    @Override
    public void onError(Throwable e) {

    }
}
