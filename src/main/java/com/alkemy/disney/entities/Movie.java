package com.alkemy.disney.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String image;
    private String tittle;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;

    private Integer score;

    @ManyToOne
    private Gender gender;

    @OneToMany
    private List<Figure> characters;

}
