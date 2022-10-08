package com.microlan.rushhandingoffline.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.microlan.rushhandingoffline.Activities.Counters;
import com.microlan.rushhandingoffline.Dialogs.AddQuentity;
import com.microlan.rushhandingoffline.Dialogs.CustomFilterDialog;
import com.microlan.rushhandingoffline.Dialogs.ProductInfo;
import com.microlan.rushhandingoffline.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductCounterAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> ProductID, ProductName, ProductPrice, ProductImage, ProductQuan;

    AddQuentity addQuentity;
    ProgressDialog progressDialog;
    String Cartimage,PacK_price,PacK_unit,Pack_size,userID;
    String SizeStat,ColorStat;
    RequestQueue requestQueue;
    CustomFilterDialog customFilterDialog;
    ProductInfo productinfo;
    String Size="0",Colour="0",PAcksize="0",Packsizestatus="0";
    ArrayList<String> SizeID, SizeName, ColorID, ColorName, ColorCode,ImageString,Packsize,PackPrice,PackMrp,PackUnit ;
     Button add_tocart;
    public ProductCounterAdapter(Context context1, ArrayList<String> productID, ArrayList<String> productName, ArrayList<String> productPrice,
                                 ArrayList<String> productImage, ArrayList<String> productQuan, String userID)
    {
        this.context = context1;
        this.ProductID = productID;
        this.ProductName = productName;
        this.ProductPrice = productPrice;
        this.ProductImage = productImage;
        this.ProductQuan = productQuan;
        this.userID = userID;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.single_grid_item_product_counter, null);
            //holder = new RecyclerView.ViewHolder();
        } else {
            gridView = (View) convertView;
        }
        requestQueue = Volley.newRequestQueue(context);

        TextView ProductNameText = (TextView)gridView.findViewById(R.id.productNameText);
        TextView ProductPriceText = (TextView)gridView.findViewById(R.id.productPriceText);
        ImageView ProductImageView = (ImageView)gridView.findViewById(R.id.productImage);
        ImageView info = (ImageView)gridView.findViewById(R.id.info);
        TextView AddProductToCartBtn = (TextView)gridView.findViewById(R.id.addBtn);
        customFilterDialog = new CustomFilterDialog(context, SizeName, ColorName);

        if (ProductImage.get(position).equals("")){

        } else {
            Picasso.get().load(ProductImage.get(position)).into(ProductImageView);
        }
        ProductNameText.setText(ProductName.get(position));
        ProductPriceText.setText(ProductPrice.get(position));

        addQuentity = new AddQuentity((Activity) context);
        productinfo = new ProductInfo((Activity) context);


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    SizeID.clear();
                    SizeName.clear();
                    Packsize.clear();
                    ColorID.clear();
                    ColorName.clear();
                    ColorCode.clear();
                    ImageString.clear();
                    Packsize.clear();
                    PackMrp.clear();
                    PackPrice.clear();
                    PackUnit.clear();
                    Packsizestatus="0";
                    Log.d("gdgf","dgfdg"+ProductID);
                    //Toast.makeText(Order_Details.this, UserID, Toast.LENGTH_SHORT).show();

                    // String insertUrl = "https://urban-mart.in/api/getProduct";
                    String insertUrl = "http://microlanpos.com/demo/api2/getProduct";
                    StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.cancel();
                            System.out.println(response);
                            String finalResponse = response.substring(1);
                            System.out.println(finalResponse);
                            //Toast.makeText(Registration.this, response, Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                String stat = jsonObj.getString("status");
                                String RelatedStat = jsonObj.getString("rel_status");
                                SizeStat = jsonObj.getString("size_status");
                                ColorStat = jsonObj.getString("color_status");
                                //   String price = jsonObj.getString("price");
                                //String msg = jsonObj.getString("msg");
                                if (stat.equals("1")){


                                    productinfo.show();


                                    final TextView name = (TextView)productinfo.findViewById(R.id.name);
                                  //  final TextView name = (TextView)productinfo.findViewById(R.id.name);
                                    final TextView prices = (TextView)productinfo.findViewById(R.id.price);
                                    final TextView desc = (TextView)productinfo.findViewById(R.id.desc);
                                    final TextView cond = (TextView)productinfo.findViewById(R.id.cond);
                                    final TextView sizes = (TextView)productinfo.findViewById(R.id.size);
                                    final ImageView image = (ImageView) productinfo.findViewById(R.id.image);
                                  //   add_tocart = (Button) productinfo.findViewById(R.id.add_tocarts);


                                    JSONArray details = jsonObj.getJSONArray("products");
                                    for (int i = 0; i < details.length(); i++) {
                                        JSONObject jsonObject = details.getJSONObject(i);
                                        String productIDString = jsonObject.getString("product_id");
                                        String productNameString = jsonObject.getString("product_name");
                                        String description = jsonObject.getString("description");
                                        String terms_conditions = jsonObject.getString("terms_conditions");
/*
                                        String productImageString = jsonObject.getString("image_name");
                                        String productPriceString = jsonObject.getString("price");
*/

                                        //Toast.makeText(MainActivity.this, categoriesImagePathString, Toast.LENGTH_SHORT).show();

                                   Log.d("","productIDString  "+productNameString);
                                   name.setText("Name : "+productNameString);
                                        desc.setText("Description  : "+description);
                                        cond.setText("Terms & Conditions : "+terms_conditions);
                                        Picasso.get().load(ProductImage.get(position)).placeholder(R.drawable.ic_menu_gallery).into(image);

                                    }

                                    if(details.length()==0)
                                    {
                                        Log.d("sadsdf","adsfs"+details.length());
                                    }

                                    for (int i = 0; i < details.length(); i++) {
                                        JSONObject jsonObject = details.getJSONObject(i);
                                        JSONArray Image = jsonObject.getJSONArray("product_image");
                                        Log.d("dgdgdg","fdfdf"+Image);
                                        JSONArray packsize = jsonObject.getJSONArray("product_price_data");

                                        if(packsize.length()==0){
                                            Packsizestatus="0";
                                           // packsizeText.setVisibility(View.GONE);
                                        }
                                        else {
                                            Log.d("dgdgdg","fdfdf"+packsize);

                                            // productimg.setVisibility(View.VISIBLE);
                                            for (int j = 0; j <packsize.length(); j++) {
                                                Log.d("ColorArray","ColorArray"+packsize);

                                                Packsizestatus="1";
                                                JSONObject jsonObject3 = packsize.getJSONObject(j);

                                                String size = jsonObject3.getString("pack_size");
                                                String price = jsonObject3.getString("price");
                                                String Mrp = jsonObject3.getString("Mrp");
                                                String unit_name = jsonObject3.getString("unit_name");
                                                PackMrp.add(Mrp);
                                                PackPrice.add(price);
                                                Packsize.add(size);
                                                PackUnit.add(unit_name);
                                                Log.d("sdsdsd","dsdsd"+Packsize);
                                                prices.setText("Product Cost : "+PackPrice);
                                                sizes.setText("Pack Size : "+Packsize);
                                                Log.d("dfdfd","sfddfdf"+PacK_price);

                                            }

                                          //  packsizerec.setAdapter(new PackSizeAdapter());

                                        }


                                        if (SizeStat.equals("1")) {
                                            JSONArray SizesArray = jsonObject.getJSONArray("sizes");

                                            Log.d("SizesArray","SizesArray"+SizesArray);
                                            for (int j = 0; j < SizesArray.length(); j++) {
                                                JSONObject jsonObject1 = SizesArray.getJSONObject(j);
                                                String SizeIDString = jsonObject1.getString("size_id");
                                                String SizeNameString = jsonObject1.getString("size");

                                                Log.d("fdfdf","fdfdf"+SizeNameString);
                                                SizeID.add(SizeIDString);
                                                SizeName.add(SizeNameString);
                                             //   SizeRecy.setAdapter(new SizeListAdapter());

                                            }
                                        } else {
                                          //  SizeDisplayText.setVisibility(View.GONE);
                                        }

                                        if (ColorStat.equals("1")) {
                                            JSONArray ColorArray = jsonObject.getJSONArray("colors");
                                            Log.d("ColorArray","ColorArray"+ColorArray);

                                            for (int k = 0; k < ColorArray.length(); k++) {
                                                JSONObject jsonObject2 = ColorArray.getJSONObject(k);
                                                String ColorIDString = jsonObject2.getString("color_id");
                                                String ColorNameString = jsonObject2.getString("color_name");
                                                String ColorCodeString = jsonObject2.getString("color_code");
                                                Log.d("sdsdsdsds","ColorNameString"+ColorNameString);

                                                ColorID.add(ColorIDString);
                                                ColorName.add(ColorNameString);
                                                Log.d("sdsdsdsds","dssdsds"+ColorName);
                                                ColorCode.add(ColorCodeString);
                                               // ColorRecy.setAdapter(new ColorAdapter());

                                            }
                                        } else {
                                          //  ColorDisplayText.setVisibility(View.GONE);
                                        }
                                    }






                                } else {

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                //Toast.makeText(PostNewAgreementProperty.this, e.toString(), Toast.LENGTH_SHORT).show();
                                System.out.print(e.toString());
                            }
                            //messageAdapter.notifyDataSetChanged();
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                            System.out.print(error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("product_id", ProductID.get(position));
                            parameters.put("user_id", userID);
                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                }


            }
        });

        SizeID = new ArrayList<String>();
        SizeName = new ArrayList<String>();
        ColorID = new ArrayList<String>();
        ColorName = new ArrayList<String>();
        ColorCode = new ArrayList<String>();
        ImageString = new ArrayList<String>();


        Packsize = new ArrayList<String>();
        PackMrp = new ArrayList<String>();
        PackPrice = new ArrayList<String>();
        PackUnit = new ArrayList<String>();

        ProductImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(false);
                    //progressDialog.setMax(100);
                    progressDialog.show();

                    SizeID.clear();
                    SizeName.clear();
                    Packsize.clear();
                    ColorID.clear();
                    ColorName.clear();
                    ColorCode.clear();
                    ImageString.clear();
                    Packsize.clear();
                    PackMrp.clear();
                    PackPrice.clear();
                    PackUnit.clear();
                    Packsizestatus="0";
                    Log.d("gdgf","dgfdg"+ProductID);
                    //Toast.makeText(Order_Details.this, UserID, Toast.LENGTH_SHORT).show();

                    // String insertUrl = "https://urban-mart.in/api/getProduct";
                    String insertUrl = "http://microlanpos.com/demo/api2/getProduct";
                    StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.cancel();
                            System.out.println(response);
                            String finalResponse = response.substring(1);
                            System.out.println(finalResponse);
                            //Toast.makeText(Registration.this, response, Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                String stat = jsonObj.getString("status");
                                String RelatedStat = jsonObj.getString("rel_status");
                                SizeStat = jsonObj.getString("size_status");
                                ColorStat = jsonObj.getString("color_status");
                                //   String price = jsonObj.getString("price");
                                //String msg = jsonObj.getString("msg");
                                if (stat.equals("1")){


                                    customFilterDialog.show();
                                    final EditText QuantityEdit = (EditText) customFilterDialog.findViewById(R.id.quantityEdit);

                                    final TextView SizeDisplayText = (TextView)customFilterDialog.findViewById(R.id.sizeText);
                                    final TextView ColorDisplayText = (TextView)customFilterDialog.findViewById(R.id.colorText);
                                    final TextView packsizeText = (TextView)customFilterDialog.findViewById(R.id.packsizeText);
                                    final TextView ReduceQuanImage = (TextView) customFilterDialog.findViewById(R.id.minusBtn);
                                    final TextView IncreaseQuanImage = (TextView) customFilterDialog.findViewById(R.id.plusBtn);
                                    final Button saveButton = (Button) customFilterDialog.findViewById(R.id.saveButton);
                                    final RecyclerView SizeRecy = (RecyclerView) customFilterDialog.findViewById(R.id.sizerec);
                                    final RecyclerView ColorRecy = (RecyclerView) customFilterDialog.findViewById(R.id.colorrec);
                                    final RecyclerView packsizerec = (RecyclerView) customFilterDialog.findViewById(R.id.packsizerec);

                                    LinearLayoutManager MyLayoutManagersize = new LinearLayoutManager(context);
                                    MyLayoutManagersize.setOrientation(LinearLayoutManager.HORIZONTAL);
                                    SizeRecy.setHasFixedSize(true);
                                    SizeRecy.setLayoutManager(MyLayoutManagersize);


                                    LinearLayoutManager MyLayoutManagercolor = new LinearLayoutManager(context);
                                    MyLayoutManagercolor.setOrientation(LinearLayoutManager.VERTICAL);
                                    ColorRecy.setHasFixedSize(true);
                                    ColorRecy.setLayoutManager(MyLayoutManagercolor);

                                    LinearLayoutManager MyLayoutManagerpack = new LinearLayoutManager(context);
                                    MyLayoutManagerpack.setOrientation(LinearLayoutManager.VERTICAL);
                                    packsizerec.setHasFixedSize(true);
                                    packsizerec.setLayoutManager(MyLayoutManagerpack);

                                    JSONArray details = jsonObj.getJSONArray("products");
                                    Log.d("sadsdf","adsfs"+details);

                                    if(details.length()==0)
                                    {
                                        Log.d("sadsdf","adsfs"+details.length());
                                    }

                                    for (int i = 0; i < details.length(); i++) {
                                        JSONObject jsonObject = details.getJSONObject(i);
                                        JSONArray Image = jsonObject.getJSONArray("product_image");
                                        Log.d("dgdgdg","fdfdf"+Image);
                                        JSONArray packsize = jsonObject.getJSONArray("product_price_data");

                                        if(packsize.length()==0){
                                            Packsizestatus="0";
                                            packsizeText.setVisibility(View.GONE);
                                        }
                                        else {
                                            Log.d("dgdgdg","fdfdf"+packsize);

                                            // productimg.setVisibility(View.VISIBLE);
                                            for (int j = 0; j <packsize.length(); j++) {
                                                Log.d("ColorArray","ColorArray"+packsize);

                                                Packsizestatus="1";
                                                JSONObject jsonObject3 = packsize.getJSONObject(j);

                                                String size = jsonObject3.getString("pack_size");
                                                String price = jsonObject3.getString("price");
                                                String Mrp = jsonObject3.getString("Mrp");
                                                String unit_name = jsonObject3.getString("unit_name");
                                                PackMrp.add(Mrp);
                                                PackPrice.add(price);
                                                Packsize.add(size);
                                                PackUnit.add(unit_name);
                                                Log.d("sdsdsd","dsdsd"+Packsize);

                                                Log.d("dfdfd","sfddfdf"+PacK_price);

                                            }

                                            packsizerec.setAdapter(new PackSizeAdapter());

                                        }


                                        if (SizeStat.equals("1")) {
                                            JSONArray SizesArray = jsonObject.getJSONArray("sizes");

                                            Log.d("SizesArray","SizesArray"+SizesArray);
                                            for (int j = 0; j < SizesArray.length(); j++) {
                                                JSONObject jsonObject1 = SizesArray.getJSONObject(j);
                                                String SizeIDString = jsonObject1.getString("size_id");
                                                String SizeNameString = jsonObject1.getString("size");

                                                Log.d("fdfdf","fdfdf"+SizeNameString);
                                                SizeID.add(SizeIDString);
                                                SizeName.add(SizeNameString);
                                                SizeRecy.setAdapter(new SizeListAdapter());

                                            }
                                        } else {
                                            SizeDisplayText.setVisibility(View.GONE);
                                        }

                                        if (ColorStat.equals("1")) {
                                            JSONArray ColorArray = jsonObject.getJSONArray("colors");
                                            Log.d("ColorArray","ColorArray"+ColorArray);

                                            for (int k = 0; k < ColorArray.length(); k++) {
                                                JSONObject jsonObject2 = ColorArray.getJSONObject(k);
                                                String ColorIDString = jsonObject2.getString("color_id");
                                                String ColorNameString = jsonObject2.getString("color_name");
                                                String ColorCodeString = jsonObject2.getString("color_code");
                                                Log.d("sdsdsdsds","ColorNameString"+ColorNameString);

                                                ColorID.add(ColorIDString);
                                                ColorName.add(ColorNameString);
                                                Log.d("sdsdsdsds","dssdsds"+ColorName);
                                                ColorCode.add(ColorCodeString);
                                                ColorRecy.setAdapter(new ColorAdapter());

                                            }
                                        } else {
                                            ColorDisplayText.setVisibility(View.GONE);
                                        }
                                    }


                                    ReduceQuanImage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int TempQuan = Integer.valueOf(QuantityEdit.getText().toString());

                                            if(TempQuan==1)
                                            {

                                            }
                                            else {
                                                TempQuan=TempQuan-1;
                                                int price= Integer.parseInt(QuantityEdit.getText().toString());
                                                Log.d("sdfdsf","sdfsdf"+TempQuan);

                                                QuantityEdit.setText(String.valueOf(TempQuan));

                                            }

                                        }
                                    });
                                    IncreaseQuanImage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int TempQuan = Integer.valueOf(QuantityEdit.getText().toString());
                                            TempQuan=TempQuan+1;
                                            int price= Integer.parseInt(QuantityEdit.getText().toString());
                                            Log.d("sdsfsdf","dsfsdf"+price);
                                            Log.d("sdfdsf","sdfsdf"+TempQuan);


                                            QuantityEdit.setText(String.valueOf(TempQuan));
                                        }
                                    });

                                    saveButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            Log.d("","PAcksize"+PAcksize);
                                            Log.d("","Packsizestatus"+Packsizestatus);
                                            if(PAcksize.equals("0")&&Packsizestatus.equals("1"))
                                            {
                                                Toast.makeText(context,"Please Select Pack Size ",Toast.LENGTH_SHORT).show();
                                            }
                                            else {


                                                addQuentity.dismiss();
                                                addQuentity.cancel();

                                                Log.d("PAckprice","packprice"+PacK_price +" "+PAcksize);
                                                ((Counters) context).AddProductToCart(ProductID.get(position), ProductName.get(position), QuantityEdit.getText().toString(),PacK_price, ProductImage.get(position), Size, Colour, PAcksize,PacK_unit);
                                                PAcksize="0";
                                                addQuentity.dismiss();
                                                customFilterDialog.dismiss();
                                            }
                                        }
                                    });



                                } else {

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                //Toast.makeText(PostNewAgreementProperty.this, e.toString(), Toast.LENGTH_SHORT).show();
                                System.out.print(e.toString());
                            }
                            //messageAdapter.notifyDataSetChanged();
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                            System.out.print(error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("product_id", ProductID.get(position));
                            parameters.put("user_id", userID);
                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                }
            }


        });

