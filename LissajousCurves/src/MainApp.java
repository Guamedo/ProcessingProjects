import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PImage;

import java.util.ArrayList;

public class MainApp extends PApplet {

    private int size = 100;
    private int cols, rows;
    private int sep = 10;
    private float offset = PI/4.0f;
    private float speed = 0.01f;

    private ArrayList<ArrayList<Curve>> curveMatrix = new ArrayList<ArrayList<Curve>>();
    private PImage img;

    public static void main(String[] args){
        String[] appletArgs = new String[] { "MainApp" };
        PApplet.main(appletArgs);
    }

    public void settings() {
        size(600, 600);

    }

    public void setup(){
        surface.setTitle("Lissajous Curves");

        cols = width/size - 1;
        rows = height/size - 1;

        for(int i = 0; i < cols; i++){
            ArrayList<Curve> curveList = new ArrayList<Curve>();
            for(int j = 0; j < rows; j++){
                Curve c = new Curve(this);
                curveList.add(c);
            }
            curveMatrix.add(curveList);
        }
        img = loadImage("img/lissajous.jpeg");
        img.filter(DILATE);
    }

    public void draw(){
        background(51);

        pushMatrix();
        image(img,0.0f, 0.0f, (float)size, (float)size);
        popMatrix();

        noFill();
        stroke(51);
        strokeWeight(10);
        rect(0, 0, size, size, 10);

        int d = size-sep;

        for(int i = 0; i < cols; i++){
            int x = i*size + 3*size/2;
            int y = size/2;

            stroke(255);
            strokeWeight(1);
            noFill();
            ellipse(x, y, d, d);

            float pointX = x + (d/2.0f)*cos(frameCount*speed*(i + 1));
            float pointY = y + (d/2.0f)*sin(frameCount*speed*(i + 1));
            strokeWeight(6);
            point(pointX, pointY);

            strokeWeight(1);
            stroke(255, 50);
            line(pointX, pointY, pointX, height);
        }

        for(int i = 0; i < cols; i++) {
            int x = size / 2;
            int y = i * size + 3 * size / 2;
            stroke(255);
            strokeWeight(1);
            noFill();
            ellipse(x, y, d, d);

            float pointX = x + (d / 2.0f) * cos(frameCount*speed*(i + 1) + offset);
            float pointY = y + (d / 2.0f) * sin(frameCount*speed*(i + 1) + offset);
            strokeWeight(6);
            point(pointX, pointY);

            strokeWeight(1);
            stroke(255, 50);
            line(pointX, pointY, width, pointY);
        }

        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                int xC = i*size + 3*size/2;
                int yR = j * size + 3*size/2;

                float pointXc = xC + (d/2.0f)*cos(frameCount*speed*(i + 1));
                float pointYr = yR + (d/2.0f)*sin(frameCount*speed*(j + 1) + offset);

                float noiseOffset = 0.015f;
                curveMatrix.get(i).get(j).points.add(new Point(new PVector(pointXc, pointYr),
                                                                        color(0,
                                                                        (1-noise(frameCount*noiseOffset, 2))*255,
                                                                        noise(frameCount*noiseOffset, 3)*255)));
                if(frameCount*speed > 2*PI - PI/6){
                    curveMatrix.get(i).get(j).points.remove(0);
                }

            }
        }

        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                curveMatrix.get(i).get(j).draw();
                /*for(Point p: curveMatrix.get(i).get(j).points){
                    strokeWeight(3);
                    stroke(p.color.x, p.color.y, p.color.z);
                    point(p.pos.x, p.pos.y);
                }*/
            }
        }
    }
}
