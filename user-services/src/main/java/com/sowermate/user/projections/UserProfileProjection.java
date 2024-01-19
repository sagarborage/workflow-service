package com.sowermate.user.projections;

import com.sowermate.user.entities.Gender;

import java.sql.Date;

public interface UserProfileProjection {

    String getFirstName();
    String getUserProfileUuid();
    String getUserUuid();

    String getLastName();

    String getAddress();

    Date getDob();

    String getProfileUrl();

    String getProfileBackUrl();

    Gender getGender();

    String getTenantUuid();

    String getUsername();

    String getPhone();
    String getRoleName();
}
