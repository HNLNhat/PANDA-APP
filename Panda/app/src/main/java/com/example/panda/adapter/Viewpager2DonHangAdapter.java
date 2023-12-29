package com.example.panda.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.panda.fragment.DonHangFrag;
import com.example.panda.fragment.LichSuFrag;

public class Viewpager2DonHangAdapter extends FragmentStateAdapter {
    public Viewpager2DonHangAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0 )
            return new DonHangFrag();
        return new LichSuFrag();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
