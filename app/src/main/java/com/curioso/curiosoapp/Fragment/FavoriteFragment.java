package com.curioso.curiosoapp.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.curioso.curiosoapp.Adapters.Recycler.FeedRecyclerAdapter;
import com.curioso.curiosoapp.Model.News;
import com.curioso.curiosoapp.R;
import com.curioso.curiosoapp.util.FavoritosParse;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment{

    RecyclerView feedRecyclerView;
    FeedRecyclerAdapter feedAdapter;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        feedRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_category);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(),
                2, //number of grid columns
                GridLayoutManager.VERTICAL,
                false);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //stagger rows custom
                return (position == 0 ? 2 : 1);
            }
        });

        feedRecyclerView.setLayoutManager(gridLayoutManager);
        feedRecyclerView.setHasFixedSize(true);
        feedAdapter = new FeedRecyclerAdapter(getContext());
        feedRecyclerView.setAdapter(feedAdapter);


        loadFirebase();

        return view;
    }

    public void loadFirebase(){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("favoritos");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    feedAdapter.addItems(item.getValue(News.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
