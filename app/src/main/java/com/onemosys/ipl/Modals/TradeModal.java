package com.onemosys.ipl.Modals;

public class TradeModal {
    public String question,info;
    public int yes, no;

    public TradeModal(){}

    public TradeModal(String question, String info, int yes, int no) {
        this.question = question;
        this.info = info;
        this.yes = yes;
        this.no = no;
    }
}
