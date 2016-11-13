package com.lentzos.nic.nerdlauncher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Nic on 13/11/2016.
 */
    //NerdLauncherFragment class extends support v4 Fragment.
    //Add a newinstance() method.
    //Override onCreateView() to stash a reference to the RecyclerView object mRecyclerView in a member variable.

public class NerdLauncherFragment extends Fragment {
    //To log the number of activities that PackageManager returns (for now).
    private static final String TAG = "NerdLauncherFragment";

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

        //Call setupAdapter() to create a RecyclerView.Adapter instance and set it on the RecyclerView object.
        //Firstly it will just generate a list of application data.
        //Create an implicit intent and get a list of activities that match the intent from PackageManager.
        setupAdapter();
        return v;
    }
    //Call setupAdapter() to create a RecyclerView.Adapter instance and set it on the RecyclerView object.
    //Firstly it will just generate a list of application data.
    //Create an implicit intent and get a list of activities that match the intent from PackageManager.
    private void setupAdapter() {
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);

        Log.i(TAG, "Found " + activities.size() + " activities.");
    }
}
