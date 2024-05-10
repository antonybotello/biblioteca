package com.usta.dao.Autor;

import java.util.List;

import com.usta.model.Autor;

public interface AutorDAO {
    void insertar(Autor autor);
    Autor buscarPorId(int id);
    List<Autor> buscarTodos();
    void actualizar(Autor autor);
    void eliminar(int id);
    
} 