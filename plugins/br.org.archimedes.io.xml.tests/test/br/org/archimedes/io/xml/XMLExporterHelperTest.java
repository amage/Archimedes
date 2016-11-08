/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Julien Renaut - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/05/31, 12:37:41, by Julien Renaut.<br>
 * It is part of package br.org.archimedes.io.xml on the br.org.archimedes.io.xml.tests project.<br>
 */

package br.org.archimedes.io.xml;

import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * XMLExporterHelper's tests
 * 
 * @author Julien Renaut
 */
public class XMLExporterHelperTest {

    // TODO Test the XML Exporting system
 
    /**
     * Tests para the XMLExporterHelper to write a point
     * 
     * @throws IOException
     *             If it can't write
     */
    @Test
    public void testPoint () throws IOException {

        Point point = new Point(0, 0);
        OutputStream output = new ByteArrayOutputStream();

        XMLExporterHelper.write(point, output);
        String result = output.toString();
        Assert.assertEquals("<point x=\"0.0\" y=\"0.0\" />", result);
    }

    /**
     * Testa para write(Point, String, OutputStream)
     * 
     * @throws IOException
     *             Se fracassar...
     */
    @Test
    public void testCustomPoint () throws IOException {

        Point point = new Point(0, 0);
        OutputStream output = new ByteArrayOutputStream();

        XMLExporterHelper.write(point, "lalala", output);
        String result = output.toString();
        Assert.assertEquals("<lalala x=\"0.0\" y=\"0.0\" />", result);
    }

    /**
     * Testa para write(Vector, OutputStream)
     * 
     * @throws IOException
     *             Se fracassar...
     */
    @Test
    public void testVector () throws IOException {

        Point point = new Point(1, 1);
        Vector vector = new Vector(point);

        OutputStream output = new ByteArrayOutputStream();

        XMLExporterHelper.write(vector, output);
        String result = output.toString();
        Assert.assertEquals("<vector>\n\t<point x=\"1.0\" y=\"1.0\" />\n</vector>", result);
    }

    /**
     * Testa para write(Vector, String, OutputStream)
     * 
     * @throws IOException
     *             Se fracassar...
     */
    @Test
    public void testCustomVector () throws IOException {

        Point point = new Point(1, 1);
        Vector vector = new Vector(point);

        OutputStream output = new ByteArrayOutputStream();

        XMLExporterHelper.write(vector, "lalala", output);
        String result = output.toString();
        Assert.assertEquals("<lalala>\n\t<point x=\"1.0\" y=\"1.0\" />\n</lalala>", result);
    }

}
