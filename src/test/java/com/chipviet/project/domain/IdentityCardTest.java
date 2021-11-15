package com.chipviet.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.chipviet.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IdentityCardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdentityCard.class);
        IdentityCard identityCard1 = new IdentityCard();
        identityCard1.setId(1L);
        IdentityCard identityCard2 = new IdentityCard();
        identityCard2.setId(identityCard1.getId());
        assertThat(identityCard1).isEqualTo(identityCard2);
        identityCard2.setId(2L);
        assertThat(identityCard1).isNotEqualTo(identityCard2);
        identityCard1.setId(null);
        assertThat(identityCard1).isNotEqualTo(identityCard2);
    }
}
