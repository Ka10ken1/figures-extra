package com.epam.rd.autotasks.figures;

class Circle extends Figure{
    private Point point;
    private double radius;

    public Circle(Point point,double radius){
        this.point = point;
        this.radius = radius;
        if(radius <= 0){
            throw new IllegalArgumentException();
        }
        if(point ==null){
            throw  new IllegalArgumentException();
        }
    }

    @Override
    public Point centroid() {
        return point;
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if (figure instanceof Circle) {
            Circle otherCircle = (Circle) figure;
            double distance = Math.sqrt(Math.pow(otherCircle.point.getX() -this.point.getX(),2)+
                    Math.pow(otherCircle.point.getY()-this.point.getY(),2));
            if(Math.abs(otherCircle.radius-this.radius)<=1e-6 && distance<=1e-6){
                return true;
            }
        }
        return false;
    }
}
