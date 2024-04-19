package com.authorization.service;

import com.authorization.dto.Country;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

@Service
@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'USER')")
public class FilterServiceImpl {

    //there is PostFilter too.
    @PreFilter("filterObject.continent == 'asia'") //only countries with continent == asia will pass
    public Collection<Country> filterNotAsianCountries(@RequestBody List<Country> countries){
        return countries;
    }


}
