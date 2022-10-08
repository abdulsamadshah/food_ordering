package com.microlan.rushhandingoffline.Activities;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ajts.androidmads.library.ExcelToSQLite;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.ajts.androidmads.sqliteimpex.SQLiteImporterExporter;
import com.microlan.rushhandingoffline.Helper.DatabaseHelper;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperBills;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperCustomer;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperProductQty;
import com.microlan.rushhandingoffline.R;
import com.opencsv.CSVReader;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ImportData extends AppCompatActivity {

    TextView ImportProductsText, ImportOrdersText, ImportCustomersText, ImportAllData, ExportAllData;
    ArrayList<String> docPaths;
    SQLiteImporterExporter sqLiteImporterExporter;
    DatabaseHelper databaseHelper;
    DatabaseHelperBills databaseHelperBills;
    DatabaseHelperCustomer databaseHelperCustomer;
    DatabaseHelperProductQty databaseHelperProductQty;
    Boolean AddData = false;
    String FileChosen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_data);

        docPaths = new ArrayList<String>();

        databaseHelper = new DatabaseHelper(ImportData.this);
        databaseHelperBills = new DatabaseHelperBills(ImportData.this);
        databaseHelperCustomer = new DatabaseHelperCustomer(ImportData.this);
        databaseHelperProductQty = new DatabaseHelperProductQty(ImportData.this);

        ImportProductsText = (TextView)findViewById(R.id.importProductsText);
        ImportOrdersText = (TextView)findViewById(R.id.importOrdersText);
        ImportCustomersText = (TextView)findViewById(R.id.importCustomers);
        ImportAllData = (TextView)findViewById(R.id.importAllDate);
        ExportAllData = (TextView)findViewById(R.id.exportAllData);

        ImportProductsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(ImportData.this, NormalFilePickActivity.class);
                intent4.putExtra(Constant.MAX_NUMBER, 9);
                intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[] {"csv"});
                startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
                FileChosen = "Products";
            }
        });

        ImportOrdersText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(ImportData.this, NormalFilePickActivity.class);
                intent4.putExtra(Constant.MAX_NUMBER, 9);
                intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[] {"csv"});
                startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
                FileChosen = "Bills";
            }
        });

        ImportCustomersText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(ImportData.this, NormalFilePickActivity.class);
                intent4.putExtra(Constant.MAX_NUMBER, 9);
                intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[] {"csv"});
                startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
                FileChosen = "Customers";
            }
        });

        ImportAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(ImportData.this, NormalFilePickActivity.class);
                intent4.putExtra(Constant.MAX_NUMBER, 1);
                intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[] {"xls"});
                startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
                FileChosen = "Customers";
            }
        });

        ExportAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SQLiteImporterExporter sqLiteImporterExporter = new SQLiteImporterExporter(getApplicationContext(), databaseHelper.getDatabaseName());
