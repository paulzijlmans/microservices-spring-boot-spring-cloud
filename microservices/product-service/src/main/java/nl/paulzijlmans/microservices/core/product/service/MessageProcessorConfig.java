package nl.paulzijlmans.microservices.core.product.service;

import nl.paulzijlmans.api.core.product.Product;
import nl.paulzijlmans.api.core.product.ProductService;
import nl.paulzijlmans.api.event.Event;
import nl.paulzijlmans.api.exceptions.EventProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MessageProcessorConfig {
    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessorConfig.class);

    private final ProductService productService;

    @Autowired
    public MessageProcessorConfig(ProductService productService) {
        this.productService = productService;
    }

    @Bean
    public Consumer<Event<Integer, Product>> messageProcessor() {
        return event -> {
            LOG.info("Process message created at {}...", event.eventCreatedAt());

            switch (event.eventType()) {
                case CREATE -> {
                    Product product = event.data();
                    LOG.info("Create product with ID: {}", product.productId());
                    productService.createProduct(product).block();
                }
                case DELETE -> {
                    int productId = event.key();
                    LOG.info("Delete recommendations with ProductID: {}", productId);
                    productService.deleteProduct(productId).block();
                }
                default -> {
                    String errorMessage = "Incorrect event type: " + event.eventType() + ", expected a CREATE or DELETE event";
                    LOG.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
                }
            }

            LOG.info("Message processing done!");

        };
    }
}
