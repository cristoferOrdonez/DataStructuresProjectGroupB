package com.example.datastructureproject_groupb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datastructureproject_groupb.db.DbEventos;
import com.example.datastructureproject_groupb.entidades.Evento;
import com.example.datastructureproject_groupb.pickers.MostrarDatePicker;
import com.example.datastructureproject_groupb.pickers.MostrarTimePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.Date;
import java.util.regex.Pattern;

public class EditarEventosVirtual extends AppCompatActivity {
    TextInputEditText nombreEvento, fechaEvento, costoEvento, horaInicioEvento, horaFinalEvento, descripcionEvento;
    MaterialAutoCompleteTextView spinnerCategoriaEvento, spinnerPlataformaEvento;
    Button cancelarEditarEvento, aceptarEditarEvento;
    long idEvento;
    private ArrayAdapter<String> categoriasAdapter, plataformasAdapter;
    private int dia = 0, mes = -1, anio = 0, horaInicio = -1, horaFinal = -1, minutosInicio = -1, minutosFinal = -1;
    private LinearLayout layoutBotones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_eventos_virtual);

        layoutBotones = findViewById(R.id.layoutBotones);

        KeyboardVisibilityEvent.setEventListener(this, isOpen -> {
            if(isOpen)
                layoutBotones.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 0));
            else {

                LinearLayout.LayoutParams nuevoParametro = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
                nuevoParametro.leftMargin = 30;
                nuevoParametro.rightMargin = 30;
                nuevoParametro.bottomMargin = 40;
                nuevoParametro.topMargin = 40;

                layoutBotones.setLayoutParams(nuevoParametro);

            }
        });

        nombreEvento = findViewById(R.id.editTextNombreEvento);
        nombreEvento.setText(getIntent().getStringExtra("NOMBRE_EVENTO"));
        fechaEvento = findViewById(R.id.editTextFechaEvento);

        String fechaEventoS = getIntent().getStringExtra("FECHA_EVENTO");

        String[] fechaEventoArr = fechaEventoS.split("/");

        dia = Integer.parseInt(fechaEventoArr[0]);
        mes = Integer.parseInt(fechaEventoArr[1]) - 1;
        anio = Integer.parseInt(fechaEventoArr[2]);

        String horarioEventoS = getIntent().getStringExtra("HORARIO_EVENTO");

        String[] horarioEventoArr = horarioEventoS.split(" - ");

        String horaInicioS = horarioEventoArr[0], horaFinalS = horarioEventoArr[1];

        if(horaInicioS.charAt(horaInicioS.length() - 4) == 'p')
            horaInicio = Integer.parseInt(horaInicioS.substring(0, horaInicioS.indexOf(':'))) + 12;
        else
            horaInicio = Integer.parseInt(horaInicioS.substring(0, horaInicioS.indexOf(':')));

        if(horaFinalS.charAt(horaFinalS.length() - 4) == 'p')
            horaFinal = Integer.parseInt(horaFinalS.substring(0, horaFinalS.indexOf(':'))) + 12;
        else
            horaFinal = Integer.parseInt(horaFinalS.substring(0, horaFinalS.indexOf(':')));

        minutosInicio = Integer.parseInt(horaInicioS.substring(horaInicioS.indexOf(':') + 1, horaInicioS.indexOf('.') - 1));

        minutosFinal = Integer.parseInt(horaFinalS.substring(horaFinalS.indexOf(':') + 1, horaFinalS.indexOf('.') - 1));

        fechaEvento.setText(getIntent().getStringExtra("FECHA_EVENTO"));

        costoEvento = findViewById(R.id.editTextCostoEvento);
        costoEvento.setText(getIntent().getStringExtra("COSTO_EVENTO"));

        String[] horario = horarioEventoS.split(" - ");
        horaInicioEvento = findViewById(R.id.editTextHoraInicioEvento);
        horaInicioEvento.setText(horario[0]);
        horaFinalEvento = findViewById(R.id.editTextHoraFinalEvento);
        horaInicioEvento.setText(horario[0]);

        descripcionEvento = findViewById(R.id.editTextDescripcionEvento);
        descripcionEvento.setText(getIntent().getStringExtra("DESCRIPCION_EVENTO"));
        spinnerPlataformaEvento = findViewById(R.id.spinnerPlataformaEvento);
        spinnerCategoriaEvento = findViewById(R.id.spinnerCategoriaEvento);

        cancelarEditarEvento = findViewById(R.id.botonCancelarEditarEvento);
        aceptarEditarEvento = findViewById(R.id.botonAceptarEditarEvento);

        plataformasAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.PLATAFORMAS);
        spinnerPlataformaEvento.setAdapter(plataformasAdapter);

        categoriasAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, Bocu.INTERESES);
        spinnerCategoriaEvento.setAdapter(categoriasAdapter);

        spinnerPlataformaEvento.setText(Bocu.PLATAFORMAS[
                obtenerIndice(Bocu.PLATAFORMAS, (getIntent().getStringExtra("PLATAFORMA_EVENTO")))], false);

        spinnerCategoriaEvento.setText(Bocu.INTERESES[getIntent().getIntExtra("CATEGORIA_EVENTO", -1)], false);


        cancelarEditarEvento.setOnClickListener(view -> cambiarAEventos());
        aceptarEditarEvento.setOnClickListener(view -> editarEventoExpositor());

        fechaEvento.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                mostrarDatePicker();
        });
        fechaEvento.setOnClickListener(view -> mostrarDatePicker());

        horaInicioEvento.setOnClickListener(view -> mostrarTimePicker(horaInicioEvento));
        horaInicioEvento.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                mostrarTimePicker(horaInicioEvento);
        });

        horaFinalEvento.setOnClickListener(view -> mostrarTimePicker(horaFinalEvento));
        horaFinalEvento.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                mostrarTimePicker(horaFinalEvento);
        });

    }

    private void mostrarTimePicker(EditText horarioEvento) {
        MostrarTimePicker timePicker = new MostrarTimePicker(
                this,
                horarioEvento,
                this.horaInicio,
                this.horaFinal,
                this.horaInicio,
                this.minutosFinal
        );
    }

    public static int obtenerIndice(String[] arregloPlataformas, String plataforma) {
        for (int i = 0; i < arregloPlataformas.length; i++) {
            if (arregloPlataformas[i].equals(plataforma)) {
                return i;
            }
        }
        return -1;
    }

    private void mostrarDatePicker(){

        MostrarDatePicker datePicker = new MostrarDatePicker(this, this.fechaEvento, this.dia, this.mes, this.anio);
    }

    public void cambiarAEventos() {
        PaginaInicio.intensionInicializacion = PaginaInicio.EVENTOS;
        Intent miIntent = new Intent(this, PaginaInicio.class);
        startActivity(miIntent);
        finishAffinity();
    }

    public void editarEventoExpositor(){
        VerificarInformacionRegistro(new View(this));
    }

    public void VerificarInformacionRegistro(View view) {

        boolean flag = true;
        String mensajeError = "";

        if(nombreEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado nombre valido\n";
            flag = false;
        }

        String verificarFechaEvento = this.fechaEvento.getText().toString().trim();

        String[] verificarTamanoFechaEvento = verificarFechaEvento.split("/");
        if(verificarFechaEvento.equals("") || verificarTamanoFechaEvento.length  != 3) {
            mensajeError += "No ha ingresado fecha valida\n";
            flag = false;
        }
        String verificarCostoEvento = costoEvento.getText().toString().trim();
        if(verificarCostoEvento.equals("") || !TextUtils.isDigitsOnly(verificarCostoEvento)) {
            mensajeError += "No ha ingresado un costo valido\n";
            flag = false;
        }
        if(horaInicioEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado hora de inicio\n";
            flag = false;
        }
        if(horaFinalEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado hora final\n";
            flag = false;
        }
        if(spinnerCategoriaEvento.getText().toString().equals("") || categoriasAdapter.getPosition(spinnerCategoriaEvento.getText().toString()) == -1) {
            mensajeError += "Seleccione un Interes\n";
            flag = false;
        }
        if(spinnerPlataformaEvento.getText().toString().equals("") || plataformasAdapter.getPosition(spinnerPlataformaEvento.getText().toString()) == -1) {
            mensajeError += "Seleccione una plataforma\n";
            flag = false;
        }
        if(descripcionEvento.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado una descripción valida\n";
            flag = false;
        }

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        if(flag)
            EditarEvento(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();
    }

    public void EditarEvento(View view) {
        String nombreEvento = this.nombreEvento.getText().toString().trim();
        String fechaEvento = this.fechaEvento.getText().toString().trim();

        String[] partesFecha = fechaEvento.split("/");
        int diaEvento = Integer.parseInt(partesFecha[0]);
        int mesEvento = Integer.parseInt(partesFecha[1]);
        int AnoEvento = Integer.parseInt(partesFecha[2]);

        String plataformaEvento = spinnerPlataformaEvento.getText().toString();
        int costoEvento = Integer.parseInt(this.costoEvento.getText().toString());
        String horarioEvento = this.horaInicioEvento.getText().toString() + " - " + this.horaFinalEvento.getText().toString();
        String descripcionEvento = this.descripcionEvento.getText().toString();

        // El 21 hace referencia a VIRTUAL
        int modalidad = 21;
        int categoria = categoriasAdapter.getPosition(spinnerCategoriaEvento.getText().toString());

        int position = getIntent().getIntExtra("POSITION", -1);
        idEvento = getIntent().getLongExtra("ID_EVENTO",-1);

        try {

            Evento evento = new Evento(
                    this,
                    idEvento,
                    nombreEvento,
                    new Date(AnoEvento, mesEvento, diaEvento),
                    plataformaEvento,
                    modalidad,
                    costoEvento,
                    horarioEvento,
                    categoria,
                    descripcionEvento,
                    Bocu.usuario.getCorreoElectronico()
            );

            Bocu.eventosExpositor.set(position, evento);
            Bocu.eventos.set(Bocu.posicionesEventosExpositor.get(position), evento);
            new DbEventos(this).editarEvento(nombreEvento,
                    AnoEvento,
                    mesEvento,
                    diaEvento,
                    plataformaEvento,
                    costoEvento,
                    horarioEvento,
                    descripcionEvento,
                    modalidad,
                    categoria,
                    String.valueOf(idEvento),
                    Bocu.usuario.getCorreoElectronico());

            Toast.makeText(this, "Evento editado con éxito", Toast.LENGTH_SHORT).show();
            cambiarAEventos();

        } catch(Exception e){
            Toast.makeText(this, "Error al editar el evento", Toast.LENGTH_SHORT).show();
        }

    }
}