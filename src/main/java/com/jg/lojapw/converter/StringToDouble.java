package com.jg.lojapw.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDouble implements Converter<String, Double> {


    @Override
    public Double convert(String str) {

        str = str.trim();

        if (str.length() > 0){
            str = str.replace(".","").replace(",", ".");
            return Double.parseDouble(str);
        }

        return 0.;
    }
}
