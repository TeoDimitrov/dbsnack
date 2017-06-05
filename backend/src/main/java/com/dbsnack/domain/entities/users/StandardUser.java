package com.dbsnack.domain.entities.users;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("standard_user")
public class StandardUser extends User {

    @Column(name = "activation_token")
    private String activationToken;

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }
}
