package com.example.ml_facedetect.main;

import android.app.Activity;
import android.graphics.Bitmap;

public interface MainContract {

    interface View {
        void setEmp(String empName);

        void errorToast(String errorMsg);
    }

    interface Presenter {
        void openCamera(Activity activity);

        void labelImages(Bitmap bitmap);

        void loadRemoteModel();

        void loadLocalModel();
    }
}
