package com.Job.JobPortal.Repository;

import com.Job.JobPortal.Model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends MongoRepository<Job,String> {

}
