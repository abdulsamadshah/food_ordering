package com.microlan.rushhandingoffline.DBAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.microlan.rushhandingoffline.Activities.Counters;
import com.microlan.rushhandingoffline.Activities.ItemInCartOffline;
import com.microlan.rushhandingoffline.Activities.OfflineCounter;
import com.microlan.rushhandingoffline.Adapters.ProductCounterAdapter;
import com.microlan.rushhandingoffline.DB.AllProductDBHelper;
import com.microlan.rushhandingoffline.DB.ItemInCartDBHelper;
import com.microlan.rushhandingoffline.Dialogs.AddQuentity;
import com.microlan.rushhandingoffline.Dialogs.CustomFilterDialog;
import com.microlan.rushhandingoffline.Dialogs.ProductInfo;
import com.microlan.rushhandingoffline.OfflineModel.AllProductModel;
import com.microlan.rushhandingoffline.OfflineModel.ItemInCardModel;
import com.microlan.rushhandingoffline.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CounterAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> ProductID, ProductName, ProductPrice, ProductImage, ProductQuan;

    AddQuentity addQuentity;
    ProgressDialog progressDialog;
    String Cartimage,PacK_price,PacK_unit,Pack_size,userID;
    String SizeStat,ColorStat;
    RequestQueue requestQueue;
    CustomFilterDialog customFilterDialog;
    ProductInfo productinfo;
    String Size="0",Colour="0",PAcksize="0",Packsizestatus="0",selectsize="0",packsize_status="0";
    ArrayList<String> SizeID, SizeName, ColorID, ColorName, ColorCode,ImageString,Packsize,PackPrice,PackMrp,PackUnit,productPrices ,productSizes,productUnit,productDesc,productTerm,ProductCode;

    ArrayList<AllProductModel> arrayListAudios;
    AllProductDBHelper dbHelper;
    ArrayList<String> singlesize,singleprice;

    ItemInCartDBHelper dbAddItemHelpers;

    Button add_tocart;

    public CounterAdapter(OfflineCounter offlineCounter, ArrayList<String> productID, ArrayList<String> productName, ArrayList<String> productPrice, ArrayList<String> productImage, ArrayList<String> productQuan, String userID, ArrayList<String> productPrices, ArrayList<String> productSizes, ArrayList<String> productUnit, ArrayList<String> productDesc, ArrayList<String> productTerm, ArrayList<String> productCode) {

        this.context = offlineCounter;
        this.ProductID = productID;
        this.ProductName = productName;
        this.ProductPrice = productPrice;
        this.ProductImage = productImage;
        this.ProductQuan = productQuan;
        this.userID = userID;
        this.productPrices = productPrices;
        this.productSizes = productSizes;
        this.productUnit = productUnit;
        this.productDesc = productDesc;
        this.productTerm = productTerm;
        this.ProductCode = productCode;

        singlesize=new ArrayList<>();
        singleprice=new ArrayList<>();

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
        dbHelper = new AllProductDBHelper(context);
        arrayListAudios =dbHelper.getallproduct();
        dbAddItemHelpers = new ItemInCartDBHelper(context);

        TextView ProductNameText = (TextView)gridView.findViewById(R.id.productNameText);
        TextView ProductPriceText = (TextView)gridView.findViewById(R.id.productPriceText);
        ImageView ProductImageView = (ImageView)gridView.findViewById(R.id.productImage);
        ImageView info = (ImageView)gridView.findViewById(R.id.info);
        TextView AddProductToCartBtn = (TextView)gridView.findViewById(R.id.addBtn);
        LinearLayout productlay=gridView.findViewById(R.id.productlay);
        customFilterDialog = new CustomFilterDialog(context, SizeName, ColorName);

        Picasso.get().load(ProductImage.get(position)).placeholder(R.drawable.ic_menu_gallery).into(ProductImageView);

        ProductNameText.setText(ProductName.get(position));
        ProductPriceText.setText(ProductPrice.get(position));

        addQuentity = new AddQuentity((Activity) context);
        productinfo = new ProductInfo((Activity) context);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    productinfo.show();

                    final TextView name = (TextView)productinfo.findViewById(R.id.name);
                    final TextView pricess = (TextView)productinfo.findViewById(R.id.price);
                    final TextView desc = (TextView)productinfo.findViewById(R.id.desc);
                    final TextView cond = (TextView)productinfo.findViewById(R.id.cond);
                    final TextView sizes = (TextView)productinfo.findViewById(R.id.size);
                    final ImageView image = (ImageView) productinfo.findViewById(R.id.image);
                    final ImageView cancel = (ImageView) productinfo.findViewById(R.id.cancel);
                       add_tocart = (Button) productinfo.findViewById(R.id.add_tocarts);

                    name.setText("Name : "+ProductName.get(position));
                    desc.setText("Description  : "+productDesc.get(position));
                    cond.setText("Terms & Conditions : "+productTerm.get(position));
                    pricess.setText("Price : "+productSizes.get(position));
                    sizes.setText("Size  : "+productPrices.get(position));

                    Picasso.get().load(ProductImage.get(position)).placeholder(R.drawable.ic_menu_gallery).into(image);


                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productinfo.dismiss();

                        }
                    });

                    add_tocart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

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

                            singlesize.clear();
                            singleprice.clear();
                            String[] res = productPrices.get(position).split("[,]");

                            for(String name : res){
                                singlesize.add(name);
                            }

                            String[] costs =productSizes .get(position).split("[,]");

                            for(String cost : costs){
                                singleprice.add(cost);
                            }


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


                            packsizerec.setAdapter(new PackSizeAdapter());
                            ColorRecy.setAdapter(new ColorAdapter());



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

                                    QuantityEdit.setText(String.valueOf(TempQuan));
                                }
                            });

                            saveButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(singlesize.size()>0&&!selectsize.equals("1"))
                                    {
                                        Toast.makeText(context,"Please Select Size",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        selectsize="0";

                                        Float price= Float.parseFloat(PacK_price);
                                        int qty= Integer.parseInt(QuantityEdit.getText().toString());
                                        Float Total=price*qty;

                                        ((OfflineCounter)context).addtocart(ProductID.get(position),ProductName.get(position),ProductImage.get(position),PacK_price,qty,Total,PAcksize,"","",PacK_unit,"",userID,ProductCode.get(position));


                                        addQuentity.dismiss();
                                        customFilterDialog.dismiss();
                                        productinfo.dismiss();

                                    }
                                    addQuentity.dismiss();
                                    customFilterDialog.dismiss();
                                    productinfo.dismiss();

                                }
                            });



                        }
                    });


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

        productlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                singlesize.clear();
                singleprice.clear();
              /*  String[] res = productSizes.get(position).split("[,]");

                for(String name : res){
                    singlesize.add(name);
                }

                String[] costs =productPrices .get(position).split("[,]");

                for(String cost : costs){
                    singleprice.add(cost);
                }
*/

                String[] res = productPrices.get(position).split("[,]");

                for(String name : res){
                    singlesize.add(name);
                }

                String[] costs =productSizes .get(position).split("[,]");

                for(String cost : costs){
                    singleprice.add(cost);
                }


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


                                    packsizerec.setAdapter(new PackSizeAdapter());
                                    ColorRecy.setAdapter(new ColorAdapter());



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

                                            QuantityEdit.setText(String.valueOf(TempQuan));
                                        }
                                    });

                                    saveButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            if(singlesize.size()>0&&!selectsize.equals("1"))
                                            {
                                                Toast.makeText(context,"Please Select Size",Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                selectsize="0";

                                                Float price= Float.parseFloat(PacK_price);
                                                int qty= Integer.parseInt(QuantityEdit.getText().toString());
                                                Float Total=price*qty;

                                                ((OfflineCounter)context).addtocart(ProductID.get(position),ProductName.get(position),ProductImage.get(position),PacK_price,qty,Total,PAcksize,"","",PacK_unit,"",userID,ProductCode.get(position));



                                                addQuentity.dismiss();
                                                customFilterDialog.dismiss();
                                                productinfo.dismiss();

                                            }
                                            addQuentity.dismiss();
                                            customFilterDialog.dismiss();
                                            productinfo.dismiss();

                                        }
                                    });



            }
        });


        return gridView;
    }



    @Override
    public int getCount() {
        return ProductID.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

/*
    private class SizeListAdapter extends RecyclerView.Adapter<SizeListAdapter.MyViewHolder> {


        private  int lastClickedPosition = -1;
        private int selectedItem=-1;


        @NonNull
        @Override
        public SizeListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sizelist, parent, false);
            return new SizeListAdapter.MyViewHolder(view);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull final SizeListAdapter.MyViewHolder myViewHolder, final int i) {



            myViewHolder.packsize.setText(productSizes.get(i));

            Log.d("","productSizes.get(i) "+productSizes.get(i));

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
*/

    private class ColorAdapter  extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {

        private int selectedItem=0;

        @NonNull
        @Override
        public ColorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.colorlist, parent, false);
            return new ColorAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ColorAdapter.MyViewHolder myViewHolder, final int i) {


            if (selectedItem == i) {
                myViewHolder.colorcard.setBackgroundResource((R.color.colorAccent));
            }
            else {

            }
            myViewHolder.colorcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Colour=ColorName.get(i);

                    int previousItem = selectedItem;
                    selectedItem = i;

                    notifyItemChanged(previousItem);
                    notifyItemChanged(i);

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
        private int selectedItem=0;
        List<String> productlist;
        List<String> elephantList;
        int size;
        @NonNull
        @Override
        public PackSizeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sizelist, parent, false);
            return new PackSizeAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PackSizeAdapter.MyViewHolder myViewHolder, final int i) {


            productlist=new ArrayList<>();
            myViewHolder.packsize.setText(singlesize.get(i)+"  "+productUnit.get(0) +" / ₹ "+singleprice.get(i));


            if (selectedItem == i) {

                myViewHolder.sizelay.setBackgroundResource((R.color.colorAccent));
                selectsize="1";

                PAcksize=singlesize.get(i);
                PacK_price=singleprice.get(i);
                PacK_unit=productUnit.get(0);

            }
            else {

            }

            myViewHolder.sizelay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PAcksize=singlesize.get(i);
                    PacK_price=singleprice.get(i);
                    PacK_unit=productUnit.get(0);

                    selectsize="1";

                    Log.d("","PacK_price"+PacK_price);
                    Log.d("","PacK_price"+PacK_price);
                    int previousItem = selectedItem;
                    selectedItem = i;

                    notifyItemChanged(previousItem);
                    notifyItemChanged(i);
                }
            });


        }

        @Override
        public int getItemCount() {

            Log.d("","singlesize.size()"+singlesize.size());
            return singlesize.size();
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
