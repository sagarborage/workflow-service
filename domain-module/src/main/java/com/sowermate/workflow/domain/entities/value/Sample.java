package com.sowermate.workflow.domain.entities.value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Sample {
    public static void main(String[] args) {
        double decimalValue = 12.34;
        BigDecimal bigDecimal = new BigDecimal(decimalValue);

// Round to nearest integer (rounding mode HALF_UP)
        //BigDecimal roundedValue = bigDecimal.setScale(0, RoundingMode.HALF_UP);
        BigDecimal roundedValue = new BigDecimal(12.8456);
        DecimalFormat df = new DecimalFormat("#");
        String formattedValue = df.format(roundedValue);
        System.out.println(formattedValue);
        float f = 12.54f;
        System.out.println(Math.round(f) );
    }
}
