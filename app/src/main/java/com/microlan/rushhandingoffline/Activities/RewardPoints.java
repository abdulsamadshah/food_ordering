package com.microlan.rushhandingoffline.Activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.microlan.rushhandingoffline.Adapters.CustomerPointsAdapter;
import com.microlan.rushhandingoffline.Helper.DatabaseHelper;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperCustomer;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperCustomerPoints;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.Views.ExpandableHeightGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.microlan.rushhandingoffline.Activities.SplashScreen.decrypt;
import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;

public class RewardPoints extends AppCompatActivity {

    ArrayList<String> CustomerID, CustomerName, CustomerPoints;
    CustomerPointsAdapter customerPointsAdapter;
    ExpandableHeightGridView CustomerPointsGrid;
    Button SubmitBtn;
    EditText PointsEdit, PointsHundredEdit;
    DatabaseHelperCustomerPoints databaseHelperCustomerPoints;
    SharedPreferences sharedPreferences;
    String PointsValue, PointUpdatedString;
    TextView LastUpdatedDateText;
    DatabaseHelper databaseHelper;
    DatabaseHelperCustomer databaseHelperCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_points);
        getSupportActionBar().setTitle("Reward Points");

        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        PointsValue = sharedPreferences.getString("point_value", "");
        PointUpdatedString = sharedPreferences.getString("point_updated_date", "");

        databaseHelperCustomerPoints = new DatabaseHelperCustomerPoints(RewardPoints.this);
        databaseHelper = new DatabaseHelper(RewardPoints.this);
        databaseHelperCustomer = new DatabaseHelperCustomer(RewardPoints.this);

        CustomerPointsGrid = (ExpandableHeightGridView)findViewById(R.id.custmoersGrid);
        PointsEdit = (EditText)findViewById(R.id.pointsEdit);
        PointsHundredEdit = (EditText)findViewById(R.id.pointHundredEdit);
        LastUpdatedDateText = (TextView)findViewById(R.id.lastUpdatedDate);
        SubmitBtn = (Button)findViewById(R.id.submitPointBtn);

        if (PointUpdatedString.equals("")){
            LastUpdatedDateText.setText("");
        } else {
            LastUpdatedDateText.setText("Last Updated Date : " + PointUpdatedString);
        }

        PointsEdit.setText(PointsValue);

        CustomerID = new ArrayList<String>();
        CustomerName = new ArrayList<String>();
        CustomerPoints = new ArrayList<String>();

        try {
            Cursor data = databaseHelperCustomer.getData();
            while (data.moveToNext()) {
                CustomerID.add("1");
                CustomerName.add(data.getString(1));
                //CustomerPoints.add(data.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < CustomerName.size(); i++) {
            String TempCustomerPoints = sharedPreferences.getString("points("+CustomerName.get(i)+")", encrypt("0"));
            TempCustomerPoints = decrypt(TempCustomerPoints);
            CustomerPoints.add(TempCustomerPoints);
            //Toast.makeText(RewardPoints.this, "points("+CustomerName.get(i)+")", Toast.LENGTH_SHORT).show();
            //Toast.makeText(RewardPoints.this, TempCustomerPoints, Toast.LENGTH_SHORT).show();
        }

//        String TempCustomerPoints = sharedPreferences.getString("points(Sushant)", encrypt("0"));
//        TempCustomerPoints = decrypt(TempCustomerPoints);
//        CustomerPoints.add(TempCustomerPoints);

        //Toast.makeText(RewardPoints.this, CustomerName.toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(RewardPoints.this, CustomerPoints.toString(), Toast.LENGTH_SHORT).show();

        customerPointsAdapter = new CustomerPointsAdapter(RewardPoints.this, CustomerID, CustomerName, CustomerPoints);
        CustomerPointsGrid.setAdapter(customerPointsAdapter);
        CustomerPointsGrid.setExpanded(true);

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PointsEdit.getText().toString().isEmpty()){
                    Toast.makeText(RewardPoints.this, "Please Enter Points Value.", Toast.LENGTH_SHORT).show();
                } else if (PointsHundredEdit.getText().toString().isEmpty()){
                    Toast.makeText(RewardPoints.this, "Please Enter Points Received.", Toast.LENGTH_SHORT).show();
                } else {
                    Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);

                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c);
                    Float PointReceived = Float.valueOf(PointsHundredEdit.getText().toString())/100;
                    SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
                    editor.putString("point_value", PointsEdit.getText().toString());
                    editor.putString("point_received", String.valueOf(PointReceived));
                    editor.putString("point_updated_date", formattedDate);
                    editor.apply();
                    Toast.makeText(RewardPoints.this, "Points Added Successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
