package com.example.conditional;

public class LinuxServiceBean implements ListService {
    @Override
    public String showListCmd() {
        return "ls";
    }
}
