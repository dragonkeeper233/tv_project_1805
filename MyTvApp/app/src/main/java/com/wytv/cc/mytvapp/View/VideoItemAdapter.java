package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wytv.cc.mytvapp.Object.PhotoObject;
import com.wytv.cc.mytvapp.Object.VideoObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;

import java.util.ArrayList;

public class VideoItemAdapter extends RecyclerView.Adapter<VideoItemAdapter.ViewHolder> {
    private ArrayList<VideoObject> videoObjects;
    private Context context;
    private LayoutInflater inf;
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .resetViewBeforeLoading(true).cacheOnDisk(true).cacheInMemory(true)
            .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build();

    public VideoItemAdapter(ArrayList<VideoObject> videoObjects, Context context) {
        this.videoObjects = videoObjects;
        this.context = context;
        inf = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        //在布局中找到所含有的UI组件
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_time);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            imageView.getLayoutParams().width = (int) (CommonUtils.getScreenWidth(context) /7);
            imageView.getLayoutParams().height = (int) (imageView.getLayoutParams().width * (0.6));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inf.inflate(R.layout.layout_video_itme, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        VideoObject object = videoObjects.get(position);
        if (object != null) {
            holder.textView.setText(object.getDate());
            ImageLoader.getInstance().loadImage(object.getImage(), options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    holder.imageView.setImageDrawable(new ColorDrawable(Color.WHITE));
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    holder.imageView.setImageDrawable(new ColorDrawable(Color.WHITE));
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.imageView.setImageBitmap(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    holder.imageView.setImageDrawable(new ColorDrawable(Color.WHITE));
                }
            });
        } else {
            holder.textView.setText("");
            holder.imageView.setImageDrawable(new ColorDrawable(Color.WHITE));
        }
    }

    @Override
    public int getItemCount() {
        return videoObjects == null ? 0 : videoObjects.size();
    }
}
