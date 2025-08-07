package com.Job.JobPortal.Controller;

import com.Job.JobPortal.Model.JobFilterRequest;
import com.Job.JobPortal.Model.JobRequestDTO;
import com.Job.JobPortal.Model.JobResponseDTO;
import com.Job.JobPortal.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping
    public JobResponseDTO createJob(@RequestBody JobRequestDTO jobRequestDTO) {
        return jobService.createJob(jobRequestDTO);
    }

    @GetMapping
    public List<JobResponseDTO> getAllJobs() {
        return jobService.getAllJobs();
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<JobResponseDTO>> filterJobs(@RequestBody JobFilterRequest jobFilterRequest) {
        Page<JobResponseDTO> Jobs = jobService.filterJobs(jobFilterRequest);
        return ResponseEntity.ok(Jobs);
    }
}


