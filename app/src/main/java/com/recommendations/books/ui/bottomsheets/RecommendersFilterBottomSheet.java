package com.recommendations.books.ui.bottomsheets;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.recommendations.books.R;
import com.recommendations.books.ui.activities.ViewAllRecommendersActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecommendersFilterBottomSheet extends BottomSheetDialogFragment {

    ArrayList<String> filterTagsList = new ArrayList<>();
    public static final String TAG = "FilterBottomSheet";

    TextView tvClose, tvApply, tvFeeError;
    Bundle bundle;

    ChipGroup chipGroup;
    Chip chipTag1, chipTag2, chipTag3, chipTag4, chipTag5, chipTag6, chipTag7, chipTag8, chipTag9, chipTag10;
    Chip chipTag11, chipTag12, chipTag13, chipTag14, chipTag15, chipTag16, chipTag17, chipTag18, chipTag19, chipTag20;

    Set<String> quickFiltersSet = new HashSet<String>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().overridePendingTransition(R.anim.mtrl_bottom_sheet_slide_out,R.anim.mtrl_bottom_sheet_slide_out);

        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        bundle = new Bundle();


        if (getArguments() != null) {

            filterTagsList = getArguments().getStringArrayList("filterTagsList");


        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.bottomsheet_recommender_filters, container, false);

        initUi(view);


        return view;

    }

    private void initUi(View view) {

        tvClose = view.findViewById(R.id.tv_close_btn_fee_bottom_sheet);
        tvFeeError = view.findViewById(R.id.tv_error_fee_range_quick_filters);
        tvApply = view.findViewById(R.id.apply_btn_fee_bottom_sheet_filter);

        chipGroup = view.findViewById(R.id.recommenders_filter_chipGroup);

        chipTag1 = view.findViewById(R.id.filterChip1);
        chipTag2 = view.findViewById(R.id.filterChip2);
        chipTag3 = view.findViewById(R.id.filterChip3);
        chipTag4 = view.findViewById(R.id.filterChip4);
        chipTag5 = view.findViewById(R.id.filterChip5);
        chipTag6 = view.findViewById(R.id.filterChip6);
        chipTag7 = view.findViewById(R.id.filterChip7);
        chipTag8 = view.findViewById(R.id.filterChip8);
        chipTag9 = view.findViewById(R.id.filterChip9);
        chipTag10 = view.findViewById(R.id.filterChip10);
        chipTag11 = view.findViewById(R.id.filterChip11);
        chipTag12 = view.findViewById(R.id.filterChip12);
        chipTag13 = view.findViewById(R.id.filterChip13);
        chipTag14 = view.findViewById(R.id.filterChip14);
        chipTag15 = view.findViewById(R.id.filterChip15);
        chipTag16 = view.findViewById(R.id.filterChip16);
        chipTag17 = view.findViewById(R.id.filterChip17);
        chipTag18 = view.findViewById(R.id.filterChip18);
        chipTag19 = view.findViewById(R.id.filterChip19);
        chipTag20 = view.findViewById(R.id.filterChip20);


        chipTag1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag1.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag1.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag2.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag3.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag4.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag5.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag6.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag7.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag8.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag9.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag10.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag11.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag12.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag13.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag14.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag15.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag16.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag17.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag18.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag18.setChecked(false);
                    showLimitToast();
                }


            }
        });

        chipTag19.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag19.setChecked(false);
                    showLimitToast();//force to unchecked the chip
                }


            }
        });

        chipTag20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<Integer> ids = chipGroup.getCheckedChipIds();
                if (ids.size() > 5) {
                    chipTag20.setChecked(false);
                    showLimitToast();

                    //force to unchecked the chip
                }


            }
        });


        tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chipGroup.getCheckedChipIds();
                List<Integer> chipsIds = chipGroup.getCheckedChipIds();

                int count = 0;
                while (count <chipsIds.size()) {

                    Chip chip = chipGroup.findViewById(chipsIds.get(count));
                    filterTagsList.add(chip.getText().toString());
                    count++;
                }

                startActivity();
                dismiss();


            }
        });


        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void showLimitToast(){
        Toast.makeText(getContext(), "you cannot select more than 5", Toast.LENGTH_SHORT).show();
    }
    private void registerForChange() {

    }

    private void startActivity() {


        bundle = new Bundle();

        Log.v(TAG, filterTagsList.toString());

        if(!filterTagsList.isEmpty())
            bundle.putStringArrayList("tagList", filterTagsList);

        Intent intent = new Intent(getContext(), ViewAllRecommendersActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        filterTagsList = null;
    }


}
