package modals;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import dal.GetId;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ritzhaki
 */
public class BookModel implements Serializable, Cloneable, GetId {

    private String _name;
    private String _aName;
    private String _id;
    private String _category;
    private String _year;
    private String _picturePath;
    private BookCondition _condition;

    public BookCondition getCondition() {
        return _condition;
    }

    public void setCondition(BookCondition _condition) {
        this._condition = _condition;
    }
    private boolean _rented;

    public boolean isRented() {
        return _rented;
    }

    public void setRented(boolean _rented) {
        this._rented = _rented;
    }

    @Override
    public BookModel clone() {
        return new BookModel(this);
    }
    private BookModel(BookModel b){
        this(b._name,b._aName,b._category,b._year, b._condition);
        this._id = this._category + "_" + this._name + "_" + UUID.randomUUID().toString();
        this._picturePath = b._id + ".jpg";
    }
    public BookModel(String _name, String _aName, String _category, String _year, BookCondition condition) {
        this._name = _name;
        this._aName = _aName;
        this._category = _category;
        this._year = _year;
        this._id = _category + "_" + _name + "_" + UUID.randomUUID().toString();
        this._picturePath = this._id + ".jpg";
        this._condition = condition;
        this._rented = false;
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

    public enum BookCondition {
        Good,
        Bad,
        UnUsed
    }

}
