package com.dbsnack.domain.entities.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("social_user")
public class SocialUser extends User {

    @Enumerated(EnumType.STRING)
    private SocialMedia socialMedia;

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }
}
