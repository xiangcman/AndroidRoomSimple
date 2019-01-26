package com.single.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.single.room.databinding.ActivityAddBinding;
import com.single.room.databinding.DialogDatepickBinding;
import com.single.room.entity.Student;
import com.single.room.model.StudentModel;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiangcheng on 19/1/4.
 */
public class AddActivity extends CommonActivity {
    Student student = new Student();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add);
        viewDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                student.name = viewDataBinding.editText2.getText().toString();
                student.code = viewDataBinding.editText.getText().toString();

                StudentModel viewModel = getViewModel(StudentModel.class);
                LiveData<Boolean> add = viewModel.add(student);
                add.observe(AddActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean) {
                            add.removeObservers(AddActivity.this);
                            Toast.makeText(AddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                            add.removeObservers(AddActivity.this);
                        } else {
//                            add.removeObservers(AddActivity.this);
                        }
                    }
                });
            }
        });
        viewDataBinding.editText3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                DialogDatepickBinding daBinding = DataBindingUtil.inflate(LayoutInflater.from(AddActivity.this),
                        R.layout.dialog_datepick, null, false);
                builder.setView(daBinding.getRoot());
                AlertDialog show = builder.show();
                daBinding.ok.setText("确定");
                daBinding.cancel.setText("取消");
                int year = Calendar.getInstance().get(Calendar.YEAR);
                int month = Calendar.getInstance().get(Calendar.MONTH);
                int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                daBinding.title.init(year, month, day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date date = new Date();
                        date.setTime(calendar.getTimeInMillis());
                        student.birthday = date;
                        viewDataBinding.editText3.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                });
                daBinding.ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                    }
                });
                daBinding.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                    }
                });
            }
        });
    }
}
