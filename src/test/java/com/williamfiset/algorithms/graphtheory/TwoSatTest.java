package com.williamfiset.algorithms.graphtheory;

import org.junit.Test;

public class TwoSatTest {
    @Test
    public void testPositiveInstance(){
        int[] a = {1, -2, -1, 3, -3, -4, -3};
        int[] b = {2, 3, -2, 4, 5, -5, 4};
        TwoSat twosat = new TwoSat(a, b, 5);
        assert(twosat.isTwoSatisfiable());
    }

    @Test
    public void testNegativeInstance(){
        int[] a = {1, -2, 1, -1, -1};
        int[] b = {2, 3, -3, -2, 2};
        TwoSat twoSat = new TwoSat(a, b, 3);
        assert(!twoSat.isTwoSatisfiable());
    }

    @Test
    public void testSimpleConstructor(){
        TwoSat twoSat = new TwoSat(5);
        int[] a = {1, -2, -1, 3, -3, -4, -3};
        int[] b = {2, 3, -2, 4, 5, -5, 4};
        twoSat.setClauses(a, b);
        assert(twoSat.isTwoSatisfiable());
    }

    @Test
    public void testClausesNotSpecified(){
        TwoSat twoSat = new TwoSat(5);
        try{
            twoSat.isTwoSatisfiable();
        }catch(IllegalStateException exception){
            assert(exception.getMessage().equals(twoSat.getIllegalStateExceptionMessage()));
        }
    }

    @Test
    public void testChangeClauses(){

        //negative instance
        int[] a = {1, -2, 1, -1, -1};
        int[] b = {2, 3, -3, -2, 2};
        TwoSat twoSat = new TwoSat(a, b, 3);
        assert(!twoSat.isTwoSatisfiable());

        //positive instance
        twoSat.initializeVertices(5);
        a = new int[]{1, -2, -1, 3, -3, -4, -3};
        b = new int[]{2, 3, -2, 4, 5, -5, 4};;
        twoSat.clear();
        twoSat.setClauses(a,b);
        assert(twoSat.isTwoSatisfiable());
    }

    @Test
    public void testClauseLengthsNotMatching(){
        TwoSat twosat = new TwoSat(new int[]{}, new int[]{}, 0);
        try{
            int[] a = {1, 2, 3};
            int[] b = {-2, 1};
            twosat = new TwoSat(a, b, 3);
        }catch(IllegalArgumentException exception){
            assert(exception.getMessage().equals(twosat.getClauseExceptionMessage()));
        }
    }

    @Test
    public void testNegativeNumVariables(){
        TwoSat twosat = new TwoSat(new int[]{}, new int[]{}, 0);
        try{
            int[] a = {1,2,3};
            int[] b = {2,3,1};

            twosat = new TwoSat(a, b, -1);
        }catch(IllegalArgumentException exception){
            assert(exception.getMessage().equals(twosat.getVertexAmountNegativeExceptionMessage()));
        }
    }
}
