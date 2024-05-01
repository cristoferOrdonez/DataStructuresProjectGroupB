package com.example.datastructureproject_groupb.pickers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.util.Calendar;

public class MostrarDatePicker {
    private Integer dia, mes, anio;
    public  MostrarDatePicker(Context context, EditText fechaEvento, int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;

        if (fechaEvento.getText().toString().isEmpty()) {
            Calendar calendario = Calendar.getInstance();
            dia = calendario.get(Calendar.DAY_OF_MONTH);
            mes = calendario.get(Calendar.MONTH);
            anio = calendario.get(Calendar.YEAR);
        }

        DatePickerDialog picker = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            this.dia = dayOfMonth;
            this.mes = month;
            this.anio = year;

            fechaEvento.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }, anio, mes, dia);

        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        picker.show();
    }
}
