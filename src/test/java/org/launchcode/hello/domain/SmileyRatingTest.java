package org.launchcode.hello.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.launchcode.hello.web.rest.TestUtil;

public class SmileyRatingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmileyRating.class);
        SmileyRating smileyRating1 = new SmileyRating();
        smileyRating1.setId(1L);
        SmileyRating smileyRating2 = new SmileyRating();
        smileyRating2.setId(smileyRating1.getId());
        assertThat(smileyRating1).isEqualTo(smileyRating2);
        smileyRating2.setId(2L);
        assertThat(smileyRating1).isNotEqualTo(smileyRating2);
        smileyRating1.setId(null);
        assertThat(smileyRating1).isNotEqualTo(smileyRating2);
    }
}
