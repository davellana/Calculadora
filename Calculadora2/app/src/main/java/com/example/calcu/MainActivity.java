package com.example.calcu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        AtomicReference<Double> valGuardat= new AtomicReference<>((double) 0);
        AtomicReference<Double> memoria= new AtomicReference<>((double) 0);
        AtomicReference<String> operation=new AtomicReference<>((String) "");
        AtomicBoolean newline= new AtomicBoolean(true);
        AtomicBoolean memoriaActiva= new AtomicBoolean(false);
        TextView txtCalc=findViewById(R.id.edttxt1);
        TextView txtprev=findViewById(R.id.txtprev);
        Button[] btnNums= {
                findViewById(R.id.btn_0),
                findViewById(R.id.btn_1),
                findViewById(R.id.btn_2),
                findViewById(R.id.btn_3),
                findViewById(R.id.btn_4),
                findViewById(R.id.btn_5),
                findViewById(R.id.btn_6),
                findViewById(R.id.btn_7),
                findViewById(R.id.btn_8),
                findViewById(R.id.btn_9),
                findViewById(R.id.btn_coma)
        };
        com.google.android.material.button.MaterialButton[] btnMemoria= {
                findViewById(R.id.btn_mc),
                findViewById(R.id.btn_msuma),
                findViewById(R.id.btn_mrest)
        };
        Button[] btnAct= {
                findViewById(R.id.btn_sum),
                findViewById(R.id.btn_rest),
                findViewById(R.id.btn_mult),
                findViewById(R.id.btn_div),
                findViewById(R.id.btn_mrms),
                findViewById(R.id.btn_mc),
                findViewById(R.id.btn_msuma),
                findViewById(R.id.btn_mrest),
                findViewById(R.id.btn_eq),
                findViewById(R.id.btn_ac)
        };

        View.OnClickListener numList=v-> {
            Button btnPressed=(Button) v;
            String numStr=btnPressed.getText().toString();
            if(newline.get()){
                if(numStr.equals(".")){
                    txtCalc.setText("0.");
                    ajustTxt(txtCalc);
                }else{
                    txtCalc.setText(numStr);
                    ajustTxt(txtCalc);
                }
                newline.set(false);
            }else{
                if(numStr.equals(".")){
                    if(!txtCalc.getText().toString().contains(".")){
                        txtCalc.append(numStr);
                        ajustTxt(txtCalc);
                    }
                }else{
                    txtCalc.append(numStr);
                    ajustTxt(txtCalc);
                }
            }
        };


        View.OnClickListener actList=v-> {
            Button btnPressed=(Button) v;
            String actStr = btnPressed.getText().toString();

            switch (actStr){
                case "AC":
                    txtCalc.setText("");
                    txtprev.setText("");
                    valGuardat.set(0.0);
                    operation.set("");
                    txtprev.setBackgroundColor(getColor(R.color.bg));
                    newline.set(false);
                    break;
                case "=":
                    double val2=getNum(txtCalc);
                    equalar(valGuardat.get(),val2,operation.get(),txtCalc);
                    if (!txtCalc.getText().toString().equals("Error")) {
                        txtCalc.setText(getStringNum(getNum(txtCalc)));
                    }
                    ajustTxt(txtCalc);
                    valGuardat.set(0.0);
                    operation.set("");
                    newline.set(true);
                    txtprev.setText("");
                    txtprev.setBackgroundColor(getColor(R.color.bg));
                    break;
                case "+":
                    valGuardat.set(getNum(txtCalc));
                    operation.set("sum");
                    txtprev.setText(getStringNum(getNum(txtCalc))+" + ");
                    txtprev.setBackgroundColor(getColor(R.color.txtbg));
                    newline.set(true);
                    break;
                case "-":
                    valGuardat.set(getNum(txtCalc));
                    operation.set("res");
                    txtprev.setText(getStringNum(getNum(txtCalc))+" - ");
                    txtprev.setBackgroundColor(getColor(R.color.txtbg));
                    newline.set(true);
                    break;
                case "x":
                    valGuardat.set(getNum(txtCalc));
                    operation.set("mul");
                    txtprev.setText(getStringNum(getNum(txtCalc))+" x ");
                    txtprev.setBackgroundColor(getColor(R.color.txtbg));
                    newline.set(true);
                    break;
                case "÷":
                    valGuardat.set(getNum(txtCalc));
                    operation.set("div");
                    txtprev.setText(getStringNum(getNum(txtCalc))+" ÷ ");
                    txtprev.setBackgroundColor(getColor(R.color.txtbg));
                    newline.set(true);
                    break;
                case "MR":
                    txtCalc.setText(getStringNum(memoria.get()));
                    ajustTxt(txtCalc);
                    break;
                case "MS":
                    memoriaActiva.set(true);
                    memoria.set(getNum(txtCalc));
                    //Activar botons M
                    for(int i=0;i<btnMemoria.length;i++){
                        btnMemoria[i].setBackgroundTintList(getColorStateList(R.color.btn2));
                        btnMemoria[i].setTextColor(getColor(R.color.txt));
                        btnMemoria[i].setStrokeColor(getColorStateList(R.color.btn2stroke));
                    }
                    ((Button) findViewById(R.id.btn_mrms)).setText("MR");
                    if(getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE){
                        ((TextView) findViewById(R.id.txtmemo)).setText(getStringNum(memoria.get())+"");
                    }
                    break;
                case "M+":
                    if(memoriaActiva.get()) {
                        memoria.set(calculmemoria(memoria.get(), getNum(txtCalc), "sum"));
                    }
                    if(getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE){
                        ((TextView) findViewById(R.id.txtmemo)).setText(getStringNum(memoria.get())+"");
                    }
                    break;
                case "M-":
                    if(memoriaActiva.get()) {
                        memoria.set(calculmemoria(memoria.get(), getNum(txtCalc), "res"));
                    }
                    if(getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE){
                        ((TextView) findViewById(R.id.txtmemo)).setText(getStringNum(memoria.get())+"");
                    }
                    break;
                case "MC":
                    memoria.set(0.0);
                    memoriaActiva.set(false);
                    //Desactivar botons M
                    for(int i=0;i<btnMemoria.length;i++){
                        btnMemoria[i].setBackgroundTintList(getColorStateList(R.color.btn2Dis));
                        btnMemoria[i].setTextColor(getColor(R.color.txtDis));
                        btnMemoria[i].setStrokeColor(getColorStateList(R.color.btn2strokeDis));
                    }
                    ((Button) findViewById(R.id.btn_mrms)).setText("MS");
                    if(getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE){
                        ((TextView) findViewById(R.id.txtmemo)).setText("Sense Valor");
                    }
                    break;
            }
        };

        //Assignar listener
        for (int i=0;i<btnNums.length;i++){
            btnNums[i].setOnClickListener(numList);
        }
        for (int i=0;i<btnAct.length;i++){
            btnAct[i].setOnClickListener(actList);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });
    }

    private void equalar(double val1, double val2, String oper,TextView txtres) {
        switch (oper){
            case "sum":
                txtres.setText(val1+val2+"");
                break;
            case "res":
                txtres.setText(val1-val2+"");
                break;
            case "mul":
                txtres.setText(val1*val2+"");
                break;
            case "div":
                if(val2!=0.0){
                    txtres.setText(val1/val2+"");
                }else{
                    txtres.setText("Error");
                }
                break;
            default:
                txtres.setText(val1+"");
                break;
        }
    }
    private double calculmemoria(double val1, double val2, String oper) {
        switch (oper){
            case "sum":
                return val1+val2;
            case "res":
                return val1-val2;
            default:
                return 0;
        }
    }

    public static double getNum(TextView txt) {
        String s=txt.getText().toString();
        try{
        if(s.contains(".")){
            while(s.endsWith("0")){
                s=s.substring(0, s.length()-1);
            }
            if (s.endsWith(".")) {
                s=s.substring(0, s.length()-1);
            }
        }

        return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    public static String getStringNum( double valor){
        String s=valor+"";
        if(s.contains(".")){
            while(s.endsWith("0")){
                s=s.substring(0, s.length()-1);
            }
            if (s.endsWith(".")) {
                s=s.substring(0, s.length()-1);
            }
        }
        return s;
    }

    private void ajustTxt(TextView txt) {
        int l = txt.getText().length();
        if(getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE){
            if (l > 15 & l < 30) {
                //Calcular mida text amb funció
                txt.setTextSize((float)(-1.2/3*l+48));
            } else if (l >= 30) {
                txt.setTextSize(12);
            } else {
                txt.setTextSize(30);
            }
        }else{
            if (l > 15 & l < 30) {
                txt.setTextSize((float)(-4.0/3*l+55));
            } else if (l >= 30) {
                txt.setTextSize(15);
            } else {
                txt.setTextSize(35);
            }
        }

    }
}