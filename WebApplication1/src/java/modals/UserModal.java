/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modals;

import java.util.Date;

/**
 *
 * @author rani
 */
public class UserModal {
    String _fname;
    String _lname;
    String _email;
    String _id;

    public String getFname() {
        return _fname;
    }

    public void setFname(String _fname) {
        this._fname = _fname;
    }

    public String getLname() {
        return _lname;
    }

    public void setLname(String _lname) {
        this._lname = _lname;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String _email) {
        this._email = _email;
    }

    public String getId() {
        return _id;
    }

    public UserModal(String _fname, String _lname, String _email) {
        this._fname = _fname;
        this._lname = _lname;
        this._email = _email;
        this._id = this._fname + this._email + new Date().toString();   
    }

    
    
}
