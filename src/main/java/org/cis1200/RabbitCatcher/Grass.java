package org.cis1200.RabbitCatcher;

public class Grass {
    private boolean containsBunny;
    private boolean filled;
    private boolean edge;
    private int positionX;
    private int positionY;

    public Grass(int px, int py) {
        containsBunny = false;
        filled = false;
        positionX = px;
        positionY = py;
        edge = false;

    }

    public boolean getEdge() {
        return edge;
    }

    public void setEdge(boolean val) {
        edge = val;
    }

    public boolean getBunny() {
        return containsBunny;
    }

    public void setBunny(boolean contain) {
        this.containsBunny = contain;
    }

    public boolean getFilled() {
        return filled;
    }

    public void setFilled() {
        this.filled = true;
    }

    public void unfill() {
        this.filled = false;
    }

}
