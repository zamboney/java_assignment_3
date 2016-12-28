/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modals;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ritzhaki
 */
public class RentModel implements Serializable {

    private String _bookId;
    private String _userId;
    private int _days;
    private Date _startTime;

    public RentModel(String _bookId, String _userId, int _days) {
        this._bookId = _bookId;
        this._userId = _userId;
        this._days = _days;
        this._startTime = new Date();
    }

    public String getBookId() {
        return _bookId;
    }

    public void setBookId(String _bookId) {
        this._bookId = _bookId;
    }

    public String getUserId() {
        return _userId;
    }

    public void setUserId(String _userId) {
        this._userId = _userId;
    }

    public int getDays() {
        return _days;
    }

    public void setDays(int _days) {
        this._days = _days;
    }

    public Date getStartTime() {
        return _startTime;
    }

    public void setStartTime(Date _startTime) {
        this._startTime = _startTime;
    }

}
