package com.onemosys.ipl.Modals;

public class TradeModal {
    public String question,info;
    public double yes, no;
    public long prizePool;

    public TradeModal(){}

    public TradeModal(String question, String info, long prizePool, double yes, double no) {
        this.question = question;
        this.info = info;
        this.prizePool = prizePool;
        this.yes = yes;
        this.no = no;
    }
}
