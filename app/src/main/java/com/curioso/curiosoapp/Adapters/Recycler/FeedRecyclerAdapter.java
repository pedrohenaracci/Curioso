package com.curioso.curiosoapp.Adapters.Recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curioso.curiosoapp.Model.News;
import com.curioso.curiosoapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.FeedViewHolder> {
    private Context context;
    private List<News> newsList = new ArrayList<>();
    private ClickRecycler_Interface onListener;

    public void setOnItemClickListener(ClickRecycler_Interface listener){
        onListener = listener;
    }

    public FeedRecyclerAdapter(Context context) {
        this.onListener = (ClickRecycler_Interface) context;
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
        return new FeedViewHolder(v);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onListener != null) {
                        int position = getAdapterPosition();
                        List<News> list = newsList;
                        if (position != RecyclerView.NO_POSITION) {
                            onListener.onItemClick(position, list);
                        }
                    }
                }
            });

            img = (ImageView) itemView.findViewById(R.id.imageItem);
            title = (TextView) itemView.findViewById(R.id.textItem);

        }

    }


}
