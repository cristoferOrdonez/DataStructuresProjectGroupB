package com.example.datastructureproject_groupb.pickers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

public class MostrarTimePicker {

    private Integer horaInicio, horaFinal, minutosInicio, minutosFinal;
    public MostrarTimePicker(Context context,
                             EditText horarioEvento,
                             int horaInicio,
                             int horaFinal,
                             int minutosInicio,
                             int minutosFinal){

        if(horarioEvento.getText().toString().equals("")){

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

        AtomicReference<String> horario = new AtomicReference<>("");

        TimePickerDialog pickerInicio = new TimePickerDialog(context, (x, y, z) -> {

            this.horaInicio = y;

            this.minutosInicio = z;

            String amOpm = (y > 12)?"p.m.":"a.m.";

            if(z > 9)
                horario.set(y%12 + ":" + z + amOpm);
            else
                horario.set(y%12 + ":0" + z + amOpm);

            TimePickerDialog pickerFinal = new TimePickerDialog(context, (x_alt, y_alt, z_alt) -> {

                this.horaFinal = y_alt;
                this.minutosFinal = z_alt;

                String amOpm_alt = (y_alt > 12)?"p.m.":"a.m.";

                if(z_alt > 9)
                    horarioEvento.setText(horario.get() + " - " + (y_alt%12) + ":" + z_alt + amOpm_alt);
                else
                    horarioEvento.setText(horario.get() + " - " + (y_alt%12) + ":0" + z_alt + amOpm_alt);

            }, horaFinal, minutosFinal, false);
            pickerFinal.setTitle("Hora de finalización");
            pickerFinal.show();

        }, horaInicio, minutosInicio, false);
        pickerInicio.setTitle("Hora de inicio");
        pickerInicio.show();

    }

}
