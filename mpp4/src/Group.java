import java.util.ArrayList;
import java.util.List;

public class Group {
    private double[] middle;
    List<Vector> participants;
    List<Vector> bckpParticipants;
    int id;
    static int Sid;
    Group(){
        this.participants = new ArrayList<>();
        this.id = Sid++;
    }

    public void calcMiddle(){
        Vector tmp = participants.get(0);
        middle = tmp.getData();

        for (int i = 0; i < middle.length; i++) {
            middle[i] = getXMean(i);
        }
    }

    public void addPart(Vector part){
        if(!participants.contains(part)) {
            participants.add(part);
        }
    }

    public double getXMean(int x){
        double mainMean = 0;

        for(Vector part:participants){
            mainMean += part.getData()[x];
        }

        mainMean /= participants.size();

        return mainMean;
    }

    public void clearList(){
        bckpParticipants = new ArrayList<>(participants);
        participants.clear();
    }

    public boolean hasChanged(){
        boolean toReturn = false;
        for (int i = 0; i < participants.size(); i++) {
            if(!bckpParticipants.contains(participants.get(i))){
                return true;
            }
        }
        for (int i = 0; i < bckpParticipants.size(); i++) {
            if(!participants.contains(bckpParticipants.get(i))){
                return true;
            }
        }
        return false;
    }

    public double[] getMiddle() {
        return middle;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                '}';
    }

    public void printGroup(){
        System.out.println("Group id = "+this.id);
        for (int i = 0; i < participants.size(); i++) {
            System.out.println(participants.get(i).getConclusion());
        }
        System.out.println();
    }
}
