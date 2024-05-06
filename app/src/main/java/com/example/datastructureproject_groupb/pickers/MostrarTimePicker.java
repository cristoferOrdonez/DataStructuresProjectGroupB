package com.example.datastructureproject_groupb.pickers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class MostrarTimePicker {

    private Integer[] horaMinutosDefecto;

    public MostrarTimePicker(Context context,
                             EditText horarioEvento,
                             Integer [] horaMinutosDefecto) {

        this.horaMinutosDefecto = horaMinutosDefecto;

        if (horarioEvento.getText().toString().equals("")) {

            Calendar calendario = Calendar.getInstance();

            this.horaMinutosDefecto[0] = calendario.get(Calendar.HOUR_OF_DAY);
            this.horaMinutosDefecto[1] = calendario.get(Calendar.MINUTE);

        }

        TimePickerDialog pickerInicio = new TimePickerDialog(context, (x, y, z) -> {

            this.horaMinutosDefecto[0] = y;
            this.horaMinutosDefecto[1] = z;

            String amOpm = (y > 12) ? "p.m." : "a.m.";

            if (z > 9) {
                horarioEvento.setText((y % 12) + ":" + z + amOpm);
            } else {
                horarioEvento.setText(y % 12 + ":0" + z + amOpm);
            }

        }, this.horaMinutosDefecto[0], this.horaMinutosDefecto[1], false);
        pickerInicio.show();
    }

}
