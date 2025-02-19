package com.raven.chart;

import java.awt.Color;

public class ModelChart {

    private String label;
    private double[] values;
    private Color color;  // Thêm thuộc tính color

    // Getter và setter cho thuộc tính color
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    // Constructor với label và values
    public ModelChart(String label, double[] values) {
        this.label = label;
        this.values = values;
    }

    // Constructor mặc định
    public ModelChart() {
    }

    // Hàm để lấy giá trị lớn nhất trong mảng values
    public double getMaxValues() {
        double max = 0;
        for (double v : values) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }
}

