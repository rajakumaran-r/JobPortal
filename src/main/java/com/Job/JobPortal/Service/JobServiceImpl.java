package com.Job.JobPortal.Service;

import com.Job.JobPortal.Model.Job;
import com.Job.JobPortal.Model.JobFilterRequest;
import com.Job.JobPortal.Model.JobRequestDTO;
import com.Job.JobPortal.Model.JobResponseDTO;
import com.Job.JobPortal.Repository.JobRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.query.Query; // ✅ correct
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JobRepository jobRepository;

    @Override
    public JobResponseDTO createJob(JobRequestDTO dto) {
        Job job = new Job();
        BeanUtils.copyProperties(dto, job);
        job.setPostedDate(LocalDate.now());
        job = jobRepository.save(job);
        JobResponseDTO responseDTO = new JobResponseDTO();
        BeanUtils.copyProperties(job, responseDTO);
        return responseDTO;
    }

    @Override
    public List<JobResponseDTO> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(job -> {
                    JobResponseDTO dto = new JobResponseDTO();
                    BeanUtils.copyProperties(job, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<JobResponseDTO> filterJobs(JobFilterRequest request) {
        Query query = new Query();

        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            query.addCriteria(Criteria.where("title").regex(request.getTitle(), "i"));
        }

        if (request.getCompany() != null && !request.getCompany().isEmpty()) {
            query.addCriteria(Criteria.where("company").is(request.getCompany()));
        }

        if (request.getLocation() != null && !request.getLocation().isEmpty()) {
            query.addCriteria(Criteria.where("location").is(request.getLocation()));
        }

        if (request.getMinSalary() != null && request.getMaxSalary() != null) {
            query.addCriteria(Criteria.where("salary").gte(request.getMinSalary()).lte(request.getMaxSalary()));
        }

        // Sorting
        String sortBy = request.getSortBy() != null ? request.getSortBy() : "postedDate";
        Sort.Direction direction = "asc".equalsIgnoreCase(request.getSortDirection()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        query.with(Sort.by(direction, sortBy));

        // Pagination
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        query.with(pageable);

        List<Job> jobs = mongoTemplate.find(query, Job.class);
        long total = mongoTemplate.count(query.skip(-1).limit(-1), Job.class);

        List<JobResponseDTO> jobDTOs = jobs.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(jobDTOs, pageable, total);
    }
    private JobResponseDTO mapToDTO(Job job) {
        JobResponseDTO dto = new JobResponseDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setCompany(job.getCompany());
        dto.setLocation(job.getLocation());
        dto.setSalary(job.getSalary());
        dto.setPostedDate(job.getPostedDate());
        return dto;
    }




}
