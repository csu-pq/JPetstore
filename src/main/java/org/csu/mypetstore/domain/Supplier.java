package org.csu.mypetstore.domain;

public class Supplier {
    private String suppid;
    private String username;
    private String password;
    private String status;
    private String addr1;
    private String addr2;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String phone;
    //id
    public String getSuppid() {
        return suppid;
    }
    public void setSuppid(String suppid) {
        this.suppid = suppid;
    }
    //username
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    //password
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    //status
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}