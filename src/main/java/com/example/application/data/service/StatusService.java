package com.example.application.data.service;

import com.example.application.data.entity.Status;
import com.example.application.data.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private StatusRepository statusRepository;

    public StatusService(final StatusRepository  statusRepository){
        this.statusRepository = statusRepository;
    }

    public List<Status> findAllStatus(){
        return statusRepository.findAll();
    }
}
