import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PImage;

import java.util.ArrayList;

public class MainApp extends PApplet{

    private float a;
    private ArrayList<PVector> points = new ArrayList<PVector>();
    private PImage img;
    private PImage mask;
    private PVector imgPos = new PVector(0, 0);

    public static void main(String[] args){
        String[] appletArgs = new String[] { "MainApp" };
        PApplet.main(appletArgs);
    }

    public void settings(){
        size(600, 600);
    }

    public void setup(){
        background(51);
        a = width/3.0f;

        // Load the nyan cat image
        img  = loadImage("img/nyan.png");
        img.loadPixels();

        // Generate a mask to remove borders
        mask = img.copy();
        mask.filter(GRAY);
        mask.filter(THRESHOLD, 0.95f);
        mask.filter(INVERT);

        // Apply the mask to the image
        img.mask(mask);
    }

    public void draw(){
        background(51);

        translate(width/2.0f, height/2.0f);

        float t = frameCount*0.02f;

        float x = (a*sqrt(2)*cos(t))/(pow(sin(t), 2.0f) + 1);
        float y = (a*sqrt(2)*cos(t)*sin(t))/(pow(sin(t), 2.0f) + 1);

        points.add(new PVector(x, y));
        if(t > 2*PI){
            points.remove(0);
        }

        stroke(200, (1-noise(t)*255), noise(t)*255);
        strokeWeight(3);
        for(int i = 0; i < points.size()-1; i++){
            line(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);
        }
        fill(200, (1-noise(t)*255), noise(t)*255);
        noStroke();

        PVector lastPoint = points.get(points.size()-1);
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
        //scale(-1, 1);
        //translate(-width/2.0f, -height/2.0f);
        translate(imgPos.x, imgPos.y);
        float angle = 0;
        if(points.size() >= 2){
            angle = atan2(points.get(points.size()-1).y - points.get(points.size()-2).y,
                    points.get(points.size()-1).x - points.get(points.size()-2).x);
        }
        rotate(angle);
        translate(-imgPos.x, -imgPos.y);
        image(img, imgPos.x, imgPos.y, 40, 30);
        popMatrix();

    }

}
