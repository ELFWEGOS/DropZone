package com.elfwegos.dropezone.models;
import com.elfwegos.dropezone.utils.LogTypes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    int id;
    LogTypes logType;
    String text;
    String dateTime;

    //CONSTRUCTOR
    public Log(int id, LogTypes logType, String text) {
        this.id = id;
        this.logType = logType;
        this.text = text;
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formateur = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        dateTime = localDateTime.format(formateur);
    }

    //TO STRING
    @Override
    public String toString(){
        return dateTime+" ["+logType.toString()+"] "+text;
    }

    //GETTERS & SETTERS
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public LogTypes getLogType() {return logType;}

    public void setLogType(LogTypes logType) {this.logType = logType;}

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}
}
