package edu.fudan.nlp.similarity;

import gnu.trove.iterator.hash.TObjectHashIterator;
import gnu.trove.set.hash.THashSet;

public class JaccardSimilarity implements ISimilarity<THashSet<Object>> {

    public float calc(THashSet<Object> s1, THashSet<Object> s2) {
        int com = 0;
        if (s1 == null || s2 == null)
            return 0;
        TObjectHashIterator<Object> it = s1.iterator();
        for ( int i = s1.size(); i-- > 0; ) {
            Object v = it.next();
            if(s2.contains(v))
                com++;
        }
        float sim = ((float) com)/(s1.size()+s2.size()-com);
        return sim;
    }
}
