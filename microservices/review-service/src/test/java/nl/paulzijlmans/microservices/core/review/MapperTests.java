package nl.paulzijlmans.microservices.core.review;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import nl.paulzijlmans.api.core.review.Review;
import nl.paulzijlmans.microservices.core.review.persistence.ReviewEntity;
import nl.paulzijlmans.microservices.core.review.services.ReviewMapper;


class MapperTests {

  private final ReviewMapper mapper = Mappers.getMapper(ReviewMapper.class);

  @Test
  void mapperTests() {

    assertNotNull(mapper);

    Review api = new Review(1, 2, "a", "s", "C", "adr");

    ReviewEntity entity = mapper.apiToEntity(api);

    assertEquals(api.productId(), entity.getProductId());
    assertEquals(api.reviewId(), entity.getReviewId());
    assertEquals(api.author(), entity.getAuthor());
    assertEquals(api.subject(), entity.getSubject());
    assertEquals(api.content(), entity.getContent());

    Review api2 = mapper.entityToApi(entity);

    assertEquals(api.productId(), api2.productId());
    assertEquals(api.reviewId(), api2.reviewId());
    assertEquals(api.author(), api2.author());
    assertEquals(api.subject(), api2.subject());
    assertEquals(api.content(), api2.content());
    assertNull(api2.serviceAddress());
  }

  @Test
  void mapperListTests() {

    assertNotNull(mapper);

    Review api = new Review(1, 2, "a", "s", "C", "adr");
    List<Review> apiList = Collections.singletonList(api);

    List<ReviewEntity> entityList = mapper.apiListToEntityList(apiList);
    assertEquals(apiList.size(), entityList.size());

    ReviewEntity entity = entityList.get(0);

    assertEquals(api.productId(), entity.getProductId());
    assertEquals(api.reviewId(), entity.getReviewId());
    assertEquals(api.author(), entity.getAuthor());
    assertEquals(api.subject(), entity.getSubject());
    assertEquals(api.content(), entity.getContent());

    List<Review> api2List = mapper.entityListToApiList(entityList);
    assertEquals(apiList.size(), api2List.size());

    Review api2 = api2List.get(0);

    assertEquals(api.productId(), api2.productId());
    assertEquals(api.reviewId(), api2.reviewId());
    assertEquals(api.author(), api2.author());
    assertEquals(api.subject(), api2.subject());
    assertEquals(api.content(), api2.content());
    assertNull(api2.serviceAddress());
  }
}
