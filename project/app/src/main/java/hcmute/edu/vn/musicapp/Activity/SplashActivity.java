package hcmute.edu.vn.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.musicapp.R;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        addmusic();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void addmusic(){
//        ContentResolver contentResolver = getContentResolver();
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//
//        String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DURATION};
//        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
//
//        if (cursor != null) {
//            int idColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
//            int titleColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
//            int durationColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
//
//            if (idColumnIndex != -1 && titleColumnIndex != -1 && durationColumnIndex != -1) {
//                if (cursor.moveToFirst()) {
//                    do {
//                        long songId = cursor.getLong(idColumnIndex);
//                        String songTitle = cursor.getString(titleColumnIndex);
//                        long duration = cursor.getLong(durationColumnIndex);
//
//                        // In ID và tiêu đề của bài hát
//                        Log.d("MediaStore", "ID: " + songId + ", Title: " + songTitle+ ", Duration: " + duration + " ms");
//                    } while (cursor.moveToNext());
//                }
//            } else {
//                Log.e("MediaStore", "Column not found");
//            }
//            cursor.close();
//        } else {
//            Log.e("MediaStore", "Cursor is null");
//        }

//        long songIdToDelete = 26; // Điền ID của bài hát bạn muốn xóa
//
//        ContentResolver contentResolver = getContentResolver();
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//
//        String selection = MediaStore.Audio.Media._ID + "=?";
//        String[] selectionArgs = new String[]{String.valueOf(songIdToDelete)};
//
//        int deletedRows = contentResolver.delete(uri, selection, selectionArgs);
//
//        if (deletedRows > 0) {
//            Log.d("MediaStore", "Deleted song with ID: " + songIdToDelete);
//        } else {
//            Log.e("MediaStore", "Failed to delete song with ID: " + songIdToDelete);
//        }

//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference().child("audio");
//
//        storageRef.listAll()
//                .addOnSuccessListener(listResult -> {
//                    for (StorageReference item : listResult.getItems()) {
//                        String songName = item.getName();
//
//                        File localFile = new File(getCacheDir(), songName);
//                        item.getFile(localFile)
//                                .addOnSuccessListener(taskSnapshot -> {
//
//                                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//                                    retriever.setDataSource(localFile.getAbsolutePath());
//
//                                    String title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
//                                    String artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
//                                    String album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
//                                    String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//
//                                    ContentValues values = new ContentValues();
//                                    values.put(MediaStore.Audio.Media.TITLE, title);
//                                    values.put(MediaStore.Audio.Media.ARTIST, artist);
//                                    values.put(MediaStore.Audio.Media.ALBUM, album);
//                                    values.put(MediaStore.Audio.Media.DISPLAY_NAME, songName);
//                                    values.put(MediaStore.Audio.Media.DATA, localFile.getAbsolutePath());
//                                    values.put(MediaStore.Audio.Media.DURATION, duration);
//
//                                    Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//
//                                    ContentResolver contentResolver = getContentResolver();
//                                    Uri newUri = contentResolver.insert(uri, values);
//
//                                    if (newUri != null) {
//                                        Log.d("MediaStore", "Added to MediaStore: " + songName);
//                                    } else {
//                                        Log.e("MediaStore", "Failed to add to MediaStore: " + songName);
//                                    }
//                                })
//                                .addOnFailureListener(e -> {
//                                    Log.e("FirebaseStorage", "Failed to download file: " + songName + ", " + e.getMessage());
//                                });
//                    }
//                })
//                .addOnFailureListener(e -> {
//                    Log.e("FirebaseStorage", "Failed to list files: " + e.getMessage());
//                });
    }
}