package com.recommendations.books.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.recommendations.books.R;
import com.recommendations.books.databinding.FragmentRateUsOneBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RatingOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingOneFragment extends Fragment {

    FragmentRateUsOneBinding binding;
    Context context;

    ImageView ivRatingIllustration;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RatingOneFragment() {
        // Required empty public constructor
    }

    public static RatingOneFragment newInstance(String param1, String param2) {
        RatingOneFragment fragment = new RatingOneFragment();
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
        binding = FragmentRateUsOneBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ivRatingIllustration = binding.imageViewRatingIllustration;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.oneStarRateUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.oneStarRateUse.setImageResource(R.drawable.ic_round_star_filled_40);
                loadFragment(new RatingTwoFragment(), null);
            }
        });

        binding.twoStarsRateUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.oneStarRateUse.setImageResource(R.drawable.ic_round_star_filled_40);
                binding.twoStarsRateUse.setImageResource(R.drawable.ic_round_star_filled_40);

                loadFragment(new RatingTwoFragment(), null);
            }
        });


        binding.threeStarsRateUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.oneStarRateUse.setImageResource(R.drawable.ic_round_star_filled_40);
                binding.twoStarsRateUse.setImageResource(R.drawable.ic_round_star_filled_40);
                binding.threeStarsRateUse.setImageResource(R.drawable.ic_round_star_filled_40);

                loadFragment(new RatingTwoFragment(), null);
            }
        });

        binding.fourStarsRateUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.oneStarRateUse.setImageResource(R.drawable.ic_round_star_filled_40);
                binding.twoStarsRateUse.setImageResource(R.drawable.ic_round_star_filled_40);
                binding.threeStarsRateUse.setImageResource(R.drawable.ic_round_star_filled_40);
                binding.fourStarsRateUse.setImageResource(R.drawable.ic_round_star_filled_40);

                loadFragment(new RatingTwoFragment(), null);
            }
        });


        binding.fiveStarsRateUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.oneStarRateUse.setImageResource(R.drawable.ic_round_star_filled_40);
                binding.twoStarsRateUse.setImageResource(R.drawable.ic_round_star_filled_40);
                binding.threeStarsRateUse.setImageResource(R.drawable.ic_round_star_filled_40);
                binding.fourStarsRateUse.setImageResource(R.drawable.ic_round_star_filled_40);
                binding.fiveStarsRateUse.setImageResource(R.drawable.ic_round_star_filled_40);

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.recommendations.books")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.recommendations.books")));
                }


                ivRatingIllustration.setImageResource(R.drawable.ic_super_thank_you);
                binding.textView.setText("Thank you very much for your positive review!");
                binding.textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                binding.textView2.setVisibility(View.INVISIBLE);

               /* Intent intent = new Intent(view.getContext(), AppUserFeedbackActivity.class);
                view.getContext().startActivity(intent);*/
            }
        });

        binding.ibBackRatingFragmentOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getActivity()!=null)
                getActivity().onBackPressed();
            }
        });


    }


    private void loadFragment(Fragment fragment, String Tag) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.rating_fragments_container, fragment)
                        .commit();
            }
        }, 500);

    }

}