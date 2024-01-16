package com.sowermate.security.projections;

public interface UserAuthSuccessDetailsProjection {


    String getFirstName();
    String getLastName();
    String getUserUuid();
    String getPhone();

    String getCompanyUuid();
    String getCompanyName();
    String getCompanyAddress();

    Boolean getIsAccountNonExpired();
    Boolean getIsAccountNonLocked();
    Boolean getIsCredentialsNonExpired();
    Long getFailedAttempt();







}
