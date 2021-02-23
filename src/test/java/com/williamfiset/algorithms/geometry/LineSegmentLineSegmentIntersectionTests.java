package com.williamfiset.algorithms.geometry;

import org.junit.*;

public class LineSegmentLineSegmentIntersectionTests {


    @Test
    public void getIntersectPoint(){
        LineSegmentLineSegmentIntersection ls = new LineSegmentLineSegmentIntersection();
        LineSegmentLineSegmentIntersection.Pt pt1 = new LineSegmentLineSegmentIntersection.Pt(0, -1);
        LineSegmentLineSegmentIntersection.Pt pt2 = new LineSegmentLineSegmentIntersection.Pt(1, 0);

        LineSegmentLineSegmentIntersection.Pt pt3 = new LineSegmentLineSegmentIntersection.Pt(0, 1);
        LineSegmentLineSegmentIntersection.Pt pt4 = new LineSegmentLineSegmentIntersection.Pt(1, 0);
        LineSegmentLineSegmentIntersection.Pt[] result = ls.lineSegmentLineSegmentIntersection(pt1, pt2, pt3, pt4);
        double x = result[0].x;
        double y = result[0].y;
        assert (x == 1.0);
        assert (y == 0.0);
    }

    @Test
    public void overlapSegments(){
        LineSegmentLineSegmentIntersection ls = new LineSegmentLineSegmentIntersection();
        LineSegmentLineSegmentIntersection.Pt pt1 = new LineSegmentLineSegmentIntersection.Pt(1, 3);
        LineSegmentLineSegmentIntersection.Pt pt2 = new LineSegmentLineSegmentIntersection.Pt(1, 0);

        LineSegmentLineSegmentIntersection.Pt pt3 = new LineSegmentLineSegmentIntersection.Pt(1, 5);
        LineSegmentLineSegmentIntersection.Pt pt4 = new LineSegmentLineSegmentIntersection.Pt(1, 0);
        LineSegmentLineSegmentIntersection.Pt[] result = ls.lineSegmentLineSegmentIntersection(pt1, pt2, pt3, pt4);
        double x = result[0].x;
        double y = result[0].y;
        assert (x == 1.0);
        assert (y == 3.0);
    }


}
