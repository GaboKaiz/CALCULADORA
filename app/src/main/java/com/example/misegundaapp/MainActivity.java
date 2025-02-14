package com.example.misegundaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    EditText edtNumero1, edtNumero2;
    TextView txtResultado;
    Button btnSumar, btnRestar, btnMultiplicar, btnDividir, btnBorrarChat, btnCalCuladoraCientifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular elementos de la interfaz con el código Java
        edtNumero1 = findViewById(R.id.edtNumero1);
        edtNumero2 = findViewById(R.id.edtNumero2);
        txtResultado = findViewById(R.id.txtResultado);
        btnSumar = findViewById(R.id.btnSumar);
        btnRestar = findViewById(R.id.btnRestar);
        btnMultiplicar = findViewById(R.id.btnMultiplicar);
        btnDividir = findViewById(R.id.btnDividir);
        btnBorrarChat = findViewById(R.id.btnBorrarChat); // Nuevo botón
        btnCalCuladoraCientifica = findViewById(R.id.btnCalCuladoraCientifica); // Nuevo botón

        // Eventos de los botones
        btnSumar.setOnClickListener(v -> calcular("+"));
        btnRestar.setOnClickListener(v -> calcular("-"));
        btnMultiplicar.setOnClickListener(v -> calcular("*"));
        btnDividir.setOnClickListener(v -> calcular("/"));

        btnCalCuladoraCientifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cientifica.class);
                startActivity(intent);
            }
        });

        // Evento del botón Borrar Chat
        btnBorrarChat.setOnClickListener(v -> {
            edtNumero1.setText("");
            edtNumero2.setText("");
            txtResultado.setText("");
        });
    }

    private void calcular(String operador) {
        String num1 = edtNumero1.getText().toString();
        String num2 = edtNumero2.getText().toString();

        if (num1.isEmpty() || num2.isEmpty()) {
            txtResultado.setText("Ingresa ambos números");
            return;
        }

        double numero1 = Double.parseDouble(num1);
        double numero2 = Double.parseDouble(num2);
        double resultado = 0;

        switch (operador) {
            case "+":
                resultado = numero1 + numero2;
                break;
            case "-":
                resultado = numero1 - numero2;
                break;
            case "*":
                resultado = numero1 * numero2;
                break;
            case "/":
                if (numero2 == 0) {
                    txtResultado.setText("No se puede dividir por 0");
                    return;
                }
                resultado = numero1 / numero2;
                break;
        }

        txtResultado.setText("Resultado: " + resultado);
    }
}
