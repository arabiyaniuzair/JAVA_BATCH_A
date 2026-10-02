abstract class Shape {
    abstract double area();
}

class Circle extends Shape {
    double radius;

    Circle(double radius) {
        this.radius=radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length, width;

    Rectangle(double length, double width) {
       this.length= length;
        this.width=width;
    }

    @Override
    double area() {
        return length * width;
    }
}

class Triangle extends Shape {
    double height;
    double base;

    Triangle(double height,double base){
        this.height=height;
        this.base=base;
    }

    @Override
    double area() {
        return 0.5*height*base;
    }
}

public class abstractPractice{
    public static void main(String[] args){
        Shape[] shapes = {
            new Circle(15),
            new Rectangle(12, 18),
            new Triangle(7, 6),
            new Circle(100)
        };
        double total=0;
        double largest=0;
        for(Shape shape:shapes){
            double currentarea=shape.area();
            System.out.printf("Area of individual shape is: %.2f%n", currentarea);
            total+=currentarea;
            if (currentarea>largest){
                largest=currentarea;
            }
        }
        System.out.printf("Total area is: %.2f%n", total);
        System.out.printf("Largest area is: %.2f%n", largest);
    }
}
