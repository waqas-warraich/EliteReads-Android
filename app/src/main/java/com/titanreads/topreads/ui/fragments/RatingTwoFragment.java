package com.titanreads.topreads.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.titanreads.topreads.R;
import com.titanreads.topreads.databinding.FragmentRatingOneBinding;
import com.titanreads.topreads.databinding.FragmentRatingTwoBinding;
import com.titanreads.topreads.ui.activities.AppUserFeedbackActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RatingTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingTwoFragment extends Fragment {

    FragmentRatingTwoBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RatingTwoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RatingTwoFragment newInstance(String param1, String param2) {
        RatingTwoFragment fragment = new RatingTwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRatingTwoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.giveSuggestionBtnRateUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AppUserFeedbackActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        binding.cancelBtnRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getActivity()!=null)
                    getActivity().onBackPressed();
            }
        });
    }
}