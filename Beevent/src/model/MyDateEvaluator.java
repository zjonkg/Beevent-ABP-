/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author angelvintos
 */
import com.toedter.calendar.IDateEvaluator;
import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

public class MyDateEvaluator extends IDateEvaluator {
    public boolean isSpecial(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        // Especifica los días específicos que deseas seleccionar
        return (year == 2024 && month == 3 && (day == 15 || day == 20 || day == 25));
    }

    public Color getSpecialForegroundColor() {
        return Color.RED; // Color rojo para los días seleccionados
    }

    public Color getSpecialBackroundColor() {
        return null; // Color de fondo para los días seleccionados (puedes devolver null si no quieres uno)
    }

    public String getSpecialTooltip() {
        return null; // Texto de información sobre herramientas para los días seleccionados (puedes devolver null si no quieres uno)
    }
}

