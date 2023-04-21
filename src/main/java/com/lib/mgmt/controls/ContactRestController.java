package com.lib.mgmt.controls;

import com.lib.mgmt.dtos.ContactDto;
import com.lib.mgmt.services.library.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/contact")
public class ContactRestController {

    private ContactService contactService;

    @Autowired
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactDto>> findAll() {
        try {
            List<ContactDto> contactDtoList = contactService.findAll();
            if (contactDtoList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(contactDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ContactDto> createBook(
            @RequestBody ContactDto contactDto) {
        ContactDto _contactDto = contactService.createContact(contactDto);
        if(_contactDto == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(_contactDto, HttpStatus.CREATED);
    }
}
