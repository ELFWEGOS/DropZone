package com.elfwegos.dropezone.services;

import com.elfwegos.dropezone.models.Log;
import com.elfwegos.dropezone.utils.LogTypes;

import java.util.ArrayList;

public class LogsManager {
    static LogsManager instance;
    public static LogsManager getInstance(){
        if (instance != null){
            return instance;
        }
        instance = new LogsManager();
        return instance;
    }

    ArrayList<Log> logsList = new ArrayList<>();
    int id = 0;

    public void addLog(LogTypes logType, String text){
        Log log = new Log(id,logType,text);
        logsList.add(log);
        id++;
        uiUpdate();
    }

    public void uiUpdate(){

    }
}
