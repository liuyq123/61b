import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture p;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        this.p = picture;
        this.width = p.width();
        this.height = p.height();
    }

    public Picture picture() {
        return this.p;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public double energy(int x, int y) {
        int deltaXR = picture().get((x + 1) % width(), y).getRed() - picture().get((x - 1 + width()) % width(), y).getRed();
        int deltaYR = picture().get(x, (y + 1) % width()).getRed() - picture().get(x, (y - 1 + width()) % width()).getRed();
        int deltaXG = picture().get((x + 1) % width(), y).getGreen() - picture().get((x - 1 + width()) % width(), y).getGreen();
        int deltaYG = picture().get(x, (y + 1) % width()).getGreen() - picture().get(x, (y - 1 + width()) % width()).getGreen();
        int deltaXB = picture().get((x + 1) % width(), y).getBlue() - picture().get((x - 1 + width()) % width(), y).getBlue();
        int deltaYB = picture().get(x, (y + 1) % width()).getBlue() - picture().get(x, (y - 1 + width()) % width()).getBlue();
        int xGradient = deltaXR ^ 2 + deltaXG ^ 2 + deltaXB ^ 2;
        int yGradient = deltaYR ^ 2 + deltaYG ^ 2 + deltaYB ^ 2;
        return (double) (xGradient + yGradient);
    }

    public int[] findHorizontalSeam() {
        int[] result = new int[height()];
        double[][] minCost = new double[width()][height()];

        for (int i = 0; i < width(); i++) {
            minCost[i][0] = energy(i, 0);
        }

        for (int i = 0; i < width(); i++) {
            for (int j = 1; j < height(); j++) {
                if (i == 0) {
                    minCost[i][j] = energy(i, j) + Math.min(minCost[i][j - 1], minCost[i + 1][j - 1]);
                    if (minCost[i][j - 1] < minCost[i + 1][j - 1]) {
                        result[j - 1] = i;
                    } else {
                        result[j - 1] = i + 1;
                    }
                } if (i == width() - 1) {
                    minCost[i][j] = energy(i, j) + Math.min(minCost[i - 1][j - 1], minCost[i][j - 1]);
                    if (minCost[i][j - 1] < minCost[i - 1][j - 1]) {
                        result[j - 1] = i;
                    } else {
                        result[j - 1] = i - 1;
                    }
                } else {
                    double smallest = Math.min(minCost[i - 1][j - 1], Math.min(minCost[i][j - 1], minCost[i + 1][j - 1]));
                    minCost[i][j] = energy(i, j) + smallest;
                    if (minCost[i - 1][j - 1] == smallest) {
                        result[j - 1] = i - 1;
                    } else if (minCost[i][j - 1] == smallest) {
                        result[j - 1] = i;
                    } else {
                        result[j - 1] = i + 1;
                    }
                }
            }
        }

        double min = minCost[0][height() - 1];
        int minIndex = 0;
        for (int i = 0; i < width(); i++) {
            if (min > minCost[i][height() - 1]) {
                min = minCost[i][height() - 1];
                minIndex = i;
            }
        }
        result[height() - 1] = minIndex;
        return result;
    }


    public int[] findVerticalSeam() {
        this.width = p.height();
        this.height = p.width();
        int[] ret = findHorizontalSeam();
        this.height = p.height();
        this.width = p.width();
        return ret;
    }

    public void removeHorizontalSeam(int[] seam) {
        SeamRemover rm = new SeamRemover();
        this.p = rm.removeHorizontalSeam(p, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        SeamRemover rm = new SeamRemover();
        this.p = rm.removeVerticalSeam(p, seam);
    }
}
