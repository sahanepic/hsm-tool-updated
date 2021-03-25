package com.sahanbcs;

import java.sql.Timestamp;
import java.util.Date;

// 
// Decompiled by Procyon v0.5.36
// 

public class LogFileCreator
{
    public static synchronized void writInforTologs(final String msg) {
        System.out.println(msg);
    }
    
    private static String getTime() {
        return new Timestamp(new Date().getTime()).toString();
    }
}
