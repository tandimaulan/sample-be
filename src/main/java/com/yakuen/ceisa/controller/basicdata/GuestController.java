package com.yakuen.ceisa.controller.basicdata;

import com.yakuen.ceisa.model.basicdata.Guest;
import com.yakuen.ceisa.service.basicdata.GuestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
