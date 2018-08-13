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

import java.util.List;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.FeedViewHolder> {

    public static ClickRecycler_Interface clickRecyclerViewInterface;
    Context mctx;
    private List<News> mList;

    public FeedRecyclerAdapter(Context mctx, List<News> mList,ClickRecycler_Interface click) {
        this.mctx = mctx;
        this.mList = mList;
        this.clickRecyclerViewInterface = click;
    }



    @NonNull
    @Override
    public FeedRecyclerAdapter.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_content, parent, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedRecyclerAdapter.FeedViewHolder holder, int position) {
        News news = mList.get(position);

        holder.title_content.setText(news.getTitle());
        holder.desc_content.setText(news.getDescription());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected class FeedViewHolder extends RecyclerView.ViewHolder {

        private TextView title_content;
        private TextView desc_content;
        private ImageView img_content;

        public FeedViewHolder(final View itemView) {
            super(itemView);

            title_content = (TextView) itemView.findViewById(R.id.tv_title_item_c);
            desc_content = (TextView) itemView.findViewById(R.id.tv_desc_item_c);
            img_content = (ImageView) itemView.findViewById(R.id.img_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickRecyclerViewInterface.onRecyclerClick(mList.get(getLayoutPosition()));

                }
            });
        }
    }
}
