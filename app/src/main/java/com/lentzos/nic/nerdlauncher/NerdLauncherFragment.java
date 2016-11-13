package com.lentzos.nic.nerdlauncher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nic on 13/11/2016.
 */
    //NerdLauncherFragment class extends support v4 Fragment.
    //Add a newinstance() method.
    //Override onCreateView() to stash a reference to the RecyclerView object mRecyclerView in a member variable.

public class NerdLauncherFragment extends Fragment {
    private RecyclerView mRecyclerView;

    public static NerdLauncherFragment newInstance() {
        return new NerdLauncherFragment();
    }
    //Override onCreateView() to stash a reference to the RecyclerView object mRecyclerView in a member variable.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nerd_launcher, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_nerd_launcher_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }
}
