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

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.github.gcauchis.scalablepress4j.ScalablePress4J;
import com.github.gcauchis.scalablepress4j.model.Design;
import com.github.gcauchis.scalablepress4j.model.DesignSide;
import com.github.gcauchis.scalablepress4j.model.DesignSides;
import com.github.gcauchis.scalablepress4j.model.Dimension;
import com.github.gcauchis.scalablepress4j.model.Position;
import com.github.gcauchis.scalablepress4j.model.PositionOffset;
import com.github.gcauchis.scalablepress4j.test.PropertyTestConfiguration;

/**
 * The Class AbstractApiTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                ScalablePress4J.class,
                PropertyTestConfiguration.class
                },
        loader = AnnotationConfigContextLoader.class)
public abstract class AbstractApiTest {

    /** The test api logger. */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    /** The scalable press spring bean. */
    @Autowired
    protected ScalablePress4J scalablePress;
    
    /**
     * Builds the test design.
     *
     * @return the design
     */
    public Design buildTestDesign() {
        Design design = new Design();
        design.setName("Test");
        design.setType("dtg");
        DesignSides designSides = new DesignSides();
        DesignSide front = new DesignSide();
        front.setArtwork("https://raw.githubusercontent.com/gcauchis/ScalablePressWrapper/master/src/test/resources/lena_150dpi.png");
        Dimension dimension = new Dimension();
        dimension.setWidth(5);
        front.setDimensions(dimension);
        Position position = new Position();
        position.setHorizontal("C");
        PositionOffset positionOffset = new PositionOffset();
        positionOffset.setTop(2.5);
        position.setOffset(positionOffset);
        front.setPosition(position);
        front.setResize(true);
        designSides.setFront(front);
        design.setSides(designSides);
        return design;
    }
}