/*
        add_tocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(false);
                    //progressDialog.setMax(100);
                    progressDialog.show();

                    SizeID.clear();
                    SizeName.clear();
                    Packsize.clear();
                    ColorID.clear();
                    ColorName.clear();
                    ColorCode.clear();
                    ImageString.clear();
                    Packsize.clear();
                    PackMrp.clear();
                    PackPrice.clear();
                    PackUnit.clear();
                    Packsizestatus="0";
                    Log.d("gdgf","dgfdg"+ProductID);

                    // String insertUrl = "https://urban-mart.in/api/getProduct";
                    String insertUrl = "https://papasmart.online/api2/getProduct";
                    StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.cancel();
                            System.out.println(response);
                            String finalResponse = response.substring(1);
                            System.out.println(finalResponse);
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                String stat = jsonObj.getString("status");
                                String RelatedStat = jsonObj.getString("rel_status");
                                SizeStat = jsonObj.getString("size_status");
                                ColorStat = jsonObj.getString("color_status");
                                //   String price = jsonObj.getString("price");
                                //String msg = jsonObj.getString("msg");
                                if (stat.equals("1")){


                                    customFilterDialog.show();
                                    final EditText QuantityEdit = (EditText) customFilterDialog.findViewById(R.id.quantityEdit);

                                    final TextView SizeDisplayText = (TextView)customFilterDialog.findViewById(R.id.sizeText);
                                    final TextView ColorDisplayText = (TextView)customFilterDialog.findViewById(R.id.colorText);
                                    final TextView packsizeText = (TextView)customFilterDialog.findViewById(R.id.packsizeText);
                                    final TextView ReduceQuanImage = (TextView) customFilterDialog.findViewById(R.id.minusBtn);
                                    final TextView IncreaseQuanImage = (TextView) customFilterDialog.findViewById(R.id.plusBtn);
                                    final Button saveButton = (Button) customFilterDialog.findViewById(R.id.saveButton);
                                    final RecyclerView SizeRecy = (RecyclerView) customFilterDialog.findViewById(R.id.sizerec);
                                    final RecyclerView ColorRecy = (RecyclerView) customFilterDialog.findViewById(R.id.colorrec);
                                    final RecyclerView packsizerec = (RecyclerView) customFilterDialog.findViewById(R.id.packsizerec);

                                    LinearLayoutManager MyLayoutManagersize = new LinearLayoutManager(context);
                                    MyLayoutManagersize.setOrientation(LinearLayoutManager.HORIZONTAL);
                                    SizeRecy.setHasFixedSize(true);
                                    SizeRecy.setLayoutManager(MyLayoutManagersize);


                                    LinearLayoutManager MyLayoutManagercolor = new LinearLayoutManager(context);
                                    MyLayoutManagercolor.setOrientation(LinearLayoutManager.VERTICAL);
                                    ColorRecy.setHasFixedSize(true);
                                    ColorRecy.setLayoutManager(MyLayoutManagercolor);

                                    LinearLayoutManager MyLayoutManagerpack = new LinearLayoutManager(context);
                                    MyLayoutManagerpack.setOrientation(LinearLayoutManager.VERTICAL);
                                    packsizerec.setHasFixedSize(true);
                                    packsizerec.setLayoutManager(MyLayoutManagerpack);

                                    JSONArray details = jsonObj.getJSONArray("products");
                                    Log.d("sadsdf","adsfs"+details);

                                    if(details.length()==0)
                                    {
                                        Log.d("sadsdf","adsfs"+details.length());
                                    }

                                    for (int i = 0; i < details.length(); i++) {
                                        JSONObject jsonObject = details.getJSONObject(i);
                                        JSONArray Image = jsonObject.getJSONArray("product_image");
                                        Log.d("dgdgdg","fdfdf"+Image);
                                        JSONArray packsize = jsonObject.getJSONArray("product_price_data");

                                        if(packsize.length()==0){
                                            Packsizestatus="0";
                                            packsizeText.setVisibility(View.GONE);
                                        }
                                        else {
                                            Log.d("dgdgdg","fdfdf"+packsize);

                                            // productimg.setVisibility(View.VISIBLE);
                                            for (int j = 0; j <packsize.length(); j++) {
                                                Log.d("ColorArray","ColorArray"+packsize);

                                                Packsizestatus="1";
                                                JSONObject jsonObject3 = packsize.getJSONObject(j);

                                                String size = jsonObject3.getString("pack_size");
                                                String price = jsonObject3.getString("price");
                                                String Mrp = jsonObject3.getString("Mrp");
                                                String unit_name = jsonObject3.getString("unit_name");
                                                PackMrp.add(Mrp);
                                                PackPrice.add(price);
                                                Packsize.add(size);
                                                PackUnit.add(unit_name);
                                                Log.d("sdsdsd","dsdsd"+Packsize);

                                            */
