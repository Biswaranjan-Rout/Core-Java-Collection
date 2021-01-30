package com.fashion.amai.activity;

import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fashion.amai.R;
import com.fashion.amai.adapter.MerchantDetailsAdapter;
import com.fashion.amai.adapter.SelectSizeAdapter;
import com.fashion.amai.model.AddToCartPojo;
import com.fashion.amai.model.ClearSelectedDesignsPojo;
import com.fashion.amai.model.GetProductById;
import com.fashion.amai.model.WishlistPojo;
import com.fashion.amai.utils.AddToCartDBHelper;
import com.fashion.amai.utils.Constants;
import com.fashion.amai.utils.MenuConverter;
import com.fashion.amai.utils.MySharedPreferences;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

public class ActivityOurDesignsProductDetailsNew extends BaseActivity implements View.OnClickListener {
    private TextView tvWishlist,product_fabric_our_design_heading,product_occasion_our_design_heading, product_pack_of_our_design_heading, product_pattern_our_design_heading,product_type_our_design_heading, product_ideal_for_our_design_heading, product_suitable_for_our_design_heading, product_sleeve_our_design_heading, product_material_our_design_heading, product_category_our_design_heading, tvBookAppointment,layout_wishlist_textview, product_fabric_our_design, product_occasion_our_design, product_pack_of_our_design, product_type_our_design, product_pattern_our_design, product_suitable_for_our_design, product_ideal_for_our_design, product_sleeve_our_design, product_category_our_design,

    product_description, product_name,price_alert,design_cost, product_material;
    private LinearLayout layout_wishlist,layout_bag;
   // private RadioGroup radioGroup;
    private View activity_details_progressBar;
    private ViewPager viewPager;

    AddToCartDBHelper dbHelper;

    TextView size_measure_small, count_left_small, size_measure_medium, count_left_medium, size_measure_large, count_left_large, size_measure_extraLarge,count_left_extraLarge;

    LinearLayout size_select_small, size_select_medium, size_select_large, size_select_extraLarge;

    boolean size_measure_small_boolean = false;
    boolean size_measure_medium_boolean = false;
    boolean size_measure_large_boolean = false;
    boolean size_measure_xlarge_boolean = false;

   // private RecyclerView recyclerViewSelectSize;
    private SelectSizeAdapter adapterSelectSize;
    private boolean isWishlisted = false;

    private int cartcount;

    int cart_count = 0;
    private String product_id;
    private TabLayout tabLayout;
    private GetProductById.ResponseObject getPRoductById;
    private  String [] sizeArray = new String[]{"s", "m", "l", "xl"};
    private String selectedSize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_designs_product_details_new);


        layout_wishlist_textview = findViewById(R.id.layout_wishlist_textview);

        size_select_small = findViewById(R.id.size_select_small);
        size_select_medium = findViewById(R.id.size_select_medium);
        size_select_large = findViewById(R.id.size_select_large);
        size_select_extraLarge = findViewById(R.id.size_select_extraLarge);
        design_cost = findViewById(R.id.design_cost_our_design);

        size_measure_small =findViewById(R.id.size_measure_small);
        count_left_small =findViewById(R.id.count_left_small);
        size_measure_medium =findViewById(R.id.size_measure_medium);
        count_left_medium =findViewById(R.id.count_left_medium);
        size_measure_large =findViewById(R.id.size_measure_large);
        count_left_large =findViewById(R.id.count_left_large);
        size_measure_extraLarge =findViewById(R.id.size_measure_extraLarge);
        count_left_extraLarge =findViewById(R.id.count_left_extraLarge);

        price_alert =findViewById(R.id.price_alert);
        product_name =findViewById(R.id.product_name_our_design);
        product_description =findViewById(R.id.product_description_our_design);
        product_material =findViewById(R.id.product_material_our_design);
        product_material_our_design_heading =findViewById(R.id.product_material_our_design_heading);
        product_category_our_design_heading =findViewById(R.id.product_category_our_design_heading);
        product_category_our_design =findViewById(R.id.product_category_our_design);
        product_sleeve_our_design_heading =findViewById(R.id.product_sleeve_our_design_heading);
        product_sleeve_our_design =findViewById(R.id.product_sleeve_our_design);
        product_ideal_for_our_design =findViewById(R.id.product_ideal_for_our_design);
        product_ideal_for_our_design_heading =findViewById(R.id.product_ideal_for_our_design_heading);
        product_suitable_for_our_design_heading =findViewById(R.id.product_suitable_for_our_design_heading);
        product_suitable_for_our_design =findViewById(R.id.product_suitable_for_our_design);
        product_pattern_our_design_heading =findViewById(R.id.product_pattern_our_design_heading);
        product_pattern_our_design =findViewById(R.id.product_pattern_our_design);
        product_type_our_design_heading =findViewById(R.id.product_type_our_design_heading);
        product_type_our_design =findViewById(R.id.product_type_our_design);
        product_pack_of_our_design_heading =findViewById(R.id.product_pack_of_our_design_heading);
        product_pack_of_our_design =findViewById(R.id.product_pack_of_our_design);
        product_occasion_our_design_heading =findViewById(R.id.product_occasion_our_design_heading);
        product_occasion_our_design =findViewById(R.id.product_occasion_our_design);
        product_fabric_our_design_heading =findViewById(R.id.product_fabric_our_design_heading);
        product_fabric_our_design =findViewById(R.id.product_fabric_our_design);
        activity_details_progressBar =findViewById(R.id.activity_details_progressBar_our_design);
