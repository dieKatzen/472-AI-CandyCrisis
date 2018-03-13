public class BoardState{
    char alpha_position;
    char occupier;

    public BoardState(char alpha_position, char occupier) {
        this.alpha_position = alpha_position;
        this.occupier = occupier;
    }

    public BoardState( char occupier) {
        this.alpha_position = alpha_position;
        this.occupier = occupier;
    }

    public char getAlpha_position() {
        return alpha_position;
    }

    public void setAlpha_position(char alpha_position) {
        this.alpha_position = alpha_position;
    }

    public char getOccupier() {
        return occupier;
    }

    public void setOccupier(char occupier) {
        this.occupier = occupier;
    }
}