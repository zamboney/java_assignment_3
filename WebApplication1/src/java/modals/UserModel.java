/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modals;

import dal.GetId;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author rani
 */
public class UserModel implements Serializable, GetId{
    String _fname;
    String _lname;
    String _email;
    String _id;
    int _dayLete; 

    public int getDayLete() {
        return _dayLete;
    }

    public void setDayLete(int _dayLete) {
        this._dayLete = _dayLete;
    }

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

    public UserModel(String _fname, String _lname, String _email) {
        this._fname = _fname;
        this._lname = _lname;
        this._email = _email;
        this._id = this._fname + this._email + new Date().getTime();   
    }

    
    
}
