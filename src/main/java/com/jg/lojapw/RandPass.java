package com.jg.lojapw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandPass {

    private static Integer getInt(Integer min, Integer max){
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }

    public static String getRandPass(){
        List<String> letras = Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""));
        List<String> randSenha = new ArrayList<>();
        for (int i = 0; i < getInt(30, 60); i++){
            randSenha.add(letras.get(getInt(0, letras.size()-1)));
        }

        return String.join("", randSenha);
    }

}
