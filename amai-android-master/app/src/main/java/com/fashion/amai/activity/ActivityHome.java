package com.fashion.amai.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fashion.amai.adapter.AdapterHome;
import com.fashion.amai.adapter.AdapterMenuTopHome;
import com.fashion.amai.adapter.MyRecyclerViewAdapter;
import com.fashion.amai.custom.ApiClient;
import com.fashion.amai.fragment.GenderBottomDialogFragment;
import com.fashion.amai.model.Login;
import com.fashion.amai.model.MeasurementPojoResponse;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.model.ModelMenuTopHome;
import com.fashion.amai.R;
import com.fashion.amai.model.Movie;
import com.fashion.amai.model.RefreshTokenPojo;
import com.fashion.amai.utils.Constants;
import com.fashion.amai.utils.HidingScrollListener;
import com.fashion.amai.utils.MenuConverter;
import com.fashion.amai.utils.MySharedPreferences;
import com.fashion.amai.utils.Paytm;
import com.fashion.amai.utils.PrefKeys;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ActivityHome extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, GenderBottomDialogFragment.ItemClickListener, PaytmPaymentTransactionCallback {
   // private ImageView ivSearch, ivNotification, ivWishlist, ivCart;
    private LinearLayout layoutFooterBottom;
    private RecyclerView recyclerMenuTop, home_recyclerview;
    private NestedScrollView nestedScrollViewHome;
    private ImageView tvDesignYourOutfits, tvOurDesigns, tv_wedding_wardrobe;
    private AdapterMenuTopHome adapterMenuTopHome;
    private LinearLayoutManager linearLayoutManagerMenuTop;
    private List<ModelMenuTopHome> modelMenuTopHomeList;
    private Handler mTimerHandler;



    private List<Movie> movieList = new ArrayList<>();
    private AdapterHome mAdapter;

   // private FrameLayout custom_designs_bottom;
   // private LinearLayout layoutNavMenuDesigns, layoutNavMenuWishlist;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    View home_progressbar;
    private File tempOutputFile; //storing the profile_image temporarily while we crop it.


   // LottieAnimationView home_progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //  prevent screenshot
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_home_test);

        initViews();
        initRecyclerMenuTop();
        setOnClickListeners();
      //  detectHomePageScroll();

        tempOutputFile = new File(getExternalCacheDir(), "temp-profile_image.JPEG");

        toolbar.setNavigationIcon(R.drawable.icon_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("AMAI");


        if (MySharedPreferences.getUsername(preferences)!=null){
            signIn(MySharedPreferences.getUsername(preferences), MySharedPreferences.getPassword(preferences));
        }

        home_progressbar = findViewById(R.id.home_progressbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.drawer_women:

                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.drawer_men:
                        Toast.makeText(getApplicationContext(),"Stared Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawer_kids:
                        Toast.makeText(getApplicationContext(),"Send Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawer_my_designs:

                        startActivity(new Intent(ActivityHome.this, ActivityMyDesigns.class));
                      //  Toast.makeText(getApplicationContext(),"Drafts Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawer_appointments:
                        startActivity(new Intent(ActivityHome.this, ActivityMyAppointments.class));
                        return true;
                    case R.id.drawer_my_order:
                        Toast.makeText(getApplicationContext(),"Trash Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawer_my_rewards:
                        Toast.makeText(getApplicationContext(),"drawer_my_rewards",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawer_my_cart:
                        startActivity(new Intent(ActivityHome.this, ActivityCartSample.class));
                        return true;
                    case R.id.drawer_my_wishlist:
                        startActivity(new Intent(ActivityHome.this, ActivityMyWishlist.class));
                        return true;
                    case R.id.drawer_account:
                        startActivity(new Intent(ActivityHome.this, ActivityProfile.class));

                        return true;
                    case R.id.drawer_measurements:

                        startActivity(new Intent(ActivityHome.this, MyMeasurementListActivity.class));

                        return true;
                    case R.id.drawer_faqs:
                        Toast.makeText(getApplicationContext(),"drawer_faqs",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawer_contacts:
                        Toast.makeText(getApplicationContext(),"drawer_contacts",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawer_live_chat:
                        Toast.makeText(getApplicationContext(),"drawer_live_chat",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawer_logout:
                        MySharedPreferences.wipe(preferences);
                        Intent intent = new Intent(ActivityHome.this, IntroScreenActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                View headerview = navigationView.getHeaderView(0);

                CircleImageView circleImageView = (CircleImageView) headerview.findViewById(R.id.profile_image);

                if (tempOutputFile.canRead()){
                    circleImageView.setImageResource(0);
                    Bitmap myBitmap = BitmapFactory.decodeFile(tempOutputFile.getAbsolutePath());
                    circleImageView.setImageBitmap(myBitmap);
                } else {
                    Glide.with(drawerView.getContext())
                            .load(MySharedPreferences.getProfileImage(preferences))
                            .fitCenter()
                            .placeholder(Drawable.createFromPath(getString(R.string.glide_placeholder)))
                            .into(circleImageView);


                   // circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_profile, null));
                }

                super.onDrawerOpened(drawerView);


            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        View headerview = navigationView.getHeaderView(0);

        RelativeLayout header = (RelativeLayout) headerview.findViewById(R.id.nav_header);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityProfile.class));
            //    startActivity(new Intent(ActivityHome.this, ActivitySchedule.class));

              //  Toast.makeText(ActivityHome.this, "clicked", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
               // drawerLayout.openDrawer(navigationView);

            }
        });

        home_recyclerview = (RecyclerView) findViewById(R.id.home_recyclerview);

        mAdapter = new AdapterHome(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        home_recyclerview.setLayoutManager(mLayoutManager);
        home_recyclerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        home_recyclerview.setItemAnimator(new DefaultItemAnimator());

        home_recyclerview.setAdapter(mAdapter);

        hideViews();
        showViews();

        prepareMovieData();
        home_recyclerview.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });

        //  MySharedPreferences.wipeToken(preferences);

        home_progressbar.setVisibility(View.VISIBLE);
        getMeasurement();
    }

    private void prepareMovieData() {
        Movie movie = new Movie("Mad Max: Fury Road", R.drawable.home_img, "2015");
        movieList.add(movie);

        movie = new Movie("Inside Out", R.drawable.home_img_02, "2015");
        movieList.add(movie);

        movie = new Movie("Star Wars: Episode VII - The Force Awakens", R.drawable.home_img, "2015");
        movieList.add(movie);

        movie = new Movie("Shaun the Sheep", R.drawable.home_img_02, "2015");
        movieList.add(movie);

        movie = new Movie("The Martian", R.drawable.home_img, "2015");
        movieList.add(movie);

        movie = new Movie("Mission: Impossible Rogue Nation", R.drawable.home_img_02, "2015");
        movieList.add(movie);

        // notify adapter about data set changes
        // so that it will render the list with new data
        mAdapter.notifyDataSetChanged();
    }

    private void hideViews() {

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutFooterBottom.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        recyclerMenuTop.animate().translationY(-recyclerMenuTop.getHeight()-120).setInterpolator(new AccelerateInterpolator(2));

        layoutFooterBottom.animate().translationY(layoutFooterBottom.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        recyclerMenuTop.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        layoutFooterBottom.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    private void initViews() {
        home_recyclerview = findViewById(R.id.home_recyclerview);
        drawerLayout = findViewById(R.id.drawer_layout);
         layoutFooterBottom = findViewById(R.id.layout_footer_bottom);
        recyclerMenuTop = findViewById(R.id.recycler_menu_top);
        nestedScrollViewHome = findViewById(R.id.nested_scroll_view_home);
        tvDesignYourOutfits = findViewById(R.id.tv_design_your_outfits);
        tvOurDesigns = findViewById(R.id.tv_our_designs);
        tv_wedding_wardrobe = findViewById(R.id.tv_wedding_wardrobe);
     //   tvHandpickedHandicrafts = findViewById(R.id.tv_handpicked_handicrafts);
    }

    private void initRecyclerMenuTop() {
        modelMenuTopHomeList = new ArrayList<>();
        adapterMenuTopHome = new AdapterMenuTopHome(ActivityHome.this, modelMenuTopHomeList);
        linearLayoutManagerMenuTop = new LinearLayoutManager(ActivityHome.this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerMenuTop.setLayoutManager(linearLayoutManagerMenuTop);
        recyclerMenuTop.setAdapter(adapterMenuTopHome);
        /*modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.men_banner), getResources().getString(R.string.menu_men), 1));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.women_banner), getResources().getString(R.string.menu_women), 2));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.trending_banner), "Trending", 3));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.clearance_banner), "Clearance", 4));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.beauty_banner), "Beauty", 5));*/

        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.men_banner), "Design Your \n Outfit", 1));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.women_banner), "Our Designs", 2));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.trending_banner), "Wedding Wardrobe", 3));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.clearance_banner), "What We Do", 4));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.men_banner), getResources().getString(R.string.menu_men), 5));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.women_banner), getResources().getString(R.string.menu_women), 6));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.trending_banner), "Trending", 7));


        adapterMenuTopHome.notifyDataSetChanged();
    }

    private void setOnClickListeners() {
        tvDesignYourOutfits.setOnClickListener(this);
         layoutFooterBottom.setOnClickListener(this);
        tvOurDesigns.setOnClickListener(this);
        tv_wedding_wardrobe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search: {

                break;
            }
           /* case R.id.iv_notification: {

                break;
            }*/
            case R.id.iv_wish_list: {

                startActivity(new Intent(ActivityHome.this, ActivityMyWishlist.class));

                break;
            }
            case R.id.iv_cart: {

                startActivity(new Intent(ActivityHome.this, ActivityCartSample.class));

                break;
            }

            case R.id.tv_design_your_outfits: {
                startActivity(new Intent(ActivityHome.this, MerchantActivity.class));
                break;
            }
            case R.id.tv_our_designs: {
                startActivity(new Intent(ActivityHome.this, ActivityOurDesigns.class));
                break;
            }
            case R.id.tv_wedding_wardrobe: {
                startActivity(new Intent(ActivityHome.this, ActivityWeddingHome.class));
                break;
            }
        /*    case R.id.tv_handpicked_handicrafts: {
                startActivity(new Intent(ActivityHome.this, MerchantActivity.class));
                break;
            }

         */

        }
    }

    private void getMeasurement() {

        retrofit2.Call<MeasurementPojoResponse> call = merchantApiInterface.getMeasurementList();
        call.enqueue(new retrofit2.Callback<MeasurementPojoResponse>() {
            @Override
            public void onResponse(retrofit2.Call<MeasurementPojoResponse> call, retrofit2.Response<MeasurementPojoResponse> response) {
                home_progressbar.setVisibility(View.GONE);

                try{
                    if(response.code()==401){

                        try{
                            getRefreshToken();

                        } catch (Exception ex){
                            ex.printStackTrace();
                        } finally {
                            getMeasurement();
                        }
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    home_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MeasurementPojoResponse> call, Throwable t) {
                t.printStackTrace();
                home_progressbar.setVisibility(View.GONE);

                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        invalidateOptionsMenu();
        getMenuInflater().inflate(R.menu.top_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.shopping_nav);

        if (MySharedPreferences.getCart(preferences)==null){
            cart_count = 0;
        } else {
            cart_count = Integer.parseInt(MySharedPreferences.getCart(preferences));
        }


        menuItem.setIcon(MenuConverter.convertLayoutToImage(getBaseContext(),cart_count,R.drawable.ic_action_shopping_bag));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_nav) {

            Toast.makeText(ActivityHome.this, "Search", Toast.LENGTH_SHORT).show();
            // Do something
            return true;
        }
        if (id == R.id.notify_nav) {

            Toast.makeText(ActivityHome.this, "Notify", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.like_nav) {

            startActivity(new Intent(this, ActivityMyWishlist.class));

            // Do something
            return true;
        }
        if (id == R.id.shopping_nav) {

            startActivity(new Intent(this, ActivityCartSample.class));


          //  Toast.makeText(ActivityHome.this, "Shopping", Toast.LENGTH_SHORT).show();

            // Do something
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void signIn(String userName, String pass) {
        HashMap<String, String> mapObject   = new HashMap<>();
        mapObject.put(PrefKeys.USER_NAME, userName);
        mapObject.put(PrefKeys.PASSWORD,  pass);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(mapObject)).toString());

        // AuthInterface.Auth apiInterface  = ApiClient.getClient().create(AuthInterface.Auth.class);
        retrofit2.Call<Login.Response> call = authApiInterface.
                signInToServer(PrefKeys.CONTENT_TYPE_APP_JSON, body);
        call.enqueue(new retrofit2.Callback<Login.Response>() {
            @Override
            public void onResponse(retrofit2.Call<Login.Response> call, retrofit2.Response<Login.Response> response) {
                if(response.code()==200){
                   // progressBar.dismiss();
                    String token = response.body().getData().getToken();
                    Integer userId  = response.body().getData().getId();
                    Log.d("token--",token);
                    // showToast(token);
                    MySharedPreferences.registerToken(preferences, token);
                    MySharedPreferences.registerUserId(preferences, String.valueOf(userId));

                }else if(response.code()==201){
                    //progressBar.dismiss();
                }else if(response.code()==401){
                   // progressBar.dismiss();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Login.Response> call, Throwable t) {
                t.printStackTrace();
               // showToast(ProductActivityOld.SOME_THING_WENT_WRONG+t.getMessage());
            }
        });

    }

    private void initializePaytmPayment(String checksumHash, Paytm paytm) {

        //getting paytm service
        PaytmPGService Service = PaytmPGService.getStagingService("https://securegw-stage.paytm.in/order/process");

        //use this when using for production
        //PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", getString(R.string.merchant_id));
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());


        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    public void showToast(String toastMessage){
        Toast.makeText(ActivityHome.this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
    finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onItemClick(String item) {
        Toast.makeText(this, item, Toast.LENGTH_LONG).show();

    }

    //all these overriden method is to detect the payment result accordingly
    @Override
    public void onTransactionResponse(Bundle bundle) {

        Toast.makeText(this, bundle.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Toast.makeText(this, s + bundle.toString(), Toast.LENGTH_LONG).show();
    }
}
