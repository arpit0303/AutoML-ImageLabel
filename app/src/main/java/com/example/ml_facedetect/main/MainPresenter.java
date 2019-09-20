package com.example.ml_facedetect.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;

import java.util.List;

class MainPresenter implements MainContract.Presenter {

    private static final String LOCAL_MODEL_NAME = "emp_local";
    private static final String REMOTE_MODEL_NAME = "emp_data_set";

    private String empName = "";
    private MainContract.View view;

    MainPresenter(MainContract.View view) {
        this.view = view;
    }

    public void openCamera(Activity activity) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, MainActivity.CAMERA_REQUEST);
    }

    public void loadRemoteModel() {
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseRemoteModel remoteModel = new FirebaseRemoteModel.Builder(REMOTE_MODEL_NAME)
                .enableModelUpdates(true)
                .setInitialDownloadConditions(conditions)
                .setUpdatesDownloadConditions(conditions)
                .build();
        FirebaseModelManager.getInstance().registerRemoteModel(remoteModel);
    }

    public void loadLocalModel() {
        FirebaseLocalModel localModel = new FirebaseLocalModel.Builder(LOCAL_MODEL_NAME)
                .setAssetFilePath("manifest.json")
                .build();
        FirebaseModelManager.getInstance().registerLocalModel(localModel);
    }


    public void labelImages(Bitmap bitmap) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionOnDeviceAutoMLImageLabelerOptions labelerOptions =
                new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder()
                        .setLocalModelName(LOCAL_MODEL_NAME)
                        .setRemoteModelName(REMOTE_MODEL_NAME)
                        .setConfidenceThreshold(0)
                        .build();
        try {
            FirebaseVisionImageLabeler labeler =
                    FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(labelerOptions);

            labeler.processImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                            float maxConfidence = 0;
                            for (FirebaseVisionImageLabel label : labels) {
                                String emp = label.getText();
                                float confidence = label.getConfidence();

                                if (confidence > maxConfidence) {
                                    maxConfidence = confidence;
                                    empName = emp;
                                }
                            }
                            view.setEmp(empName);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            empName = "";
                            view.errorToast(e.getMessage());
                        }
                    });
        } catch (FirebaseMLException e) {
            e.printStackTrace();
        }
    }
}
