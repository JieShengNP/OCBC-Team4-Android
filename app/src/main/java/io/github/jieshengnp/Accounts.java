package io.github.jieshengnp;

import java.util.HashMap;

public class Accounts {
    private String BankNo;
    private double Balance;
    private HashMap<String, Boolean> Owner;
    private String Status;
    private String Type;

    public String getBankNo() {
        return BankNo;
    }

    public void setBankNo(String bankNo) {
        BankNo = bankNo;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Accounts(){
        Owner = new HashMap<>();
    }
}
