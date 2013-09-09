/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import java.util.Random;

/**
 *
 * @author ArHowk
 */
public class MathExtra 
{
    public static void randomNumb(double min, double max, int count){
        Random r = new Random();
        for(int i = 0; i < count; i++){
            System.out.println("Rand " + i + " : " + (r.nextInt((int)(max-min))+min + r.nextDouble()));
        }
    }
    public static double limit(double d, double min, double max){
        if(d < min){
            return min;
        }else if(d > max){
            return max;
        }else{
            return d;
        }
    }
    public static double limit4D(double d, double min1, double max1, double min2, double max2){ 
        if(d < ((max1 + min2) / 2)){
            if(d < min1){
                return min1;
            }else if(d > max1){
                return max1;
            }else{
                return d;
            }
        }else{
            if(d < min2){
                return min2;
            }else if(d > max2){
                return max2;
            }else{
                return d;
            }
        }
    }
    public static double[] stringToDoubleArray(String s){
        double d[] = new double[60];
        int currentMarker = 0;
        int lastMarker = -1;
        for(int i = 0; i < s.length(); i++){
                if(s.substring(i, i + 1).equals(",")){
                    d[currentMarker] = Double.parseDouble(s.substring(lastMarker + 1, i));
                    lastMarker = i;
                    currentMarker++;
                }else if(i == s.length() - 1){
                    d[currentMarker] = Double.parseDouble(s.substring(lastMarker + 1, i + 1));
                }
        }
        return d;
    }
}
