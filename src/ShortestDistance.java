import javafx.geometry.Point2D;

import java.util.ArrayList;

public class ShortestDistance {
    /** Data field: point to the right of the mid point of the set*/
    private static ArrayList<Point2D> rightContainer = new ArrayList<>();

    /** Data field: points to the left of the mid point of the set*/
    private static ArrayList<Point2D> leftContainer = new ArrayList<>();

    public static void main(String[] args) {
	Point2D[] point2DS = {new Point2D(1,6),new Point2D(2,7),new Point2D(1,3),new Point2D(3,5), new Point2D(4,5), new Point2D(8,9)};

        System.out.println("The shortest distance is " + getShortestDistance(point2DS));

    }

    /** Get the shortest distance*/
    public static double getShortestDistance(Point2D[] setOfPoints){
        // sort the set of points
        sortPoints(setOfPoints);

        return shortestDistance(setOfPoints); // return the shortest distance
    }

    /** Sort points with increasing order of their x-coordinates
     * and if any two points have the same x-coordinates sort them with respect to their y-coordinates
     * */
    private static void sortPoints(Point2D[] setOfPoints){
        Point2D minimum; // minimum point
        int indexOfMinimum; // index of the minimum

        for (int i = 0; i < setOfPoints.length - 1; i++){
            minimum = setOfPoints[i]; // minimum points
            indexOfMinimum = i;

            for (int j = i + 1; j < setOfPoints.length; j++){
                if (minimum.getX() > setOfPoints[j].getX()){
                    minimum = setOfPoints[j];
                    indexOfMinimum = j;
                }
                else if (minimum.getX() == setOfPoints[j].getX()){
                    if (minimum.getY() > setOfPoints[j].getY()){
                        minimum = setOfPoints[j];
                        indexOfMinimum = j;
                    }
                }

            }
            if (indexOfMinimum != i){
                setOfPoints[indexOfMinimum] = setOfPoints[i];
                setOfPoints[i] = minimum;
            }

        }
    }

    /**Divide the set of points into two distinct sets and for each set find the smallest distance.
     *This is an example of divide and conquer.
     *  */
    private static double smallestDistanceInSubset(Point2D[] setOfPoints){
        // The smallest distance in the lower half of the set of points
        double distance1 = findSmallestDistance(1,(setOfPoints.length - 1) / 2,0,setOfPoints);

        // The smallest distance in the upper left of the set of points
        double distance2 = findSmallestDistance((setOfPoints.length - 1) / 2 ,setOfPoints.length - 1,0,setOfPoints);

        return Math.min(distance1,distance2);
    }

    /** Helper method for smallestDistance*/
    private static double findSmallestDistance(int lowerIndex, int upperIndex, double distance, Point2D[] setOfPoints){
        if (lowerIndex == upperIndex) // Base case
            return distance;
        else {
            if (distance < computeDistance(setOfPoints[lowerIndex - 1],(setOfPoints[lowerIndex])))
                distance = computeDistance(setOfPoints[lowerIndex - 1],(setOfPoints[lowerIndex])); // update distance
           return findSmallestDistance(lowerIndex + 1,upperIndex,distance,setOfPoints);
        }
    }

    /** Compute the distance between two points*/
    private static double computeDistance(Point2D point1, Point2D point2){
        return point1.distance(point2);
    }

    /** Find points that are within the smallest distance d of the mid point of the point set*/
    private static void addPointsToSpecificContainer(Point2D[] setOfPoints, double distance){
        int indexOfMidPoint = setOfPoints.length / 2;

        for (Point2D setOfPoint : setOfPoints) {
            if (setOfPoints[indexOfMidPoint].getX() - setOfPoint.getX() <= distance)
                leftContainer.add(setOfPoint); // add the points to the left container

            else if (setOfPoint.getX() - setOfPoints[indexOfMidPoint].getX() <= distance)
                rightContainer.add(setOfPoint); // add the point to the right container
        }
    }

    /** Find the shortest distance*/
    private static double shortestDistance(Point2D[] setOfPoints){
        double distance = smallestDistanceInSubset(setOfPoints);

        // Add points to the containers
        addPointsToSpecificContainer(setOfPoints,distance);

        // find the shortest distance
        for (Point2D point2D : leftContainer)
            for (Point2D d : rightContainer) {
                if (computeDistance(point2D, d) < distance)
                    distance = computeDistance(point2D, d); // update distance
            }

        return distance;
    }
}
