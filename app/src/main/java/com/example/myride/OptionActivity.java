package com.example.myride;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;

public class OptionActivity extends AppCompatActivity {

    private TextView price, discount;
    private Button apply;
    double fare, code= 0.0;
    private EditText mPromoCode;
    private Button logout;

    FirebaseAuth firebaseAuth;

    double promo = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        price = findViewById(R.id.pric);
        mPromoCode = findViewById(R.id.promocode_M);
        discount = findViewById(R.id.dis);
        apply = findViewById(R.id.apply);
        logout = findViewById(R.id.button_H);

        firebaseAuth = FirebaseAuth.getInstance();


        fare = getIntent().getDoubleExtra("Price", 0);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formatted = decimalFormat.format(fare);
        price.setText(formatted);
        discount.setText(formatted);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String promocode = mPromoCode.getText().toString();
                if (promocode.equals("myride10")) {
                    code = Double.parseDouble(promocode.replaceAll("[^0-9]", ""));
                    promo = fare - code;
                    DecimalFormat dFormat = new DecimalFormat("0.00");
                    String format = dFormat.format(promo);
                    discount.setText(format);
                    mPromoCode.setEnabled(false);
                    apply.setEnabled(false);
                }
                else {
                    Toast.makeText(OptionActivity.this, "Invalid PromoCode", Toast.LENGTH_SHORT).show();

                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }

    public void btn_ShowDialog(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(OptionActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

        Button cOD, uPI, cancel;
        cOD = (Button)mView.findViewById(R.id.cod);
        uPI = (Button)mView.findViewById(R.id.upi);
        cancel = (Button)mView.findViewById(R.id.cancleride);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        cOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        uPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choose = new Intent(OptionActivity.this,MainActivity.class);
                choose.putExtra("Price", (fare-code));
                startActivity(choose);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
