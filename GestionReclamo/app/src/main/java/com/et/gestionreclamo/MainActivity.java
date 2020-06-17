package com.et.gestionreclamo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.et.gestionreclamo.entidad.Cierre;
import com.et.gestionreclamo.entidad.RecCierreMovil;
import com.et.gestionreclamo.entidad.ResAuth;
import com.et.gestionreclamo.entidad.Usuario;
import com.et.gestionreclamo.intefaces.ServiceConsumer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView tv_respuesta, tv_derivar;
    EditText et_detalle;
    LinearLayout contenerdor, contenerdorVoltages;
    Switch sw_consumidor;
    ArrayList<Cierre> lstCierre;
    Retrofit retrofit;
    ServiceConsumer servicio;
    final String urlRec = "http://10.223.105.28:8180/com/service/recmovil/";
    final String urlSeg = "http://10.223.105.28:8180/security-rest/v1/auth/";
    String token;
    private final String con_ausente = " Consumidor ausente.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_respuesta = (TextView) findViewById(R.id.id_txt_respuesta);
        tv_derivar = (TextView) findViewById(R.id.id_txt_derivar);
        et_detalle = (EditText) findViewById(R.id.id_et_detalle);
        sw_consumidor = (Switch) findViewById(R.id.id_switch);
        contenerdor = (LinearLayout) findViewById(R.id.id_linearLayout);
        contenerdorVoltages = (LinearLayout) findViewById(R.id.id_linearLayout_voltages);
        retrofit = new Retrofit.Builder().baseUrl(urlSeg).addConverterFactory(GsonConverterFactory.create()).build();
        servicio = retrofit.create(ServiceConsumer.class);
        autenticarse();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_confirmar:
                confirmDialog();
                break;
            case R.id.id_switch:
                if (sw_consumidor.isChecked()) {
                    et_detalle.setText(et_detalle.getText() + con_ausente);
                } else {
                    et_detalle.setText(et_detalle.getText().toString().replace(con_ausente, ""));
                }
                break;
        }
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar Reclamo ");
        builder.setMessage("Esta seguro de cerrar el reclamo ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Elegiste cerrar el reclamo", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "No quieres cerrar el reclamo", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void autenticarse() {
        try {
            Usuario usuario = new Usuario("joel.sosa", "123");
            Call<ResAuth> auth = servicio.getUserLogin(usuario);
            //Call<ResAuth> auth = servicio.getAuth("joel.sosa", "123","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFTkRFIFRFQ05PTE9HSUFTIFNFQyIsImlhdCI6MTU2OTg3NDk4OCwiZXhwIjoxNjAxNDExMDYwLCJhdWQiOiJldC5ibyIsInN1YiI6IkVOREUgVEVDTk9MT0dJQVMiLCJzZXJ2aWNpbyI6IkFHQyJ9.C_-0Ll337IKduXQB-wHgjzVymCoRXlRUg-gU-oR2wbc");
            auth.enqueue(new Callback<ResAuth>() {
                @Override
                public void onResponse(Call<ResAuth> call, Response<ResAuth> response) {
                    try {
                        Log.d("JUANCA", "ENTRO POR ON_RESPONSE");
                        if (response.body() != null) {
                            ResAuth resAuth = response.body();
                            if (resAuth.getJwtToken() != null && !resAuth.getJwtToken().isEmpty() && resAuth.getCode() == 0) {
                                Log.d("= codigo de respuesta: ", "" + resAuth.getJwtToken());
                                token = resAuth.getJwtToken();
                                crearSpinner(0); //creamos spinner raiz
                            } else {
                                Log.d("No logro autenticarse: ", "" + resAuth.getMsg());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResAuth> call, Throwable t) {
                    Log.d("ERROR DE SERVICIO ", " FALLO LA CONEXION " + t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearSpinner(int id) {
        Log.d("CREAT SPIINER ", " id = " + id);
        retrofit = new Retrofit.Builder().baseUrl(urlRec).addConverterFactory(GsonConverterFactory.create()).build();
        servicio = retrofit.create(ServiceConsumer.class);
        //Call<List<Cierre>> call = servicio.getChild(token, id);
        RecCierreMovil reccierre = new RecCierreMovil(19);
        Call<List<Cierre>> call = servicio.getNodos(token, reccierre);
        call.enqueue(new Callback<List<Cierre>>() {
            @Override
            public void onResponse(Call<List<Cierre>> call, Response<List<Cierre>> response) {
                if (response.isSuccessful()) {
                    List<Cierre> cierreList = response.body();
                    Cierre cierre = new Cierre(0, 0, "0", "-Seleccione-", 0, 0, 0, "", "", "", 0, "", 0, false);
                    lstCierre = new ArrayList<>();
                    lstCierre.add(cierre);
                    boolean tieneDatos = false;
                    for (Cierre c : cierreList) {
                        cierre = new Cierre(c.getId(), c.getId_raiz(), c.getCodigo(), c.getNombre(), c.getOrigen(), c.getCausa(), c.getCausaInterna(), c.getMotivo(), c.getObservacion(), c.getObsInterna(), c.getDerivar_id(), c.getDerivar_dsc(), c.getCantVolt(), c.isProcedente());
                        lstCierre.add(cierre);
                        tieneDatos = true;
                    }
                    if (tieneDatos) {
                        Spinner drop = new Spinner(getApplicationContext());
                        ArrayAdapter<Cierre> adapter = new ArrayAdapter<Cierre>(getApplicationContext(), R.layout.spinner_layout, lstCierre);
                        drop.setAdapter(adapter);
                        contenerdor.addView(drop);
                        drop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position > 0) { //posicion 0 = 'seleccione'
                                    Cierre c = (Cierre) parent.getSelectedItem();
                                    llenarCampos(c);
                                    //eliminarmos posteriores spinners
                                    int posicionSpinerActual = contenerdor.indexOfChild(parent) + 1;
                                    int cantBorrar = contenerdor.getChildCount() - posicionSpinerActual;
                                    for (int i = 1; i <= cantBorrar; i++) {
                                        int ultimoSpiner = contenerdor.getChildCount();
                                        contenerdor.removeViewAt(ultimoSpiner - 1);
                                    }
                                    crearSpinner(c.getId());
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                } else {
                    Log.i("- JUANCA -", "Error No hay respuesta en onResponse = " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Cierre>> call, Throwable t) {
                Log.i("JUANCA", "Error en onFailure = " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Error = " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void llenarCampos(Cierre c) {
        if (c.isProcedente()) {
            tv_respuesta.setTextColor(Color.RED);
            tv_respuesta.setText("PROCEDENTE");
        } else {
            tv_respuesta.setTextColor(Color.BLUE);
            tv_respuesta.setText("IMPROCEDENTE");
        }
        tv_derivar.setText("Derivar a :");
        //et_detalle.setText(c.getDescripcion());

        //llenamos voltages si corresponde
        if (c.getCantVolt() > 0) {
            contenerdorVoltages.setVisibility(View.VISIBLE);
            contenerdorVoltages.removeAllViews();
            TextView tv = new TextView(getApplicationContext());
            tv.setText("VOLTAGES");
            tv.setTextSize(24);
            contenerdorVoltages.addView(tv);
            for (int i = 1; i <= c.getCantVolt(); i++) {
                EditText et = new EditText(getApplicationContext());
                et.setId(i);
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                contenerdorVoltages.addView(et);
                et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            if (v instanceof EditText) {
                                EditText edit = (EditText) v;
                                et_detalle.setText(et_detalle.getText().toString() + edit.getText() + "V. ");
                            }
                        }
                    }
                });
            }
        } else {
            contenerdorVoltages.setVisibility(View.INVISIBLE);
        }
    }
}