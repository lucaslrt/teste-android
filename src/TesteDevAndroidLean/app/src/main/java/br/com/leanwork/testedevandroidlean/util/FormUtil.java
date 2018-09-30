package br.com.leanwork.testedevandroidlean.util;

public class FormUtil {

    public static boolean formularioPreenchido(String... strings) {
        for (String string : strings) if (string == null || string.isEmpty()) return false;
        return true;
    }

}
