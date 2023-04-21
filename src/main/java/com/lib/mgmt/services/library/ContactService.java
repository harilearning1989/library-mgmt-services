package com.lib.mgmt.services.library;

import com.lib.mgmt.dtos.ContactDto;

import java.util.List;

public interface ContactService {
    List<ContactDto> findAll();

    ContactDto createContact(ContactDto contactDto);
}
