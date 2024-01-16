package com.sowermate.user.services;

import com.sowermate.flexipunch.dtos.EmployeeDetailsDto;

public interface UserProfileService {

    EmployeeDetailsDto createUserProfile(EmployeeDetailsDto employeeDetailsDto);
    EmployeeDetailsDto updateUserProfile(EmployeeDetailsDto employeeDetailsDto);
}
