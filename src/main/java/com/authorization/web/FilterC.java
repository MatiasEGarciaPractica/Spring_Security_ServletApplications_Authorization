package com.authorization.web;

import com.authorization.dto.Country;
import com.authorization.service.FilterServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/filter")
public class FilterC {

    private final FilterServiceImpl filterService;

    public FilterC(FilterServiceImpl filterService){
        this.filterService = filterService;
    }

    @GetMapping(value = "/asian", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Country>> asianFilter(@RequestBody List<Country> countries){
        return ResponseEntity.ok(filterService.filterNotAsianCountries(countries));
    }

}