//        toolbar.setNavigationIcon(R.drawable.icon_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("AMAI");

        Intent intent = getIntent();
        product_id = intent.getStringExtra(Constants.PRODUCT_ID);
        //  imageUrl  = intent.getStringExtra(Constants.IMAGE_URL);
        //      Log.d("newURL**",imageUrl);

        if (MySharedPreferences.getCart(preferences)==null){
            cartcount = 0;
        } else {
            cartcount = Integer.parseInt(MySharedPreferences.getCart(preferences));

        }

        initViews();
        setOnClickListeners();


        getProductById();


        if(MySharedPreferences.getCart(preferences)!=null){
            cart_count = Integer.parseInt(MySharedPreferences.getCart(preferences));
        } else {
            cart_count = 0;
        }
    }

    private void getProductById() {

        retrofit2.Call<GetProductById.ResponseObject> call = productApiInterface.
                getProductById( product_id);
        call.enqueue(new retrofit2.Callback<GetProductById.ResponseObject>() {
            @Override
            public void onResponse(retrofit2.Call<GetProductById.ResponseObject> call, retrofit2.Response<GetProductById.ResponseObject> response) {
                if(response.code()==200){
                    handleResponse(response);

                }else if(response.code()==201){

                }else if(response.code()==401){

                }
            }

            @Override
            public void onFailure(retrofit2.Call<GetProductById.ResponseObject> call, Throwable t) {
                t.printStackTrace();

                //showToast(SOME_THING_WENT_WRONG);

            }
        });
    }

    private void handleResponse(Response<GetProductById.ResponseObject> response) {
        getPRoductById = response.body();

        viewPager.setAdapter(new MerchantDetailsAdapter(getApplicationContext(), getPRoductById.getData().getImages()));
        tabLayout.setupWithViewPager(viewPager, true);

        settingProfileParameter();

        design_cost.setText(String.valueOf("Rs. " + getPRoductById.getData().getDefaultPrice()));


        count_left_small.setText(String.valueOf(response.body().getData().getAvailableSizes().get("s") + " left"));
        count_left_medium.setText(String.valueOf(response.body().getData().getAvailableSizes().get("m") + " left"));
        count_left_large.setText(String.valueOf(response.body().getData().getAvailableSizes().get("l") + " left"));
        count_left_extraLarge.setText(String.valueOf(response.body().getData().getAvailableSizes().get("xl") + " left"));

        size_select_small.setOnClickListener(this);
        size_select_medium.setOnClickListener(this);
        size_select_large.setOnClickListener(this);
        size_select_extraLarge.setOnClickListener(this);

        if (getPRoductById.getData().isIs_wishlisted()){
            layout_wishlist_textview.setText("Remove from Wishlist");
        //    holder.add_to_wishlist.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.like_heart));
            isWishlisted = true;

        } else {
            layout_wishlist_textview.setText("Add To Wishlist");
          //  holder.add_to_wishlist.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.icon_heart));
            isWishlisted = false;
        }



       // JsonObject jsonObject = new JsonObject();
       // adapterSelectSize = new SelectSizeAdapter(getApplicationContext(), sizeArray, getPRoductById.getData().getAvailableSizes());
      /*  recyclerViewSelectSize.setHasFixedSize(true);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerViewSelectSize.setLayoutManager(LayoutManager);
        recyclerViewSelectSize.setAdapter(adapterSelectSize);

       */

        design_cost.setText(String.valueOf("Rs. " + getPRoductById.getData().getPrices().get("l")));

        size_measure_large_boolean = true;
        size_measure_large.setBackground(getResources().getDrawable(R.drawable.button_shape_alphabet_selected));
        size_measure_large.setTextColor(getResources().getColor(R.color.dotHighlight));

        activity_details_progressBar.setVisibility(View.GONE);
    }

    private void settingProfileParameter() {
        product_name.setText(getPRoductById.getData().getProductName());
        if (getPRoductById.getData().getMaterialDetail()!=null){
            product_material.setText(String.valueOf(getPRoductById.getData().getMaterialDetail()));
            product_material_our_design_heading.setVisibility(View.VISIBLE);

        } else {
            product_material_our_design_heading.setVisibility(View.GONE);
            product_material.setVisibility(View.GONE);
        }

        product_description.setText(String.valueOf(getPRoductById.getData().getDescription()));

        if(getPRoductById.getData().getFabric()!=null){
            product_fabric_our_design.setText(getPRoductById.getData().getFabric());
            product_fabric_our_design_heading.setVisibility(View.VISIBLE);
        } else {
            product_fabric_our_design.setVisibility(View.GONE);
            product_fabric_our_design_heading.setVisibility(View.GONE);
        }

        if(getPRoductById.getData().getOccasion()!=null){
            product_occasion_our_design.setText(getPRoductById.getData().getOccasion());
            product_occasion_our_design_heading.setVisibility(View.VISIBLE);
        } else {
            product_occasion_our_design.setVisibility(View.GONE);
            product_occasion_our_design_heading.setVisibility(View.GONE);
        }

        if(getPRoductById.getData().getPack_of()!=null){
            product_pack_of_our_design.setText(getPRoductById.getData().getPack_of());
            product_pack_of_our_design_heading.setVisibility(View.VISIBLE);
        } else {
            product_pack_of_our_design.setVisibility(View.GONE);
            product_pack_of_our_design_heading.setVisibility(View.GONE);
        }

        if(getPRoductById.getData().getType()!=null){
            product_type_our_design.setText(getPRoductById.getData().getType());
            product_type_our_design_heading.setVisibility(View.VISIBLE);
        } else {
            product_type_our_design_heading.setVisibility(View.GONE);
            product_type_our_design.setVisibility(View.GONE);
        }

        if(getPRoductById.getData().getPattern()!=null){
            product_pattern_our_design.setText(getPRoductById.getData().getPattern());
            product_pattern_our_design_heading.setVisibility(View.VISIBLE);
        } else {
            product_pattern_our_design_heading.setVisibility(View.GONE);
            product_pattern_our_design.setVisibility(View.GONE);
        }

        if(getPRoductById.getData().getSuitable_for()!=null){
            product_suitable_for_our_design.setText(getPRoductById.getData().getSuitable_for());
            product_suitable_for_our_design_heading.setVisibility(View.VISIBLE);
        } else {
             product_suitable_for_our_design_heading.setVisibility(View.GONE);
            product_suitable_for_our_design.setVisibility(View.GONE);
        }

        if(getPRoductById.getData().getIdeal_for()!=null){
            product_ideal_for_our_design.setText(getPRoductById.getData().getIdeal_for());
            product_ideal_for_our_design_heading.setVisibility(View.VISIBLE);
        } else {
            product_ideal_for_our_design_heading.setVisibility(View.GONE);
            product_ideal_for_our_design.setVisibility(View.GONE);
        }

        if(getPRoductById.getData().getCategory()!=null){
            product_category_our_design.setText(getPRoductById.getData().getCategory());
            product_category_our_design_heading.setVisibility(View.VISIBLE);
        } else {
            product_category_our_design.setVisibility(View.GONE);
            product_category_our_design_heading.setVisibility(View.GONE);
        }

        if(getPRoductById.getData().getSleeve()!=null){
            product_sleeve_our_design.setText(getPRoductById.getData().getSleeve());
            product_sleeve_our_design_heading.setVisibility(View.VISIBLE);
        } else {
            product_sleeve_our_design_heading.setVisibility(View.GONE);
            product_sleeve_our_design.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        // ivBack = findViewById(R.id.iv_back);
        tvBookAppointment = findViewById(R.id.tv_book_appointment);
        layout_wishlist = findViewById(R.id.layout_wishlist);
        layout_bag = findViewById(R.id.layout_bag);
      //  radioGroup = findViewById(R.id.radio_group);
        tabLayout =  findViewById(R.id.tabDots_our_design);

        viewPager = findViewById(R.id.view_page_designer_our_design);

      /*  radioGroup.setVisibility(View.GONE);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_btn_book_call) {
                Intent intentActivityTimeSlot = new Intent(ActivityOurDesignsProductDetailsNew.this, ActivitySchedule.class);
                intentActivityTimeSlot.putExtra("radio","bookCall");
                startActivity(intentActivityTimeSlot);
            } else if (checkedId == R.id.radio_btn_book_appointment) {
                Intent intentActivityTimeSlot = new Intent(ActivityOurDesignsProductDetailsNew.this, ActivitySelectAddress.class);
                intentActivityTimeSlot.putExtra("radio","bookAppointment");
                startActivity(intentActivityTimeSlot);                }
        });

       */

      //  recyclerViewSelectSize = findViewById(R.id.recycler_select_size);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        invalidateOptionsMenu();
        getMenuInflater().inflate(R.menu.top_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.shopping_nav);

        int cart = 0;

        if (MySharedPreferences.getCart(preferences)==null){
            cart = 0;
        } else {
            cart = Integer.parseInt(MySharedPreferences.getCart(preferences));
        }


        menuItem.setIcon(MenuConverter.convertLayoutToImage(getBaseContext(),cartcount,R.drawable.ic_action_shopping_bag));

        return true;
    }

    private void setOnClickListeners() {
        // ivBack.setOnClickListener(this);
        layout_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isWishlisted){

                    layout_wishlist_textview.setText("Add To Wishlist");

                    //   unselectDesign(dataSet.get(position).getId().toString());
                    isWishlisted = false;

                    deleteWishList(Constants.PRODUCT, Integer.parseInt(getPRoductById.getData().getProductId()));

                    //holder.add_to_wishlist.setImageDrawable(view.getResources().getDrawable(R.drawable.icon_heart, null));
                } else {
                    isWishlisted = true;
                    layout_wishlist_textview.setText("Remove From Wishlist");

                    JsonObject design = new JsonObject();
                    try {
                        design.addProperty("item_type", "product");
                        design.addProperty("item_id", getPRoductById.getData().getProductId());

                    } catch (JsonIOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    addToWishlist(design);
                   // holder.add_to_wishlist.setImageDrawable(view.getResources().getDrawable(R.drawable.like_heart, null));

                }

            }
        });
        layout_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String product_name, String price_add_to_cart, String size, String price, int product_id, String product_url
            /*    String name = getPRoductById.getData().getProductName();
                String price_add_to_cart = "1";
                String size = getPRoductById.getData().getDefaultSize();
                String image = getPRoductById.getData().getImages().get(0);
                dbHelper = new AddToCartDBHelper(ActivityOurDesignsProductDetailsNew.this, preferences);
                //create new person
                //String product_name, String price_add_to_cart, String size, String price, int product_id, String product_url
                AddToCartProduct person = new AddToCartProduct(name, price_add_to_cart, size,1200,1, image);
                dbHelper.saveNewPerson(person);

             */

            sendProductDetailsOnline();


            }
        });
    }



    /*
    {
	"product_id" : 90,
	"product_quantity":1,
	"product_size":"l"
}
     */

    private void sendProductDetailsOnline() {
        checkSize();
        JsonObject design = new JsonObject();


        try {
            design.addProperty("product_id", Integer.parseInt(product_id));
            design.addProperty("product_quantity", 1);
            design.addProperty("product_size", selectedSize);


        } catch (JsonIOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        retrofit2.Call<AddToCartPojo> call = merchantApiInterface.addToCart(design);
        call.enqueue(new retrofit2.Callback<AddToCartPojo>() {
            @Override
            public void onResponse(retrofit2.Call<AddToCartPojo> call, retrofit2.Response<AddToCartPojo> response) {
                try{
                    if(response.code()==200){
                        // listMerchantProductById = response.body().getData();

                        cartcount++;
                        invalidateOptionsMenu();
                        Log.d("*****","Here in block 2");
                        return;

                    }else if(response.code()==409){

                        getErrorResponse(response, ActivityOurDesignsProductDetailsNew.this);

                        // product_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                        return;
                    }else if(response.code()==201){
                        //  showToast(SOME_THING_WENT_WRONG);
                        successMessage(response.body());
                        cartcount++;
                        invalidateOptionsMenu();
                        //  product_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    // product_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddToCartPojo> call, Throwable t) {
                t.printStackTrace();
                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });

    }

    private void successMessage(AddToCartPojo addToCartPojo) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityOurDesignsProductDetailsNew.this);
        alertDialogBuilder.setTitle("Success");
        alertDialogBuilder.setMessage(addToCartPojo.getMessage());
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.show();
    }


    private void deleteWishList(String type, int id) {

        retrofit2.Call<ClearSelectedDesignsPojo> call = merchantApiInterface.deleteWishList(type, id);
        call.enqueue(new retrofit2.Callback<ClearSelectedDesignsPojo>() {
            @Override
            public void onResponse(retrofit2.Call<ClearSelectedDesignsPojo> call, retrofit2.Response<ClearSelectedDesignsPojo> response) {
                try{
                    if(response.code()==200){
                        // listMerchantProductById = response.body().getData();


                        Log.d("*****","Here in block 2");

                    }else if(response.code()==201){
                        // product_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        //  showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        //  product_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    // product_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ClearSelectedDesignsPojo> call, Throwable t) {
                t.printStackTrace();
                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }


    private void addToWishlist(JsonObject wishlist) {
        retrofit2.Call<WishlistPojo> call = merchantApiInterface.addToWishlist(wishlist);
        call.enqueue(new retrofit2.Callback<WishlistPojo>() {
            @Override
            public void onResponse(retrofit2.Call<WishlistPojo> call, retrofit2.Response<WishlistPojo> response) {
                try{
                    if(response.code()==200){
                        // listMerchantProductById = response.body().getData();
                        Log.d("*****","Here in block 2");


                       // isWishlisted = true;

                        //select_design_icon
                    }
                    else if(response.code()==201){
                        // product_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        // showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        //product_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    //product_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WishlistPojo> call, Throwable t) {
                t.printStackTrace();
                // showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    private void uncheckAll(){
        if (size_measure_small_boolean){
            size_measure_small_boolean = false;
            size_measure_small.setBackground(getResources().getDrawable(R.drawable.button_shape_alphabet));
            size_measure_small.setTextColor(getResources().getColor(R.color.colorBlack));
          //  count_left_small.setTextColor(getResources().getColor(R.color.colorBlack));
        } else if (size_measure_medium_boolean){
            size_measure_medium_boolean = false;
            size_measure_medium.setBackground(getResources().getDrawable(R.drawable.button_shape_alphabet));
            size_measure_medium.setTextColor(getResources().getColor(R.color.colorBlack));
       //     count_left_medium.setTextColor(getResources().getColor(R.color.colorBlack));
        } else if (size_measure_large_boolean){
            size_measure_large_boolean = false;
            size_measure_large.setBackground(getResources().getDrawable(R.drawable.button_shape_alphabet));
            size_measure_large.setTextColor(getResources().getColor(R.color.colorBlack));
         //   count_left_large.setTextColor(getResources().getColor(R.color.colorBlack));
        } else if (size_measure_xlarge_boolean){
            size_measure_xlarge_boolean = false;
            size_measure_extraLarge.setBackground(getResources().getDrawable(R.drawable.button_shape_alphabet));
            size_measure_extraLarge.setTextColor(getResources().getColor(R.color.colorBlack));
         //   count_left_extraLarge.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }


    private void checkSize(){
        if (size_measure_small_boolean){
            selectedSize = "s";
        } else if (size_measure_medium_boolean){
            selectedSize = "m";
        } else if (size_measure_large_boolean) {
            selectedSize = "l";
        }
            else if (size_measure_xlarge_boolean){
            selectedSize = "xl";
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.size_select_small:
                uncheckAll();
                design_cost.setText(String.valueOf("Rs. " + getPRoductById.getData().getPrices().get("s")));

                size_measure_small_boolean = true;
                size_measure_small.setBackground(getResources().getDrawable(R.drawable.button_shape_alphabet_selected));
                size_measure_small.setTextColor(getResources().getColor(R.color.dotHighlight));

                break;
            case R.id.size_select_medium:
                uncheckAll();
                design_cost.setText(String.valueOf("Rs. " + getPRoductById.getData().getPrices().get("m")));

                size_measure_medium_boolean = true;
                size_measure_medium.setBackground(getResources().getDrawable(R.drawable.button_shape_alphabet_selected));
                size_measure_medium.setTextColor(getResources().getColor(R.color.dotHighlight));

                break;
            case R.id.size_select_large:
                uncheckAll();
                design_cost.setText(String.valueOf("Rs. " + getPRoductById.getData().getPrices().get("l")));

                size_measure_large_boolean = true;
                size_measure_large.setBackground(getResources().getDrawable(R.drawable.button_shape_alphabet_selected));
                size_measure_large.setTextColor(getResources().getColor(R.color.dotHighlight));

                break;
            case R.id.size_select_extraLarge:
                uncheckAll();
                design_cost.setText(String.valueOf("Rs. " + getPRoductById.getData().getPrices().get("xl")));

                size_measure_xlarge_boolean = true;
                size_measure_extraLarge.setBackground(getResources().getDrawable(R.drawable.button_shape_alphabet_selected));
                size_measure_extraLarge.setTextColor(getResources().getColor(R.color.dotHighlight));

                break;
        }
    }
}


