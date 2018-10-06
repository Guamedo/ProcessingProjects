import processing.core.PApplet;

import java.util.ArrayList;

class Curve {

    private PApplet parent;
    ArrayList<Point> points;

    Curve(PApplet p) {
        this.parent = p;
        this.points = new ArrayList<Point>();
        p.ellipse(0, 0, 50, 50);
    }

    void draw(){
        for(int i = 0; i < this.points.size()-1; i++){
            this.parent.strokeWeight(2);
            this.parent.stroke(this.points.get(i).col);
            this.parent.line(this.points.get(i).pos.x, this.points.get(i).pos.y,
                                this.points.get(i + 1).pos.x, this.points.get(i + 1).pos.y);
        }
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
