package io.github.jieshengnp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Application implements Serializable {
    private String applicationID;
    private List<Applicant> applicantList = new ArrayList<Applicant>();
    private String applicationCode;
    private HashMap<String, Boolean> applicantAccepted = new HashMap<>();

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public List<Applicant> getApplicantList() {
        return applicantList;
    }

    public void setApplicantList(List<Applicant> applicantList) {
        this.applicantList = applicantList;
    }

    public void addApplicant(Applicant applicant){
        applicantList.add(applicant);
    }

    public void setApplicant(int index,Applicant applicant){
        this.applicantList.set(index, applicant);
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public HashMap<String, Boolean> getApplicantAccepted() {
        return applicantAccepted;
    }

    public void setApplicantAccepted(HashMap<String, Boolean> applicantAccepted) {
        this.applicantAccepted = applicantAccepted;
    }

    public Application(){}

    public Application(String applicationID, String applicationCode, Applicant applicant){
        this.applicationID = applicationID;
        this.applicationCode = applicationCode;
        this.applicantList.add(applicant);
    }

    public Application(String applicationID, String applicationCode){
        this.applicationID = applicationID;
        this.applicationCode = applicationCode;
    }
}
