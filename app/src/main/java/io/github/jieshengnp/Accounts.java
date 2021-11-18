package io.github.jieshengnp;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Accounts implements Serializable {
    private String BankNo;
    private double Balance;
    private HashMap<String, String> Owner;
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

    public HashMap<String, String> getOwner() {
        return Owner;
    }

    public void setOwner(HashMap<String, String> owner) {
        Owner = owner;
    }
}
