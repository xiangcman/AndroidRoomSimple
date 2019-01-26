package com.single.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by xiangcheng on 19/1/4.
 */

@Entity(foreignKeys = @ForeignKey(entity = Student.class, parentColumns = "code", childColumns = "student_id",onDelete = CASCADE),
        indices = @Index(value={"student_id"},unique = true))
public class Report {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String student_id;
    public int grade;
    public String subject;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
