package hcmute.edu.vn.musicapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hcmute.edu.vn.musicapp.Activity.PlayerActivity;
import hcmute.edu.vn.musicapp.Model.Song;
import hcmute.edu.vn.musicapp.R;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private Context mContext;
    public static ArrayList<Song> mSongs;

    public SongAdapter(Context context, ArrayList<Song> mSongs) {
        this.mContext = context;
        this.mSongs = mSongs;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.mTextViewTitle.setText(mSongs.get(position).getTitle());
        holder.mTextViewArtist.setText(mSongs.get(position).getArtist());

        Uri uri = Uri.parse(mSongs.get(position).getPath());
        holder.bind(uri);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder{

        TextView mTextViewTitle, mTextViewArtist;
        ImageView mImageViewSong;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.txtName);
            mTextViewArtist = itemView.findViewById(R.id.txtArtist);
            mImageViewSong = itemView.findViewById(R.id.imgSong);
        }
        public void bind(Uri uri) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(uri.toString());
            byte[] art = retriever.getEmbeddedPicture();
            if(art != null){
                Glide.with(itemView.getContext()).load(art).into(mImageViewSong);
            }else {
                Glide.with(itemView.getContext()).load(R.drawable.img_default).into(mImageViewSong);
            }
        }
    }

    public void updateList(ArrayList<Song> songArrayList){
        mSongs = new ArrayList<>();
        mSongs.addAll(songArrayList);
        notifyDataSetChanged();
    }
}
