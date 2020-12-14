package com.edeja.edejaEducation.helpers;

import com.edeja.edejaEducation.models.UserPlay;
import java.util.List;

public class UserPlayList {

    private List<UserPlay> list;

    public List<UserPlay> getList() {
        return list;
    }

    public void setList(List<UserPlay> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "UserPlayList{" +
                "list=" + list +
                '}';
    }
}
