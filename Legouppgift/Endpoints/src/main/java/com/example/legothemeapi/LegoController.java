package com.example.legothemeapi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;

@RestController
public class LegoController {
    @Autowired
    private  LegoService legoService;


    @PutMapping("/search/{searchValue}")
    public Collection search( @PathVariable("searchValue") String searchValue) {

        return legoService.getSpecificTheme(searchValue);
    }

    @GetMapping("/getAll")
    public Collection<Lego> getAll(final Long objectId) {
        return  legoService.getAllLego();
    }

}