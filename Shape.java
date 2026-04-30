import java.awt.Color;

public class Shape {
    public int[][] coordinates;
    public Color color;
    public int x;
    public int y;

    public Shape(int[][] coords, Color col) {
        this.coordinates = coords;
        this.color = col;
    }

    public void spawn(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void moveLeft() {
        this.x--;
    }

    public void moveRight() {
        this.x++;
    }

    public void moveDown() {
        this.y++;
    }
}
