package com.example.numad22fa_team37.GroupProject.Courses;

import android.app.Application;

import com.example.numad22fa_team37.R;

import java.util.ArrayList;

public class CourseList extends Application {

    private static final ArrayList<Integer> mCourseIds = new ArrayList<>();


    public static ArrayList<Integer> getCourseList() {
        mCourseIds.add(R.drawable.c);
        mCourseIds.add(R.drawable.web_dev);
        mCourseIds.add(R.drawable.data_structures);
        mCourseIds.add(R.drawable.network);
        mCourseIds.add(R.drawable.algo);



        return mCourseIds;
    }
}
