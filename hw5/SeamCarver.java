import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture p;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        this.p = new Picture(picture);
        this.width = p.width();
        this.height = p.height();
    }

    public Picture picture() {
        return new Picture(this.p);
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

        double deltaXR = this.p.get((x + 1) % width(), y).getRed() - this.p.get((x - 1 + width()) % width(), y).getRed();
        double deltaYR = this.p.get(x, (y + 1) % height()).getRed() - this.p.get(x, (y - 1 + height()) % height()).getRed();
        double deltaXG = this.p.get((x + 1) % width(), y).getGreen() - this.p.get((x - 1 + width()) % width(), y).getGreen();
        double deltaYG = this.p.get(x, (y + 1) % height()).getGreen() - this.p.get(x, (y - 1 + height()) % height()).getGreen();
        double deltaXB = this.p.get((x + 1) % width(), y).getBlue() - this.p.get((x - 1 + width()) % width(), y).getBlue();
        double deltaYB = this.p.get(x, (y + 1) % height()).getBlue() - this.p.get(x, (y - 1 + height()) % height()).getBlue();
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
                tmp.set(j, i, this.p.get(i, j));
            }
        }

        this.p = tmp;
        int t = width();
        this.width = height();
        this.height = t;
    }


    public int[] findVerticalSeam() {
        int[] seam = new int[height];
        double totalEnergy = Double.MAX_VALUE;

        for (int col = 0; col < width; col++) {
            int y = 0;
            int x = col;
            int[] temp = new int[height];
            double tempEnergy = energy(x, y);
            temp[y] = x;
            y++;

            double topE = 0.0, leftE = 0.0, rightE = 0.0;


            while (y < height) {
                int top = x;
                int left = x - 1;
                int right = x + 1;

                topE = energy(top, y);
                if (left >= 0) {
                    leftE = energy(left, y);
                } else {
                    leftE = Double.MAX_VALUE;
                }

                if (right < width) {
                    rightE = energy(right, y);
                } else {
                    rightE = Double.MAX_VALUE;
                }

                if (topE <= leftE && topE <= rightE) {
                    tempEnergy += topE;
                    temp[y] = top;
                    x = top;
                } else if (leftE <= topE && leftE <= rightE) {
                    tempEnergy += leftE;
                    temp[y] = left;
                    x = left;
                } else {
                    tempEnergy += rightE;
                    temp[y] = right;
                    x = right;
                }

                y++;
            }

            if (tempEnergy <= totalEnergy) {
                totalEnergy = tempEnergy;
                seam = temp;
            }
        }

        return seam;
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
