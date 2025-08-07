package com.Job.JobPortal.Model;

import lombok.Data;

@Data
public class JobFilterRequest {
    private String title;
    private String company;
    private String location;
    private Integer minSalary;
    private Integer maxSalary;

    private String sortBy;
    private String sortDirection;

    private int page = 0;
    private int size = 10;

}
