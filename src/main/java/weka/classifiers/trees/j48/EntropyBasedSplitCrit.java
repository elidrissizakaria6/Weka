/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    EntropyBasedSplitCritCustomized.java
 *    Copyright (C) 1999 University of Waikato, Hamilton, New Zealand
 *
 */

package weka.classifiers.trees.j48;

/**
 * "Abstract" class for computing splitting criteria
 * based on the entropy of a class distribution.
 *
 * @author Eibe Frank (eibe@cs.waikato.ac.nz)
 * @version $Revision: 1.8 $
 */
public abstract class EntropyBasedSplitCrit
  extends SplitCriterion {

  /** for serialization */
  private static final long serialVersionUID = -2618691439791653056L;

  /** The log of 2. */
  protected static double log2 = Math.log(2);

  /**
   * Help method for computing entropy.
   */
  public final double logFunc(double num) {

    // Constant hard coded for efficiency reasons
    if (num < 1e-6)
      return 0;
    else
      return num*Math.log(num)/log2;
  }

  /**
   * Computes entropy of distribution before splitting.
   */
  public final double oldEntbak(Distribution bags) {//TODO
	System.out.println("old entropy");
    double returnValue = 0;
    int j;

    for (j=0;j<bags.numClasses();j++)
      returnValue = returnValue+logFunc(bags.perClass(j));
    return logFunc(bags.total())-returnValue; 
  }
/**
 * 
 * Zakaria computeGini
 */
  public final double oldEnt(Distribution bags) {//TODO
	  double[] dist =new double[bags.numClasses()]; 
	  double total = 0;
	  for (int j=0;j<bags.numClasses();j++){
		  dist[j]=bags.perClass(j);
		  total+=dist[j];
	  }
	  if (total==0) return 0;
	    double val = 0;
	    for (int i=0; i<dist.length; i++) {
	      val += (dist[i]/total)*(dist[i]/total);
	    }
	    System.out.println("numclass"+bags.numClasses()+" numbags"+bags.numBags());
	    return 1- val;
	  }
  /**
   * Computes entropy of distribution after splitting.
   */
  public final double newEntbak(Distribution bags) {
    
    double returnValue = 0;
    int i,j;
    for (i=0;i<bags.numBags();i++){
      for (j=0;j<bags.numClasses();j++)
    returnValue = returnValue+logFunc(bags.perClassPerBag(i,j));
      returnValue = returnValue-logFunc(bags.perBag(i));
    }
    return -returnValue;
  }
  /**
   * GINI zakaria
   */
  public final double newEnt(Distribution bags) {
	  double[] dist =new double[bags.numClasses()]; 
	  double n,ni,Somme = 0;
	  double total = 0;
	    double returnValue = 0;
	    n=0;
	    double val = 0;
	    for (int j=0;j<bags.numClasses();j++){
	    	n+=bags.perClass(j);
	    }
	    for (int k=0;k<bags.numBags();k++){
	    for (int j=0;j<bags.numClasses();j++){
			  dist[j]=bags.perClassPerBag(k, j);
			  total+=dist[j];
			  val += (dist[j]/total)*(dist[j]/total);
			  ni=bags.perClass(j);
			  Somme+=ni/n*(val);
		  }
	    total=0;
		    
//		    for (int i=0; i<dist.length; i++) {
//		      val += (dist[i]/total)*(dist[i]/total);
//			    ni=bags.perClass(i);
//			    Somme+=ni/n*(val); 
//		    }
	    }
	    System.out.println("N="+n+"Somme"+(-Somme));
	    return Somme;
	  }
  /**
   * Computes entropy after splitting without considering the
   * class values.
   */
  public final double splitEnt(Distribution bags) {
    double returnValue = 0;
    int i;

    for (i=0;i<bags.numBags();i++)
      returnValue = returnValue+logFunc(bags.perBag(i));
    return logFunc(bags.total())-returnValue;
  }
}

