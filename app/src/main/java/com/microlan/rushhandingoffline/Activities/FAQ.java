package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.microlan.rushhandingoffline.Adapters.FAQAdapter;
import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;

public class FAQ extends AppCompatActivity {

    ArrayList<String> FAQQuestion, FAQDescription;
    FAQAdapter faqAdapter;
    GridView FAQGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        getSupportActionBar().setTitle("FAQ");

        FAQGrid = (GridView)findViewById(R.id.faqGrid);

        FAQQuestion = new ArrayList<String>();
        FAQDescription = new ArrayList<String>();

        FAQQuestion.add("How to update WhatsApp");
        FAQDescription.add("You can easily update WhatsApp from your phone's application store. Please note if you received a message that isn't supported by your version of WhatsApp, you'll need to update WhatsApp.");

        FAQQuestion.add("How to update WhatsApp");
        FAQDescription.add("You can easily update WhatsApp from your phone's application store. Please note if you received a message that isn't supported by your version of WhatsApp, you'll need to update WhatsApp.");

        FAQQuestion.add("How to update WhatsApp");
        FAQDescription.add("You can easily update WhatsApp from your phone's application store. Please note if you received a message that isn't supported by your version of WhatsApp, you'll need to update WhatsApp.");

        FAQQuestion.add("How to update WhatsApp");
        FAQDescription.add("You can easily update WhatsApp from your phone's application store. Please note if you received a message that isn't supported by your version of WhatsApp, you'll need to update WhatsApp.");

        FAQQuestion.add("How to update WhatsApp");
        FAQDescription.add("You can easily update WhatsApp from your phone's application store. Please note if you received a message that isn't supported by your version of WhatsApp, you'll need to update WhatsApp.");

        FAQQuestion.add("How to update WhatsApp");
        FAQDescription.add("You can easily update WhatsApp from your phone's application store. Please note if you received a message that isn't supported by your version of WhatsApp, you'll need to update WhatsApp.");

        faqAdapter = new FAQAdapter(FAQ.this, FAQQuestion, FAQDescription);
        FAQGrid.setAdapter(faqAdapter);
    }

}
