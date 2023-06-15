package project.main.webstore.domain.review.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import project.main.webstore.domain.review.service.ReviewGetService;
import project.main.webstore.domain.review.service.ReviewService;


@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
class ReviewControllerTest {

    @MockBean
    ReviewService reviewService;
    @MockBean
    ReviewGetService reviewGetService;

    @Test
    void test() {

    }
}