package com.microlan.rushhandingoffline.Adapters;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.microlan.rushhandingoffline.Activities.Counters;
import com.microlan.rushhandingoffline.Activities.ItemInCartOffline;
import com.microlan.rushhandingoffline.DB.ItemInCartDBHelper;
import com.microlan.rushhandingoffline.OfflineModel.ItemInCardModel;
import com.microlan.rushhandingoffline.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CounterProductRecyclerAdapter extends RecyclerView.Adapter<CounterProductsViewHolder> {
    Context context;
    ArrayList<String> CartID, ProductID, ProductName, ProductImage, ProductQuan,CArtPAckSize,cartprice,cartpackUnit,cartUnitTotal,cartProductcode;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String userID;
    ItemInCartDBHelper dbAddItemHelpers;

    public CounterProductRecyclerAdapter(Context context1, ArrayList<String> cartID, ArrayList<String> productID, ArrayList<String> productName, ArrayList<String> productImage,
                                         ArrayList<String> productQuan, ArrayList<String> CArtPAckSize, ArrayList<String> cartprice, ArrayList<String> cartpackUnit, String userID, ArrayList<String> cartUnitTotal, ArrayList<String> cartProductcode)
    {
        this.context = context1;
        this.CartID = cartID;
        this.ProductID = productID;
        this.ProductName = productName;
        this.ProductImage = productImage;
        this.ProductQuan = productQuan;
        this.cartprice = cartprice;
        this.CArtPAckSize = CArtPAckSize;
        this.cartpackUnit = cartpackUnit;
        this.userID = userID;
        this.cartUnitTotal = cartUnitTotal;
        this.cartProductcode = cartProductcode;
    }


    public CounterProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_grid_item_products_added_to_counter, parent, false);
        CounterProductsViewHolder holder = new CounterProductsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CounterProductsViewHolder holder, final int position) {
        holder.ProductNameText.setText(ProductName.get(position)  +"  "+cartProductcode.get(position));
        holder.ProductQuanText.setText(ProductQuan.get(position));
        holder.packsizeprice.setText("Total :₹  "+cartUnitTotal.get(position));
        holder.singlepric.setText("Price :₹  "+cartprice.get(position) );

        holder.productNamepacksize.setText("("+CArtPAckSize.get(position)+" "+cartpackUnit.get(position)+")");
        Picasso.get().load(ProductImage.get(position)).placeholder(R.drawable.ic_menu_gallery).into(holder.ProductImageView);
        requestQueue = Volley.newRequestQueue(context);
        holder.deletecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAddItemHelpers.deleteItem(CartID.get(position));
                ((ItemInCartOffline)context).getCartitem();

            }
        });

        Log.d("","productNamepacksize"+cartUnitTotal.get(position));
        dbAddItemHelpers = new ItemInCartDBHelper(context);

        holder.PlusQuanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProductQuan.size() < 1){

                } else {
                    int tempQuan = Integer.valueOf(ProductQuan.get(position));
                    if (tempQuan == 0) {
                        //Toast.makeText(context, "Item Will Be Removed From Cart.", Toast.LENGTH_SHORT).show();
                    } else {
                        tempQuan++;

                       int price= Integer.parseInt(cartprice.get(position));
                       int qty=tempQuan;
                       Float total= Float.valueOf(price*qty);

                        Log.d("","CartID.get(position)"+CartID.get(position));
                        Log.d("","CartID.get(position)"+total);
                        Log.d("","CartID.get(position)"+tempQuan);

                        ItemInCardModel recordingItem = new ItemInCardModel(CartID.get(position),(tempQuan),total);
                        dbAddItemHelpers.updateItemIncart(recordingItem);
                        ((ItemInCartOffline)context).getCartitem();

                        /*
                        UpdateCart(ProductID.get(position), CartID.get(position), String.valueOf(tempQuan));
                    */}
                }
            }
        });

        holder.MinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProductQuan.size() < 1){

                } else {
                    int tempQuan = Integer.valueOf(ProductQuan.get(position));
                    if (tempQuan == 1) {
                        //Toast.makeText(context, "Item Will Be Removed From Cart.", Toast.LENGTH_SHORT).show();
                      //  deleteItem(CartID.get(position));
                       // ItemInCardModel recordingItem = new ItemInCardModel(CartID.get(position),String.valueOf(tempQuan),1000);
                        dbAddItemHelpers.deleteItem(CartID.get(position));
                        ((ItemInCartOffline)context).getCartitem();

                    } else {
                        tempQuan--;

                        int price= Integer.parseInt(cartprice.get(position));
                        int qty=tempQuan;
                        Float total= Float.valueOf(price*qty);


                        ItemInCardModel recordingItem = new ItemInCardModel(CartID.get(position),(tempQuan),total);
                        dbAddItemHelpers.updateItemIncart(recordingItem);
                        ((ItemInCartOffline)context).getCartitem();


                        // UpdateCart(ProductID.get(position), CartID.get(position), String.valueOf(tempQuan));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ProductName.size();
    }

    public void UpdateCart (final String ProductID, final String CartID, final String Qty)
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();


        String insertUrl = "http://microlanpos.com/demo/api2/update_cart";
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
                    //String msg = jsonObj.getString("msg");
                    if (stat.equals("1")){
                        ((Counters)context).GetCartProducts(userID);
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
                parameters.put("id", ProductID);
                parameters.put("cart_id", CartID);
                parameters.put("qty", Qty);
                return parameters;
            }
        };
        requestQueue.add(request);
    }

}
