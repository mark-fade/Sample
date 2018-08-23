package com.example.sample.ui.homepage;

import android.app.Activity;

import com.example.sample.base.BaseModel;
import com.example.sample.ui.backanalysis.BackAnalysisActivity;
import com.example.sample.ui.customview.CustomViewActivity;
import com.example.sample.ui.fourcomp.FourCompActivity;
import com.example.sample.ui.generateapk.GenerateAPKActivity;
import com.example.sample.ui.gitlab.GitLabStudyActivity;
import com.example.sample.ui.homepage.adapter.StudyAdapter;
import com.example.sample.ui.mvptest.MvpTestActivity;

import java.util.ArrayList;
import java.util.List;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public class HomePageModel implements BaseModel {

    String[] listContent = {"MVP"
            , "四大组件"
            , "自定义View"
            , "代码库搭建(svn、git使用，gitLab使用）"
            , "打包相关"
            , "Android逆向"
            , "通知"
            , "蓝牙"
            , "进程间通信"
    };

    public List<StudyAdapter.StudyBean> getStudyList() {
        List<StudyAdapter.StudyBean> list = new ArrayList<>();
        for (String ct : listContent) {
            list.add(new StudyAdapter.StudyBean(ct).setaClass(getIntentClass(ct)));
        }
        return list;
    }

    private Class<? extends Activity> getIntentClass(String name) {
        if (name.equals(listContent[0])) {
            return MvpTestActivity.class;
        } else if (name.equals(listContent[1])) {
            return FourCompActivity.class;
        } else if (name.equals(listContent[2])) {
            return CustomViewActivity.class;
        } else if (name.equals(listContent[3])) {
            return GitLabStudyActivity.class;
        } else if (name.equals(listContent[4])) {
            return GenerateAPKActivity.class;
        } else if (name.equals(listContent[5])) {
            return BackAnalysisActivity.class;
        }
        return null;
    }

}