/*    PacK_price=PackPrice.get(0);
                                                Pack_Mrp=PackMrp.get(0);
                                                Pack_size=Packsize.get(0);
*//*

                                                Log.d("dfdfd","sfddfdf"+PacK_price);

                                            }

                                            //   ProductImageAdapter adaper=new ProductImageAdapter(getApplicationContext(),ImageString);
                                            packsizerec.setAdapter(new PackSizeAdapter());

                                        }


                                        if (SizeStat.equals("1")) {
                                            JSONArray SizesArray = jsonObject.getJSONArray("sizes");

                                            Log.d("SizesArray","SizesArray"+SizesArray);
                                            for (int j = 0; j < SizesArray.length(); j++) {
                                                JSONObject jsonObject1 = SizesArray.getJSONObject(j);
                                                String SizeIDString = jsonObject1.getString("size_id");
                                                String SizeNameString = jsonObject1.getString("size");

                                                Log.d("fdfdf","fdfdf"+SizeNameString);
                                                SizeID.add(SizeIDString);
                                                SizeName.add(SizeNameString);
                                                SizeRecy.setAdapter(new SizeListAdapter());

                                            }
                                        } else {
                                            SizeDisplayText.setVisibility(View.GONE);
                                        }

                                        if (ColorStat.equals("1")) {
                                            JSONArray ColorArray = jsonObject.getJSONArray("colors");
                                            Log.d("ColorArray","ColorArray"+ColorArray);

                                            for (int k = 0; k < ColorArray.length(); k++) {
                                                JSONObject jsonObject2 = ColorArray.getJSONObject(k);
                                                String ColorIDString = jsonObject2.getString("color_id");
                                                String ColorNameString = jsonObject2.getString("color_name");
                                                String ColorCodeString = jsonObject2.getString("color_code");
                                                Log.d("sdsdsdsds","ColorNameString"+ColorNameString);

                                                ColorID.add(ColorIDString);
                                                ColorName.add(ColorNameString);
                                                Log.d("sdsdsdsds","dssdsds"+ColorName);
                                                ColorCode.add(ColorCodeString);
                                                ColorRecy.setAdapter(new ColorAdapter());

                                            }
                                        } else {
                                            ColorDisplayText.setVisibility(View.GONE);
                                        }
                                    }


                                    ReduceQuanImage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int TempQuan = Integer.valueOf(QuantityEdit.getText().toString());

                                            if(TempQuan==1)
                                            {

                                            }
                                            else {
                                                TempQuan=TempQuan-1;
                                                int price= Integer.parseInt(QuantityEdit.getText().toString());
                                                Log.d("sdfdsf","sdfsdf"+TempQuan);

                                                QuantityEdit.setText(String.valueOf(TempQuan));

                                            }

                                        }
                                    });
                                    IncreaseQuanImage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int TempQuan = Integer.valueOf(QuantityEdit.getText().toString());
                                            TempQuan=TempQuan+1;
                                            int price= Integer.parseInt(QuantityEdit.getText().toString());
                                            Log.d("sdsfsdf","dsfsdf"+price);
                                            Log.d("sdfdsf","sdfsdf"+TempQuan);


                                            QuantityEdit.setText(String.valueOf(TempQuan));
                                        }
                                    });

                                    saveButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            Log.d("","PAcksize"+PAcksize);
                                            Log.d("","Packsizestatus"+Packsizestatus);
                                            if(PAcksize.equals("0")&&Packsizestatus.equals("1"))
                                            {
                                                Toast.makeText(context,"Please Select Pack Size ",Toast.LENGTH_SHORT).show();
                                            }
                                            else {


                                                addQuentity.dismiss();
                                                addQuentity.cancel();

                                                Log.d("PAckprice","packprice"+PacK_price +" "+PAcksize);
                                                ((Counters) context).AddProductToCart(ProductID.get(position), ProductName.get(position), QuantityEdit.getText().toString(),PacK_price, ProductImage.get(position), Size, Colour, PAcksize,PacK_unit);
                                                PAcksize="0";
                                                addQuentity.dismiss();
                                                customFilterDialog.dismiss();
                                            }
                                        }
                                    });



                                } else {

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.print(e.toString());
                            }
                            //messageAdapter.notifyDataSetChanged();
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                            System.out.print(error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("product_id", ProductID.get(position));
                            parameters.put("user_id", userID);
                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                }
           }
        });
*/
        AddProductToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(false);
                    //progressDialog.setMax(100);
                    progressDialog.show();

                    SizeID.clear();
                    SizeName.clear();
                    Packsize.clear();
                    ColorID.clear();
                    ColorName.clear();
                    ColorCode.clear();
                    ImageString.clear();
                    Packsize.clear();
                    PackMrp.clear();
                    PackPrice.clear();
                    PackUnit.clear();
                    Packsizestatus="0";
                    Log.d("gdgf","dgfdg"+ProductID);

                    // String insertUrl = "https://urban-mart.in/api/getProduct";
                    String insertUrl = "https://microlanpos.com/demo/api2/getProduct";
                    StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.cancel();
                            System.out.println(response);
                            String finalResponse = response.substring(1);
                            System.out.println(finalResponse);
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                String stat = jsonObj.getString("status");
                                String RelatedStat = jsonObj.getString("rel_status");
                                SizeStat = jsonObj.getString("size_status");
                                ColorStat = jsonObj.getString("color_status");
                                //   String price = jsonObj.getString("price");
                                //String msg = jsonObj.getString("msg");
                                if (stat.equals("1")){


                                    customFilterDialog.show();
                                    final EditText QuantityEdit = (EditText) customFilterDialog.findViewById(R.id.quantityEdit);

                                    final TextView SizeDisplayText = (TextView)customFilterDialog.findViewById(R.id.sizeText);
                                    final TextView ColorDisplayText = (TextView)customFilterDialog.findViewById(R.id.colorText);
                                    final TextView packsizeText = (TextView)customFilterDialog.findViewById(R.id.packsizeText);
                                    final TextView ReduceQuanImage = (TextView) customFilterDialog.findViewById(R.id.minusBtn);
                                    final TextView IncreaseQuanImage = (TextView) customFilterDialog.findViewById(R.id.plusBtn);
                                    final Button saveButton = (Button) customFilterDialog.findViewById(R.id.saveButton);
                                    final RecyclerView SizeRecy = (RecyclerView) customFilterDialog.findViewById(R.id.sizerec);
                                    final RecyclerView ColorRecy = (RecyclerView) customFilterDialog.findViewById(R.id.colorrec);
                                    final RecyclerView packsizerec = (RecyclerView) customFilterDialog.findViewById(R.id.packsizerec);

                                    LinearLayoutManager MyLayoutManagersize = new LinearLayoutManager(context);
                                    MyLayoutManagersize.setOrientation(LinearLayoutManager.HORIZONTAL);
                                    SizeRecy.setHasFixedSize(true);
                                    SizeRecy.setLayoutManager(MyLayoutManagersize);


                                    LinearLayoutManager MyLayoutManagercolor = new LinearLayoutManager(context);
                                    MyLayoutManagercolor.setOrientation(LinearLayoutManager.VERTICAL);
                                    ColorRecy.setHasFixedSize(true);
                                    ColorRecy.setLayoutManager(MyLayoutManagercolor);

                                    LinearLayoutManager MyLayoutManagerpack = new LinearLayoutManager(context);
                                    MyLayoutManagerpack.setOrientation(LinearLayoutManager.VERTICAL);
                                    packsizerec.setHasFixedSize(true);
                                    packsizerec.setLayoutManager(MyLayoutManagerpack);

                                    JSONArray details = jsonObj.getJSONArray("products");
                                    Log.d("sadsdf","adsfs"+details);

                                    if(details.length()==0)
                                    {
                                        Log.d("sadsdf","adsfs"+details.length());
                                    }

                                    for (int i = 0; i < details.length(); i++) {
                                        JSONObject jsonObject = details.getJSONObject(i);
                                        JSONArray Image = jsonObject.getJSONArray("product_image");
                                        Log.d("dgdgdg","fdfdf"+Image);
                                        JSONArray packsize = jsonObject.getJSONArray("product_price_data");

                                        if(packsize.length()==0){
                                            Packsizestatus="0";
                                            packsizeText.setVisibility(View.GONE);
                                        }
                                        else {
                                            Log.d("dgdgdg","fdfdf"+packsize);

                                            // productimg.setVisibility(View.VISIBLE);
                                            for (int j = 0; j <packsize.length(); j++) {
                                                Log.d("ColorArray","ColorArray"+packsize);

                                                Packsizestatus="1";
                                                JSONObject jsonObject3 = packsize.getJSONObject(j);

                                                String size = jsonObject3.getString("pack_size");
                                                String price = jsonObject3.getString("price");
                                                String Mrp = jsonObject3.getString("Mrp");
                                                String unit_name = jsonObject3.getString("unit_name");
                                                PackMrp.add(Mrp);
                                                PackPrice.add(price);
                                                Packsize.add(size);
                                                PackUnit.add(unit_name);
                                                Log.d("sdsdsd","dsdsd"+Packsize);

                                            /*    PacK_price=PackPrice.get(0);
                                                Pack_Mrp=PackMrp.get(0);
                                                Pack_size=Packsize.get(0);
*/
                                                Log.d("dfdfd","sfddfdf"+PacK_price);

                                            }

                                            //   ProductImageAdapter adaper=new ProductImageAdapter(getApplicationContext(),ImageString);
                                            packsizerec.setAdapter(new PackSizeAdapter());

                                        }


                                        if (SizeStat.equals("1")) {
                                            JSONArray SizesArray = jsonObject.getJSONArray("sizes");

                                            Log.d("SizesArray","SizesArray"+SizesArray);
                                            for (int j = 0; j < SizesArray.length(); j++) {
                                                JSONObject jsonObject1 = SizesArray.getJSONObject(j);
                                                String SizeIDString = jsonObject1.getString("size_id");
                                                String SizeNameString = jsonObject1.getString("size");

                                                Log.d("fdfdf","fdfdf"+SizeNameString);
                                                SizeID.add(SizeIDString);
                                                SizeName.add(SizeNameString);
                                                SizeRecy.setAdapter(new SizeListAdapter());

                                            }
                                        } else {
                                            SizeDisplayText.setVisibility(View.GONE);
                                        }

                                        if (ColorStat.equals("1")) {
                                            JSONArray ColorArray = jsonObject.getJSONArray("colors");
                                            Log.d("ColorArray","ColorArray"+ColorArray);

                                            for (int k = 0; k < ColorArray.length(); k++) {
                                                JSONObject jsonObject2 = ColorArray.getJSONObject(k);
                                                String ColorIDString = jsonObject2.getString("color_id");
                                                String ColorNameString = jsonObject2.getString("color_name");
                                                String ColorCodeString = jsonObject2.getString("color_code");
                                                Log.d("sdsdsdsds","ColorNameString"+ColorNameString);

                                                ColorID.add(ColorIDString);
                                                ColorName.add(ColorNameString);
                                                Log.d("sdsdsdsds","dssdsds"+ColorName);
                                                ColorCode.add(ColorCodeString);
                                                ColorRecy.setAdapter(new ColorAdapter());

                                            }
                                        } else {
                                            ColorDisplayText.setVisibility(View.GONE);
                                        }
                                    }


                                    ReduceQuanImage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int TempQuan = Integer.valueOf(QuantityEdit.getText().toString());

                                            if(TempQuan==1)
                                            {

                                            }
                                            else {
                                                TempQuan=TempQuan-1;
                                                int price= Integer.parseInt(QuantityEdit.getText().toString());
                                                Log.d("sdfdsf","sdfsdf"+TempQuan);

                                                QuantityEdit.setText(String.valueOf(TempQuan));

                                            }

                                        }
                                    });
                                    IncreaseQuanImage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int TempQuan = Integer.valueOf(QuantityEdit.getText().toString());
                                            TempQuan=TempQuan+1;
                                            int price= Integer.parseInt(QuantityEdit.getText().toString());
                                            Log.d("sdsfsdf","dsfsdf"+price);
                                            Log.d("sdfdsf","sdfsdf"+TempQuan);


                                            QuantityEdit.setText(String.valueOf(TempQuan));
                                        }
                                    });

                                    saveButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            Log.d("","PAcksize"+PAcksize);
                                            Log.d("","Packsizestatus"+Packsizestatus);
                                            if(PAcksize.equals("0")&&Packsizestatus.equals("1"))
                                            {
                                                Toast.makeText(context,"Please Select Pack Size ",Toast.LENGTH_SHORT).show();
                                            }
                                            else {


                                                addQuentity.dismiss();
                                                addQuentity.cancel();

                                                Log.d("PAckprice","packprice"+PacK_price +" "+PAcksize);
                                                ((Counters) context).AddProductToCart(ProductID.get(position), ProductName.get(position), QuantityEdit.getText().toString(),PacK_price, ProductImage.get(position), Size, Colour, PAcksize,PacK_unit);
                                                PAcksize="0";
                                                addQuentity.dismiss();
                                                customFilterDialog.dismiss();
                                            }
                                        }
                                    });



                                } else {

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.print(e.toString());
                            }
                            //messageAdapter.notifyDataSetChanged();
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                            System.out.print(error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("product_id", ProductID.get(position));
                            parameters.put("user_id", userID);
                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                }
           }
        });


        return gridView;
    }



    @Override
    public int getCount() {
        return ProductName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class SizeListAdapter extends RecyclerView.Adapter<SizeListAdapter.MyViewHolder> {


        private  int lastClickedPosition = -1;
        private int selectedItem=-1;


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sizelist, parent, false);
            return new MyViewHolder(view);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {



            myViewHolder.packsize.setText(SizeName.get(i));


            if (selectedItem == i) {
                myViewHolder.sizelay.setBackgroundResource((R.color.colorAccent));
            }
            else {
                //  myViewHolder.sizelay.setBackgroundResource((R.color.grey));

            }




            myViewHolder.packsize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Size=SizeName.get(i);
                  //  Log.d("dfdfd","Size "+Size);

                    int previousItem = selectedItem;
                    selectedItem = i;

                    notifyItemChanged(previousItem);
                    notifyItemChanged(i);



                }
            });


        }

        @Override
        public int getItemCount() {
            return SizeName.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView packsize;
            CardView sizelay;

            public MyViewHolder(View itemView) {
                super(itemView);

                packsize = (TextView) itemView.findViewById(R.id.packsize);
                sizelay =  itemView.findViewById(R.id.sizelay);
            }

        }

    }

    private class ColorAdapter  extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {

        private int selectedItem=-1;

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.colorlist, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


            if (selectedItem == i) {
                myViewHolder.colorcard.setBackgroundResource((R.color.colorAccent));
            }
            else {

            }

            Pack_size=ColorName.get(0);

            myViewHolder.colorcode.setText(ColorCode.get(i));
            myViewHolder.colorname.setText(ColorName.get(i));

                myViewHolder.colorimage.setVisibility(View.VISIBLE);
                Log.d("rrrrrrrr","rtttttt");




            myViewHolder.colorcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Colour=ColorName.get(i);

                    int previousItem = selectedItem;
                    selectedItem = i;

                    notifyItemChanged(previousItem);
                    notifyItemChanged(i);


                    Log.d("dfdfdf","dfdfd"+Colour);

                }
            });
        }

        @Override
        public int getItemCount() {
            return ColorID.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            ImageView colorimage;
            TextView colorname,colorcode,code_color;
            LinearLayout colorcard;
            public MyViewHolder(View itemView) {
                super(itemView);

                colorname = (TextView) itemView.findViewById(R.id.colorname);
                colorcode = (TextView) itemView.findViewById(R.id.colorcode);
                code_color = (TextView) itemView.findViewById(R.id.code_color);
                colorimage = (ImageView) itemView.findViewById(R.id.colorimage);
                colorcard =  itemView.findViewById(R.id.colorcard);

            }

        }

    }

    private class PackSizeAdapter extends RecyclerView.Adapter <PackSizeAdapter.MyViewHolder> {
        private int selectedItem=-1;

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sizelist, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

            myViewHolder.packsize.setText(Packsize.get(i)+"  "+PackUnit.get(i));

            if (selectedItem == i) {
                myViewHolder.sizelay.setBackgroundResource((R.color.colorAccent));
            }
            else {
                //  myViewHolder.sizelay.setBackgroundResource((R.color.grey));

            }

            myViewHolder.sizelay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PAcksize=Packsize.get(i);
                    PacK_price=PackPrice.get(i);
                    PacK_unit=PackUnit.get(i);

                    Log.d("fdfdfdf","PacK_price"+PacK_price);
                    Log.d("fdfdfdf","PackPrice.get(i)"+PackPrice.get(i));
                    int previousItem = selectedItem;
                    selectedItem = i;

                    notifyItemChanged(previousItem);
                    notifyItemChanged(i);
                }
            });


        }

        @Override
        public int getItemCount() {
            return Packsize.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView packsize;
            CardView sizelay;

            public MyViewHolder(View itemView) {
                super(itemView);

                packsize = (TextView) itemView.findViewById(R.id.packsize);
                sizelay=(CardView)itemView.findViewById(R.id.sizelay);
            }

        }

    }
}
