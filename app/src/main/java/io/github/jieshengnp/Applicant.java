package io.github.jieshengnp;

import java.io.Serializable;

public class Applicant implements Serializable{
    private String fullName;
    private Boolean singPass;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getSingPass() {
        return singPass;
    }

    public void setSingPass(Boolean singPass) {
        this.singPass = singPass;
    }

    public Applicant(){}

    public Applicant(String fullName, Boolean singPass){
        this.fullName = fullName;
        this.singPass = singPass;
    }
}
