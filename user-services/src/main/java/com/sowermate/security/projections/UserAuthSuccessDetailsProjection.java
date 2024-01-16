package com.sowermate.security.projections;

public interface UserAuthSuccessDetailsProjection {

    String getIso2Code();

    String getUserUuid();
    String getFirstName();
    String getLastName();
    Boolean getIsAccountNonExpired();
    Boolean getIsAccountNonLocked();
    Boolean getIsCredentialsNonExpired();
    Long getFailedAttempt();

    String getPhone();

    String getCompanyUuid();
    String getCompanyName();
    String getCompanyAddress();


}
