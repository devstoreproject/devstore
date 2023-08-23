package project.main.webstore.domain.review.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import project.main.webstore.domain.image.mapper.ImageMapper;
import project.main.webstore.domain.review.mapper.ReviewMapper;
import project.main.webstore.domain.review.service.ReviewGetService;
import project.main.webstore.domain.review.service.ReviewService;


@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class ReviewControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    ReviewGetService getService;
    @MockBean
    ReviewService service;
    @MockBean
    ReviewMapper reviewMapper;
    @MockBean
    ImageMapper imageMapper;




    @Test
    @DisplayName("리뷰 등록 성공")
    void post_review_test() throws Exception{
        // given

        // when

        // then

    }

}