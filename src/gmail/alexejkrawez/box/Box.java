package gmail.alexejkrawez.box;

import gmail.alexejkrawez.exceptions.BoxAlreadyContainsShapeException;
import gmail.alexejkrawez.exceptions.BoxIncorrectArgumentException;
import gmail.alexejkrawez.figure.Circle;
import gmail.alexejkrawez.figure.Figure;
import gmail.alexejkrawez.figure.Rectangle;
import gmail.alexejkrawez.figure.Triangle;
import gmail.alexejkrawez.film.Film;
import gmail.alexejkrawez.paper.Paper;
import gmail.alexejkrawez.paper.PaperCircle;

import java.io.Serializable;
import java.util.*;

public class Box implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * Sorting by form of shapes.
     */
    public static Comparator<Figure> shape_comparator = new Comparator<Figure>() {
        @Override
        public int compare(Figure shape1, Figure shape2) {
            if(shape1 instanceof Rectangle) {
                return -1;
            } else if (shape1 instanceof Triangle) {
                return 0;
            } else if (shape1 instanceof Circle) {
                return 1;
            }
            return -1;
        }
    };

    /**
     * Sorting by area of shapes.
     */
    public static Comparator<Figure> area_comparator = new Comparator<Figure>() {
        @Override
        public int compare(Figure shape1, Figure shape2) {
            if (shape1.getArea() < shape2.getArea()) {
                return -1;
            } else if (shape1.getArea() == shape2.getArea()) {
                return 0;
            }
            return 1;
        }
    };

    /**
     * Sorting by color of shapes.
     */
    public static Comparator<Figure> color_comparator = new Comparator<Figure>() {
        @Override
        public int compare(Figure shape1, Figure shape2) { //вначале плёнки и null-бумажные, остальное по алфавиту
            try {
                if(shape1 instanceof Paper & shape2 instanceof Paper) {
                    return ((Paper) shape1).getColor().toString().compareTo(((Paper) shape2).getColor().toString());
                } else if (shape1 instanceof Film & shape2 instanceof Film) {
                    return 0;
                } else if (shape1 instanceof Paper) {
                    if (((Paper) shape1).getColor() != null) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else if (shape2 instanceof Paper) {
                    if (((Paper) shape2).getColor() != null) {
                        return -1;
                    } else {
                        return 0;
                    }
                }

            } catch (NullPointerException e) {
                if (((Paper) shape1).getColor() == null & ((Paper) shape2).getColor() == null) {
                    return 0;
                } else if (((Paper) shape1).getColor() == null) {
                    return -1;
                } else {
                    return 1;
                }
            }

            return 0;
        }
    };

    /**
     * Array of shapes.
     */
    private ArrayList<Figure> box = new ArrayList<>();

    public ArrayList<Figure> getBox() {
        return box;
    }

    /**
     * Adds a shape to the box.
     *
     * @param shape the shape that to be added to the box.
     * @return true if the shape doesn't exist.
     */
    public boolean addShape(Figure shape) {
        if (!box.contains(shape)) {
            box.add(shape);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a group of shapes to the box.
     *
     * @param shapes the shapes that to be added to the box.
     * @throws BoxAlreadyContainsShapeException box already contains one from specific shapes.
     */
    public void addGroupOfShapes(Figure... shapes) throws BoxAlreadyContainsShapeException {
        ArrayList<Figure> list = new ArrayList<Figure>(Arrays.asList(shapes));
        if (!box.containsAll(list)) {
            box.addAll(list);
        } else {
            throw new BoxAlreadyContainsShapeException("The one from shapes is already in box!");
        }
    }

    /**
     * Adds a list of shapes to the box.
     *
     * @param shapes the ArrayList of shapes that to be added to the box.
     * @throws BoxAlreadyContainsShapeException box already contains one from specific shapes.
     */
    public void addListOfShapes(ArrayList<Figure> shapes) throws BoxAlreadyContainsShapeException {
        if (!box.containsAll(shapes)) {
            box.addAll(shapes);
        } else {
            throw new BoxAlreadyContainsShapeException("The one from shapes is already in box!");
        }
    }

    /**
     * Get shape by number.
     *
     * @param number number of the shape.
     * @return the specific shape.
     * @throws BoxIncorrectArgumentException go outside the list.
     */
    public Figure getByNumber(int number) throws BoxIncorrectArgumentException {
        if (number < 0) {
            throw new BoxIncorrectArgumentException();
        } else {
            return box.get(number);
        }
    }

    /**
     * Extract shape by number.
     *
     * @param number number of the shape.
     * @return removes the shape from the box and returns the specific shape.
     * @throws BoxIncorrectArgumentException go outside the list.
     */
    public Figure extractByNumber(int number) throws BoxIncorrectArgumentException {
        if (number < 0) {
            throw new BoxIncorrectArgumentException();
        } else {
            return box.remove(number);
        }
    }

    /**
     * Extract group of shapes by their indexes.
     *
     * @param index1 number of the first shape.
     * @param index2 number of the last shape.
     * @return removes the shapes from the box and returns the ArrayList of specific shapes.
     * @throws BoxIncorrectArgumentException go outside the list.
     */
    public ArrayList<Figure> extractGroupOfShapes(int index1, int index2) throws BoxIncorrectArgumentException {
        if (index1 < 0 || index2 < 0) {
            throw new BoxIncorrectArgumentException();
        } else {
            ArrayList<Figure> group = new ArrayList<Figure>(box.subList(index1, index2 + 1));
            box.removeAll(group);
            return group;
        }
    }

    /**
     * Extract all shapes by specific information.
     *
     * @return removes the shapes from the box and returns the ArrayList of shapes with specific information.
     */
    public ArrayList<Figure> extractAllShapesByInfo(String info) {
        Iterator<Figure> iter = box.iterator();
        ArrayList<Figure> fromBox = new ArrayList<Figure>();
        while (iter.hasNext()) {
            Figure shape = iter.next();
            if ((shape.toString()).contains(info)) {
                fromBox.add(shape);
                iter.remove();
            }
        }

        return fromBox;
    }

    /**
     * Extract all film shapes.
     *
     * @return removes the shapes from the box and returns the ArrayList of film shapes.
     */
    public ArrayList<Figure> extractAllFilmShapes() {
        Iterator<Figure> iter = box.iterator();
        ArrayList<Figure> fromBox = new ArrayList<Figure>();
        while (iter.hasNext()) {
            Figure shape = iter.next();
            if (shape instanceof Film) {
                fromBox.add(shape);
                iter.remove();
            }
        }

        return fromBox;
    }

    /**
     * Extract all red circles.
     *
     * @return removes the shapes from the box and returns the ArrayList of red circles.
     */
    public ArrayList<Figure> extractAllRedCircles() {
        Iterator<Figure> iter = box.iterator();
        ArrayList<Figure> fromBox = new ArrayList<Figure>();
        while (iter.hasNext()) {
            Figure shape = iter.next();
            if (shape instanceof PaperCircle) {
                if (((PaperCircle) shape).getColor() == Paper.Color.RED) {
                    fromBox.add(shape);
                    iter.remove();
                }
            }
        }

        return fromBox;
    }

    /**
     * Replace shape by number.
     *
     * @param number number of the shape.
     * @param shape new shape.
     * @return old shape.
     * @throws BoxIncorrectArgumentException go outside the list.
     * @throws BoxAlreadyContainsShapeException box already contains specific shape.
     */
    public Figure replaceByNumber(int number, Figure shape)
            throws BoxIncorrectArgumentException, BoxAlreadyContainsShapeException {

        if (number < 1) {
            throw new BoxIncorrectArgumentException();
        }else if (box.contains(shape)) {
            throw new BoxAlreadyContainsShapeException("The shape is already in box!");
        } else {
            return box.set(number - 1, shape);
        }
    }

    /**
     * Find equivalent shape in box.
     *
     * @param shape shape, the equivalent of which is to be found in the box.
     * @return equivalent shape (if shape exist) else null.
     */
    public Figure findEquivalent(Figure shape) {
        for (Figure i : box) {
           if (i.equals(shape)) {
               return i;
           }
        }
        return null;
    }

    /**
     * Get total area of the shapes.
     *
     * @return sum area of shapes.
     */
    public double sumArea() {
        double sum = 0;
        for (Figure element : box) {
            sum += element.getArea();
        }
        return sum;
    }

    /**
     * Get total perimeter of the shapes.
     *
     * @return sum perimeter of shapes.
     */
    public double sumPerimeter() {
        double sum = 0;
        for (Figure element : box) {
            sum += element.getPerimeter();
        }
        return sum;
    }

    /**
     * Remove all circles from box.
     */
    public void removeAllCircles() {
        Iterator<Figure> iter = box.iterator();
        while (iter.hasNext()) {
            Figure shape = iter.next();
            if (shape instanceof Circle) {
                iter.remove();
            }
        }
    }

    /**
     * Remove all film shapes from box.
     */
    public void removeAllFilmShapes() {
        Iterator<Figure> iter = box.iterator();
        while (iter.hasNext()) {
            Figure shape = iter.next();
            if (shape instanceof Film) {
                iter.remove();
            }
        }
    }

    /**
     * Overrides toString() method.
     *
     * @return all shapes which are in the box.
     */
    @Override
    public String toString() {
        return "Box{" +
                "box=" + box +
                "}";
    }

}