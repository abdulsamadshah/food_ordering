package com.microlan.rushhandingoffline.Activities;

import android.database.Cursor;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.microlan.rushhandingoffline.Adapters.CustomersAdapter;
import com.microlan.rushhandingoffline.Dialogs.FileNameDialog;
import com.microlan.rushhandingoffline.Helper.CSVWriter;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperCustomer;
import com.microlan.rushhandingoffline.R;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Customers extends AppCompatActivity {

    ArrayList<String> CustomerNames, CustomerLocation, CustomerNumber;
    DatabaseHelperCustomer databaseHelperCustomer;
    CustomersAdapter customersAdapter;
    GridView CustomersGrid;
    String [] ColumnName;
    Button ExportCustomersBtn;
    FileNameDialog fileNameDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        getSupportActionBar().setTitle("Customers");

        fileNameDialog = new FileNameDialog(Customers.this);

        CustomersGrid = (GridView)findViewById(R.id.customersGrid);
        ExportCustomersBtn = (Button)findViewById(R.id.exportCustomersBtn);

        databaseHelperCustomer = new DatabaseHelperCustomer(Customers.this);
        ColumnName = new String[]{"Name", "Phone", "Address"};

        CustomerNames = new ArrayList<String>();
        CustomerLocation = new ArrayList<String>();
        CustomerNumber = new ArrayList<String>();

        try {
            Cursor Customers1 = databaseHelperCustomer.getData();
            while (Customers1.moveToNext()) {
                CustomerNames.add(Customers1.getString(1));
                CustomerLocation.add(Customers1.getString(3));
                CustomerNumber.add(Customers1.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        customersAdapter = new CustomersAdapter(Customers.this, CustomerNames, CustomerLocation, CustomerNumber);
        customersAdapter.notifyDataSetChanged();
        CustomersGrid.setAdapter(customersAdapter);

        ExportCustomersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileNameDialog.show();

                final EditText ExportNameEdit = (EditText)fileNameDialog.findViewById(R.id.fileNameEdit);
                Button SaveButton = (Button)fileNameDialog.findViewById(R.id.saveBtn);

                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();

                ExportNameEdit.setText(ts);

                SaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ExportNameEdit.getText().toString().isEmpty()){
                            Toast.makeText(Customers.this, "File Name Cannot Be Empty.", Toast.LENGTH_SHORT).show();
                        } else if (ExportNameEdit.getText().toString().length() < 6){
                            Toast.makeText(Customers.this, "File Name Must Be At Least 6 Characters Long.", Toast.LENGTH_SHORT).show();
                        } else {
                            ExportDB(ExportNameEdit.getText().toString());
                        }
                        fileNameDialog.dismiss();
                    }
                });
            }
        });
    }

    public void ExportDB (String FileName)
    {
        File exportDir = new File(Environment.getExternalStorageDirectory(), "Microlan POS Outputs");
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        File file = new File(exportDir, FileName + ".csv");

        try{

            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            csvWrite.writeNext(ColumnName);

            for (int i = 0; i< CustomerNames.size(); i++){
                String arrStr [] = {CustomerNames.get(i), CustomerNumber.get(i), CustomerLocation.get(i)};
                csvWrite.writeNext(arrStr);
            }

            csvWrite.close();
            Toast.makeText(Customers.this, "CSV File Successfully Created.", Toast.LENGTH_SHORT).show();



        } catch (Exception e){
            Toast.makeText(Customers.this, "Can not Create CSV File.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
