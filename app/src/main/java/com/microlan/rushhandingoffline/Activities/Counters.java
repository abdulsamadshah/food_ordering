package com.microlan.rushhandingoffline.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.microlan.rushhandingoffline.Adapters.CategoryAdapter;
import com.microlan.rushhandingoffline.Adapters.ProductCounterAdapter;
import com.microlan.rushhandingoffline.Dialogs.BillDetailsDialog;
import com.microlan.rushhandingoffline.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.microlan.rushhandingoffline.Activities.SplashScreen.decrypt;
import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;

public class Counters extends AppCompatActivity {

    ArrayList<String> ProductID, ProductName, ProductPrice, ProductImage, ProductQuan;
    ArrayList<String> CategoryID, CategoryName, CategoryImage;
    Button SaveBtn;
    BillDetailsDialog billDetailsDialog;
    GridView ProductsGrid, CategoriesGrid;
    ProductCounterAdapter productCounterAdapter;
    CategoryAdapter categoryAdapter;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    TextView TotalAmountText;
    String CartSGSTAmount = "", CartIGSTAmount = "", CartCGSTAmount = "", CartTotalAmount = "", CustomerStateString = "";
    EditText SearchProductsEdit;
    TextView CustomerPointsText, CustomerWalletText;
    EditText CustomerPointsEdit, CustomerWalletEdit;
    String Intro = "", UserID,session_id;
    SharedPreferences sharedPreferences;

    private static final int REQUEST_CODE_QR_SCAN = 101;
    ImageView scanqr,down;
    RelativeLayout card;
    RecyclerView categoriesrecy;
    TextView countText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counters);
        getSupportActionBar().setTitle("Take Orders");
        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        billDetailsDialog = new BillDetailsDialog(Counters.this);

        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        Intro = sharedPreferences.getString("Intro", encrypt("No"));
        UserID = sharedPreferences.getString("id", "");
        session_id = sharedPreferences.getString("session_id", "");
        Intro = decrypt(Intro);
        //  UserID = decrypt(UserID);


        Log.d("", "UserIDy" + UserID);

        categoriesrecy = (RecyclerView) findViewById(R.id.categoriesrecy);
        SaveBtn = (Button) findViewById(R.id.saveBtn);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        down=findViewById(R.id.down);
        SearchProductsEdit = (EditText) findViewById(R.id.searchProductEdit);
        CategoriesGrid = (GridView) findViewById(R.id.categoriesGrid);

        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(Counters.this);
        MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoriesrecy.setLayoutManager(MyLayoutManager);
        categoriesrecy.setHasFixedSize(true);

        countText=findViewById(R.id.countText);



        ProductsGrid = (GridView) findViewById(R.id.productsGrid);
        TotalAmountText = (TextView) findViewById(R.id.totalAmountText);
        scanqr = (ImageView) findViewById(R.id.scanqr);
        card = (RelativeLayout) findViewById(R.id.card);


        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoriesrecy.getVisibility()==View.VISIBLE)
                {
                    categoriesrecy.setVisibility(View.GONE);


                }
                else {
                    categoriesrecy.setVisibility(View.VISIBLE);
                }
            }
        });

        ProductID = new ArrayList<String>();
        ProductName = new ArrayList<String>();
        ProductPrice = new ArrayList<String>();
        ProductImage = new ArrayList<String>();
        ProductQuan = new ArrayList<String>();

        CategoryID = new ArrayList<String>();
        CategoryName = new ArrayList<String>();
        CategoryImage = new ArrayList<String>();




        scanqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Counters.this, QrCodeActivity.class);
                startActivityForResult( i,REQUEST_CODE_QR_SCAN);
            }
        });
/*
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Counters.this, ItemInCart.class);
                startActivityForResult( i,REQUEST_CODE_QR_SCAN);
            }
        });
*/



      //  ClearCart(UserID);

