package com.example.sample.ui.customview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sample.R;
import com.example.sample.ui.customview.view.TuyaView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZiTFragment extends Fragment {

    @BindView(R.id.zi_t_view)
    TuyaView zi_t_view;
    @BindView(R.id.bt_clear)
    Button bt_clear;
    @BindView(R.id.bt_revoke)
    Button bt_revoke;

    public ZiTFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zi_t, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zi_t_view.clear();
            }
        });
        bt_revoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zi_t_view.revoke();
            }
        });
    }

}
