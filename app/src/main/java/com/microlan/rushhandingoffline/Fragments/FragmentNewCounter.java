package com.microlan.rushhandingoffline.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.microlan.rushhandingoffline.Adapters.ProductCounterAdapter;
import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.microlan.rushhandingoffline.Activities.SplashScreen.decrypt;
import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;

public class FragmentNewCounter extends Fragment {

    GridView ProductsGrid;
    ArrayList<String> ProductID, ProductName, ProductPrice, ProductImage, ProductQuan;
    ProductCounterAdapter productCounterAdapter;
    String Intro = "",UserID;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_counter, container, false);
        ProductsGrid = (GridView)rootView.findViewById(R.id.productsGrid);

        sharedPreferences = getContext().getSharedPreferences("myPref", MODE_PRIVATE);
        Intro = sharedPreferences.getString("Intro", encrypt("No"));
        UserID = sharedPreferences.getString("id", "");
        Intro = decrypt(Intro);

        ProductID = new ArrayList<String>();
        ProductName = new ArrayList<String>();
        ProductImage = new ArrayList<String>();
        ProductPrice = new ArrayList<String>();
        ProductQuan = new ArrayList<String>();

        ProductID.add("1");
        ProductName.add("Pepsi 500ml");
        ProductImage.add("http://www.pepsi.ca/sites/pepsi.ca/files/Pepsi%20355%20mL%20Can%20Dry%20Straight%20Bilingual-1_m196x.png");
        ProductPrice.add("₹45");
        ProductQuan.add("1");

        productCounterAdapter = new ProductCounterAdapter(getContext(), ProductID, ProductName, ProductPrice, ProductImage, ProductQuan, UserID);
        ProductsGrid.setAdapter(productCounterAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
