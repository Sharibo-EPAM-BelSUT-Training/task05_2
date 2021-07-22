package gmail.alexejkrawez;

/**
 * @name Java.SE.05-2
 * @package gmail.alexejkrawez;
 * @file Girl.java
 * @author Alexej Krawez
 * @email AlexejKrawez@gmail.com
 * @created 03.01.2019
 * @updated 17.07.2021
 * @version 1.1
 */

import gmail.alexejkrawez.box.Box;
import gmail.alexejkrawez.exceptions.BoxAlreadyContainsShapeException;
import gmail.alexejkrawez.exceptions.BoxIncorrectArgumentException;
import gmail.alexejkrawez.exceptions.WriteReadException;
import gmail.alexejkrawez.figure.Figure;
import gmail.alexejkrawez.film.FilmCircle;
import gmail.alexejkrawez.film.FilmRectangle;
import gmail.alexejkrawez.film.FilmTriangle;
import gmail.alexejkrawez.paper.PaperCircle;
import gmail.alexejkrawez.paper.PaperRectangle;
import gmail.alexejkrawez.paper.PaperTriangle;
import gmail.alexejkrawez.serialisation.Connector;

import java.util.*;

import static gmail.alexejkrawez.paper.Paper.Color.*;

public class Girl {

    public static void main(String[] args) {
        Box box = new Box();

        PaperRectangle pr1 = new PaperRectangle(12, 15);
        PaperTriangle pt1 = new PaperTriangle(11, 12, 13);
        PaperTriangle pt2 = new PaperTriangle(10, 11, 12);
        PaperCircle pc1 = new PaperCircle(24);

        FilmRectangle fr1 = new FilmRectangle(12, 15);
        FilmRectangle fr2 = new FilmRectangle(16, 18);
        FilmTriangle ft1 = new FilmTriangle(12, 13, 14);
        FilmCircle fc1 = new FilmCircle(6);
        FilmCircle fc2 = new FilmCircle(8);

        FilmCircle fc3 = new FilmCircle(ft1);
        pc1.setColor(GREEN);
        PaperCircle pc2 = new PaperCircle(pc1);
        pr1.setColor(RED);
        pt1.setColor(BLUE);
        pt1.setColor(BLACK);

        box.addShape(pr1);
        box.addShape(fr1);
        box.addShape(pt1);
        box.addShape(pc2);
        box.addShape(fc3);
        box.addShape(pt2);
        box.addShape(fr2);


        // Task05:
        System.out.println("\nTask05:");

        PaperRectangle pr4 = new PaperRectangle(22, 25);
        pr4.setColor(BLUE);
        PaperRectangle pr5 = new PaperRectangle(24, 27);
        PaperTriangle pt4 = new PaperTriangle(21, 22, 23);
        pt4.setColor(YELLOW);
        PaperTriangle pt5 = new PaperTriangle(20, 21, 22);
        pt5.setColor(WHITE);
        PaperCircle pc4 = new PaperCircle(24);
        pc4.setColor(RED);
        PaperCircle pc5 = new PaperCircle(21);
        PaperCircle pc6 = new PaperCircle(21);
        pc5.setColor(RED);

        FilmRectangle fr4 = new FilmRectangle(22, 25);
        FilmRectangle fr5 = new FilmRectangle(26, 28);
        FilmTriangle ft4 = new FilmTriangle(22, 23, 24);
        FilmCircle fc4 = new FilmCircle(18);


        System.out.println("\n3. Adding a group of shapes to the box, "
        + "extracting a group of shapes from the box, adding a list of shapes to the box:");
        try {
            box.addGroupOfShapes(pr4, pt4, fr4, pt5, fr5, ft4, pc4, fc4, pc5, pr5, pc6);
        } catch (BoxAlreadyContainsShapeException e) {
            e.printStackTrace();
        }

        System.out.println(box.toString()); // 13 фигур

        ArrayList<Figure> list = new ArrayList<Figure>();
        try {
            list = box.extractGroupOfShapes(2, 5);
        } catch (BoxIncorrectArgumentException e) {
            e.printStackTrace();
        }

        System.out.println("\nList: " + list); // 4 фигуры
        System.out.println(box.toString()); // 9 фигур

        try {
            box.addListOfShapes(list);
        } catch (BoxAlreadyContainsShapeException e) {
            e.printStackTrace();
        }

        System.out.println("\n" + box.toString()); // снова 13 фигур


        System.out.println("\n1. Sorting by form, area and color of shapes.");
        Collections.sort(box.getBox(), Box.shape_comparator);
        System.out.println("\n" + box.toString());

        Collections.sort(box.getBox(), Box.area_comparator);
        for (Figure shape : box.getBox()) {
            System.out.println(shape);
            System.out.print(shape.getArea());
        }

        Collections.sort(box.getBox(), Box.color_comparator);
        System.out.println("\n\n" + box.toString());


        System.out.println("\n4. Extracting shapes with specific info, all film shapes, all red circles:");
        list = box.extractAllShapesByInfo("side_a");
        System.out.println("List of shapes with specific info: " + list); // достаёт все треугольники, только у них есть side_a
        System.out.println("\n" + box.toString());

        list = box.extractAllFilmShapes();
        System.out.println("\nList with film shapes: " + list); // достаёт 3 плёночные фигуры
        System.out.println("\n" + box.toString()); // остаются только бумажные

        list = box.extractAllRedCircles();
        System.out.println("\nList with red circles: " + list); // достаёт 2 красных круга
        System.out.println("\n" + box.toString()); // остался бесцветный круг
    }
}