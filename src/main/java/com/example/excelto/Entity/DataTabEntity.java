package com.example.excelto.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "date_tab")
public class DataTabEntity extends AbstractEntity{

    @Column(name = "date_time")
    private String dateTime;
}


