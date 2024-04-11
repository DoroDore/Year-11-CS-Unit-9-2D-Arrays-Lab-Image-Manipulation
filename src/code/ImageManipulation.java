package code;

import image.APImage;
import image.Pixel;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        APImage test = new APImage("cyberpunk2077.jpg");
        //test.draw();
        //grayScale("cyberpunk2077.jpg");
        //blackAndWhite("cyberpunk2077.jpg");
        rotateImage("cyberpunk2077.jpg");
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        Pixel[][] thing = new Pixel[image.getHeight()][image.getWidth()];
        for(int i = 0; i < thing.length; i++) {
            for (int j = 0; j < thing[i].length; j++) {
                thing[i][j] = image.getPixel(j,i);
                int average = getAverageColour(image.getPixel(j,i));
                thing[i][j].setRed(average);
                thing[i][j].setBlue(average);
                thing[i][j].setGreen(average);
            }
        }
        //image.setName("GS" + image.getName());
        image.saveAs();
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
        int thing = pixel.getBlue() + pixel.getGreen() + pixel.getRed();
        return (int)thing/3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        Pixel[][] thing = new Pixel[image.getHeight()][image.getWidth()];
        for(int i = 0; i < thing.length; i++) {
            for (int j = 0; j < thing[i].length; j++) {
                thing[i][j] = image.getPixel(j,i);
                int average = getAverageColour(image.getPixel(j,i));
                if (average < 128) {
                    average = 0;
                }
                else {
                    average = 255;
                }
                thing[i][j].setRed(average);
                thing[i][j].setBlue(average);
                thing[i][j].setGreen(average);
            }
        }
        //image.setName("GS" + image.getName());
        image.saveAs();
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold) {
        APImage image = new APImage(pathToFile);
        Pixel[][] pixels = new Pixel[image.getHeight()][image.getWidth()];

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                pixels[i][j] = image.getPixel(j, i);
                if (i < image.getHeight() - 1 && j < image.getWidth() - 1) {
                    int currentPixelAverage = getAverageColour(image.getPixel(j, i));
                    int belowPixelAverage = getAverageColour(image.getPixel(j, i + 1));
                    int rightPixelAverage = getAverageColour(image.getPixel(j + 1, i));

                    if (Math.abs(currentPixelAverage - belowPixelAverage) > threshold ||
                            Math.abs(currentPixelAverage - rightPixelAverage) > threshold) {
                        pixels[i][j].setRed(0);
                        pixels[i][j].setBlue(0);
                        pixels[i][j].setGreen(0);
                    }
                    else {
                        pixels[i][j].setRed(255);
                        pixels[i][j].setBlue(255);
                        pixels[i][j].setGreen(255);
                    }
                }
            }
        }

        image.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        Pixel[][] thing = new Pixel[image.getWidth()][image.getHeight()];
        for (int i = 0; i < thing.length; i++) {
            for (int j = 0; j < thing[i].length; j++) {
                thing[i][j] = image.getPixel(thing.length - i - 1, j);
                //If I forget, this basically just grabs the pixels in reverse order
            }
        }
        for (int i = 0; i < thing.length; i++) {
            for (int j = 0; j < thing[i].length; j++) {
                image.setPixel(i, j, thing[i][j]);
            }
        }
        image.saveAs();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        APImage rotatedImage = new APImage(image.getHeight(),image.getWidth());
        Pixel[][] thing = new Pixel[image.getWidth()][image.getHeight()];
        for (int i = 0; i < thing.length; i++) {
            for (int j = 0; j < thing[0].length; j++) {
                rotatedImage.setPixel(j, i, image.getPixel(i, image.getHeight()-1-j));
            }
        }
        rotatedImage.draw();
    }

}
