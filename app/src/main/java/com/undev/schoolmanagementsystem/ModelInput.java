package com.undev.schoolmanagementsystem;

public class ModelInput {
    private String classrollnumber, fname, lname, classname, year, position, bday, phone, sex, permaddress, curraddress,
    employeeid,teacherid;

    public ModelInput() {
    }

    public ModelInput(String classrollnumber, String fname, String lname, String classname, String year, String position, String bday,
                      String phone, String sex, String permaddress, String curraddress,String teacherid, String employeeid) {
        this.classrollnumber = classrollnumber;
        this.fname = fname;
        this.lname = lname;
        this.classname = classname;
        this.year = year;
        this.position = position;
        this.bday = bday;
        this.phone = phone;
        this.sex = sex;
        this.permaddress = permaddress;
        this.curraddress = curraddress;
        this.teacherid = teacherid;
        this.employeeid = employeeid;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public String getClassrollnumber() {
        return classrollnumber;
    }

    public void setClassrollnumber(String classrollnumber) {
        this.classrollnumber = classrollnumber;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPermaddress() {
        return permaddress;
    }

    public void setPermaddress(String permaddress) {
        this.permaddress = permaddress;
    }

    public String getCurraddress() {
        return curraddress;
    }

    public void setCurraddress(String curraddress) {
        this.curraddress = curraddress;
    }
}
