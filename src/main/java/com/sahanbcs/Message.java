package com.sahanbcs;
// 
// Decompiled by Procyon v0.5.36
// 

public class Message
{
    public static String LOGIN_L;
    public static String MAIN_LIST;
    public static String LIST_K_TYPE;
    
    static {
        Message.LOGIN_L = "\n__________________________________________________________\n__________________________________________________________\n               Safe-net HSM Key Utility Tool \n            \u00c2© Epic Lanka Technologies (Pvt) Ltd\n                         Version 1.0 \n__________________________________________________________\n";
        Message.MAIN_LIST = "\n\t1 Import keys using eLMK[KIR].\n\t2 Export keys using eLMK[KIS].\n\t3 Exit.\n";
        Message.LIST_K_TYPE = "\n\t1  Key type PPK.\n\t2 Key type PVK.\n\t3 Key type CVK.\n\t4 Key type CSCK.\n\t5 Key type DMKac.\n\t6 Key type KTM.\n\t7 Key type KIR.\n\t8 Key type KIS.\n\t9 Key type PVK,DT.\n";
    }
}
