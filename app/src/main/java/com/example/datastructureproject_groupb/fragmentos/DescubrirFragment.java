package com.example.datastructureproject_groupb.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.datastructureproject_groupb.R;

public class DescubrirFragment extends Fragment {

    public DescubrirFragment() {
    }

    public static DescubrirFragment newInstance() {
        DescubrirFragment fragment = new DescubrirFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_descubrir, container, false);
    }
}