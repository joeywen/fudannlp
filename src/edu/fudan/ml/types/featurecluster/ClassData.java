package edu.fudan.ml.types.featurecluster;

public class ClassData {
    private int key;
    private double[] label;
    private int count;
    static int allCount;

    public ClassData(int key, double[] label, int count) {
        this.key = key;
        this.label = label;
        this.count = count;
    }

    public void update(double[] label, int count) {
        this.label = label;
        this.count = count;
    }

    /**
     * @return the key
     */
    public int getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * @return the label
     */
    public double[] getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(double[] label) {
        this.label = label;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the allCount
     */
    public int getAllCount() {
        return allCount;
    }

    /**
     * @param allCount the allCount to set
     */
    public void setAllCount(int allCount) {
        ClassData.allCount = allCount;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("key: " + key + "\n" + "label: ");
        for (double l : label) {
            sb.append(l + " ");
        }
        sb.append("\ncount: " + count);
        return sb.toString();
    }
}
