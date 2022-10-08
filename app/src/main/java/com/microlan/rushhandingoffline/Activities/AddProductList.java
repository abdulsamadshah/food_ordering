package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;
import java.util.List;

public class AddProductList extends AppCompatActivity {
    int k = -1;
    int flag;
    int ss=0;
    ArrayList<String> applnserverinstnos = new ArrayList<String>();
    public static EditText textView[] = new EditText[100];
    ArrayList<Integer> count=new ArrayList<>();
    int i=0;

    TextView addmore;
    CardView addcart;
    Button productadd;
    LinearLayout container;
    int j;
    List<EditText> allEds = new ArrayList<EditText>();
    EditText packsize1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_list);
        getSupportActionBar().setTitle("Add Product");

        addmore=findViewById(R.id.addmore);
        addcart=findViewById(R.id.addcart);
        productadd=findViewById(R.id.productadd);
        packsize1=findViewById(R.id.packsize1);
        final LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        container = (LinearLayout)findViewById(R.id.container);

        allEds.add(packsize1);
        addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  addNewView();
                j++;
               /* for( j=0;j<=j;j++)
                {
               */     LayoutInflater layoutInflater =
                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.addrow, null);
                    count.add(j);

                    EditText barcode=addView.findViewById(R.id.barcode);
                    EditText mrp=addView.findViewById(R.id.mrp);
                    EditText price=addView.findViewById(R.id.price);
                    final EditText packsize=addView.findViewById(R.id.packsize);
                    TextView buttonRemove = (TextView)addView.findViewById(R.id.remove);
                   /* allEds.add(barcode);
                    allEds.add(mrp);
                    allEds.add(price);
                 */   allEds.add(packsize);

                    buttonRemove.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                       //     count.remove(j);
                            allEds.remove(packsize);

                            ((LinearLayout)addView.getParent()).removeView(addView);
                        }});
                    container.addView(addView);

               // }



               // applnserverinstnos.add(price.getText().toString());
            }
        });



        productadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("applserver   "+".."+allEds.size());
                System.out.println("applserver   "+count.size());

                String[] strings = new String[allEds.size()];

                for(int i=0; i < allEds.size(); i++){
                    strings[i] = allEds.get(i).getText().toString();
                    Log.e("My data ", strings[i]);
                }


            }
        });
    }

}