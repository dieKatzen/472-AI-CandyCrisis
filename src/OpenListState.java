public class OpenListState  implements Comparable<OpenListState>{
    BoardState bs;
    int heuristicValue;

    public BoardState getBs() {
        return bs;
    }

    public void setBs(BoardState bs) {
        this.bs = bs;
    }

    public int getHeuristicValue() {
        return heuristicValue;
    }

    public void setHeuristicValue(int heuristicValue) {
        this.heuristicValue = heuristicValue;
    }

    public OpenListState(BoardState bs, int heuristicValue) {
        this.bs = bs;

        this.heuristicValue = heuristicValue;
    }

    @Override
    public int compareTo(OpenListState ops){
        if(this.getHeuristicValue() == ops.getHeuristicValue()){
            return 0;
        }else if (this.getHeuristicValue() > ops.getHeuristicValue()){
            return 1;
        }else{
            return -1;
        }
    }
}
