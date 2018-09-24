package com.curioso.curiosoapp.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.curioso.curiosoapp.MainActivity;
import com.curioso.curiosoapp.R;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView name;
    private MainActivity mActivity;

    messageFragment listener;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public interface messageFragment {
        public void onMessage(String tal);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        name = (TextView) v.findViewById(R.id.name);


        Bundle bundle = getArguments();

        mActivity = (MainActivity) getActivity();



        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (messageFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement messageFragmen");
        }


    }
}
