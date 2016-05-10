package com.github.gcauchis.scalablepress4j.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.gcauchis.scalablepress4j.model.Category;
import com.github.gcauchis.scalablepress4j.model.Color;
import com.github.gcauchis.scalablepress4j.model.ColorAvailability;
import com.github.gcauchis.scalablepress4j.model.Design;
import com.github.gcauchis.scalablepress4j.model.DesignResponse;
import com.github.gcauchis.scalablepress4j.model.Product;
import com.github.gcauchis.scalablepress4j.model.ProductAvailability;
import com.github.gcauchis.scalablepress4j.model.ProductOveriew;

public class WorkFlowTest extends AbstractServiceTest {

    private BillingService billing;
    private DesignService design;
    private EventService event;
    private OrderService order;
    private ProductService product;
    private QuoteService quote;
    private ReshipService reship;

    @Before
    public void init() {
        billing = scalablePress.billing();
        design = scalablePress.design();
        order = scalablePress.order();
        event = scalablePress.event();
        product = scalablePress.product();
        quote = scalablePress.quote();
        reship = scalablePress.reship();
    }

    @Test
    public void context() {
        Assert.assertNotNull(billing);
        Assert.assertNotNull(design);
        Assert.assertNotNull(order);
        Assert.assertNotNull(event);
        Assert.assertNotNull(product);
        Assert.assertNotNull(quote);
        Assert.assertNotNull(reship);
    }

    @Test
    public void workflow() {
        log.info("###################################################################################");
        log.info("############################### Start workflow test ###############################");
        log.info("###################################################################################");
        final List<Category> categories = product.getCategories();
        Assert.assertNotNull(categories);
        Assert.assertFalse(categories.isEmpty());
        log.info("Categories: {}", categories.toString());

        final String categoryId = "short-sleeve-shirts";
        Category category = categories.stream().filter(c -> categoryId.equals(c.getCategoryId())).findFirst().orElse(null);
        Assert.assertNotNull("category " + categoryId + " not found", category);
        Assert.assertEquals(categoryId, category.getCategoryId());
        Assert.assertNull(category.getProducts());

        category = product.getCategoryProducts(categoryId);
        Assert.assertNotNull("category " + categoryId + " not found", category);
        Assert.assertNotNull(category.getProducts());
        Assert.assertEquals(categoryId, category.getCategoryId());
        log.info("Category products: {}", category);

        final String productId = "gildan-ultra-cotton-t-shirt";
        ProductOveriew productOveriew = category.getProducts().stream().filter(p -> productId.equals(p.getId())).findFirst().orElse(null);
        Assert.assertNotNull("Product " + productId + " not found in category " + categoryId, productOveriew);
        Assert.assertEquals(productId, productOveriew.getId());

        final Product productItem = product.getProductInformation(productId);
        Assert.assertNotNull("Product " + productId + " not found", productItem);
        Assert.assertEquals(productId, productItem.getProductId());
        log.info("Product: {}", productItem);
        Assert.assertTrue("Product " + productItem + " should be available", Boolean.parseBoolean(productItem.getAvailable()));

        Assert.assertNotNull(productItem.getColors());
        final String productColorId = "black";
        final Color productColor = productItem.getColors().stream().filter(c -> productColorId.equalsIgnoreCase(c.getName())).findFirst().orElse(null);
        Assert.assertNotNull("Color " + productColorId + " not found in product " + productId, productColor);

        final String productColorSize = "xxl";
        Assert.assertNotNull(productColor.getSizes());
        Assert.assertTrue("Size " + productColorSize + " not fond in color " + productColorId + " of product " + productId, productColor.getSizes().stream().filter(s -> productColorSize.equals(s)).findFirst().isPresent());
        
        final ProductAvailability productAvailability = product.getProductAvailability(productId);
        Assert.assertNotNull(productAvailability);
        log.info("ProductAvailability: {}", productAvailability);
        Assert.assertNotNull(productAvailability.getColorsAvailability());
        final ColorAvailability colorAvailability = productAvailability.getColorsAvailability().get(productColorId);
        Assert.assertNotNull("Color " + productColorId + " not available", colorAvailability);
        Assert.assertNotNull(colorAvailability.getSizesAvailability());
        Assert.assertNotNull(colorAvailability.getSizesAvailability().get(productColorSize));
        Assert.assertTrue("Size " + productColorSize + " unavailable for color " + productColorId, colorAvailability.getSizesAvailability().get(productColorSize) > 0);
        
        Design designRequest = buildTestDesign();
        DesignResponse response = design.create(designRequest);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getDesignId());
        log.info("Design created: {} ",response.toString());
        log.info("Design created with designId = {}", response.getDesignId());
        final String designId = response.getDesignId();
        
        DesignResponse retieveDesign = design.retrieve(designId);
        Assert.assertNotNull(retieveDesign);
        Assert.assertEquals(designId, retieveDesign.getDesignId());
        log.info("Design retrieved: {} ", retieveDesign.toString());
        
        
        
        
        
        
        
        
        
        
        
        DesignResponse deleted = design.delete(designId);
        Assert.assertNotNull(deleted);
        Assert.assertEquals(designId, deleted.getDesignId());
        log.info("Deleted design: {} ", deleted.toString());
        
        log.info("###################################################################################");
        log.info("################################ End workflow test ################################");
        log.info("###################################################################################");
    }
}
