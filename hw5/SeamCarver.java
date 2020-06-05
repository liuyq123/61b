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

    // x: column, y: row
    public double energy(int x, int y) {
        if (x < 0 || x >= width()) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        if (y < 0 || y >= height()) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        double deltaXR = picture().get((x + 1) % width(), y).getRed() - picture().get((x - 1 + width()) % width(), y).getRed();
        double deltaYR = picture().get(x, (y + 1) % height()).getRed() - picture().get(x, (y - 1 + height()) % height()).getRed();
        double deltaXG = picture().get((x + 1) % width(), y).getGreen() - picture().get((x - 1 + width()) % width(), y).getGreen();
        double deltaYG = picture().get(x, (y + 1) % height()).getGreen() - picture().get(x, (y - 1 + height()) % height()).getGreen();
        double deltaXB = picture().get((x + 1) % width(), y).getBlue() - picture().get((x - 1 + width()) % width(), y).getBlue();
        double deltaYB = picture().get(x, (y + 1) % height()).getBlue() - picture().get(x, (y - 1 + height()) % height()).getBlue();
        double xGradient = deltaXR * deltaXR + deltaXG * deltaXG + deltaXB * deltaXB;
        double yGradient = deltaYR * deltaYR + deltaYG * deltaYG + deltaYB * deltaYB;
        return xGradient + yGradient;
    }

    public int[] findHorizontalSeam() {
        transpose();
        int[] ret = findVerticalSeam();
        transpose();
        return ret;
    }

    private void transpose() {
        Picture tmp = new Picture(height(), width());
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                tmp.set(j, i, picture().get(i, j));
            }
        }

        this.p = tmp;
        int t = width();
        this.width = height();
        this.height = t;
    }


    public int[] findVerticalSeam() {
        int[] result = new int[height()];
        double[][] minCost = new double[width()][height()];

        // compute minCost for each element
        for (int i = 0; i < width(); i++) {
            minCost[i][0] = energy(i, 0);
        }

        for (int j = 1; j < height(); j++){
            for (int i = 0; i < width(); i++) {
                if (i == 0) {
                    minCost[i][j] = energy(i, j) + Math.min(minCost[i][j - 1], minCost[i + 1][j - 1]);
                } else if (i == width() - 1) {
                    minCost[i][j] = energy(i, j) + Math.min(minCost[i - 1][j - 1], minCost[i][j - 1]);
                } else {
                    double smallest = Math.min(Math.min(minCost[i][j - 1], minCost[i - 1][j - 1]),  minCost[i + 1][j - 1]);
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

        int i = minIndex;
        for (int j = height() - 1; j > 0; j--) {
            if (i == 0) {
                if (minCost[i][j - 1] < minCost[i + 1][j - 1]) {
                    result[j - 1] = i;
                } else {
                    result[j - 1] = i + 1;
                }
            } else if (i == width() - 1) {
                if (minCost[i][j - 1] < minCost[i - 1][j - 1]) {
                    result[j - 1] = i;
                } else {
                    result[j - 1] = i - 1;
                }
            } else {
                double smallest = Math.min(Math.min(minCost[i][j - 1], minCost[i - 1][j - 1]),  minCost[i + 1][j - 1]);
                if (minCost[i - 1][j - 1] == smallest) {
                    result[j - 1] = i - 1;
                } else if (minCost[i][j - 1] == smallest) {
                    result[j - 1] = i;
                } else {
                    result[j - 1] = i + 1;
                }
            }
            i = result[j - 1];
        }

        return result;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (validateSeam(seam)) {
            SeamRemover rm = new SeamRemover();
            this.p = rm.removeHorizontalSeam(p, seam);
            this.height--;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void removeVerticalSeam(int[] seam) {
        if (validateSeam(seam)) {
            SeamRemover rm = new SeamRemover();
            this.p = rm.removeVerticalSeam(p, seam);
            this.width--;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean validateSeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[1] - seam[i + 1]) > 1) {
                return false;
            }
        }
        return true;
    }
}
