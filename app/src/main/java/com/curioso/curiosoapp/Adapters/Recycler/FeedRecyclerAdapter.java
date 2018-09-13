package com.curioso.curiosoapp.Adapters.Recycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.curioso.curiosoapp.Fragment.FeedFragment;
import com.curioso.curiosoapp.Model.News;
import com.curioso.curiosoapp.R;
import com.curioso.curiosoapp.WebViewActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.FeedViewHolder> {
    private Context context;
    private List<News> newsList = new ArrayList<>();



    public FeedRecyclerAdapter(Context context) {

        this.context = context;
    }

    public void addItems(News news) {
        this.newsList.add(news);
        notifyItemInserted(newsList.size() - 1);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        final FeedViewHolder vHolder = new FeedViewHolder(v);

        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Cliquei: "+String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                List<News> data = newsList;
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url",data.get(vHolder.getAdapterPosition()).getLink());
                context.startActivity(intent);
            }
        });


        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {

        News currentItem = newsList.get(position);


        Picasso.get()
                .load(currentItem.getImagemURL())
                .into(holder.img);
        holder.title.setText(currentItem.getTitulo());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;

        public FeedViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.imageItem);
            title = (TextView) itemView.findViewById(R.id.textItem);

        }
    }
}
