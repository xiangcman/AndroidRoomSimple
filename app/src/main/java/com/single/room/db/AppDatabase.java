package com.single.room.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.single.room.dao.StudentDao;
import com.single.room.entity.Student;

/**
 * Created by xiangcheng on 19/1/3.
 */
@Database(entities = {Student.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "basic-sample-db";

    public abstract StudentDao studentDao();
}
