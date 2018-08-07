package com.example.sample.ui.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sample.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public class StudyAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<StudyBean> mData = new ArrayList<>();

    public StudyAdapter(Context mContext, List<StudyBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_homepage_study, null);
        view.setLayoutParams(layoutParams);
        return new SViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SViewHolder sViewHolder = (SViewHolder) holder;
        sViewHolder.text.setText(mData.get(position).text);
        sViewHolder.itemView.setTag(mData.get(position));
        sViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudyBean studyBean = (StudyBean) view.getTag();
                if (studyBean.aClass != null) {
                    Intent it = new Intent(mContext, studyBean.aClass);
                    it.putExtra("text", studyBean.text);
                    mContext.startActivity(it);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mData.size();
    }


    class SViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public SViewHolder(View itemView) {
            super(itemView);
            text = ButterKnife.findById(itemView, R.id.text);
        }
    }

    public static class StudyBean {

        public String text;
        public Class<? extends Activity> aClass;

        public StudyBean(String text) {
            this.text = text;
        }

        public StudyBean setaClass(Class<? extends Activity> aClass) {
            this.aClass = aClass;
            return this;
        }
    }

}
