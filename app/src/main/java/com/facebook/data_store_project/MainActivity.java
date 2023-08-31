package com.facebook.data_store_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

TextView textView1,textView2;
EditText editText1,editText2;
Button btn,btn1,btn2;
private int currentIndex = 0;
    MyDatabaseHelper dphelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1=(TextView)findViewById(R.id.textView1);
        textView2=(TextView)findViewById(R.id.textView2);
        editText1=(EditText)findViewById(R.id.edittext1);
        editText2=(EditText)findViewById(R.id.edittext2);
        btn=(Button)findViewById(R.id.buttonsave);
        btn1=(Button)findViewById(R.id.buttonpre);
        btn2=(Button)findViewById(R.id.buttonnex);
        dphelper = new MyDatabaseHelper(this);
        //dphelper.deleteAllData();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editText1.getText().toString();
                String phonenumber=editText2.getText().toString();
                dphelper.addcontact(name,phonenumber);
                Toast.makeText(MainActivity.this,"Added to Database",Toast.LENGTH_SHORT).show();
                editText1.getText().clear();
                editText2.getText().clear();
                restartActivity();
                /*dphelper.addcontact("uzair", "88836737367");
                dphelper.addcontact("majeed", "88836372367");
                dphelper.addcontact("najar", "888363736227");
                dphelper.addcontact("muhib", "8863736227");
                 fetchdetails();
                */
            }
        });
        ArrayList<ContactModel> arraycontacts=dphelper.fetchcontact();
        btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn1.setEnabled(true);
                    btn2.setText("Next");
                    if (currentIndex < arraycontacts.size()) {
                        String currentData1 = arraycontacts.get(currentIndex).name;
                        String currentData2 = arraycontacts.get(currentIndex).phoneNo;
                        textView1.setText("Name:\n"+currentData1);
                        textView2.setText("PhoneNumber:\n"+currentData2);

                        currentIndex++;
                        } else {
                            // Reset the index if it reaches the end of the dataList
                        Toast.makeText(MainActivity.this,"You Reached End",Toast.LENGTH_SHORT).show();
                        //currentIndex = 0;
                        btn2.setEnabled(false);
                        }
                }
            });
            btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setEnabled(true);
                if (currentIndex > 0) {
                    currentIndex--;
                    String currentData3 = arraycontacts.get(currentIndex).name;
                    String currentData4 = arraycontacts.get(currentIndex).phoneNo;
                    textView1.setText("Name:\n"+currentData3);
                    textView2.setText("PhoneNumber:\n"+currentData4);
                } else {
                    // Move to the last data if at the beginning of the dataList
                    btn1.setEnabled(false);
                    Toast.makeText(MainActivity.this,"You Reached To Start",Toast.LENGTH_SHORT).show();
                    //currentIndex = arraycontacts.size();
                }
            }
        });
            fetchdetails();
    }
       /*ContactModel model=new ContactModel();
        model.id=1;
        model.phoneNo="123567899";
        dphelper.updateContact(model);*/


       //dphelper.deletecontact(1);


       /* dphelper.deleteAllData();
        fetchdetails();*/


    public void fetchdetails(){
        ArrayList<ContactModel> arraycontacts=dphelper.fetchcontact();
        for(int i=0;i<arraycontacts.size();i++)
            Log.d("Contact_Info","Name:"+arraycontacts.get(i).name+",PhoneNumber:"+arraycontacts.get(i).phoneNo);
    }
    private void restartActivity() {
        Intent intent = getIntent();
        finish(); // Close the current activity
        startActivity(intent); // Start the activity again
    }
}