/*
        CategoriesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetCatProducts(CategoryID.get(position));
            }
        });
*/

        GetCartNumber(UserID);

        SearchProductsEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (SearchProductsEdit.getText().toString().isEmpty()) {

                } else if (SearchProductsEdit.getText().toString().length() < 3) {

                } else {
                    SearchAllProducts(SearchProductsEdit.getText().toString());
                }
            }
        });

        GetAllCategories();
        GetAllProducts();
        GetCartProducts(UserID);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.counter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_scan) {
            Intent i = new Intent(Counters.this, QrCodeActivity.class);
            startActivityForResult( i,REQUEST_CODE_QR_SCAN);
            return true;
        } else if (id == R.id.action_barcode_scan) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void ClearCart(final String UserID) {
        progressDialog = new ProgressDialog(Counters.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();

        String insertUrl = "http://microlanpos.com/demo/api2/delete_cart";
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

                    if (stat.equals("1")) {
                    } else {
                        GetAllCategories();
//                        Toast.makeText(Counters.this, "Sorry Products Not Found.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Counters.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("id", UserID);

                Log.d("delete cart", "delete cart" + parameters);
                return parameters;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    public void GetAllCategories() {
        progressDialog = new ProgressDialog(Counters.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://microlanpos.com/demo/api2/get_all_category";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.cancel();
                        System.out.print(response.toString());

                        try {
                            String stat = response.getString("status");
                            if (stat.equals("1")) {
                                JSONArray details = response.getJSONArray("category_list");
                                for (int i = 0; i < details.length(); i++) {
                                    JSONObject jsonObject = details.getJSONObject(i);
                                    String categoryIDString = jsonObject.getString("category_id");
                                    String categoryNameString = jsonObject.getString("category_name");
                                    String categoriesImageString = jsonObject.getString("image_name");

                                    CategoryID.add(categoryIDString);
                                    CategoryName.add(categoryNameString);

                                    CategoryImage.add("http://microlanpos.com/demo/uploads/categoryimg/" + categoriesImageString);
                                    //Toast.makeText(MainActivity.this, categoriesImagePathString, Toast.LENGTH_SHORT).show();
                                }

                                Log.d("","CategoryImage"+CategoryImage);
                               // categoryAdapter = new CategoryAdapter(Counters.this, CategoryID, CategoryName, CategoryImage, UserID, ImageString);

                             //   categoriesrecy.setAdapter(categoryAdapter);

                               // CategoriesGrid.setAdapter(categoryAdapter);

                                GetAllProducts();
                            } else {
                                Toast.makeText(Counters.this, "Products Not Found.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("Error.Response");
                        progressDialog.cancel();

                    }
                }
        );

        requestQueue.add(getRequest);
    }

    public void GetAllProducts() {
        /*progressDialog = new ProgressDialog(Counters.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();
*/
        ProductID.clear();
        ProductName.clear();
        ProductPrice.clear();
        ProductImage.clear();
        ProductQuan.clear();

        final String url = "http://microlanpos.com/demo/api2/get_all_product";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      //  progressDialog.cancel();
                        System.out.print(response.toString());

                        try {
                            String stat = response.getString("status");
                            if (stat.equals("1")) {
                                JSONArray details = response.getJSONArray("product_list");
                                for (int i = 0; i < details.length(); i++) {
                                    JSONObject jsonObject = details.getJSONObject(i);
                                    String productIDString = jsonObject.getString("product_id");
                                    String productNameString = jsonObject.getString("product_name");
                                    String productImageString = jsonObject.getString("image_name");
                                    String productPriceString = jsonObject.getString("price");

                                    ProductID.add(productIDString);
                                    ProductName.add(productNameString);

                                    String[] namesList = productPriceString.split(",");

                                    String price = namesList[0];

                                    ProductPrice.add("₹" + price);
                                    ProductImage.add("http://microlanpos.com/demo/products/" + productImageString);
                                    ProductQuan.add("1");
                                    //Toast.makeText(MainActivity.this, categoriesImagePathString, Toast.LENGTH_SHORT).show();
                                }

                                productCounterAdapter = new ProductCounterAdapter(Counters.this, ProductID, ProductName, ProductPrice, ProductImage, ProductQuan, UserID);
                                productCounterAdapter.notifyDataSetChanged();
                                ProductsGrid.setAdapter(productCounterAdapter);

                            } else {
                                Toast.makeText(Counters.this, "Products Not Found.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("Error.Response");

                     //   progressDialog.cancel();

                    }
                }
        );

        requestQueue.add(getRequest);
    }


    public void GetCatProducts(final String CategoryID) {
        progressDialog = new ProgressDialog(Counters.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();

        ProductID.clear();
        ProductName.clear();
        ProductPrice.clear();
        ProductImage.clear();
        ProductQuan.clear();

        //Toast.makeText(Counters.this, CategoryID, Toast.LENGTH_SHORT).show();

        String insertUrl = "http://microlanpos.com/demo/api2/get_product_as_per_category";
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

                    if (stat.equals("1")) {
                        ProductsGrid.setVisibility(View.VISIBLE);
                        JSONArray catDetails = jsonObj.getJSONArray("category__product_list");
                        for (int j = 0; j < catDetails.length(); j++) {
                            JSONObject catJSON = catDetails.getJSONObject(j);
                            JSONArray details = catJSON.getJSONArray("product_list");
                            for (int i = 0; i < details.length(); i++) {
                                JSONObject jsonObject = details.getJSONObject(i);
                                String productIDString = jsonObject.getString("product_id");
                                String productNameString = jsonObject.getString("product_name");
                                String productImageString = jsonObject.getString("image_name");
                                String productPriceString = jsonObject.getString("price");

                                Log.d("dfdfdf", "wwwwwwwww" + productImageString);
                                ProductID.add(productIDString);
                                ProductName.add(productNameString);
                                String[] separated = productPriceString.split(",");
                                String str1 = separated[0];
                                Log.d("","str1"+str1);
                              //  String str2 = separated[1];

                                ProductPrice.add("₹" + str1);
                                ProductImage.add("http://microlanpos.com/demo/products/" + productImageString);
                                ProductQuan.add("1");
                            }
                        }

                        productCounterAdapter = new ProductCounterAdapter(Counters.this, ProductID, ProductName, ProductPrice, ProductImage, ProductQuan, UserID);
                        productCounterAdapter.notifyDataSetChanged();
                        ProductsGrid.setAdapter(productCounterAdapter);
                    } else {
                        ProductsGrid.setVisibility(View.GONE);
                        Toast.makeText(Counters.this, "Sorry Products Not Found.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Counters.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
                progressDialog.cancel();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("category_id", CategoryID);
                return parameters;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    public void AddProductToCart(final String ProductID, final String ProductName, final String PurchaseQty,
                                 final String Price, final String Image, final String size, final String colour, final String PAcksize, final String pacK_unit) {
        progressDialog = new ProgressDialog(Counters.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();


        String insertUrl = "http://microlanpos.com/demo/api2/addItemToCart";
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

                    if (stat.equals("1")) {
                        Toast.makeText(Counters.this, "Item Added to Cart Successfully.", Toast.LENGTH_SHORT).show();
                        GetCartProducts(UserID);
                        GetCartNumber(UserID);

                    } else {
                        ProductsGrid.setVisibility(View.GONE);
                        Toast.makeText(Counters.this, "Sorry Products Not Found.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Counters.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("product_id", ProductID);
                parameters.put("product_name", ProductName);
                parameters.put("purchase_qty", PurchaseQty);
                parameters.put("price", Price);
                parameters.put("pack_image", Image);
                parameters.put("user_id", UserID);
                parameters.put("product_size", size);
                parameters.put("product_color", colour);
                parameters.put("pack_size", PAcksize);

                Log.d("fgghgh", "dfdfdf" + parameters);
                return parameters;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    private void GetCartNumber(final String userID) {
        //  String insertUrl = "https://urban-mart.in/api/count_in_cart";
        String insertUrl = "http://microlanpos.com/demo/api2/count_in_cart";
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                String finalResponse = response.substring(1);
                System.out.println(finalResponse);
                //Toast.makeText(Registration.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    String Count = jsonObj.getString("count");
                    Log.d("","Count"+Count);
                    if (Count.equals("0")){
                        countText.setVisibility(View.GONE);
                    } else {
                        countText.setVisibility(View.VISIBLE);
                        countText.setText(Count);
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
                Toast.makeText(Counters.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", userID);
                return parameters;
            }
        };
        requestQueue.add(request);
    }



    public void GetCartProducts(final String UserID) {
/*
        progressDialog = new ProgressDialog(Counters.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();
*/


        String insertUrl = "http://microlanpos.com/demo/api2/item_in_cart";
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  progressDialog.cancel();
                System.out.println(response);
                String finalResponse = response.substring(1);
                System.out.println(finalResponse);
                //Toast.makeText(Registration.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    String stat = jsonObj.getString("status");

                    if (stat.equals("1")) {
                        TotalAmountText.setVisibility(View.VISIBLE);
                        String totalAmount = jsonObj.getString("total");
                        TotalAmountText.setText(totalAmount);
                        JSONArray catDetails = jsonObj.getJSONArray("item_in_cart");
                        Log.d("s", "catDetails" + catDetails);
                        Log.d("s", "jsonObj" + jsonObj);

                      } else {
                        TotalAmountText.setVisibility(View.INVISIBLE);
//                        Toast.makeText(Counters.this, "Sorry Products Not Found.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Counters.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", UserID);
                Log.d("fdfd", "dfdfdf" + parameters);
                return parameters;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    public void SearchAllProducts(final String key) {
        progressDialog = new ProgressDialog(Counters.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();

        ProductID.clear();
        ProductName.clear();
        ProductPrice.clear();
        ProductImage.clear();
        ProductQuan.clear();

        //Toast.makeText(Counters.this, CategoryID, Toast.LENGTH_SHORT).show();

        String insertUrl = "http://microlanpos.com/demo/api2/search_products";
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();
                System.out.println(response);
                String finalResponse = response.substring(1);
                System.out.println(finalResponse);
                //Toast.makeText(Counters.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    String stat = jsonObj.getString("status");

                    if (stat.equals("1")) {
                        ProductsGrid.setVisibility(View.VISIBLE);
                        JSONArray details = jsonObj.getJSONArray("searchdata");
                        for (int i = 0; i < details.length(); i++) {
                            JSONObject jsonObject = details.getJSONObject(i);
                            String productIDString = jsonObject.getString("product_id");
                            String productNameString = jsonObject.getString("product_name");
                            String productImageString = jsonObject.getString("image_name");
                            String productPriceString = jsonObject.getString("price");

                            ProductID.add(productIDString);
                            ProductName.add(productNameString);
                            ProductPrice.add("₹" + productPriceString);
                            ProductImage.add("http://microlanpos.com/demo/products/" + productImageString);
                            ProductQuan.add("1");
                            //Toast.makeText(MainActivity.this, categoriesImagePathString, Toast.LENGTH_SHORT).show();
                        }

                        productCounterAdapter = new ProductCounterAdapter(Counters.this, ProductID, ProductName, ProductPrice, ProductImage, ProductQuan, UserID);
                        productCounterAdapter.notifyDataSetChanged();
                        ProductsGrid.setAdapter(productCounterAdapter);
                    } else {
                        ProductsGrid.setVisibility(View.GONE);
                        Toast.makeText(Counters.this, "Sorry Products Not Found.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Counters.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
                System.out.print(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("key", key);
                return parameters;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK)
        {
            //Log.d(LOGTAG,"COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(Counters.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            Log.d("","result"+result);


/*
            parameters.put("user_id", UserID);
            parameters.put("customer_id", customerId);
            parameters.put("map_with_id", map_with_id);
            parameters.put("map_with", map_with);
*/

            AddQRProducts(result,session_id,UserID);
        }
    }

    private void AddQRProducts(final String result, final String Sessionid, String userID) {
        String insertUrl = "http://microlanpos.com/demo/api2/addBarcodeScanProduct";
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
                  //  String msg = jsonObj.getString("msg");

                    Log.d("dfdfdfdf", "dfdfdf" + jsonObj);

                    if (stat.equals("1")) {
                        Toast.makeText(Counters.this, "Item Added to Cart Successfully.", Toast.LENGTH_SHORT).show();
                       // order_no = jsonObj.getString("order_number");

                        GetCartProducts(UserID);
                    } else {
                        //ProductsGrid.setVisibility(View.GONE);
                        Toast.makeText(Counters.this, "Try Again", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Counters.this, "add item " + error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("session_id", Sessionid);
                parameters.put("barcode_no", result);
                parameters.put("user_id", UserID);



                Log.d("parameter", "parameter" + parameters);
                return parameters;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);



    }




}
