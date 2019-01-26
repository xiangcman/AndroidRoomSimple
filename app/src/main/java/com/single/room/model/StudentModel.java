package com.single.room.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.single.room.db.DatabaseCreator;
import com.single.room.entity.Student;

import java.util.List;

/**
 * Created by xiangcheng on 19/1/3.
 */

public class StudentModel extends AndroidViewModel {
    public ObservableField<Student> studentObservableField = new ObservableField<>();
    private final MediatorLiveData<List<Student>> listLiveData = new MediatorLiveData<>();
    private DatabaseCreator databaseCreator;
    private final MutableLiveData<Boolean> mIsAdd = new MutableLiveData<>();

    public StudentModel(@NonNull Application application) {
        super(application);
        createDataBase();
    }

    private void createDataBase() {
        databaseCreator = DatabaseCreator.getInstance(getApplication());
        databaseCreator.createDb(getApplication());
    }

    public LiveData<List<Student>> getAllStudent() {
        LiveData<List<Student>> listLiveData = databaseCreator.getDatabase().studentDao().loadAllStudents();
        return listLiveData;
    }

    public LiveData<List<Student>> deleteStudent(Student student) {
        new AsyncTask<Void, Void, List<Student>>() {
            @Override
            protected List<Student> doInBackground(Void... voids) {
                databaseCreator.getDatabase().beginTransaction();
                try {
                    Log.d("tag", "add");
                    databaseCreator.getDatabase().studentDao().deleteOne(student);
                    databaseCreator.getDatabase().setTransactionSuccessful();
                    return databaseCreator.getDatabase().studentDao().selectAll();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("tag", "add fail");
                    return null;
                } finally {
                    databaseCreator.getDatabase().endTransaction();
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<Student> aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid != null) {
                    listLiveData.setValue(aVoid);
                }
            }
        }.execute();
        return listLiveData;
    }

    public LiveData<List<Student>> updateStudent(Student student) {
        new AsyncTask<Void, Void, List<Student>>() {
            @Override
            protected List<Student> doInBackground(Void... voids) {
                databaseCreator.getDatabase().beginTransaction();
                try {
                    Log.d("tag", "add");
                    databaseCreator.getDatabase().studentDao().updateOne(student);
                    databaseCreator.getDatabase().setTransactionSuccessful();
                    return databaseCreator.getDatabase().studentDao().selectAll();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("tag", "add fail");
                    return null;
                } finally {
                    databaseCreator.getDatabase().endTransaction();
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<Student> aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid != null) {
                    listLiveData.setValue(aVoid);
                }
            }
        }.execute();
        return listLiveData;
    }

    public LiveData<Student> selectStudent(String code) {
        LiveData<Student> listLiveData = databaseCreator.getDatabase().studentDao().selectOne(code);
        return listLiveData;
    }

    public void setStudent(Student student) {
        studentObservableField.set(student);
    }

    public LiveData<Boolean> add(Student student) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                databaseCreator.getDatabase().beginTransaction();
                try {
                    Log.d("tag", "add");
                    databaseCreator.getDatabase().studentDao().insert(student);
                    databaseCreator.getDatabase().setTransactionSuccessful();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("tag", "add fail");
                    return false;
                } finally {
                    databaseCreator.getDatabase().endTransaction();
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mIsAdd.setValue(false);
            }

            @Override
            protected void onPostExecute(Boolean aVoid) {
                super.onPostExecute(aVoid);
                mIsAdd.setValue(aVoid);
            }
        }.execute();
        return mIsAdd;
    }

}
