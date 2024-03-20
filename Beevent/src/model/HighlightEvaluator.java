/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.toedter.calendar.IDateEvaluator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author angelvintos
 */
public class HighlightEvaluator extends IDateEvaluator {
    private final List<Date> list = new ArrayList<>();
    public void add(Date date) {
        list.add(date);
    }

    public void remove(Date date) {
        list.remove(date);
    }

    public void setDates(List<Date> dates) {
        list.addAll(dates);
    }


    public Color getInvalidBackroundColor() {
        return Color.BLACK;
    }

    public Color getInvalidForegroundColor() {
        return null;
    }

    public String getInvalidTooltip() {
        return null;
    }


    public Color getSpecialBackroundColor() {
        return Color.GREEN;
    }


    public Color getSpecialForegroundColor() {
        return Color.RED;
    }


    public String getSpecialTooltip() {
        return "filled";
    }

    public boolean isInvalid(Date date) {
        return false;
    }

  
    public boolean isSpecial(Date date) {
        return list.contains(date);
    }
}
