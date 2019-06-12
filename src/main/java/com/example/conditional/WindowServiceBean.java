package com.example.conditional;

public class WindowServiceBean implements ListService{

    @Override
    public String showListCmd() {
        return "dir";
    }
}
