package com.chipviet.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.chipviet.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RescueTeamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RescueTeam.class);
        RescueTeam rescueTeam1 = new RescueTeam();
        rescueTeam1.setId(1L);
        RescueTeam rescueTeam2 = new RescueTeam();
        rescueTeam2.setId(rescueTeam1.getId());
        assertThat(rescueTeam1).isEqualTo(rescueTeam2);
        rescueTeam2.setId(2L);
        assertThat(rescueTeam1).isNotEqualTo(rescueTeam2);
        rescueTeam1.setId(null);
        assertThat(rescueTeam1).isNotEqualTo(rescueTeam2);
    }
}
