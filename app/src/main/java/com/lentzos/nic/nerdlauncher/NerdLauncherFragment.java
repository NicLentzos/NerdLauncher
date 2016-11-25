package com.lentzos.nic.nerdlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
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
        //Sort the ResolveInfo objects returned from the package manager alphabetically by label
        //using the resolvinfo.loadlabel method.
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            public int compare(ResolveInfo a, ResolveInfo b) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(
                        a.loadLabel(pm).toString(),
                        b.loadLabel(pm).toString());
            }
        });

        Log.i(TAG, "Found " + activities.size() + " activities.");
        //update setupadapter() to create an instance of ActivityAdapter and set it as the
        //recyclerview's adapter.
        mRecyclerView.setAdapter(new ActivityAdapter(activities));
    }

    //define a viewholder that displays an activity's label.
    //store the activity's ResolveInfo in a member variable for later use.
    //now implement onclicklistener so we can select an activity.
    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ResolveInfo mResolveInfo;
        private TextView mNameTextView;

        public ActivityHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView;
            //set onclicklistener
            mNameTextView.setOnClickListener(this);
        }

        public void bindActivity(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            String appName = mResolveInfo.loadLabel(pm).toString();
            mNameTextView.setText(appName);
        }

        //implement onClick
        //sending an action as part of an explicit intent.
        @Override
        public void onClick(View v) {
            ActivityInfo activityInfo = mResolveInfo.activityInfo;
            //Get class and package name from the metadata and use them to create an explicit intent using the intent method.
            //public Intent setClassName(String packageName, String className)
            //before, we used an Intent constructor that accepts a Context and Class name. This created a ComponentName
            //which the Intent really needs.
            Intent i = new Intent(Intent.ACTION_MAIN).setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //.addFlags(Intent.Fl...) creates a new task for programs you run from the launcher, so when you
            //view them in the overview screen you can return to them directly (or clear them).
            startActivity(i);
        }
    }

    //now to add a recyclerview.adapter implementation.
    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {
        private final List<ResolveInfo> mActivities;

        public ActivityAdapter(List<ResolveInfo> activities) {
            mActivities = activities;
        }

        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ActivityHolder(view);
        }

        @Override
        public void onBindViewHolder(ActivityHolder activityHolder, int position) {
            ResolveInfo resolveInfo = mActivities.get(position);
            activityHolder.bindActivity(resolveInfo);
        }

        @Override
        public int getItemCount() {
            return mActivities.size();
        }
    }
}
