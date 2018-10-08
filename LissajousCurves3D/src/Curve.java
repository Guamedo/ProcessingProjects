import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

class Curve {

    private PApplet parent;
    ArrayList<Point> points;
    private PVector center;

    Curve(PApplet p, PVector center) {
        this.parent = p;
        this.points = new ArrayList<Point>();
        this.center = center;
        p.ellipse(0, 0, 50, 50);
    }

    void draw(){
        this.parent.pushMatrix();
        this.parent.translate(this.center.x, this.center.y, this.center.z);
        this.parent.rotateY(this.parent.frameCount*0.01f);
        this.parent.rotateZ(this.parent.frameCount*0.01f);
        for(int i = 0; i < this.points.size()-1; i++){
            this.parent.strokeWeight(3);
            this.parent.stroke(this.points.get(i).col);
            this.parent.line(this.points.get(i).pos.x  - this.center.x,
                                this.points.get(i).pos.y  - this.center.y,
                                this.points.get(i).pos.z  - this.center.z,
                                this.points.get(i + 1).pos.x - this.center.x,
                                this.points.get(i + 1).pos.y - this.center.y,
                                this.points.get(i+1).pos.z - this.center.z);
        }

        if(this.points.size() > 0){
            /*this.parent.strokeWeight(6);
            this.parent.point(0, 0, 0);
            this.parent.strokeWeight(1);
            this.parent.line(0,
                                0,
                                0,
                                this.points.get(this.points.size() - 1).pos.x  - this.center.x,
                                this.points.get(this.points.size() - 1).pos.y  - this.center.y,
                                this.points.get(this.points.size() - 1).pos.z  - this.center.z);*/

            this.parent.translate(this.points.get(this.points.size() - 1).pos.x  - this.center.x,
                                    this.points.get(this.points.size() - 1).pos.y  - this.center.y,
                                    this.points.get(this.points.size() - 1).pos.z  - this.center.z);
            this.parent.strokeWeight(8);
            this.parent.point(0, 0, 0);
        }
        this.parent.popMatrix();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Points:\n");
        for(Point p: this.points){
            str.append("    (X = ").append(p.pos.x).append(", Y = ").append(p.pos.y).append(")\n");
        }
        return str.toString();
    }
}
