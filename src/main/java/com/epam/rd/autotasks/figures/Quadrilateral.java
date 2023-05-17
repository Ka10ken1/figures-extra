package com.epam.rd.autotasks.figures;

import java.text.DecimalFormat;

class Quadrilateral extends Figure {
    private Point point1;
    private  Point point2;
    private  Point point3;
    private Point point4;

    public Quadrilateral(Point point1, Point point2, Point point3, Point point4) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
        validateQuadrilateral();
    }
    private void validateQuadrilateral() {
        if (point1 == null || point2 == null || point3 == null || point4 == null) {
            throw new IllegalArgumentException();
        }
        if (areCollinear(point1, point2, point3) || areCollinear(point1, point3, point4) ||
                areCollinear(point1, point4, point2) || areCollinear(point2, point3, point4)) {
            throw new IllegalArgumentException();
        }
        if(!isConvex()){
            throw  new IllegalArgumentException();
        }
        if (!isPlanar()) {
            throw new IllegalArgumentException();
        }
    }

    private boolean areCollinear(Point p1, Point p2, Point p3) {
        double delta = getDelta();
        return Math.abs((p1.getX() * (p2.getY() - p3.getY())
                + p2.getX() * (p3.getY() - p1.getY())
                + p3.getX() * (p1.getY() - p2.getY()))) <= delta;
    }

    private boolean isPlanar() {
        double x1 = point1.getX();
        double y1 = point1.getY();

        double x2 = point2.getX();
        double y2 = point2.getY();

        double x3 = point3.getX();
        double y3 = point3.getY();

        double x4 = point4.getX();
        double y4 = point4.getY();
        double area = 0.5 * ((x1 * y2) + (x2 * y3) + (x3 * y4) + (x4 * y1)
                - (x2 * y1) - (x3 * y2) - (x4 * y3) - (x1 * y4));
        return Math.abs(area) >= getDelta();
    }


    private double getDelta() {
        return 1e-6;
    }

    public boolean isConvex() {
        boolean isPositive = false;
        boolean isNegative = false;
        int n = 4;
        Point[] points = {point1,point2,point3,point4};

        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            int k = (i + 2) % n;
            double crossProduct = crossProductLength(points[i], points[j], points[k]);
            if (crossProduct > 0) {
                isPositive = true;
            } else if (crossProduct < 0) {
                isNegative = true;
            }

            if (isPositive && isNegative) {
                return false;
            }
        }
        return true;
    }

    private static double crossProductLength(Point p1, Point p2, Point p3) {
        double ux = p2.getX() - p1.getX();
        double uy = p2.getY() - p1.getY();
        double vx = p3.getX() - p2.getX();
        double vy = p3.getY() - p2.getY();

        return ux * vy - uy * vx;
    }


    @Override
    public Point centroid() {
        Point center1 = centroidTriangle(point1,point2,point3);
        Point center2 = centroidTriangle(point1,point3,point4);
        Point center3 = centroidTriangle(point1,point4,point2);
        Point center4 = centroidTriangle(point2,point4,point3);
        return new Segment(center1,center2).intersection(new Segment(center3,center4));
    }

    public Point centroidTriangle(Point point1,Point point2,Point point3) {
        double centroidX = (point1.getX() + point2.getX() + point3.getX()) / 3.0;
        double centroidY = (point1.getY() + point2.getY() + point3.getY()) / 3.0;
        return new Point(centroidX, centroidY);
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if(figure instanceof Quadrilateral){
            Quadrilateral quad = (Quadrilateral) figure;
            Point[] points = {point1,point2,point3,point4};
            Point[] quadPoints = {quad.point1,quad.point2,quad.point3,quad.point4};

            for(Point point:points){
                boolean flag = false;
                for(Point quadP:quadPoints){
                    if(Math.abs(point.getX() - quadP.getX())<= getDelta() &&Math.abs(point.getY() -quadP.getY()) <=getDelta()){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
