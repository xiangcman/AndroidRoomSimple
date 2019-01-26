package com.single.room;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.single.room.model.StudentModel;

/**
 * Created by xiangcheng on 19/1/3.
 */

public abstract class CommonActivity extends AppCompatActivity {
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new StudentModel(mApplication);
//            try {
//                T t = modelClass.newInstance();
//                return t;
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            return null;
        }
    }

    public <T extends ViewModel> T getViewModel(Class<T> modelClass) {
        return new Factory(getApplication()).create(modelClass);
    }
}
