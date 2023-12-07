package hcmute.edu.vn.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import hcmute.edu.vn.musicapp.Adapter.MainViewPagerAdapter;
import hcmute.edu.vn.musicapp.Fragment.AlbumFragment;
import hcmute.edu.vn.musicapp.Fragment.SongFragment;
import hcmute.edu.vn.musicapp.Model.Song;
import hcmute.edu.vn.musicapp.R;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    TabLayout tabLayout;
    ViewPager viewPager;
    public static final int REQUEST_CODE = 1;
    public static ArrayList<Song> songs;
    public static boolean shuffleBoolean = false, repeatBoolean = false;
    public static ArrayList<Song> albums = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.myViewPager);
        tabLayout = findViewById(R.id.myTabLayout);

        permission();
    }

    private void permission(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }else {
            songs = getAllAudio(this);
            initViewPagerAdapter();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                songs = getAllAudio(this);
                initViewPagerAdapter();
            }else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
    }

    private void initViewPagerAdapter() {

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new SongFragment(), "Songs");

        mainViewPagerAdapter.addFragment(new AlbumFragment(), "Album");

        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);



        tabLayout.getTabAt(0).setIcon(R.drawable.ic_songs);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_albums);

        // mặc định màu icon tab Home khi chạy ctr
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.getIcon().setTint(ContextCompat.getColor(getApplicationContext(), R.color.teal_200));

        // đổi màu icon khi đc chọn
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal_200), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public static ArrayList<Song> getAllAudio(Context context){
        ArrayList<String> duplicate = new ArrayList<>();
        ArrayList<Song> tempAudioList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String [] projection = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media._ID
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String artist = cursor.getString(2);
                String duration = cursor.getString(3);
                String path = cursor.getString(4);
                String id = cursor.getString(5);
                Song song = new Song(id, path, title, album, artist, duration);
                Log.e("Path: " + path, "Album: " + album);
                tempAudioList.add(song);
                if(!duplicate.contains(album)){
                    albums.add(song);
                    duplicate.add(album);
                }
            }
            cursor.close();
        }
        return tempAudioList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userInput = s.toLowerCase();
        ArrayList<Song> song = new ArrayList<>();
        for (Song song1 : songs){
            if(song1.getTitle().toLowerCase().contains(userInput)){
                song.add(song1);
            }
        }
        SongFragment.mSongAdapter.updateList(song);
        return true;
    }
}