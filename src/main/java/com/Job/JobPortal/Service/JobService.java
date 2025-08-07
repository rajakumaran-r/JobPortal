package com.Job.JobPortal.Service;

import com.Job.JobPortal.Model.JobFilterRequest;
import com.Job.JobPortal.Model.JobRequestDTO;
import com.Job.JobPortal.Model.JobResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface JobService {
    JobResponseDTO createJob(JobRequestDTO jobRequestDTO);
    List<JobResponseDTO> getAllJobs();
    Page<JobResponseDTO> filterJobs(JobFilterRequest filterRequest);

}
