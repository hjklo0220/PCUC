package com.example.pcuc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabcount;

    public TabPagerAdapter(FragmentManager fm, int tabcount){

        super(fm);
        this.tabcount = tabcount;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HomeTab homeTab = new HomeTab();
                return homeTab;
            case 1:
                WriteTab writeTab = new WriteTab();
                return  writeTab;
            case 2:
                SearchTab searchTab = new SearchTab();
                return searchTab;
            case 3:
                NoticeTab noticeTab = new NoticeTab();
                return noticeTab;
             default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
