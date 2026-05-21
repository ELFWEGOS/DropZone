package com.elfwegos.dropezone.models;
import com.elfwegos.dropezone.utils.LogTypes;

public class Log {
    int id;
    LogTypes logType;
    String text;

    //CONSTRUCTOR
    public Log(int id, LogTypes logType, String text) {
        this.id = id;
        this.logType = logType;
        this.text = text;
    }



    //GETTERS & SETTERS
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public LogTypes getLogType() {return logType;}

    public void setLogType(LogTypes logType) {this.logType = logType;}

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}
}
