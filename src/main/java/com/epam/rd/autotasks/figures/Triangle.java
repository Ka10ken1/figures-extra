package com.epam.rd.autotasks.figures;

class Triangle extends Figure {
    private Point point1;
    private Point point2;
    private Point point3;

    public Triangle(Point point1, Point point2, Point point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        validateTriangle();
    }

    private void validateTriangle() {
        if (point1 == null || point2 == null || point3 == null) {
            throw new IllegalArgumentException();
        }
        if (CollinearPoints(point1, point2, point3)) {
            throw new IllegalArgumentException();
        }
        double area = Area(point1, point2, point3);
        if (Math.abs(area) < getDelta()) {
            throw new IllegalArgumentException();
        }
    }

    private boolean CollinearPoints(Point p1, Point p2, Point p3) {
        double delta = getDelta();
        return Math.abs((p1.getX() * (p2.getY() - p3.getY())
                + p2.getX() * (p3.getY() - p1.getY())
                + p3.getX() * (p1.getY() - p2.getY()))) <= delta;
    }

    private double Area(Point p1, Point p2, Point p3) {
        double AB = distance(p1,p2);
        double BC = distance(p2,p3);
        double AC = distance(p1,p3);
        double p = (AB + AC + BC) /2.0;
        return Math.sqrt(p*(p-AB) * (p-AC) * (p-BC));
    }

    private double getDelta() {
        return 1e-6;
    }
    private double distance(Point a,Point b){
        double x = b.getX() - a.getX();
        double y = b.getY() - a.getY();
        return Math.sqrt(x*x + y*y);
    }

    @Override
    public Point centroid() {
        double centroidX = (point1.getX() + point2.getX() + point3.getX()) / 3.0;
        double centroidY = (point1.getY() + point2.getY() + point3.getY()) / 3.0;
        return new Point(centroidX, centroidY);
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if(figure instanceof Triangle){
            Triangle triangle = (Triangle) figure;
         Point[] points = {triangle.point1,triangle.point2,triangle.point3};
         boolean flag = true;
         for(Point point:points){
             if(!MathchingPoint(point)){
                 flag = false;
                 break;
             }
         }
         return flag;
        }
        return false;
    }

    private boolean MathchingPoint(Point p){
        double delta = 1e-6;
        for(Point point : new Point[]{point1,point2,point3}){
            if(Math.abs(p.getX() -point.getX())<=delta && Math.abs(p.getY() - point.getY())<=delta){
                return true;
            }
        }
        return false;
    }
}
