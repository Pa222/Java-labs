package com.example.lab1.services;

import com.example.lab1.dto.PublisherDeleteDto;
import com.example.lab1.dto.PublisherDto;
import com.example.lab1.model.Publisher;
import com.example.lab1.repos.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    @Autowired
    PublisherRepository publisherRepository;

    public ServiceResult addPublisher(PublisherDto info){
        publisherRepository.addPublisher(info.getPublisherName());
        return new ServiceResult(ServiceCode.CREATED, "Publisher successfully added");
    }

    public Iterable<Publisher> getPublishers(){
        return publisherRepository.getPublishers();
    }

    public Iterable<Publisher> getPublishersByPageNumber(int page, int size) {return publisherRepository.getPublishersByPageNumber(page, size);}

    public int getPublishersCount() {return publisherRepository.getPublisherCount();}

    public Publisher getById(Long id) {return publisherRepository.getById(id);}

    public ServiceResult editPublisher(Publisher info){
        publisherRepository.editPublisher(info.getId(), info.getPublisherName());

        return new ServiceResult(ServiceCode.OK, "Game successfully edited");
    }

    public ServiceResult deletePublisher(PublisherDeleteDto info){
        publisherRepository.deletePublisher(info.getId());

        return new ServiceResult(ServiceCode.OK, "Game successfully deleted");
    }
}
