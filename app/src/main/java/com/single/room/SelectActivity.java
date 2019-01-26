package com.single.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.single.room.databinding.ActivitySelectBinding;
import com.single.room.entity.Student;
import com.single.room.model.StudentModel;

/**
 * Created by xiangcheng on 19/1/4.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class SelectActivity extends CommonActivity {
//    Student student = new Student();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySelectBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_select);
        StudentModel viewModel = getViewModel(StudentModel.class);
        viewDataBinding.setStudentModel(viewModel);
        viewDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = viewDataBinding.editText.getText().toString();
                LiveData<Student> studentLiveData = viewModel.selectStudent(code);
                studentLiveData.observe(SelectActivity.this, new Observer<Student>() {
                    @Override
                    public void onChanged(@Nullable Student student) {
                        studentLiveData.removeObservers(SelectActivity.this);
                        viewModel.setStudent(student);
                    }
                });
            }
        });
//        viewDataBinding.editText3.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(SelectActivity.this);
//                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.set(Calendar.YEAR, year);
//                        calendar.set(Calendar.MONTH, month);
//                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        Date date = new Date();
//                        date.setTime(calendar.getTimeInMillis());
//                        student.birthday = date;
//                        viewDataBinding.editText3.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
//                    }
//                });
//                datePickerDialog.show();
//            }
//        });
    }
}
