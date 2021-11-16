package io.github.jieshengnp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Application implements Serializable {
    private String applicationID;
    private List<Applicant> applicantList = new ArrayList<Applicant>();
    private String applicationCode;

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

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public Application(){}

    public Application(String applicationID, String applicationCode, Applicant applicant){
        this.applicationID = applicationID;
        this.applicationCode = applicationCode;
        this.applicantList.add(applicant);
    }
}
