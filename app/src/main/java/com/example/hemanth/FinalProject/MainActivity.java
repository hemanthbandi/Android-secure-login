package com.example.hemanth.FinalProject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText emailAdress;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name = (EditText)findViewById(R.id.name);
        emailAdress = (EditText) findViewById(R.id.email);
        register = (Button) findViewById(R.id.register);
      final   DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        String email = db.getContact();
        if(!TextUtils.isEmpty(email)&&email!=null)
        //
        {
            // String email = db.getContact(name.getText().toString());
            //   Log.e("Juno ", "Inserting .."+email);
            // Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent
            );
            finish();

        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Insert: ", "Inserting ..");



                    long result =  db.addContact(new Contact(name.getText().toString(), emailAdress.getText().toString()));
                    if(result>0) {
                        Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        startActivity(intent
                        );
                        finish();

                }

            }
        });
    }

}
