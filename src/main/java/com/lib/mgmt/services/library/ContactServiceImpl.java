package com.lib.mgmt.services.library;

import com.lib.mgmt.dtos.ContactDto;
import com.lib.mgmt.models.library.Contact;
import com.lib.mgmt.repos.library.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<ContactDto> findAll() {
        List<Contact> contactList = contactRepository.findAll();
        List<ContactDto> contactDtoList = contactList
                .stream()
                .map(m -> {
                    ContactDto dto = modelToDto(m);
                    return dto;
                })
                .collect(Collectors.toList());
        return contactDtoList;
    }

    @Override
    public ContactDto createContact(ContactDto dto) {
        Contact model = dtoToModel(dto);
        model = contactRepository.save(model);
        dto = modelToDto(model);
        return dto;
    }

    private Contact dtoToModel(ContactDto dto) {
        Contact model = new Contact();
        model.setName(dto.getName());
        model.setEmail(dto.getEmail());
        model.setMobile(dto.getMobile());
        model.setMessage(dto.getMessage());
        model.setCreatedAt(new Date());
        return model;
    }

    private ContactDto modelToDto(Contact m) {
        ContactDto dto = new ContactDto();
        dto.setId(m.getId());
        dto.setName(m.getName());
        dto.setEmail(m.getEmail());
        dto.setMobile(m.getMobile());
        dto.setMessage(m.getMessage());
        return dto;
    }
}
