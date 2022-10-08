package com.microlan.rushhandingoffline.Activities;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.microlan.rushhandingoffline.Adapters.PremiumMembershipAdapter;
import com.microlan.rushhandingoffline.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PremiumMembership extends AppCompatActivity implements PaymentResultListener {

    ArrayList<String> MonthsList, AmountList;
    PremiumMembershipAdapter premiumMembershipAdapter;
    GridView MembershipsGrid;
    String OrderID, TodaysDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_membership);
        getSupportActionBar().setTitle("Licenses");

        MembershipsGrid = (GridView)findViewById(R.id.membershipsGrid);

        MonthsList = new ArrayList<String>();
        AmountList = new ArrayList<String>();

        MonthsList.add("12 Months");
        AmountList.add("₹1");

        MonthsList.add("6 Months");
        AmountList.add("₹5399");

        MonthsList.add("3 Months");
        AmountList.add("₹2850");

        MonthsList.add("1 Months");
        AmountList.add("₹999");

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        TodaysDate = df.format(c);

        premiumMembershipAdapter = new PremiumMembershipAdapter(PremiumMembership.this, MonthsList, AmountList);
        premiumMembershipAdapter.notifyDataSetChanged();
        MembershipsGrid.setAdapter(premiumMembershipAdapter);

        MembershipsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startPayment("100");
                        break;
                    case 1:
                        startPayment("539900");
                        break;
                    case 2:
                        startPayment("285000");
                        break;
                    case 3:
                        startPayment("99900");
                        break;
                }
            }
        });
    }

    public void startPayment(String Amount) {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setImage(R.mipmap.mln_pos_icon);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */

        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: Rentomojo || HasGeek etc.
             */
            options.put("name", "Microlan POS");

            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */

            OrderID = getRandomNumberString();
            options.put("description", "POS"+OrderID);

            options.put("currency", "INR");

//            options.put("prefill.contact", Number);
//            options.put("prefill.email", Email);
            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */
            options.put("amount", Amount);

            checkout.open(activity, options);
        } catch(Exception e) {
            //Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        /**
         * Add your logic here for a successfull payment response
         */
        Toast.makeText(PremiumMembership.this, razorpayPaymentID, Toast.LENGTH_SHORT).show();
        Log.d("","razorpayPaymentID"+razorpayPaymentID);
        //paymentNoticeInApp(OrderID, userID, String.valueOf(Integer.valueOf(PaymentAmount.getText().toString()) * 100), "1", razorpayPaymentID);
    }

    @Override
    public void onPaymentError(int code, String response) {
        /**
         * Add your logic here for a failed payment response
         */
        Toast.makeText(PremiumMembership.this, response, Toast.LENGTH_SHORT).show();
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
