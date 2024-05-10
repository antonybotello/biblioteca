package com.usta.controller;

import java.io.IOException;

import com.usta.App;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    public void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}