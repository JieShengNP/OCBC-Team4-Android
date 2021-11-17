package io.github.jieshengnp;

import java.io.Serializable;
import java.util.Date;

public class Applicant implements Serializable{
    private Boolean isSingPass;
    private String Title;
    private String Name;
    private String Nationality;
    private String NRIC;
    private String Race;
    private String DOB;
    private String Gender;
    private String Postal;
    private String Street;
    private String Block;
    private String Unit;
    private String Mobile;
    private String Email;
    private String Occupation;
    private String Martial;

    public Boolean getSingPass() {
        return isSingPass;
    }

    public void setSingPass(Boolean singPass) {
        isSingPass = singPass;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        name = Name;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getNRIC() {
        return NRIC;
    }

    public void setNRIC(String NRIC) {
        this.NRIC = NRIC;
    }

    public String getRace() {
        return Race;
    }

    public void setRace(String race) {
        Race = race;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPostal() {
        return Postal;
    }

    public void setPostal(String postal) {
        Postal = postal;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getMartial() {
        return Martial;
    }

    public void setMartial(String martial) {
        Martial = martial;
    }

    public Applicant(){}

    public Applicant(String title, String name, String nationality, String NRIC, String race, String DOB, String gender, String postal, String street, String block, String unit, String mobile, String email, String occupation, String martial) {
        Title = title;
        Name = name;
        Nationality = nationality;
        this.NRIC = NRIC;
        Race = race;
        this.DOB = DOB;
        Gender = gender;
        Postal = postal;
        Street = street;
        Block = block;
        Unit = unit;
        Mobile = mobile;
        Email = email;
        Occupation = occupation;
        Martial = martial;
    }

}
