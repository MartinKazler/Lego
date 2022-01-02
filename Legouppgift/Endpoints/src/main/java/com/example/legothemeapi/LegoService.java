package com.example.legothemeapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class LegoService {

    @Autowired
    LegoRepository repository;

    public Collection<Lego> getAllLego() {

        return repository.getAll().stream()
                .sorted(Comparator.comparing(Lego::get_id)).collect(Collectors.toList());
    }

    public Collection<Lego> getSpecificTheme(String searchedLegoTheme) {

        return repository.getAll()
                .stream()
                .filter(x -> x.getName().toLowerCase().matches(".*" + searchedLegoTheme.toLowerCase() + ".*") ||
                        searchedLegoTheme.equals(Integer.toString(x.get_id())) ||
                        searchedLegoTheme.equals(Integer.toString(x.getParentId()))
                )
                .collect(Collectors.toList());

    }
}