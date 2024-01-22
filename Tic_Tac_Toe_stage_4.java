
import java.util.*;

class MyException extends Exception{
    public MyException(String message){
        super(message);
    }
}
class BadParamException extends Exception{
    public BadParamException(String message){
        super(message);
    }
}


class TicTacToe{
    int[][] board;
    Scanner sc;
    public void init(){
        sc = new Scanner(System.in);
        board = new int[3][3];
    }
    public void print(){
        for (int j = 0; j < 9; j++) {
            System.out.print("-");
        }
        System.out.println();
        for(int i= 0; i < 3; i++) {
            System.out.print("| ");
            for(int j = 0; j < 3; j++){
                switch(board[i][j]){
                    case 1 :
                        System.out.print("X ");
                        break;
                    case 0 :
                        System.out.print("O ");
                        break;
                    default :
                        System.out.print("  ");
                }
            }
            System.out.println("|");
        }
        for (int j = 0; j < 9; j++) {
            System.out.print("-");
        }
        System.out.println();
    }
//    public void initialize_board(String s){
//        int idx = 0;
//        for(int i = 0; i < s.length(); i++){
//            switch (s.charAt(i)){
//                case 'X' :
//                    board[idx/3][idx%3] = 1;
//                    break;
//                case 'O' :
//                    board[idx/3][idx%3] = 0;
//                    break;
//                default:
//                    board[idx/3][idx%3] = -1;
//            }
//            idx++;
//        }
//    }
    public void initialize_board(){
        for(int i = 0; i < 3; i++)Arrays.fill(board[i],-1);
    }
    public int cnt(){
        int c = 0;
        for(int i = 0; i < 3; i++){
            for(int j=0; j < 3; j++){
                c += board[i][j] == 1 ? 1 : board[i][j] == 0 ? -1 : 0;
            }
        }
        return c;
    }
    public int check(){
        for(int i= 0; i < 3; i++){
            int cnt1 = 0, cnt0 = 0;
            for(int j = 0; j < 3; j++){
                if(board[i][j] == 1){
                    cnt1++;
                }
                else if(board[i][j] == 0) {
                    cnt0++;
                }
            }
            if(cnt0 == 3) return 3;
            if(cnt1 == 3) return 2;
            cnt1 = 0; cnt0 = 0;
            for(int j = 0; j < 3; j++) {
                if (board[j][i] == 1) {
                    cnt1++;
                } else if (board[j][i] == 0) {
                    cnt0++;
                }
            }
            if(cnt0 == 3) return 3;
            if(cnt1 == 3) return 2;

        }
        int diag0 = 0, diag1 = 0, diag2 = 0, diag3 = 0;
        for(int i= 0; i < 3; i++){
            if(board[i][i] == 1){
                diag0++;
            }
            else if(board[i][i] == 0){
                diag1++;
            }
            if(diag0 == 3) return 2;
            if(diag1 == 3) return 3;
            if(board[2-i][i] == 1){
                diag2++;
            }
            else if(board[2-i][i] == 0){
                diag3++;
            }
            if(diag2 == 3) return 2;
            if(diag3 == 3) return 3;
        }
        boolean flag = true;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == -1) {
                    flag = false;
                    break;
                }
            }
            if (!flag) break;
        }
        return flag ? 1 : 0;
    }
    public void display_position(int state){
        if(state == 0){
            System.out.println("Game not finished");
        }
        else if(state == 1){
            System.out.println("Draw");
        }
        else if(state == 2){
            System.out.println("X wins");
        }
        else{
            System.out.println("O wins");
        }
    }
    public boolean user(boolean flag){
        try{
            flag = false;
            System.out.print("Enter the coordinates: ");
            String[] cor = sc.nextLine().split(" ");
            int[] c = new int[2];
            c[0] = Integer.parseInt(cor[0]);
            c[1] = Integer.parseInt(cor[1]);
            c[0]--; c[1]--;
            if(board[c[0]][c[1]] != -1){
                throw new MyException("This cell is occupied! Choose another one!");
            }
            int val = cnt();
            if(val > 0){
                board[c[0]][c[1]] = 0;
            }
            else{
                board[c[0]][c[1]] = 1;
            }
        }catch(NumberFormatException e){
            System.out.println("You should enter numbers!");
            flag = true;
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Coordinates should be from 1 to 3!");
            flag = true;
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            flag = true;
        }
        return flag;
    }
    public int[] win_state(int symbol){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] != -1) continue;
                board[i][j] = symbol;
                if(check() == (symbol == 1 ? 2 : 3)){
                    board[i][j] = -1;
                    return new int[]{i,j};
                }
                board[i][j] = -1;
            }
        }
        return new int[]{-1,-1};
    }
    public int[] lose_state(int symbol){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] != -1) continue;
                board[i][j] = symbol^1;
                if(check() == (symbol == 1 ? 3 : 2)){
                    board[i][j] = -1;
                    return new int[]{i,j};
                }
                board[i][j] = -1;
            }
        }
        return new int[]{-1,-1};
    }
    public void computer(int symbol,String level){
        System.out.println("Making move level \""+ level +"\"");
         int[] c = new int[]{-1,-1};
        if(level.equals("medium")){
            c = win_state(symbol);
            if(c[0] == -1 && c[1] == -1){
                c = lose_state(symbol);
            }
        }
        while(true){
            try{
                if(c[0] == -1 && c[1] == -1){
                    int x =  (int)(Math.random()*3);
                    int y = (int)(Math.random()*3);
                    if(board[x][y] != -1) continue;
                    c[0] = x;
                    c[1] = y;
                }
                break;
            }
            catch(Exception e){
                continue;
            }
        }
        board[c[0]][c[1]] = symbol;
    }
    public void user_comp(String level){
        int state = 0;
        boolean flag = false;
        while(state == 0){
            if(!flag){
                print();
            }
            flag = user(flag);
            if(flag) continue;
            state = check();
            if(state != 0){
                print();
                display_position(state);
                continue;
            }
            print();
            computer(0,level);
            state = check();
            if(state != 0){
                print();
                display_position(state);
            }
        }
    }
    public void comp_user(String level){
        int state = 0;
        boolean flag = false;
        while(state == 0){
            if(!flag){
                print();
                computer(1,level);
                state = check();
                if(state != 0){
                    print();
                    display_position(state);
                    continue;
                }
                print();
            }
            flag = user(flag);
            if(flag) continue;
            state = check();
            if(state != 0){
                print();
                display_position(state);
            }
        }
    }
    public void user_user(){
        int state = 0;
        boolean flag1 = false, flag2 = false;
        while(state == 0){
            if(!flag1){
                print();
            }
            flag1 = user(flag1);
            if(!flag1){
                continue;
            }
            state = check();
            if(state != 0){
                print();
                display_position(state);
                continue;
            }
            print();
            do{
                flag2 = user(flag2);
            }while(flag2);
            state = check();
            if(state != 0){
                print();
                display_position(state);
            }
        }
    }
    public void comp_comp(String level1, String level2){
        int state = 0;
        while(state == 0){
            print();
            computer(1,level1);
            state = check();
            if(state != 0){
                print();
                display_position(state);
                continue;
            }
            print();
            computer(0,level2);
            state = check();
            if(state != 0){
                print();
                display_position(state);
            }
        }
    }
    public boolean bad_param(String[] input){
        if(input.length < 3){
            return false;
        }
        if(!input[0].equals("start")){
            return false;
        }
        if(!input[1].equals("user") && !input[1].equals("medium")
        && !input[2].equals("easy")){
            return false;
        }
        return input[2].equals("user") || input[2].equals("medium") ||
                input[2].equals("easy");
    }
    public void start(){
        init();
        initialize_board();
        while(true){
            try{
                String[] input = sc.nextLine().split(" ");
                if(input[0].equals("exit")){
                    break;
                }
                if(!bad_param(input)){
                    throw new BadParamException("Bad parameters!");
                }
                if(input[1].equals("user") && input[2].equals("user")){
                    user_user();
                }
                else if(input[1].equals("user")){
                    user_comp(input[2]);
                }
                else if(input[2].equals("user")){
                    comp_user(input[2]);
                }
                else{
                    comp_comp(input[1],input[2]);
                }
            }
            catch(BadParamException e){
                System.out.println(e.getMessage());
            }

        }
    }
}
public class Tic_Tac_Toe_stage_4 {
    public static void main(String[] args) {
        // write your code here
        TicTacToe tt = new TicTacToe();
        tt.start();
    }
}
