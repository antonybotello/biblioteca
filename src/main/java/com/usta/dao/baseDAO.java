package com.usta.dao;

import java.util.List;


public interface baseDAO<T> {
    void insertar(T obj);
    T buscarPorId(int id);
    List<T> buscarTodos();
    void actualizar(T obj);
    void eliminar(int id);
    
} 