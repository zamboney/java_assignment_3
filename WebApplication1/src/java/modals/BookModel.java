package modals;

import java.io.Serializable;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ritzhaki
 */
public class BookModel implements Serializable {
    private String _name;
    private String _aName;
    private String _id;
    private String _category;
    private String _year;
    private String _picturePath;

    public BookModel(String _name, String _aName, String _category, String _year) {
        this._name = _name;
        this._aName = _aName;
        this._category = _category;
        this._year = _year;
        this._id = _category+"_"+_name+"_"+new Date().getTime();
        this._picturePath = this._id + ".jpg";
    }

    public String getName() {
        return _name;
    }

    public String getaName() {
        return _aName;
    }

    public String getId() {
        return _id;
    }

    public String getCategory() {
        return _category;
    }

    public String getYear() {
        return _year;
    }

    public String getPicturePath() {
        return _picturePath;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public void setaName(String _aName) {
        this._aName = _aName;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public void setCategory(String _category) {
        this._category = _category;
    }

    public void setYear(String _year) {
        this._year = _year;
    }

    public void setPicturePath(String _picturePath) {
        this._picturePath = _picturePath;
    }
    
}
