package com.example.datastructureproject_groupb.pickers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class MostrarTimePicker {

    private Integer horaInicio, horaFinal, minutosInicio, minutosFinal;

    public MostrarTimePicker(Context context,
                             EditText horarioEvento,
                             int horaInicio,
                             int horaFinal,
                             int minutosInicio,
                             int minutosFinal) {

        if (horarioEvento.getText().toString().equals("")) {

            Calendar calendario = Calendar.getInstance();

            this.horaInicio = calendario.get(Calendar.HOUR_OF_DAY);
            this.horaFinal = calendario.get(Calendar.HOUR_OF_DAY);
            this.minutosFinal = calendario.get(Calendar.MINUTE);
            this.minutosInicio = calendario.get(Calendar.MINUTE);

        } else {

            this.horaInicio = horaInicio;
            this.horaFinal = horaFinal;
            this.minutosFinal = minutosFinal;
            this.minutosInicio = minutosInicio;

        }

        TimePickerDialog pickerInicio = new TimePickerDialog(context, (x, y, z) -> {

            this.horaInicio = y;

            this.minutosInicio = z;

            String amOpm = (y > 12) ? "p.m." : "a.m.";

            if (z > 9) {
                horarioEvento.setText((y % 12) + ":" + z + amOpm);
            } else {
                horarioEvento.setText(y % 12 + ":0" + z + amOpm);
            }


        }, horaInicio, minutosInicio, false);
        pickerInicio.show();
    }
}
