package com.recommendations.books.ui.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.recommendations.books.MainActivity;
import com.recommendations.books.R;
import com.recommendations.books.databinding.FragmentAccountsBinding;
import com.recommendations.books.models.User;
import com.recommendations.books.ui.activities.AboutAppActivity;
import com.recommendations.books.ui.activities.AppRatingActivity;
import com.recommendations.books.ui.activities.AppUserFeedbackActivity;
import com.recommendations.books.ui.activities.LoginActivity;
import com.recommendations.books.ui.activities.PrivacyPolicyActivity;
import com.recommendations.books.ui.activities.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.mikhaellopez.circularimageview.CircularImageView;


public class SettingsFragment extends Fragment {


    FirebaseAuth mAuth;
    final String TAG = "AccountFragment";
    private LinearLayoutCompat registrationContainerLL;

    RadioButton radioDarkMode, radioLightMode, radioSystemMode;
    RadioGroup radioGroup;

    TextView tvAboutApp;
    TextView tvDeleteAccountData;
    NavController navController;
    View varView = null;
    private boolean monitoringConnectivity = false;
    boolean isConnected = true;
    private TextView tvAccoutLb, tvTeacherProfileLb, tvRegistrationLb, tvEditProfile, tvSetPrivacy, tvDeleteProfile, tvProfileViews, tvViewProfile, tvProfileViewsCounter, tvFavouritesCounter;
    private LinearLayoutCompat signUpLLBtn, registerAsTutorLL, postRequestLL;
    private TextView signInTvBtn, contactUsTv, giveFeedBackTv, accountSettings, shareAppTv, PrivacyPolicyTv, logOutTv, tvAccountSettings;
    private TextView tvFavoritesFragmentBtn, tvContactUs;
    AppCompatButton btnYes, btnNO;
    TextView tvDialogTitle;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference userAccountDocRef;
    String userAccountId;

    LinearLayoutCompat llAccountImageNameContainer;

    User user;

    ImageView ivSettings;
    TextView tvAccountName, tvAccountEmail;
    CircularImageView ivAccountProfileImage;
    private View vSep1, vSep2;

    String userName, userEmail, userImageUrl;
    EventListener<DocumentSnapshot> eventListenerAccounts;
    private ListenerRegistration listenerRegistrationUserAccount;

    Context context;


    String themeMode;
    FirebaseUser currentUser;
    LinearLayoutCompat llProgressBarContainer;

    private FragmentAccountsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            readUserSharedPrefData();


        readUserSettingsSharedPrefData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = com.recommendations.books.databinding.FragmentAccountsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = root.getContext();
        varView = root;

