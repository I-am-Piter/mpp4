import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Group> groups = new ArrayList<>();
        List<Vector> list = new ArrayList<>();
        try {
            list = readAllData("mpp4/iris_data.txt");
            for(Vector v:list){
                System.out.println(v);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Scanner kScanner = new Scanner(System.in);
        System.out.println("wprowadz K");
        int k = kScanner.nextInt();

        for (int i = 0; i < k; i++) {
            groups.add(new Group());
        }

        int[] randoms = getXrandoms(groups.size(),list.size());

        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).addPart(list.get(randoms[i]));
            System.out.println(groups.get(i));
        }



        Vector tmp = null;
        int iter = 0;

        do{
            for (int i = 0; i < groups.size(); i++) {
                Group tmpG = groups.get(i);
                tmpG.calcMiddle();
                tmpG.clearList();
            }

            for (int i = 0; i < list.size(); i++) {
                tmp = list.get(i);
                groups.get(getLeastDistanceIndex(calcDistanceToGroups(tmp,groups))).addPart(tmp);
            }

            //TODO Błąd kwadratowy

            System.out.println("iter:"+iter++);
        }while(!areGroupsStable(groups));

        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).printGroup();

        }

        //obliczanie entropii TODO

        System.out.println("Wprowadz dane to klasyfikacji");
        double[] dataToClassify = new double[list.get(0).getDataCount()];
        for (int i = 0; i < dataToClassify.length; i++) {
            dataToClassify[i] = kScanner.nextDouble();
        }

        tmp = new Vector(dataToClassify);


        System.out.println("Twój przypadek został zaklasyfikowany do grupy:");
        groups.get(getLeastDistanceIndex(calcDistanceToGroups(tmp,groups))).printGroup();

    }
    public static boolean areGroupsStable(List<Group> groups){
        for (int i = 0; i < groups.size(); i++) {
            if(groups.get(i).hasChanged()){
                return false;
            }
        }
        return true;
    }

    public static double[] calcDistanceToGroups(Vector v,List<Group> groups){
        double[] vectorData = v.getData();
        double[] distances = new double[groups.size()];

        double[] mid;

        for (int i = 0; i < groups.size(); i++) {
            mid = groups.get(i).getMiddle();

            for (int j = 0; j < vectorData.length; j++) {
                distances[i] += Math.pow(vectorData[i]-mid[i],2);
            }

            distances[i] = Math.sqrt(distances[i]);
        }

        return distances;
    }

    public static int getLeastDistanceIndex(double[] distances){
        int index = 0;
        double leastDist = Double.MAX_VALUE;

        for (int i = 0; i < distances.length; i++) {
            if(distances[i] < leastDist){
                leastDist = distances[i];
                index = i;
            }
        }

        return index;
    }

    public static int[] getXrandoms(int x,int size){

        int[] tab = new int[x];
        boolean valid = true;
        do {
            for (int i = 0; i < x; i++) {
                tab[i] = (int) (Math.random() * size);
            }
            valid = validate(tab);
        }while(!valid);

        return tab;
    }

    private static boolean validate(int[] list){
        for (int i = 0; i < list.length; i++) {
            for (int j = i+1; j < list.length; j++) {
                if(list[i] == list[j]){
                    return false;
                }
            }
        }
        return true;
    }

    public static List<Vector> readAllData(String filePath) throws FileNotFoundException {
        List<Vector> toReturn = new ArrayList<>();
        File file = new File(filePath);
        Scanner input = new Scanner(file);
        String tmp;
        while(input.hasNextLine()){
            tmp = input.nextLine();
            String[] data = tmp.split("\t");
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].replaceAll(" ","");
            }
            toReturn.add(new Vector(data));
        }
        return toReturn;
    }
}
