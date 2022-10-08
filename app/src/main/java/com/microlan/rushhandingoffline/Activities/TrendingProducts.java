package com.microlan.rushhandingoffline.Activities;

import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.microlan.rushhandingoffline.Adapters.CustomerPointsAdapter;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperTrendingProducts;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.Views.ExpandableHeightGridView;

import java.util.ArrayList;

public class TrendingProducts extends AppCompatActivity {

    ArrayList<String> ProductID, ProductName, ProductSoldTimes;
    CustomerPointsAdapter customerPointsAdapter;
    ExpandableHeightGridView TopSoldProductsGrid;
    DatabaseHelperTrendingProducts databaseHelperTrendingProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_products);
        getSupportActionBar().setTitle("Trending Products");

        databaseHelperTrendingProducts = new DatabaseHelperTrendingProducts(TrendingProducts.this);
        TopSoldProductsGrid = (ExpandableHeightGridView)findViewById(R.id.topSoldProductsGrid);

        ProductID = new ArrayList<String>();
        ProductName = new ArrayList<String>();
        ProductSoldTimes = new ArrayList<String>();

        try {
            Cursor data2 = databaseHelperTrendingProducts.getData();
            while (data2.moveToNext()) {
                for (int i = 0; i <= 10; i++) {
                    String TempProductName = data2.getString(2);
                    if (ProductName.contains(TempProductName)) {
                        int TempIndex = ProductName.indexOf(TempProductName);
                        int TempSoldTimes = Integer.valueOf(ProductSoldTimes.get(TempIndex)) + 1;
                        ProductSoldTimes.set(TempIndex, String.valueOf(TempSoldTimes));
                    } else {
                        ProductID.add("1");
                        ProductName.add(TempProductName);
                        ProductSoldTimes.add("1");
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        customerPointsAdapter = new CustomerPointsAdapter(TrendingProducts.this, ProductID, ProductName, ProductSoldTimes);
        TopSoldProductsGrid.setAdapter(customerPointsAdapter);
        TopSoldProductsGrid.setExpanded(true);
    }
}
