/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Gabriel Cauchis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.gcauchis.scalablepress4j.api;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.gcauchis.scalablepress4j.model.Category;
import com.github.gcauchis.scalablepress4j.model.Product;
import com.github.gcauchis.scalablepress4j.model.ProductAvailability;

/**
 * The Class ProductApiTest.
 */
public class ProductApiTest extends AbstractApiTest {

    /** The product api. */
    private ProductApi productApi;

    /**
     * Inits the test.
     */
    @Before
    public void init() {
        productApi = scalablePress.productApi();
    }

    /**
     * Test context.
     */
    @Test
    public void context() {
        Assert.assertNotNull(productApi);
    }

    /**
     * Test gets the categories.
     */
    @Test
    public void getCategories() {
        List<Category> categories = productApi.getCategories();
        Assert.assertNotNull(categories);
        log.info(categories.toString());
        Assert.assertFalse(categories.isEmpty());
    }

    /**
     * Test gets the category products.
     */
    @Test
    public void getCategoryProducts() {
        Category category = productApi.getCategoryProducts("short-sleeve-shirts");
        Assert.assertNotNull(category);
        log.info(category.toString());
    }

    /**
     * Test gets the product information.
     */
    @Test
    public void getProductInformation() {
        Product product = productApi.getProductInformation("gildan-ultra-cotton-t-shirt");
        Assert.assertNotNull(product);
        log.info(product.toString());
    }

    /**
     * Test gets the product availability.
     */
    @Test
    public void getProductAvailability() {
        ProductAvailability productAvailability = productApi.getProductAvailability("gildan-ultra-cotton-t-shirt");
        Assert.assertNotNull(productAvailability);
        log.info(productAvailability.toString());
    }
}
