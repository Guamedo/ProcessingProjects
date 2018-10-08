import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PImage;

import java.util.ArrayList;

public class MainApp extends PApplet{

    private final int RED = color(255, 0, 0);
    private final int ORANGE = color(255, 165, 0);
    private final int YELLOW = color(255, 255, 0);
    private final int GREEN = color(0, 255, 0);
    private final int BLUE = color(0, 0, 255);
    private final int INDIGO = color(75, 0, 130);
    private final int VIOLET = color(238, 130, 238);
    private final int colorNumber = 7;

    private float a;
    private ArrayList<ArrayList<Point>> points = new ArrayList<ArrayList<Point>>();
    private PImage img;
    private PVector imgPos = new PVector(0, 0);
    private int rainbow[];

    public static void main(String[] args){
        String[] appletArgs = new String[] { "MainApp" };
        PApplet.main(appletArgs);
    }

    public void settings(){
        size(900, 600);
    }

    public void setup(){
        background(51);

        rainbow = new int[colorNumber];
        rainbow[0] = RED;
        rainbow[1] = ORANGE;
        rainbow[2] = YELLOW;
        rainbow[3] = GREEN;
        rainbow[4] = BLUE;
        rainbow[5] = INDIGO;
        rainbow[6] = VIOLET;

        for(int i = 0; i < colorNumber; i++){
            points.add(new ArrayList<Point>());
        }

        a = width/3.0f;

        // Load the nyan cat image
        img  = loadImage("img/nyan.png");
        img.loadPixels();

        // Generate a mask to remove borders
        PImage mask = img.copy();
        mask.filter(GRAY);
        mask.filter(THRESHOLD, 0.95f);
        mask.filter(INVERT);

        // Apply the mask to the image
        img.mask(mask);
    }

    public void draw(){
        background(51);

        translate(width/2.0f, height/2.0f);


        float t = frameCount*0.01f;

        for(int j = 0; j < colorNumber; j++) {

            float offset = (j-3)*2.0f;
            float x = ((a+offset) * sqrt(2) * cos(t + PI/2)) / (pow(sin(t + PI/2), 2.0f) + 1);
            float y = ((a+offset) * sqrt(2) * cos(t + PI/2) * sin(t + PI/2)) / (pow(sin(t + PI/2), 2.0f) + 1);

            points.get(j).add(new Point(new PVector(x, y), rainbow[j], ceil(abs(sin(t)*4))));
            if (t > 2 * PI) {
                points.get(j).remove(0);
            }

            for (int i = 0; i < points.get(j).size() - 1; i++) {
                strokeWeight(points.get(j).get(i).w);
                stroke(points.get(j).get(i).col, 150);
                line(points.get(j).get(i+1).pos.x, points.get(j).get(i+1).pos.y,
                        points.get(j).get(i).pos.x, points.get(j).get(i).pos.y);
            }
        }
        fill(200, (1-noise(t)*255), noise(t)*255);
        noStroke();
        PVector lastPoint = points.get(4).get(points.get(4).size()-1).pos;
        ellipse(lastPoint.x, lastPoint.y, 10, 10);

        PVector F1 = new PVector(a, 0);
        PVector F2 = new PVector(-a, 0);

        stroke(255, 100);
        strokeWeight(1);
        line(F1.x, F1.y, lastPoint.x, lastPoint.y);
        line(F2.x, F2.y, lastPoint.x, lastPoint.y);

        noStroke();
        fill(255);
        ellipse(F2.x, F2.y, 10, 10);
        text("F2",F2.x+5, F2.y-5);

        ellipse(F1.x, F1.y, 10, 10);
        text("F1",F1.x+5, F1.y-5);

        imgPos = lastPoint;
        pushMatrix();
        imageMode(CENTER);
        translate(imgPos.x, imgPos.y);
        float angle = 0;
        if(points.get(4).size() >= 2){
            angle = atan2(points.get(4).get(points.get(4).size()-1).pos.y - points.get(4).get(points.get(4).size()-2).pos.y,
                    points.get(4).get(points.get(4).size()-1).pos.x - points.get(4).get(points.get(4).size()-2).pos.x);
        }
        rotate(angle);
        translate(-imgPos.x, -imgPos.y);
        image(img, imgPos.x, imgPos.y, 40, 30);
        popMatrix();

    }

}
