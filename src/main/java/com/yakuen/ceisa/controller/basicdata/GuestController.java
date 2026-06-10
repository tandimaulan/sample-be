package com.yakuen.ceisa.controller.basicdata;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yakuen.ceisa.model.basicdata.Guest;
import com.yakuen.ceisa.service.basicdata.GuestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guest")
@RequiredArgsConstructor
public class GuestController {
    private final GuestService guestService;

    @PostMapping("/all")
    public List<Guest> getAllGuests() {
        return guestService.getGuests();
    }
}
