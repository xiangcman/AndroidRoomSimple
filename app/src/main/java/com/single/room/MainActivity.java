package com.single.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.single.room.adapter.BaseRecyclerViewBingAdapter;
import com.single.room.databinding.ActivityMainBinding;
import com.single.room.databinding.DialogHintBinding;
import com.single.room.databinding.LayoutStudentItemBinding;
import com.single.room.entity.Student;
import com.single.room.model.StudentModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CommonActivity {
    private ActivityMainBinding viewDataBinding;
    private List<Student> students = new ArrayList<>();
    private BaseRecyclerViewBingAdapter adapter;
    private StudentModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = getViewModel(StudentModel.class);

        viewDataBinding.setAdd(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
        viewDataBinding.setSearch(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectActivity.class));
            }
        });
        viewDataBinding.list.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.list.setAdapter(adapter = new BaseRecyclerViewBingAdapter<Student, LayoutStudentItemBinding>(this, students, BR.student) {
            @Override
            protected int getItemLayout(int viewType) {
                return R.layout.layout_student_item;
            }

            @Override
            protected void itemClick(Student item, int position) {

            }

            @Override
            protected void generateItem(LayoutStudentItemBinding dataBing, Student student, int position) {
                dataBing.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        DialogHintBinding daBinding = DataBindingUtil.inflate(LayoutInflater.from(MainActivity.this),
                                R.layout.dialog_hint, null, false);
                        builder.setView(daBinding.getRoot());
                        AlertDialog show = builder.show();
                        daBinding.ok.setText("删除");
                        daBinding.cancel.setText("修改");
                        daBinding.cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                show.dismiss();
                            }
                        });
                        daBinding.ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                show.dismiss();
                                LiveData<List<Student>> listLiveData = viewModel.deleteStudent(student);
                                listLiveData.observe(MainActivity.this, new Observer<List<Student>>() {
                                    @Override
                                    public void onChanged(@Nullable List<Student> students) {
                                        MainActivity.this.students.clear();
                                        MainActivity.this.students.addAll(students);
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                            }
                        });
                        daBinding.cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                show.dismiss();
                                student.name = "修改后的名字";
                                LiveData<List<Student>> listLiveData = viewModel.updateStudent(student);
                                listLiveData.observe(MainActivity.this, new Observer<List<Student>>() {
                                    @Override
                                    public void onChanged(@Nullable List<Student> students) {
                                        MainActivity.this.students.clear();
                                        MainActivity.this.students.addAll(students);
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                            }
                        });
                        return false;

                    }
                });
            }
        });
        generateList();
    }

    private void generateList() {
        LiveData<List<Student>> allStudent = viewModel.getAllStudent();
        allStudent.observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable List<Student> students) {
                allStudent.removeObservers(MainActivity.this);
                MainActivity.this.students.clear();
                MainActivity.this.students.addAll(students);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        generateList();
    }
}
