/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Bruno da Hora, Luiz Real - initial API and implementation<br>
 * Bruno Klava, Luiz Real - behavior fixed<br>
 * <br>
 * This file was created on 30/04/2009, 07:23:56.<br>
 * It is part of br.org.archimedes.extenders on the br.org.archimedes.extend project.<br>
 */

package br.org.archimedes.extenders;

import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;
import br.org.archimedes.semiline.Semiline;

import java.util.Collection;
import java.util.List;

/**
 * @author Bruno da Hora, Luiz Real
 */
public class PolylineExtender implements Extender {

    private IntersectionManager intersectionManager;

    public void extend (Element element, Collection<Element> references, Point extremePoint)
            throws NullArgumentException {

        if (element == null || references == null || extremePoint == null) {
            throw new NullArgumentException();
        }
        
        intersectionManager = new IntersectionManagerEPLoader().getIntersectionManager();

        Polyline polyline = (Polyline) element;

        List<Point> points = polyline.getPoints();
        extremePoint = points.get(getIndexInPolyline(polyline, extremePoint));

        if (!doExtend(polyline, references, extremePoint)) {
            Point otherExtreme = getOtherExtreme(polyline, extremePoint);
            
            doExtend(polyline, references, otherExtreme);
        }

    }
    
    private boolean doExtend(Polyline polyline, Collection<Element> references, Point extremePoint) throws NullArgumentException {
        Semiline semiline = createHelperSemiline(polyline, extremePoint);

        Collection<Point> intersectionPoints = intersectionManager.getIntersectionsBetween(
                semiline, references);
        
        Point nearestReferencePoint = getNearestReferencePoint(extremePoint, polyline,
                intersectionPoints);

        if (nearestReferencePoint != null) {
            extremePoint.setX(nearestReferencePoint.getX());
            extremePoint.setY(nearestReferencePoint.getY());
            return true;
        }
        return false;
    }
    
    private Point getOtherExtreme (Polyline polyline, Point extremePoint) {
        int index = getIndexInPolyline(polyline, extremePoint);
        List<Point> points = polyline.getPoints();
        return points.get(points.size() - 1 - index);
    }

    private Semiline createHelperSemiline(Polyline polyline, Point extremeToExtend) {
        Point semilineInitial;
        int index = getIndexInPolyline(polyline, extremeToExtend);
        List<Point> points = polyline.getPoints();

        if (index == 0) {
            semilineInitial = points.get(1);
        }
        else {
            semilineInitial = points.get(points.size() - 2);
        }
        Point extremePoint = polyline.getPoints().get(index);

        Semiline semiline = null;
        try {
            semiline = new Semiline(semilineInitial, extremePoint);
        }
        catch (NullArgumentException e) {
            // Won't reach here
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Should not happen
            e.printStackTrace();
        }
        return semiline;
    }

    private Point getNearestReferencePoint (Point extremePoint, Polyline polyline,
            Collection<Point> intersectionPoints) throws NullArgumentException {

        Point nearestReferencePoint = null;
        double minDistance = Double.MAX_VALUE;

        for (Point point : intersectionPoints) {

            if (polyline.contains(point)) {
                continue;
            }

            double distanceToRef = Geometrics.calculateDistance(point, extremePoint);
            if (distanceToRef < minDistance) {
                nearestReferencePoint = point;
                minDistance = distanceToRef;
            }
        }
        return nearestReferencePoint;
    }

    private int getIndexInPolyline (Polyline polyline, Point extremePoint) {

        List<Point> points = polyline.getPoints();
        int lastIndex = points.size() - 1;
        if (points.get(lastIndex).equals(extremePoint))
            return lastIndex;
        else {
            return 0;
        }
    }

}
