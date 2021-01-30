package com.fashion.amai.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fashion.amai.adapter.AdapterProfile;
import com.fashion.amai.R;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.model.Profilepojo;
import com.fashion.amai.model.UpdateProfilePojo;
import com.fashion.amai.utils.MySharedPreferences;
import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ActivityProfile extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerProfile;
    private LinearLayoutManager linearLayoutManager;
    private AdapterProfile adapterProfile;
    private List<String> profileOptionsList;
    private TextView tvLogout;
    private File tempOutputFile; //storing the profile_image temporarily while we crop it.
    private Uri resultUri;
    private ImageView profile_image;
    private static final int REQUEST_SELECT_IMAGE = 1800;
    private static final int PERMISSION_REQUEST_CODE = 1121;
    private View profile_progressbar;

    private static final String[] TAKE_PICTURE_PERM = {CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
       // toolbar = (Toolbar) findViewById(R.id.toolbar);

        getSupportActionBar().setTitle("Profile Page");

        tempOutputFile = new File(getExternalCacheDir(), "temp-profile_image.JPEG");


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setOnClickListeners();



        if (tempOutputFile.canRead()){

            profile_image.setImageResource(0);
            Bitmap myBitmap = BitmapFactory.decodeFile(tempOutputFile.getAbsolutePath());
            profile_image.setImageBitmap(myBitmap);
        } else {
            Glide.with(ActivityProfile.this)
                    .load(MySharedPreferences.getProfileImage(preferences))
                    .fitCenter()
                    .placeholder(Drawable.createFromPath(getString(R.string.glide_placeholder)))
                    .into(profile_image);

           // profile_image.setImageDrawable(getResources().getDrawable(R.drawable.icon_profile, null));
        }

        getUserProfile();

    }

    private void initViews() {

        profileOptionsList = Arrays.asList(getResources().getStringArray(R.array.profile_options_list));
        profile_image = findViewById(R.id.profile_image);
        profile_progressbar = findViewById(R.id.profile_progressbar);

        recyclerProfile = findViewById(R.id.recycler_profile);
        linearLayoutManager = new LinearLayoutManager(this);
        adapterProfile = new AdapterProfile(this, profileOptionsList);
        recyclerProfile.setLayoutManager(linearLayoutManager);
        recyclerProfile.setAdapter(adapterProfile);
       // ivBack = findViewById(R.id.iv_back);
        tvLogout = findViewById(R.id.tv_logout);
    }

    private void setOnClickListeners() {
       // ivBack.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        profile_image.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_logout:{
              //  finishAffinity();
                MySharedPreferences.wipe(preferences);
                Intent intent = new Intent(ActivityProfile.this, IntroScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            }

            case R.id.profile_image:{
              //  finishAffinity();
                askPermissions();
                break;
            }
        }
    }

    private void askPermissions() {
        int requestCode = 232;
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(ActivityProfile.this, Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        } else {
            changeAvatar();

        }

    }

    private void getUserProfile() {

        retrofit2.Call<Profilepojo> call = merchantApiInterface.getUserProfile();
        call.enqueue(new retrofit2.Callback<Profilepojo>() {
            @Override
            public void onResponse(retrofit2.Call<Profilepojo> call, retrofit2.Response<Profilepojo> response) {

                profile_progressbar.setVisibility(View.GONE);

                try{
                    if(response.code()==200){

                    }else if(response.code()==201){
                        profile_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        //  showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        profile_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    profile_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Profilepojo> call, Throwable t) {
                t.printStackTrace();
                profile_progressbar.setVisibility(View.GONE);

                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    private void uploadUserProfile(Uri imageUrl, String first_name, String last_name, String dob, String gender) {
        profile_progressbar.setVisibility(View.VISIBLE);

        File file = new File(imageUrl.getPath());

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("add_to_cart_image/jpeg"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);


        retrofit2.Call<UpdateProfilePojo> call = merchantApiInterface.sendUserDetails(body);
        call.enqueue(new retrofit2.Callback<UpdateProfilePojo>() {
            @Override
            public void onResponse(retrofit2.Call<UpdateProfilePojo> call, retrofit2.Response<UpdateProfilePojo> response) {

                profile_progressbar.setVisibility(View.GONE);


                try{
                    if(response.code()==200){

                        MySharedPreferences.registerProfileImage(preferences, response.body().getData().getProfileImage());

                        Log.d("add_to_cart_image URL", response.body().getData().getProfileImage());

                    }else if(response.code()==201){
                        profile_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        //  showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        profile_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    profile_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfilePojo> call, Throwable t) {
                t.printStackTrace();
                profile_progressbar.setVisibility(View.GONE);

                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    private void changeAvatar() {
        List<Intent> otherImageCaptureIntent = new ArrayList<>();
        List<ResolveInfo> otherImageCaptureActivities =
                getApplication().getPackageManager().queryIntentActivities(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                        0); // finding all intents in apps which can handle capture add_to_cart_image
        // loop through all these intents and for each of these activities we need to store an intent
        for (ResolveInfo info : otherImageCaptureActivities) { // Resolve info represents an activity on the system that does our work
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.setClassName(info.activityInfo.packageName,
                    info.activityInfo.name); // declaring explicitly the class where we will go
            // where the picture activity dump the add_to_cart_image
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempOutputFile));
            otherImageCaptureIntent.add(captureIntent);
        }


        // above code is only for taking picture and letting it go through another app for cropping before setting to imageview
        // now below is for choosing the add_to_cart_image from device

        Intent selectImageIntent = new Intent(Intent.ACTION_PICK);
        selectImageIntent.setType("add_to_cart_image/*");

        Intent chooser = Intent.createChooser(selectImageIntent, "Choose Avatar");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, otherImageCaptureIntent.toArray(
                new Parcelable[otherImageCaptureActivities.size()]));  // add 2nd para as intent of parcelables.

        startActivityForResult(chooser, REQUEST_SELECT_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            tempOutputFile.delete();
            return;
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_IMAGE) {
                // user selected an add_to_cart_image off their device. other condition they took the add_to_cart_image and that add_to_cart_image is in our tempoutput file
                Uri outputFile;
                Uri tempFileUri = Uri.fromFile(tempOutputFile);
                // if statement will detect if the user selected an add_to_cart_image from the device or took an add_to_cart_image
                if (data != null && (data.getAction() == null || !data.getAction()
                        .equals(MediaStore.ACTION_IMAGE_CAPTURE))) {
                    //then it means user selected an add_to_cart_image off the device
                    // so we can get the Uri of that add_to_cart_image using data.getData
                    outputFile = data.getData();
                    // Now we need to do the crop
                } else {
                    // add_to_cart_image was out temp file. user took an add_to_cart_image using camera
                    outputFile = tempFileUri;
                    // Now we need to do the crop
                }
                startCropActivity(outputFile);
            } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

                final Uri resultUri = UCrop.getOutput(data);

                this.resultUri = resultUri;

                tempOutputFile = new File(resultUri.getPath());

             //   Timber.e(String.valueOf(Uri.fromFile(tempOutputFile)));

                uploadUserProfile(resultUri, "harish", "kumar", "2005-10-16", "female");

                profile_image.setImageResource(0);


                if (tempOutputFile.canRead()){

                    Bitmap myBitmap = BitmapFactory.decodeFile(tempOutputFile.getAbsolutePath());
                    profile_image.setImageBitmap(myBitmap);
                } else {
                    profile_image.setImageDrawable(getResources().getDrawable(R.drawable.icon_profile, null));
                }


                //   profile_image.setImageDrawable(getResources().getDrawable(R.drawable.image_accepted));

                // avatarProgressFrame.setVisibility(View.VISIBLE);
                // getBus.post(new Account.ChangeAvatarRequest(Uri.fromFile(tempOutputFile)));
            }
        }
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();

        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        options.withMaxResultSize(960, 1028);
        options.setFreeStyleCropEnabled(true);
        uCrop.useSourceImageAspectRatio();

        options.setShowCropGrid(false);
        options.setMaxScaleMultiplier(10.0f);
        return uCrop.withOptions(options);
    }

    private void startCropActivity(@NonNull Uri uri) {

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(tempOutputFile));

        uCrop = advancedConfig(uCrop);
        uCrop.start(ActivityProfile.this);
    }


}