        bindUi();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }

    private void bindUi() {


        ivAccountProfileImage = binding.civProfileImageUserAccount;
        Glide.with(context)
                .load(R.drawable.person_placeholder_new2)
                .into(ivAccountProfileImage);

        tvAccountName = binding.tvAccountName;
        tvAccountEmail = binding.tvAccountEmailSettingsTab;
        ivSettings = binding.ivSettingsAccount;

        tvAboutApp = binding.tvAboutAppSettingsTab;

        llAccountImageNameContainer = binding.llImageContainerAccounts;
        registrationContainerLL = binding.llRegistrationContainer;
        tvRegistrationLb = binding.tvLabelRegistration;
        signInTvBtn = binding.signInTvBtnAt;
        signUpLLBtn = binding.llSignupContainerProfileTab;
        logOutTv = binding.tvLogout;

        tvDeleteAccountData = binding.deleteMyDataBtn;

        tvAccountSettings = binding.tvAccountSettings;
        vSep1 = binding.accountSettingsSep;
        vSep2 = binding.accountSavedSep;


        accountSettings = binding.tvAccountSettings;
        tvFavoritesFragmentBtn = binding.tvAccountSaved;
        llProgressBarContainer = varView.findViewById(R.id.progressBarAccountsTab);




        if (themeMode != null) {
            if (themeMode.contains("Dark")) {
                binding.tvDarkModeIndicator.setText("On");

            } else if (themeMode.contains("Light")) {
                binding.tvDarkModeIndicator.setText("Off");
            }
        }



/*


        binding.tvAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            }
        });

*/

        binding.llSignupContainerProfileTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.signInTvBtnAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_dialog_mtrl, null);
                dialogBuilder.setView(dialogView);

                btnYes = dialogView.findViewById(R.id.alert_btn_yes);
                btnNO = dialogView.findViewById(R.id.alert_btn_no);

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_round_edge_dialog));
                alertDialog.show();

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        llProgressBarContainer.setVisibility(View.VISIBLE);
                        FirebaseAuth.getInstance().signOut();

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userAccountData", MODE_PRIVATE);
                                sharedPreferences.edit().clear().apply();

                                llProgressBarContainer.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "You logout successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);


                            }
                        }, 1000);

                    }
                });

                btnNO.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();

                    }
                });
            }
        });

        tvDeleteAccountData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_dialog_mtrl, null);
                dialogBuilder.setView(dialogView);

                tvDialogTitle = dialogView.findViewById(R.id.title_alert_dialog);

                btnYes = dialogView.findViewById(R.id.alert_btn_yes);
                btnYes.setTextColor(getActivity().getResources().getColor(R.color.red));
                btnNO = dialogView.findViewById(R.id.alert_btn_no);

                tvDialogTitle.setText("Are you sure you want to delete your account?");

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_round_edge_dialog));
                alertDialog.show();

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        llProgressBarContainer.setVisibility(View.VISIBLE);

                        DocumentReference documentReference = db.collection("BookRecAndroidUserAccounts").document(userAccountId);

                        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                FirebaseAuth.getInstance().signOut();
                                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userAccountData", MODE_PRIVATE);
                                                sharedPreferences.edit().clear().apply();

                                                llProgressBarContainer.setVisibility(View.GONE);
                                                Toast.makeText(getContext(), "You deleted your account successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                startActivity(intent);




                                            }
                                        }, 1000);

                                    }
                                });

                            }
                        });

                        //FirebaseAuth.getInstance().signOut();


                    }
                });



                btnNO.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();

                    }
                });

            }
        });


        binding.tvContactUsSettingsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);

                email.putExtra(Intent.EXTRA_TITLE, "Send Email To EliteReads");
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"waqas@paktutorr.com"});

                if (userName != null)
                    email.putExtra(Intent.EXTRA_SUBJECT, userName + " from EliteReads App");
                else
                    email.putExtra(Intent.EXTRA_SUBJECT, "User Query " + " from EliteReads App");

                String deviceInfo = "\n\n\n\nDevice Specs: " + "\n" + "MANUFACTURER: " + Build.MANUFACTURER + "\n" + "MODEL: " +
                        Build.MODEL + "\n" + "BRAND: " + Build.BRAND + "\n" + "Display: " + Build.DISPLAY + "\n" + "USER: " + Build.USER + "\n" + "SDK: " +
                        Build.VERSION.SDK + "\n" + "RELEASE: " + Build.VERSION.RELEASE;


                email.putExtra(Intent.EXTRA_TEXT, deviceInfo);


                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email App :"));
            }
        });

        binding.tvRateUsSettingsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), AppRatingActivity.class);
                view.getContext().startActivity(intent);

            }
        });

        binding.tvShareAppSettingsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Want to know book recommendation from World's richest and successful peoples ? " +
                        "\nDownload EliteReads App now to learn or review the key takeaways of most recommended books in minutes: https://play.google.com/store/apps/details?id=com.titanreads.topreads");

                // shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, tutorProfile.getTutorName()+" on PakTutors");
                startActivity(Intent.createChooser(shareIntent, "Share via"));

                /*Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Want to know book recommendation from World's richest and successful peoples ? " +
                        "\nDownload EliteReads App now to learn or review the key takeaways of most recommended books in minutes: https://play.google.com/store/apps/details?id=com.titanreads.topreads");
                // shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, tutorProfile.getTutorName()+" on PakTutors");
                startActivity(Intent.createChooser(shareIntent, "Share via"));*/
            }
        });

        binding.tvUserSuggestionFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), AppUserFeedbackActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        binding.tvTermsAndConditionsSettingsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), PrivacyPolicyActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        binding.tvAboutAppSettingsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutAppActivity.class);
                startActivity(intent);
            }
        });


        binding.llDarkModeBtnAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* android:clickable="true"
                android:focusable="true"*/
                // Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment);
                //  navController.navigateUp();
                //  navController.navigate(R.id.fragmentDarkModeSettings);

                // navController.navigate(R.id.action_fragmentSettings_to_fragmentDarkMode);


                setUpDarkModeAlter();

            }
        });


    }


    private void setUpDarkModeAlter() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_dialog_dark_mode_settings, null);
        dialogBuilder.setView(dialogView);


        radioGroup = dialogView.findViewById(R.id.radio_group_darkMode_settings);
        radioDarkMode = dialogView.findViewById(R.id.radio_darkMode);
        radioLightMode = dialogView.findViewById(R.id.radio_lightMode);


        if (themeMode != null) {
            if (themeMode.contains("Dark")) {
                radioDarkMode.setChecked(true);
                binding.tvDarkModeIndicator.setText("On");

            } else if (themeMode.contains("Light")) {
                radioLightMode.setChecked(true);
                binding.tvDarkModeIndicator.setText("Off");
            }
        }

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_round_edge_dialog));
        alertDialog.show();


        radioGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId) {

                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);

                        if (checkedId == radioDarkMode.getId()) {
                            radioDarkMode.setChecked(true);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            updateUserSettingsSharePrefs("Dark");
                            binding.tvDarkModeIndicator.setText("On");
                            alertDialog.dismiss();

                        } else if (checkedId == radioLightMode.getId()) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            radioLightMode.setChecked(true);

                            updateUserSettingsSharePrefs("Light");
                            binding.tvDarkModeIndicator.setText("Off");

                            alertDialog.dismiss();
                        }


                    }
                });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void readUserSharedPrefData() {


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userAccountData", MODE_PRIVATE);
        userAccountId = sharedPreferences.getString("userAccountId", "");
        userName = sharedPreferences.getString("name", "");
        userEmail = sharedPreferences.getString("email", "");
        userImageUrl = sharedPreferences.getString("imageUrl", "");


    }

    private void hideProfileSection() {

       /* tvTeacherProfileLb.setVisibility(View.GONE);
        tvViewProfile.setVisibility(View.GONE);
        tvEditProfile.setVisibility(View.GONE);
        tvSetPrivacy.setVisibility(View.GONE);
        tvTuitionRequests.setVisibility(View.GONE);
        llTutorProfileStatusContainer.setVisibility(View.GONE);

        vSep3.setVisibility(View.GONE);
        vSep4.setVisibility(View.GONE);
        vSep5.setVisibility(View.GONE);
        vSep6.setVisibility(View.GONE);
        vSep7.setVisibility(View.GONE);
        vSep9.setVisibility(View.GONE);*/

    }

    private void showProfileSection() {

       /* tvTeacherProfileLb.setVisibility(View.VISIBLE);
        tvViewProfile.setVisibility(View.VISIBLE);
        tvEditProfile.setVisibility(View.VISIBLE);
        tvSetPrivacy.setVisibility(View.VISIBLE);
        tvTuitionRequests.setVisibility(View.VISIBLE);
        llTutorProfileStatusContainer.setVisibility(View.GONE);

        vSep3.setVisibility(View.VISIBLE);
        vSep4.setVisibility(View.VISIBLE);
        vSep5.setVisibility(View.VISIBLE);
        vSep6.setVisibility(View.VISIBLE);
        vSep7.setVisibility(View.VISIBLE);
        vSep9.setVisibility(View.VISIBLE);
*/


    }

    private void hideRegistrationSection() {

        binding.tvLabelRegistration.setVisibility(View.GONE);
        binding.llRegistrationContainer.setVisibility(View.GONE);

    }

    private void hideAccountsSection() {


    }

    @Override
    public void onStart() {
        super.onStart();
        /*if(currentUser!=null)
            readUserSharedPrefData();*/

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            tvDeleteAccountData.setVisibility(View.VISIBLE);
            logOutTv.setVisibility(View.VISIBLE);
            hideRegistrationSection();
            llAccountImageNameContainer.setVisibility(View.VISIBLE);

            /*if(!name.trim().isEmpty())
                tvAccountName.setText(name);*/

            if (!TextUtils.isEmpty(userName)) {
                tvAccountName.setText(userName);
            }

            if (!TextUtils.isEmpty(userEmail)) {


                tvAccountEmail.setText(userEmail);
            }
            if (!TextUtils.isEmpty(userImageUrl)) {

                if (userImageUrl.length() > 10)
                    Glide.with(context)
                            .load(userImageUrl)
                            .placeholder(R.drawable.person_placeholder_new2)
                            .into(ivAccountProfileImage);
                else {
                    ivAccountProfileImage.setImageResource(R.drawable.person_placeholder_new2);
                }
            }


        } else {

            //  hideAccountsSection();
            llAccountImageNameContainer.setVisibility(View.GONE);
        }

    }


    private void readUserSettingsSharedPrefData() {


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("guestUserData", MODE_PRIVATE);

        if (sharedPreferences.contains("ThemeMode")) {
            themeMode = sharedPreferences.getString("ThemeMode", "");
        }

    }


    private void updateUserSettingsSharePrefs(String mode) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("guestUserData", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("ThemeMode", mode);

        myEdit.apply();
    }

}