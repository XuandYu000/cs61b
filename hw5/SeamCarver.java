/**
 * @Auther: XuZhiyu
 * @Date: 2024/3/20 下午9:27
 */
import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture pic;
    private int Width;
    private int Height;
    private int[][] Col;
    private int[][][] rgb;
    private int[][] energy;
    public SeamCarver(Picture picture) {
        this.pic = picture;
        setInial(this.pic);
    }

    private void setInial(Picture picture) {
        this.Width = picture.width();
        this.Height = picture.height();
        this.Col = new int[this.Width][this.Height];
        this.rgb = new int[this.Width][this.Height][3];
        for (int i = 0; i < Width; i++) {
            for(int j = 0; j < Height; j++) {
                int color = picture.getRGB(i, j);
                Col[i][j] = color;
                rgb[i][j][0] = (color >> 16) & 0xff;
                rgb[i][j][1] = (color >> 8) & 0xff;
                rgb[i][j][2] = color & 0xff;
            }
        }
        energy = cal_energy(rgb);
    }
    public Picture picture() {
        return pic;
    }                       // current picture
    public     int width() {
        return Width;
    }                         // width of current picture
    public     int height() {
        return Height;
    }                        // height of current picture

    private int[][] cal_energy(int[][][] rgb) {
        int[][] res = new int[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                int delX = 0;
                int delY = 0;
                int delX_x0 = getX(i - 1);
                int delX_y0 = getY(j);
                int delX_x1 = getX(i + 1);
                int delX_y1 = getY(j);

                int delY_x0 = getX(i);
                int delY_y0 = getY(j - 1);
                int delY_x1 = getX(i);
                int delY_y1 = getY(j + 1);
                for (int k = 0; k < 3; k++) {
                    delX +=  (rgb[delX_x0][delX_y0][k] - rgb[delX_x1][delX_y1][k])
                            * (rgb[delX_x0][delX_y0][k] - rgb[delX_x1][delX_y1][k]);
                    delY +=  (rgb[delY_x0][delY_y0][k] - rgb[delY_x1][delY_y1][k])
                            * (rgb[delY_x0][delY_y0][k] - rgb[delY_x1][delY_y1][k]);
                }
                res[i][j] = delX + delY;
            }
        }
        return res;
    }

    private int getX(int i) {
        return (i + width()) % width();
    }

    private int getY(int j) {
        return (j + height()) % height();
    }
    public  double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new java.lang.IndexOutOfBoundsException("The passed in argument wrong");
        }
        return energy[x][y];
    }            // energy of pixel at column x and row y
    public   int[] findHorizontalSeam(){
        int[][] e = new int[height()][width()];
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                e[i][j] = energy[j][i];
            }
        }
        return dp(e, height(), width());
    }            // sequence of indices for horizontal seam
    public   int[] findVerticalSeam() {
        return dp(energy, width(), height());
    }              // sequence of indices for vertical seam

    private int[] dp(int[][] energy, int w, int h) {
        int[] res = new int[h];
        Node[][] M = new Node[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                M[i][j] = new Node(energy[i][j], i, j);
            }
        }
        for(int j = 1; j < h; j++) {
            for (int i = 0; i < w; i++) {
                Node minNode = min(M, i - 1, i, i + 1, j - 1);
                M[i][j].setTotalEnergy(minNode.getTotalEnergy());
                M[i][j].setPre(minNode.getX());
            }
        }

        int minSub = -1;
        int Min = Integer.MAX_VALUE;
        for (int i = 0; i < w; i++) {
            if (Min > M[i][h - 1].getTotalEnergy()) {
                Min = M[i][h - 1].getTotalEnergy();
                minSub = i;
            }
        }

        for (int i = h - 1; i >= 0; i--) {
            res[i] = minSub;
            minSub = M[minSub][i].getPre();
        }
        return res;
    }
    private class Node {
        private int totalEnergy;
        private int pre;
        private int x;
        private int y;

        public Node(int e, int i, int j) {
            totalEnergy = e;
            x = i;
            y = j;
            pre = -1;
        }

        public void setTotalEnergy(int e) {
            totalEnergy += e;
        }

        public int getTotalEnergy() {
            return totalEnergy;
        }

        public void setPre(int y) {
            pre = y;
        }

        public int getPre() {
            return pre;
        }

        public int getX() {
            return x;
        }
    }

    private Node min(Node[][] M,int l, int m, int r, int j) {
        if (l < 0) {
            return M[m][j].getTotalEnergy() <= M[r][j].getTotalEnergy() ? M[m][j] : M[r][j];
        } else if (r >= M.length) {
            return M[l][j].getTotalEnergy() <= M[m][j].getTotalEnergy() ? M[l][j] : M[m][j];
        } else {
            Node Min = M[l][j].getTotalEnergy() <= M[m][j].getTotalEnergy() ? M[l][j] : M[m][j];
            return Min.getTotalEnergy() <= M[r][j].getTotalEnergy() ? Min : M[r][j];
        }
    }
    public    void removeHorizontalSeam(int[] seam){
        if (seam.length != width() || !arrayLegal(seam)) {
            throw new java.lang.IllegalArgumentException("Passed in seam is not legal");
        }
        Picture newPic = new Picture(width(), height() - 1);
        for (int col = 0; col < pic.width(); col ++) {
            int offset = 0;
            for (int row = 0; row < pic.height(); row ++) {
                if (row == seam[col]) {
                    offset = -1;
                    continue;
                }
                newPic.setRGB(col, row + offset, Col[col][row]);
            }
        }
        this.pic = newPic;
        setInial(this.pic);
    }   // remove horizontal seam from picture
    public    void removeVerticalSeam(int[] seam) {
        if (seam.length != width() || !arrayLegal(seam)) {
            throw new java.lang.IllegalArgumentException("Passed in seam is not legal");
        }
        Picture newPic = new Picture(width() - 1, height());
        for (int j = 0; j <height(); j++) {
        for (int row = 0; row < pic.height(); row ++) {
            int offset = 0;
            for (int col = 0;  col < pic.width(); col ++) {
                if (col == seam[row]) {
                    offset = -1;
                    continue;
                }
                newPic.setRGB(col + offset, row, Col[col][row]);
            }
            }
        }
        this.pic = newPic;
        setInial(this.pic);
    }     // remove vertical seam from picture

    private boolean arrayLegal(int[] path) {
        if (path == null) {
            return false;
        }
        for (int i = 0; i < path.length - 1; i++) {
            if (Math.abs(path[i] - path[i + 1]) > 1) {
                return false;
            }
        }
        return true;
    }
}
