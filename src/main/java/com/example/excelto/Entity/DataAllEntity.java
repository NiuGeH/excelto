package com.example.excelto.Entity;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Data
@Table(name = "data_all")
public class DataAllEntity extends AbstractEntity{

    @Column(name = "data_more")
    @Lob
    private String dataMore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_tab_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private DataTabEntity dataTabEntity;

    @Transient
    private ArrayList<ArrayList<String>> list;
}
