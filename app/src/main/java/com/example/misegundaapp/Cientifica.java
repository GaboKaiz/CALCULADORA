package com.example.misegundaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.util.Stack;

public class Cientifica extends AppCompatActivity {
    private EditText txtDisplay;
    private String currentInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cientifica);

        txtDisplay = findViewById(R.id.txtDisplay);
        Button buttonRetroceder = findViewById(R.id.buttonRetroceder);

        // Configurar botón de retroceso
        buttonRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad y vuelve a la anterior
            }
        });
    }

    // Manejo de números y paréntesis
    public void onDigitClick(View view) {
        Button button = (Button) view;
        currentInput += button.getText().toString();
        txtDisplay.setText(currentInput);
    }

    // Manejo de operadores matemáticos
    public void onOperatorClick(View view) {
        Button button = (Button) view;
        currentInput += " " + button.getText().toString() + " ";
        txtDisplay.setText(currentInput);
    }

    // Manejo de funciones científicas
    public void onFunctionClick(View view) {
        Button button = (Button) view;
        String function = button.getText().toString();

        try {
            double value = Double.parseDouble(currentInput);
            double result = 0;

            switch (function) {
                case "sin":
                    result = Math.sin(Math.toRadians(value));
                    break;
                case "cos":
                    result = Math.cos(Math.toRadians(value));
                    break;
                case "tan":
                    result = Math.tan(Math.toRadians(value));
                    break;
                case "√":
                    result = Math.sqrt(value);
                    break;
            }

            currentInput = new DecimalFormat("#.######").format(result);
            txtDisplay.setText(currentInput);
        } catch (Exception e) {
            txtDisplay.setText("Error");
            currentInput = "";
        }
    }

    // Manejo del botón de limpiar
    public void onClearClick(View view) {
        currentInput = "";
        txtDisplay.setText("");
    }

    // Evaluación de la expresión matemática
    public void onEqualClick(View view) {
        try {
            double result = evaluateExpression(currentInput);
            currentInput = new DecimalFormat("#.######").format(result);
            txtDisplay.setText(currentInput);
        } catch (Exception e) {
            txtDisplay.setText("Error");
            currentInput = "";
        }
    }

    // Método para evaluar expresiones matemáticas
    private double evaluateExpression(String expression) {
        String[] tokens = expression.split(" ");
        Stack<Double> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                numbers.push(Double.parseDouble(token));
            } else if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^")) {
                while (!operators.isEmpty() && precedence(token) <= precedence(operators.peek())) {
                    double num2 = numbers.pop();
                    double num1 = numbers.pop();
                    numbers.push(applyOperator(operators.pop(), num1, num2));
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            double num2 = numbers.pop();
            double num1 = numbers.pop();
            numbers.push(applyOperator(operators.pop(), num1, num2));
        }

        return numbers.pop();
    }

    // Método para obtener la precedencia de operadores
    private int precedence(String operator) {
        switch (operator) {
            case "^": return 3;
            case "*":
            case "/": return 2;
            case "+":
            case "-": return 1;
            default: return 0;
        }
    }

    // Aplicar operador matemático a dos números
    private double applyOperator(String operator, double a, double b) {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            case "^": return Math.pow(a, b);
            default: return 0;
        }
    }
}
