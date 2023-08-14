package com.example.application.data.service;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private CompanyService companyService;
    private ContactService contactService;
    private StatusService statusService;

    public CrmService(final CompanyService companyService, final ContactService contactService, final  StatusService statusService){
        this.companyService = companyService;
        this.contactService = contactService;
        this.statusService = statusService;
    }

    public List<Company> getCompanyList(){
        return companyService.findAllCompanies();
    }

    public List<Status> getStatusList(){
        return statusService.findAllStatus();
    }

    public List<Contact> getContactList(String filter){
        return contactService.findAllContact(filter);
    }

    public void saveContact(Contact contact){
        contactService.saveContact(contact);
    }

    public void deleteContact(Contact contact){
        contactService.deleteContact(contact);
    }



}
