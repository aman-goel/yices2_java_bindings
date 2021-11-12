package com.sri.yices;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TermsMCSAT extends Terms {

    /**
     * Blasts "tuple tree" type into tuple trees of variables
     *
     * @param ty:  yices type
     */
    static int typeBlast(int ty, Collection<Integer> newvars) {
        if (Types.isTuple(ty)){
            int children = Types.numChildren(ty);
            int[] result = new int[children];
            for(int i = 0; i < children; i++){
                result[i] = typeBlast(Types.child(ty,i), newvars);
            }
            return Terms.tuple(result);
        } else {
            int var = Terms.newUninterpretedTerm(ty);
            newvars.add(var);
            return var;
        }
    }

    static public int newUninterpretedTerm(int ty) throws YicesException {
        return typeBlast(ty, new LinkedList<Integer>());
    }

    static public int newUninterpretedTerm(String name, int tau) throws YicesException {
        int t = newUninterpretedTerm(tau);
        Yices.setTermName(t, name);
        return t;
    }

    static public int eq(int left, int right) throws YicesException {
        int ty = typeOf(left);
        if (Types.isTuple(ty)){
            int[] children = Types.children(ty);
            int[] result = new int[children.length];
            for(int i = 0; i < children.length; i++){
                result[i] = eq(Terms.select(i+1, left), Terms.select(i+1, right)); // recursive call
            }
            return Terms.and(result);
        } else {
            return Terms.eq(left, right);
        }
    }

    static public int neq(int left, int right) throws YicesException {
        return Terms.not(eq(left, right));
    }

    static public int distinct(int... arg) throws YicesException {
        if (arg.length == 2) return neq(arg[0], arg[1]);
        return Terms.distinct(arg);
    }

    static public int distinct(List<Integer> args) throws YicesException {
        return distinct(args.stream().mapToInt(Integer::intValue).toArray());
    }

}
