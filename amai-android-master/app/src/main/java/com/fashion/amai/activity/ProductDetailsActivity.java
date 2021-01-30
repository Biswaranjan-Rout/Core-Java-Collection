package com.fashion.amai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.fashion.amai.adapter.MerchantDetailsAdapter;
import com.fashion.amai.R;
import com.fashion.amai.model.GetProductById;
import com.fashion.amai.model.SelectDesignPojo;
import com.fashion.amai.model.UnSelectPojo;
import com.fashion.amai.utils.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class ProductDetailsActivity extends BaseActivity implements View.OnClickListener {
   // private ImageView ivBack;
    private TextView tvWishlist, layout_wishlist_textview, product_fabric_heading, product_occasion_heading, product_pack_of_heading, product_type_heading, product_pattern_heading, product_suitable_for_heading, product_ideal_for_heading, product_sleeve_heading, product_category_heading, product_material_heading, product_details_heading, product_material,product_fabric, product_occasion, product_pack_of, product_type, product_pattern, product_suitable_for, product_ideal_for, product_category,product_sleeve, tvBookAppointment,product_description, product_name,price_alert,design_cost;
   // private RadioGroup radioGroup;
    private View activity_details_progressBar;
    private ViewPager viewPager;
    private LinearLayout select_this_product, add_product_bottom_layout;
    private LinearLayout layout_remove_product, layout_add_more_products;
    //private CardView add_product_bottom_layout, product_details_select;
    //private RecyclerView recyclerViewSelectSize;
    // private SelectSizeAdapter adapterSelectSize;
    private String product_id;
    private TabLayout tabLayout;
    private GetProductById.ResponseObject getPRoductById;
    private  String [] sizeArray = new String[]{"S", "M", "L", "XL", "XXL"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_details);
        design_cost = findViewById(R.id.design_cost);
        price_alert =findViewById(R.id.price_alert);
        layout_wishlist_textview =findViewById(R.id.layout_wishlist_textview);
        layout_remove_product =findViewById(R.id.layout_remove_product);
        layout_add_more_products =findViewById(R.id.layout_add_more_products);
        add_product_bottom_layout =findViewById(R.id.add_product_bottom_layout);
        add_product_bottom_layout =findViewById(R.id.add_product_bottom_layout);
        product_details_heading =findViewById(R.id.product_details_heading);
        product_name =findViewById(R.id.product_name);
        product_suitable_for_heading =findViewById(R.id.product_suitable_for_heading);
        product_pack_of_heading =findViewById(R.id.product_pack_of_heading);
        product_fabric_heading =findViewById(R.id.product_fabric_heading);
        product_occasion_heading =findViewById(R.id.product_occasion_heading);
        product_pattern_heading =findViewById(R.id.product_pattern_heading);
        product_type_heading =findViewById(R.id.product_type_heading);
        product_category_heading =findViewById(R.id.product_category_heading);
        product_material_heading =findViewById(R.id.product_material_heading);
        product_sleeve_heading =findViewById(R.id.product_sleeve_heading);
        product_ideal_for_heading =findViewById(R.id.product_ideal_for_heading);
        product_description =findViewById(R.id.product_details);
        product_category =findViewById(R.id.product_category);
        product_suitable_for =findViewById(R.id.product_suitable_for);
        product_pack_of =findViewById(R.id.product_pack_of);
        select_this_product =findViewById(R.id.select_this_product);
        product_fabric =findViewById(R.id.product_fabric);
        product_occasion =findViewById(R.id.product_occasion);
        product_type =findViewById(R.id.product_type);
        product_pattern =findViewById(R.id.product_pattern);
        product_ideal_for =findViewById(R.id.product_ideal_for);
        product_sleeve =findViewById(R.id.product_sleeve);
        product_material =findViewById(R.id.product_material);
        activity_details_progressBar =findViewById(R.id.activity_details_progressBar);

       // add_product_bottom_layout.setVisibility(View.GONE);
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


        initViews();
        setOnClickListeners();

        getProductById();
    }

    private void getProductById() {

        retrofit2.Call<GetProductById.ResponseObject> call = productApiInterface.
                getProductById( product_id);
        call.enqueue(new retrofit2.Callback<GetProductById.ResponseObject>() {
            @Override
            public void onResponse(retrofit2.Call<GetProductById.ResponseObject> call, retrofit2.Response<GetProductById.ResponseObject> response) {
                if(response.code()==200){
                    getPRoductById = response.body();

                    viewPager.setAdapter(new MerchantDetailsAdapter(getApplicationContext(), getPRoductById.getData().getImages()));
                    tabLayout.setupWithViewPager(viewPager, true);

                    if (getPRoductById.getData().isIs_selected()){
                        add_product_bottom_layout.setVisibility(View.VISIBLE);
                        select_this_product.setVisibility(View.INVISIBLE);

                    } else {
                        select_this_product.setVisibility(View.VISIBLE);
                        add_product_bottom_layout.setVisibility(View.INVISIBLE);

                    }

                    product_name.setText(getPRoductById.getData().getProductName());
                    design_cost.setText(String.valueOf(getPRoductById.getData().getDefaultPrice()));

                    if (getPRoductById.getData().getMaterialDetail()!=null){
                        product_material.setText(String.valueOf(getPRoductById.getData().getMaterialDetail()));

                        product_material_heading.setVisibility(View.VISIBLE);
                    } else{
                        product_material.setVisibility(View.GONE);

                        product_material_heading.setVisibility(View.GONE);
                    }

                    if (getPRoductById.getData().getDescription()!=null){
                        product_description.setText(String.valueOf(getPRoductById.getData().getDescription()));
                        product_details_heading.setVisibility(View.VISIBLE);
                    } else {
                        product_description.setVisibility(View.GONE);
                        product_details_heading.setVisibility(View.GONE);
                    }


                    if(getPRoductById.getData().getFabric()!=null){
                        product_fabric.setText(getPRoductById.getData().getFabric());
                        product_fabric_heading.setVisibility(View.VISIBLE);
                    } else {
                        product_fabric.setVisibility(View.GONE);
                        product_fabric_heading.setVisibility(View.GONE);
                    }

                    if(getPRoductById.getData().getOccasion()!=null){
                        product_occasion.setText(getPRoductById.getData().getOccasion());
                        product_occasion_heading.setVisibility(View.VISIBLE);
                    } else {
                        product_occasion.setVisibility(View.GONE);
                        product_occasion_heading.setVisibility(View.GONE);
                    }

                    if(getPRoductById.getData().getPack_of()!=null){
                        product_pack_of.setText(getPRoductById.getData().getPack_of());
                        product_pack_of_heading.setVisibility(View.VISIBLE);
                    } else {
                        product_pack_of.setVisibility(View.GONE);
                        product_pack_of_heading.setVisibility(View.GONE);
                    }

                    if(getPRoductById.getData().getType()!=null){
                        product_type.setText(getPRoductById.getData().getType());
                        product_type_heading.setVisibility(View.VISIBLE);
                    } else{
                        product_type.setVisibility(View.GONE);
                        product_type_heading.setVisibility(View.GONE);
                    }

                    if(getPRoductById.getData().getPattern()!=null){
                        product_pattern.setText(getPRoductById.getData().getPattern());
                        product_pattern_heading.setVisibility(View.VISIBLE);
                    } else {
                        product_pattern.setVisibility(View.GONE);
                        product_pattern_heading.setVisibility(View.GONE);
                    }

                    if(getPRoductById.getData().getSuitable_for()!=null){
                        product_suitable_for.setText(getPRoductById.getData().getSuitable_for());
                        product_suitable_for_heading.setVisibility(View.VISIBLE);
                    } else {
                        product_suitable_for.setVisibility(View.GONE);
                        product_suitable_for_heading.setVisibility(View.GONE);
                    }

                    if(getPRoductById.getData().getIdeal_for()!=null){
                        product_ideal_for.setText(getPRoductById.getData().getIdeal_for());
                        product_ideal_for_heading.setVisibility(View.VISIBLE);
                    } else {
                        product_ideal_for.setVisibility(View.GONE);
                        product_ideal_for_heading.setVisibility(View.GONE);
                    }

                    if(getPRoductById.getData().getCategory()!=null){
                        product_category.setText(getPRoductById.getData().getCategory());
                        product_category_heading.setVisibility(View.VISIBLE);
                    } else {
                        product_category.setVisibility(View.GONE);
                        product_category_heading.setVisibility(View.GONE);
                    }

                    if(getPRoductById.getData().getSleeve()!=null){
                        product_sleeve.setText(getPRoductById.getData().getSleeve());
                        product_sleeve_heading.setVisibility(View.VISIBLE);
                    } else {
                        product_sleeve.setVisibility(View.GONE);
                        product_sleeve_heading.setVisibility(View.GONE);
                    }

                    activity_details_progressBar.setVisibility(View.GONE);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_menu, menu);
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

            Toast.makeText(ProductDetailsActivity.this, "Search", Toast.LENGTH_SHORT).show();
            // Do something
            return true;
        }
        if (id == R.id.notify_nav) {

            Toast.makeText(ProductDetailsActivity.this, "Notify", Toast.LENGTH_SHORT).show();

            // Do something
            return true;
        }
        if (id == R.id.like_nav) {

            Toast.makeText(ProductDetailsActivity.this, "Like", Toast.LENGTH_SHORT).show();

            // Do something
            return true;
        }
        if (id == R.id.shopping_nav) {

            Toast.makeText(ProductDetailsActivity.this, "Shopping", Toast.LENGTH_SHORT).show();

            // Do something
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        // ivBack = findViewById(R.id.iv_back);
        tvBookAppointment = findViewById(R.id.tv_book_appointment);
   //     cardViewButtons = findViewById(R.id.layout_gender);
       // radioGroup = findViewById(R.id.radio_group);
        tabLayout =  findViewById(R.id.tabDots);

        viewPager = findViewById(R.id.view_page_designer);

       /* radioGroup.setVisibility(View.GONE);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
             if (checkedId == R.id.radio_btn_book_call) {
                Intent intentActivityTimeSlot = new Intent(ProductDetailsActivity.this, ActivitySchedule.class);
                intentActivityTimeSlot.putExtra("radio","bookCall");
                startActivity(intentActivityTimeSlot);
            } else if (checkedId == R.id.radio_btn_book_appointment) {
                Intent intentActivityTimeSlot = new Intent(ProductDetailsActivity.this, ActivitySelectAddress.class);
                intentActivityTimeSlot.putExtra("radio","bookAppointment");
                startActivity(intentActivityTimeSlot);                }
        });

        */

        /* recyclerViewSelectSize = findViewById(R.id.recycler_select_size);
        adapterSelectSize = new SelectSizeAdapter(getApplicationContext(), sizeArray);
        recyclerViewSelectSize.setHasFixedSize(true);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, true);
        recyclerViewSelectSize.setLayoutManager(LayoutManager);
        recyclerViewSelectSize.setAdapter(adapterSelectSize);

         */
    }

    private void setOnClickListeners() {
        // ivBack.setOnClickListener(this);
        tvBookAppointment.setOnClickListener(this);
        select_this_product.setOnClickListener(this);
        layout_add_more_products.setOnClickListener(this);
        layout_remove_product.setOnClickListener(this);
     //   cardViewButtons.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.layout_remove_product: {

                activity_details_progressBar.setVisibility(View.VISIBLE);
                unselectDesign(product_id);
                layout_wishlist_textview.setText("Item Removed");


                break;
            }
            case R.id.layout_add_more_products: {

                finish();

                break;
            }
            case R.id.tv_book_appointment: {
                /*cardViewButtons.setVisibility(View.GONE);
                radioGroup.setVisibility(View.VISIBLE);*/
                break;
            }
            case R.id.select_this_product: {


                activity_details_progressBar.setVisibility(View.VISIBLE);


                JsonObject design = new JsonObject();

                try {
                    design.addProperty("product_id", Integer.parseInt(product_id));

                } catch (JsonIOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                selectDesign(design);

                break;
            }
        }
    }

    private void unselectDesign(String productId) {
        retrofit2.Call<UnSelectPojo> call = merchantApiInterface.unselectproduct(String.valueOf(productId));
        call.enqueue(new retrofit2.Callback<UnSelectPojo>() {
            @Override
            public void onResponse(retrofit2.Call<UnSelectPojo> call, retrofit2.Response<UnSelectPojo> response) {

                activity_details_progressBar.setVisibility(View.GONE);

                try{
                    if(response.code()==200){
                        activity_details_progressBar.setVisibility(View.GONE);

                        // listMerchantProductById = response.body().getData();
                        Log.d("*****","Here in block 2");

                        //select_design_icon
                    }
                    else if(response.code()==201){
                        activity_details_progressBar.setVisibility(View.GONE);

                        // product_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        activity_details_progressBar.setVisibility(View.GONE);

                        // showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        //product_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    activity_details_progressBar.setVisibility(View.GONE);

                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    //product_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UnSelectPojo> call, Throwable t) {
                t.printStackTrace();
                activity_details_progressBar.setVisibility(View.GONE);

                // showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    public void showToast(String toastMessage) {
        Toast.makeText(ProductDetailsActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    private void selectDesign(JsonObject design) {

        retrofit2.Call<SelectDesignPojo> call = merchantApiInterface.selectDesign( design);
        call.enqueue(new retrofit2.Callback<SelectDesignPojo>() {
            @Override
            public void onResponse(retrofit2.Call<SelectDesignPojo> call, retrofit2.Response<SelectDesignPojo> response) {

              //  Log.d("Design Error", retrofit2.Response.error(400))
                try{
                    if(response.code()==200){

                        Log.d("*****","Here in block 2");


                    }else if(response.code()==201){

                        SelectDesignPojo.DesignData selectDesignPojo = response.body().getData();
                        Log.d("*****","Here in block 2");

                        Intent intent=new Intent();
                        intent.putExtra("MESSAGE","MESSAGE");
                        setResult(1,intent);
                        finish();


                        activity_details_progressBar.setVisibility(View.GONE);
                     /*   if (selectDesignPojo!=null){
                            new AlertDialog.Builder(ProductDetailsActivity.this)
                                    .setTitle("Successfully Added")
                                    .setMessage("You have successfully selected this Design")
                                    .setCancelable(true)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            finish();
                                        }
                                    })
                                    .show();



                        } */

                    }else if(response.code()==401){
                     //   showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        activity_details_progressBar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                   // showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    activity_details_progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SelectDesignPojo> call, Throwable t) {
                t.printStackTrace();
                activity_details_progressBar.setVisibility(View.GONE);
                //showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }
}
