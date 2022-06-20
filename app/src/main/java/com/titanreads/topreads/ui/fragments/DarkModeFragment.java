package com.titanreads.topreads.ui.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.titanreads.topreads.R;
import com.titanreads.topreads.databinding.FragmentDarkModeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DarkModeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DarkModeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Context context;

    int checkedRadioId = 0;
    String themeMode;
    FragmentDarkModeBinding binding;
    RadioButton radioDarkMode, radioLightMode, radioSystemMode;
    RadioGroup radioGroup;

    ImageButton ibBack;
    public DarkModeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DarkModeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DarkModeFragment newInstance(String param1, String param2) {
        DarkModeFragment fragment = new DarkModeFragment();
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

        readUserSharedPrefData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = com.titanreads.topreads.databinding.FragmentDarkModeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = root.getContext();
       // varView = root;

        bindUi();



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void bindUi(){

        radioGroup = binding.radioGroupDarkModeSettings;

        radioDarkMode = binding.radioDarkMode;
        radioLightMode = binding.radioLightMode;
        radioSystemMode = binding.radioSystemDefaultMode;



        if(themeMode!=null)
        {
            if(themeMode.contains("Dark")){

                if(checkedRadioId == radioDarkMode.getId()){
                    radioDarkMode.setChecked(true);

                }

                else if(themeMode.contains("Light")){

                    radioLightMode.setChecked(true);
                }

            }
        }


        binding.ibBackDarkModeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getActivity()!=null)
                getActivity().onBackPressed();
            }
        });
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {

                        RadioButton radioButton = (RadioButton)group.findViewById(checkedId);

                        if(checkedId == radioDarkMode.getId()){
                            radioDarkMode.setChecked(true);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                            updateUserSettingsSharePrefs("Dark");
                        }

                        else if(checkedId == radioLightMode.getId()){
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            radioLightMode.setChecked(true);
                            updateUserSettingsSharePrefs("Light");
                        }


                    }
                });

    }



    private void readUserSharedPrefData(){


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("guestUserData", MODE_PRIVATE);

        if(sharedPreferences.contains("ThemeMode")){
            themeMode = sharedPreferences.getString("ThemeMode", "");
        }

    }


    private void updateUserSettingsSharePrefs(String mode){
        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences("guestUserData",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("ThemeMode", mode);

        myEdit.apply();
    }
}