//                // Listeners for Import and Export DB
//                sqLiteImporterExporter.setOnImportListener(new SQLiteImporterExporter.ImportListener() {
//                    @Override
//                    public void onSuccess(String message) {
//                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Exception exception) {
//                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                sqLiteImporterExporter.setOnExportListener(new SQLiteImporterExporter.ExportListener() {
//                    @Override
//                    public void onSuccess(String message) {
//                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Exception exception) {
//                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                try {
//                    sqLiteImporterExporter.exportDataBase(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Microlan_POS_OUTPUTS/");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);

                SQLiteToExcel sqliteToExcel = new SQLiteToExcel(ImportData.this, databaseHelper.getDatabaseName());
                sqliteToExcel.exportAllTables("POSProductsBackupDate:"+formattedDate+".xls", new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {

                    }
                    @Override
                    public void onCompleted(String filePath) {

                    }
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });

                SQLiteToExcel sqliteToExcel1 = new SQLiteToExcel(ImportData.this, databaseHelperBills.getDatabaseName());
                sqliteToExcel1.exportAllTables("POSOrdersBackupDate:"+formattedDate+".xls", new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {

                    }
                    @Override
                    public void onCompleted(String filePath) {

                    }
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });

                SQLiteToExcel sqliteToExcel2 = new SQLiteToExcel(ImportData.this, databaseHelperCustomer.getDatabaseName());
                sqliteToExcel2.exportAllTables("POSCustomersBackupDate:"+formattedDate+".xls", new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {

                    }
                    @Override
                    public void onCompleted(String filePath) {

                    }
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });

                SQLiteToExcel sqliteToExcel3 = new SQLiteToExcel(ImportData.this, databaseHelperProductQty.getDatabaseName());
                sqliteToExcel3.exportAllTables("POSProductsQtyBackupDate:"+formattedDate+".xls", new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {

                    }
                    @Override
                    public void onCompleted(String filePath) {
                        Toast.makeText(ImportData.this, "Successfully Exported All Data.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    public void GetFileProducts (final String FilePath)
    {
        try {
            File csvfile = new File(FilePath);
            CSVReader reader = new CSVReader(new FileReader(csvfile));
            String[] nextLine;

            int i = 0;

            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                //Toast.makeText(ImportData.this, nextLine[0] + nextLine[1] + "etc...", Toast.LENGTH_SHORT).show();
                if (i == 0){

                } else {
                    databaseHelper.addData(nextLine[1], nextLine[2], nextLine[3]);
                    i = i +1;
                }
                //Toast.makeText(ImportData.this, "Products Added Successfully.", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(ImportData.this, "Products Added Successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetFileBills (final String FilePath)
    {
        try {
            File csvfile = new File(FilePath);
            CSVReader reader = new CSVReader(new FileReader(csvfile));
            String[] nextLine;

            int i = 0;

            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                //Toast.makeText(ImportData.this, nextLine[0] + nextLine[1] + "etc...", Toast.LENGTH_SHORT).show();
                if (i == 0){

                } else {
                    databaseHelperBills.addData(nextLine[1], nextLine[2], nextLine[3]);
                    i = i +1;
                }
                //Toast.makeText(ImportData.this, "Products Added Successfully.", Toast.LENGTH_SHORT).show();
            }



            Toast.makeText(ImportData.this, "Bills Added Successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetFileCustomers (final String FilePath)
    {
        try {
            File csvfile = new File(FilePath);
            CSVReader reader = new CSVReader(new FileReader(csvfile));
            String[] nextLine;

            int i = 0;

            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                //Toast.makeText(ImportData.this, nextLine[0] + nextLine[1] + "etc...", Toast.LENGTH_SHORT).show();
                if (i == 0){

                } else {
                    databaseHelperCustomer.addData(nextLine[1], nextLine[2], nextLine[3]);
                    i = i +1;
                }
                //Toast.makeText(ImportData.this, "Products Added Successfully.", Toast.LENGTH_SHORT).show();
            }



            Toast.makeText(ImportData.this, "Customers Added Successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_PICK_FILE) {
            if (resultCode == RESULT_OK) {
                if (FileChosen.equals("Products")) {
                    ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    //GetFileProducts(list.get(0).getPath());
//                    try {
//                        sqLiteImporterExporter.importDataBase(list.get(0).getPath());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    ExcelToSQLite excelToSQLite = new ExcelToSQLite(getApplicationContext(), databaseHelper.getDatabaseName());
                    excelToSQLite.importFromFile(list.get(0).getPath(), new ExcelToSQLite.ImportListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onCompleted(String dbName) {

                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });

                    ExcelToSQLite excelToSQLite1 = new ExcelToSQLite(getApplicationContext(), databaseHelperBills.getDatabaseName());
                    excelToSQLite1.importFromFile(list.get(0).getPath(), new ExcelToSQLite.ImportListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onCompleted(String dbName) {

                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });

                    ExcelToSQLite excelToSQLite3 = new ExcelToSQLite(getApplicationContext(), databaseHelperCustomer.getDatabaseName());
                    excelToSQLite3.importFromFile(list.get(0).getPath(), new ExcelToSQLite.ImportListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onCompleted(String dbName) {

                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });

                    ExcelToSQLite excelToSQLite4 = new ExcelToSQLite(getApplicationContext(), databaseHelperProductQty.getDatabaseName());
                    excelToSQLite4.importFromFile(list.get(0).getPath(), new ExcelToSQLite.ImportListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onCompleted(String dbName) {
                            Toast.makeText(ImportData.this, "Successfully Imported All Data.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });


                } else if (FileChosen.equals("Bills")){
                    ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    GetFileBills(list.get(0).getPath());
                } else if (FileChosen.equals("Customers")){
                    ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    GetFileCustomers(list.get(0).getPath());
                }
            } else {

            }
        } else {

        }
    }
}
