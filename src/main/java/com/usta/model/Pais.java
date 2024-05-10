package com.usta.model;

public enum Pais {
    ANTIGUA_Y_BARBUDA,
    ARGENTINA,
    BAHAMAS,
    BARBADOS,
    BELICE,
    BOLIVIA,
    BRASIL,
    CANADÁ,
    CHILE,
    COLOMBIA,
    COSTA_RICA,
    CUBA,
    DOMINICA,
    ECUADOR,
    EL_SALVADOR,
    ESTADOS_UNIDOS,
    GRANADA,
    GUATEMALA,
    GUYANA,
    HAITÍ,
    HONDURAS,
    JAMAICA,
    MÉXICO,
    NICARAGUA,
    PANAMÁ,
    PARAGUAY,
    PERÚ,
    REPÚBLICA_DOMINICANA,
    SAN_CRISTÓBAL_Y_NIEVES,
    SAN_VICENTE_Y_LAS_GRANADINAS,
    SANTA_LUCÍA,
    SURINAM,
    TRINIDAD_Y_TOBAGO,
    URUGUAY,
    VENEZUELA;
    @Override
    public String toString() {
        String[] palabras = name().toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for (String palabra : palabras) {
            sb.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
