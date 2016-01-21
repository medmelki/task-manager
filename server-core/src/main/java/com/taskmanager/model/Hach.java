package com.taskmanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "hach")
public class Hach implements Serializable {

    @Id
    private String hach;


    public Hach() {
        this.hach = UUID.randomUUID().toString();
    }

    public Hach(String hach) {
        this.hach = hach;
    }

    public String getHach() {
        return hach;
    }

    public void setHach(String hach) {
        this.hach = hach;
    }
}
