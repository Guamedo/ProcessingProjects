import processing.core.PApplet;

public class MainApp extends PApplet{

    private float[][] buffer1;
    private float[][] buffer2;
    private float damping = 0.95f;

    public static void main(String[] args){
        String[] appletArgs = new String[] { "MainApp" };
        PApplet.main(appletArgs);
    }

    public void settings(){
        size(600, 600);
    }

    public void setup(){
        background(51);
        buffer1 = new float[width][height];
        buffer2 = new float[width][height];

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                buffer1[i][j] = 0;
                buffer2[i][j] = 0;
            }
        }
    }

    public void draw(){
        background(51);
        updateBuffers();
        loadPixels();
        for(int i = 1; i < width-1; i++) {
            for (int j = 1; j < height - 1; j++) {
                pixels[i*width+j] = color(255-buffer2[i][j]*255.0f);
            }
        }
        updatePixels();
        for(int i = 1; i < width-1; i++) {
            for (int j = 1; j < height - 1; j++) {
                float tmp = buffer2[i][j];
                buffer2[i][j] = buffer1[i][j];
                buffer1[i][j] = tmp;
            }
        }

        if(mousePressed){
            int x = round(mouseX);
            int y = round(mouseY);

            if(x >= 0 && x < width && y >= 0 && y < height){
                buffer1[y][x] = 10.0f;
            }
        }
    }

    public void updateBuffers(){
        for(int i = 1; i < width-1; i++){
            for(int j = 1; j < height-1; j++){
                buffer2[i][j] = (buffer1[i-1][j] +
                                    buffer1[i+1][j] +
                                    buffer1[i][j+1] +
                                    buffer1[i][j-1]) / 2.0f - buffer2[i][j];
                buffer2[i][j] = buffer2[i][j]*damping;
            }
        }
    }

}
