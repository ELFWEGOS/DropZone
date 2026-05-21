package com.elfwegos.dropezone.services;

import com.elfwegos.dropezone.models.Log;
import com.elfwegos.dropezone.utils.LogTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;

public class LogsManager {
    static LogsManager instance;
    public static LogsManager getInstance(){
        if (instance != null){
            return instance;
        }
        instance = new LogsManager();
        return instance;
    }

    ObservableList<Log> logsList = FXCollections.observableArrayList();
    int id = 0;

    public void addLog(LogTypes logType, String text){
        Log log = new Log(id,logType,text);
        logsList.add(log);
        id++;
    }
    public ObservableList<Log> getLogsList(){
        return logsList;
    }
}
