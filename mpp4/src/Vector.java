import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vector {
    private double[] data;

    private String conclusion;


    public Vector(String[] dataV){
        this.conclusion = dataV[dataV.length-1];
        this.data = new double[dataV.length-1];
        for (int i = 0; i < data.length; i++) {
            dataV[i] = dataV[i].replaceAll(",",".");
            this.data[i] = Double.parseDouble(dataV[i]);
        }
    }

    public Vector(double[] dataV){
        this.conclusion = "test";
        this.data = dataV;
    }

    public int getDataCount(){
        return data.length;
    }


    public String getConclusion() {
        return conclusion;
    }

    public double[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "data=" + Arrays.toString(data) +
                ", conclusion='" + conclusion + '\'' +
                '}';
    }
}
