package com.lentzos.nic.nerdlauncher;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//nerdlauncheractivity.java will host a single fragment and should be a subclass of singlefragmentactivity.
//createFragment() is overridden to return a nerdlauncherfragment.
//nerdlauncherfragment will display a list of application names in a RecyclerView.
public class NerdLauncherActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return NerdLauncherFragment.newInstance();
    }
}
