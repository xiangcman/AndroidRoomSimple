package com.single.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.single.room.entity.Student;

import java.util.List;

/**
 * Created by xiangcheng on 19/1/3.
 */
@Dao
public interface StudentDao {
    @Query("SELECT * FROM student")
    LiveData<List<Student>> loadAllStudents();

    @Query("SELECT * FROM student")
    List<Student> selectAll();

    @Query("SELECT * FROM student where code = :code")
    LiveData<Student> selectOne(String code);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Student student);

    @Delete
    void deleteOne(Student student);

    @Update
    void updateOne(Student student);
}
