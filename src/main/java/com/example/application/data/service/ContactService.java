package com.example.application.data.service;

import com.example.application.data.entity.Contact;
import com.example.application.data.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private ContactRepository contactRepository;

    public ContactService(final ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    public List<Contact> findAllContact(String filter){
        if(filter == null || filter.isEmpty()){
            return contactRepository.findAll();
        }else{
            return contactRepository.search(filter);
        }
    }

    public long countContacts(){
        return contactRepository.count();
    }

    public void deleteContact(Contact contact){
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact){
        if(contact==null){
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }
}
