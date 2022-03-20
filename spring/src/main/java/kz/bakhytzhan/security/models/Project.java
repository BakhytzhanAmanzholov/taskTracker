package kz.bakhytzhan.security.models;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

// This is a model and an entity "Project". It has person many(Projects)-to-one(Person) dependencies
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    @ManyToOne
    private Person person;
    private Date startDate;
    private Date completionDate;
    private String status;
    private int priority;
}
