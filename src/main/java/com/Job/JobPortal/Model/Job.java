package com.Job.JobPortal.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Data
@Document(collection = "jobs")
public class Job {
    @Id
    private String id;
    private String title;
    private String company;
    private String location;
    private String JobType;
    private int experienceRequired;
    private double salary;
    private LocalDate postedDate;
}
