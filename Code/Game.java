/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sea_battle;

import java.io.*;
import java.util.Random;
import java.util.Scanner;
import sea_battle.Player;
import sea_battle.Board;
import sea_battle.Ship;
import sea_battle.Ranking;
import java.util.*;

public class Game {

    Game() {
        player_1 = new Player();
        player_2 = new Player();
        len = 10;
    }

    private Player player_1;
    private Player player_2;

    public Player getPlayer_1() {
        return player_1;
    }

    public void setPlayer_1(Player player_1) {
        this.player_1 = player_1;
    }

    public Player getPlayer_2() {
        return player_2;
    }

    public void setPlayer_2(Player player_2) {
        this.player_2 = player_2;
    }
    public int len;

    public int level = 1;
    void Setting_game(){
        while (true) {
            System.out.print("Tuy chon:\n" + "1. Che do choi\n" + "2. Kich thuoc bang\n" + "3. Exit\n");
            Scanner sc = new Scanner(System.in);
            int cn = sc.nextInt();
            if (cn == 1) {
                System.out.print("Chon che do choi:\n" + "1. Level 1\n" + "2. Level 2\n");
                cn = sc.nextInt();
                if (cn == 1) level = 1;
                else level = 2;
                System.out.println("Thanh cong!");
            }
            else if (cn == 2) {
                System.out.print("Chuon kich thuoc bang ban muon choi\n" + "1. 10 X 10\n" + "2. 20 X 20\n");
                cn = sc.nextInt();
                if (cn == 1) {
                    len = 10;
                }
                else len = 20;
                System.out.println("Thanh cong!");
            }
            else {
                System.out.println("Thoat thanh cong!");
                break;
            }
        }
    }
    void clrscr(){
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }
    //kiem tra vi tri tan cong
    int  Check_position(int x, int y){
        if (x >= 1 && x <= len && y >= 1 && y <= len) return 1;
        else return 0;
    }
    int Check_win(char[][] matrix) {
        for (int i = 1; i <= len; i ++) {
            for (int j = 0; j <= len; j ++) {
                if (matrix[i][j] == '#') return 0;
            }
        }
        return 1;
    }
    String random() {
        // Các ký tự mà bạn muốn sử dụng trong chuỗi ngẫu nhiên
        String characters = "ABCDEFGHIJ";
        if (len == 20) characters = "ABCDEFGHIJKLMNOPQRST";
        else characters = "ABCDEFGHIJ";
        // Khởi tạo đối tượng Random
        Random random = new Random();
        // Chọn ngẫu nhiên một ký tự từ chuỗi characters
        char randomChar = characters.charAt(random.nextInt(characters.length()));
        // Chọn ngẫu nhiên một số từ 1 đến number
        int number = 9;
        if (len == 20) number = 19;
        else number = 9;
        int randomNumber = random.nextInt(number) + 1;
        // Kết hợp ký tự và số để tạo chuỗi
        String randomString = String.valueOf(randomChar) + String.valueOf(randomNumber);
        return randomString;
    }
    Vector<Ranking> ranking = new Vector<>(); //danh sach bang xep hang
    // Đường dẫn của file bảng xếp hạng
    void doc_and_xoa_file() {
        String filePath = "E:/Java/main/src/main/java/sea_battle/danh_sach_ranking.txt";

        // Đọc dữ liệu từ file và đưa vào Vector
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String name = parts[0];
                int attacks = Integer.parseInt(parts[1]);
                int remainingShips = Integer.parseInt(parts[2]);

                ranking.add(new Ranking(name, attacks, remainingShips));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Xóa dữ liệu từ file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(""); // Xóa toàn bộ dữ liệu trong file
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(ranking, Comparator.comparing(Ranking::getAttackCount).thenComparing(Ranking::getShip_live).thenComparing(Ranking::getPlayerName));


    }
    void ghi_du_lieu() {
        String filePath = "E:/Java/main/src/main/java/sea_battle/danh_sach_ranking.txt";
        // Ghi dữ liệu từ Vector vào file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Ranking rankings : ranking) {
                bw.write(rankings.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void xoa_du_lieu(){
        ranking.clear();
    }
    void in_rank(){
        if (ranking.size() == 0) System.out.println("Chua co danh sach!");
        int f = 1;
        for (Ranking rankings : ranking) {
            System.out.println(f++ + " " + rankings);
        }
    }
    //muc 1
    public int luot_1 = 1;
    void Level_1(){
        Scanner sc = new Scanner(System.in);
        if (luot_1 == 1) {
            clrscr();
            player_1.setPoint(0);
            player_1.setNumber_of_ships_live(5);
            player_1.setNumber_of_ships_destroy(0);
            player_1.getBoard().Draw_board();
            //sc.nextLine();
            String begin, end;
            //tau tuan tra
            for (int i = 0; i < 2; i ++) {
                System.out.println("Nhap toa do dat thuyen tuan tra (do dai 2 o) so " + (i + 1) + ":" );
                begin = sc.next();
                end = sc.next();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                if (i == 0) {
                    //cap nhap toa do cua tau tuan tra 1
                    //diem dau
                    player_1.getPatrol_boat_1().setX_begin((int)begin.charAt(0) - 'A' + 1);
                    player_1.getPatrol_boat_1().setY_begin((int)begin.charAt(1) - '0');
                    //diem cuoi
                    player_1.getPatrol_boat_1().setX_end((int)end.charAt(0) - 'A' + 1);
                    player_1.getPatrol_boat_1().setY_end((int)end.charAt(1) - '0');
                    //kiem tra toa do dat tau
                    while (player_1.getPatrol_boat_1().check_Ship_position(player_1.getMatrix(), len, 2) == 0) {
                        System.out.println("Vui long nhap lai toa do!");
                        begin = sc.next();
                        end = sc.next();
                        if (begin.compareTo(end) > 0) {
                            String temp = begin;
                            begin = end;
                            end = temp;
                        }
                        //cap nhap toa do cua tau tuan tra 1
                        //diem dau
                        player_1.getPatrol_boat_1().setX_begin((int)begin.charAt(0) - 'A' + 1);
                        player_1.getPatrol_boat_1().setY_begin((int)begin.charAt(1) - '0');
                        //diem cuoi
                        player_1.getPatrol_boat_1().setX_end((int)end.charAt(0) - 'A' + 1);
                        player_1.getPatrol_boat_1().setY_end((int)end.charAt(1) - '0');
                    }
                    //thiet lap tau
                    player_1.getPatrol_boat_1().set_up_ship(player_1.getMatrix());
                    player_1.show_board_of_player();
                }
                else {
                    //cap nhap toa do cua tau tuan tra 2
                    //diem dau
                    player_1.getPatrol_boat_2().setX_begin((int)begin.charAt(0) - 'A' + 1);
                    player_1.getPatrol_boat_2().setY_begin((int)begin.charAt(1) - '0');
                    //diem cuoi
                    player_1.getPatrol_boat_2().setX_end((int)end.charAt(0) - 'A' + 1);
                    player_1.getPatrol_boat_2().setY_end((int)end.charAt(1) - '0');
                    //kiem tra toa do dat tau
                    while (player_1.getPatrol_boat_2().check_Ship_position(player_1.getMatrix(), len, 2) == 0) {
                        System.out.println("Vui long nhap lai toa do!");
                        begin = sc.next();
                        end = sc.next();
                        if (begin.compareTo(end) > 0) {
                            String temp = begin;
                            begin = end;
                            end = temp;
                        }
                        //cap nhap toa do cua tau tuan tra 2
                        //diem dau
                        player_1.getPatrol_boat_2().setX_begin((int)begin.charAt(0) - 'A' + 1);
                        player_1.getPatrol_boat_2().setY_begin((int)begin.charAt(1) - '0');
                        //diem cuoi
                        player_1.getPatrol_boat_2().setX_end((int)end.charAt(0) - 'A' + 1);
                        player_1.getPatrol_boat_2().setY_end((int)end.charAt(1) - '0');
                    }
                    //thiet lap tau
                    player_1.getPatrol_boat_2().set_up_ship(player_1.getMatrix());
                    player_1.show_board_of_player();
                }
            }
            //tau khu truc
            System.out.println("Nhap toa do cua tau khu truc (do dai 4 o): ");
            begin = sc.next();
            end = sc.next();
            if (begin.compareTo(end) > 0) {
                String temp = begin;
                begin = end;
                end = temp;
            }
            //cap nhap toa do cua tau
            //diem dau
            player_1.getDestroyer_boat().setX_begin((int)begin.charAt(0) - 'A' + 1);
            player_1.getDestroyer_boat().setY_begin((int)begin.charAt(1) - '0');
            //diem cuoi
            player_1.getDestroyer_boat().setX_end((int)end.charAt(0) - 'A' + 1);
            player_1.getDestroyer_boat().setY_end((int)end.charAt(1) - '0');
            //kiem tra toa do dat tau
            while (player_1.getDestroyer_boat().check_Ship_position(player_1.getMatrix(), len, 4) == 0) {
                System.out.println("Vui long nhap lai toa do!");
                begin = sc.next();
                end = sc.next();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                //cap nhap toa do cua tau khu truc
                //diem dau
                player_1.getDestroyer_boat().setX_begin((int)begin.charAt(0) - 'A' + 1);
                player_1.getDestroyer_boat().setY_begin((int)begin.charAt(1) - '0');
                //diem cuoi
                player_1.getDestroyer_boat().setX_end((int)end.charAt(0) - 'A' + 1);
                player_1.getDestroyer_boat().setY_end((int)end.charAt(1) - '0');
            }
            //thiet lap tau
            player_1.getDestroyer_boat().set_up_ship(player_1.getMatrix());
            player_1.show_board_of_player();
            //tau ngam
            System.out.println("Nhap toa do cua tau ngam (do dai 3 o): ");
            begin = sc.next();
            end = sc.next();
            if (begin.compareTo(end) > 0) {
                String temp = begin;
                begin = end;
                end = temp;
            }
            //cap nhap toa do cua tau
            //diem dau
            player_1.getSubmarine().setX_begin((int)begin.charAt(0) - 'A' + 1);
            player_1.getSubmarine().setY_begin((int)begin.charAt(1) - '0');
            //diem cuoi
            player_1.getSubmarine().setX_end((int)end.charAt(0) - 'A' + 1);
            player_1.getSubmarine().setY_end((int)end.charAt(1) - '0');
            //kiem tra toa do dat tau
            while (player_1.getSubmarine().check_Ship_position(player_1.getMatrix(), len, 3) == 0) {
                System.out.println("Vui long nhap lai toa do!");
                begin = sc.next();
                end = sc.next();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                //cap nhap toa do cua tau ngam
                //diem dau
                player_1.getSubmarine().setX_begin((int)begin.charAt(0) - 'A' + 1);
                player_1.getSubmarine().setY_begin((int)begin.charAt(1) - '0');
                //diem cuoi
                player_1.getSubmarine().setX_end((int)end.charAt(0) - 'A' + 1);
                player_1.getSubmarine().setY_end((int)end.charAt(1) - '0');
            }
            //thiet lap tau
            player_1.getSubmarine().set_up_ship(player_1.getMatrix());
            player_1.show_board_of_player();
            //thiet giap ham
            System.out.println("Nhap toa do cua thiet giap ham (do dai 5 o): ");
            begin = sc.next();
            end = sc.next();
            if (begin.compareTo(end) > 0) {
                String temp = begin;
                begin = end;
                end = temp;
            }
            //cap nhap toa do cua tau
            //diem dau
            player_1.getBattle_ship().setX_begin((int)begin.charAt(0) - 'A' + 1);
            player_1.getBattle_ship().setY_begin((int)begin.charAt(1) - '0');
            //diem cuoi
            player_1.getBattle_ship().setX_end((int)end.charAt(0) - 'A' + 1);
            player_1.getBattle_ship().setY_end((int)end.charAt(1) - '0');
            //kiem tra toa do dat tau
            while (player_1.getBattle_ship().check_Ship_position(player_1.getMatrix(), len, 5) == 0) {
                System.out.println("Vui long nhap lai toa do!");
                begin = sc.next();
                end = sc.next();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                //cap nhap toa do cua thiet giap ham
                //diem dau
                player_1.getBattle_ship().setX_begin((int)begin.charAt(0) - 'A' + 1);
                player_1.getBattle_ship().setY_begin((int)begin.charAt(1) - '0');
                //diem cuoi
                player_1.getBattle_ship().setX_end((int)end.charAt(0) - 'A' + 1);
                player_1.getBattle_ship().setY_end((int)end.charAt(1) - '0');
            }
            //thiet lap tau
            player_1.getBattle_ship().set_up_ship(player_1.getMatrix());
            player_1.show_board_of_player();
            luot_1 ++;
        }
        else  {
            //Nguoi choi 2
            clrscr();
            player_2.setPoint(0);
            player_2.setNumber_of_ships_live(5);
            player_2.setNumber_of_ships_destroy(0);
            player_2.getBoard().Draw_board();
            String begin, end;
            //tau tuan tra
            for (int i = 0; i < 2; i ++) {
                System.out.println("Nhap toa do dat thuyen tuan tra (do dai 2 o) so " + (i + 1) + ":" );
                begin = sc.next();
                end = sc.next();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                if (i == 0) {
                    //cap nhap toa do cua tau tuan tra 1
                    //diem dau
                    player_2.getPatrol_boat_1().setX_begin((int)begin.charAt(0) - 'A' + 1);
                    player_2.getPatrol_boat_1().setY_begin((int)begin.charAt(1) - '0');
                    //diem cuoi
                    player_2.getPatrol_boat_1().setX_end((int)end.charAt(0) - 'A' + 1);
                    player_2.getPatrol_boat_1().setY_end((int)end.charAt(1) - '0');
                    //kiem tra toa do dat tau
                    while (player_2.getPatrol_boat_1().check_Ship_position(player_2.getMatrix(), len, 2) == 0) {
                        System.out.println("Vui long nhap lai toa do!");
                        begin = sc.next();
                        end = sc.next();
                        if (begin.compareTo(end) > 0) {
                            String temp = begin;
                            begin = end;
                            end = temp;
                        }
                        //cap nhap toa do cua tau tuan tra 1
                        //diem dau
                        player_2.getPatrol_boat_1().setX_begin((int)begin.charAt(0) - 'A' + 1);
                        player_2.getPatrol_boat_1().setY_begin((int)begin.charAt(1) - '0');
                        //diem cuoi
                        player_2.getPatrol_boat_1().setX_end((int)end.charAt(0) - 'A' + 1);
                        player_2.getPatrol_boat_1().setY_end((int)end.charAt(1) - '0');
                    }
                    //thiet lap tau
                    player_2.getPatrol_boat_1().set_up_ship(player_2.getMatrix());
                    player_2.show_board_of_player();
                }
                else {
                    //cap nhap toa do cua tau tuan tra 2
                    //diem dau
                    player_2.getPatrol_boat_2().setX_begin((int)begin.charAt(0) - 'A' + 1);
                    player_2.getPatrol_boat_2().setY_begin((int)begin.charAt(1) - '0');
                    //diem cuoi
                    player_2.getPatrol_boat_2().setX_end((int)end.charAt(0) - 'A' + 1);
                    player_2.getPatrol_boat_2().setY_end((int)end.charAt(1) - '0');
                    //kiem tra toa do dat tau
                    while (player_2.getPatrol_boat_2().check_Ship_position(player_2.getMatrix(), len, 2) == 0) {
                        System.out.println("Vui long nhap lai toa do!");
                        begin = sc.next();
                        end = sc.next();
                        if (begin.compareTo(end) > 0) {
                            String temp = begin;
                            begin = end;
                            end = temp;
                        }
                        //cap nhap toa do cua tau tuan tra 2
                        //diem dau
                        player_2.getPatrol_boat_2().setX_begin((int)begin.charAt(0) - 'A' + 1);
                        player_2.getPatrol_boat_2().setY_begin((int)begin.charAt(1) - '0');
                        //diem cuoi
                        player_2.getPatrol_boat_2().setX_end((int)end.charAt(0) - 'A' + 1);
                        player_2.getPatrol_boat_2().setY_end((int)end.charAt(1) - '0');
                    }
                    //thiet lap tau
                    player_2.getPatrol_boat_2().set_up_ship(player_2.getMatrix());
                    player_2.show_board_of_player();
                }
            }
            //tau khu truc
            System.out.println("Nhap toa do cua tau khu truc (do dai 4 o): ");
            begin = sc.next();
            end = sc.next();
            if (begin.compareTo(end) > 0) {
                String temp = begin;
                begin = end;
                end = temp;
            }
            //cap nhap toa do cua tau
            //diem dau
            player_2.getDestroyer_boat().setX_begin((int)begin.charAt(0) - 'A' + 1);
            player_2.getDestroyer_boat().setY_begin((int)begin.charAt(1) - '0');
            //diem cuoi
            player_2.getDestroyer_boat().setX_end((int)end.charAt(0) - 'A' + 1);
            player_2.getDestroyer_boat().setY_end((int)end.charAt(1) - '0');
            //kiem tra toa do dat tau
            while (player_2.getDestroyer_boat().check_Ship_position(player_2.getMatrix(), len, 4) == 0) {
                System.out.println("Vui long nhap lai toa do!");
                begin = sc.next();
                end = sc.next();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                //cap nhap toa do cua tau khu truc
                //diem dau
                player_2.getDestroyer_boat().setX_begin((int)begin.charAt(0) - 'A' + 1);
                player_2.getDestroyer_boat().setY_begin((int)begin.charAt(1) - '0');
                //diem cuoi
                player_2.getDestroyer_boat().setX_end((int)end.charAt(0) - 'A' + 1);
                player_2.getDestroyer_boat().setY_end((int)end.charAt(1) - '0');
            }
            //thiet lap tau
            player_2.getDestroyer_boat().set_up_ship(player_2.getMatrix());
            player_2.show_board_of_player();
            //tau ngam
            System.out.println("Nhap toa do cua tau ngam (do dai 3 o): ");
            begin = sc.next();
            end = sc.next();
            if (begin.compareTo(end) > 0) {
                String temp = begin;
                begin = end;
                end = temp;
            }
            //cap nhap toa do cua tau
            //diem dau
            player_2.getSubmarine().setX_begin((int)begin.charAt(0) - 'A' + 1);
            player_2.getSubmarine().setY_begin((int)begin.charAt(1) - '0');
            //diem cuoi
            player_2.getSubmarine().setX_end((int)end.charAt(0) - 'A' + 1);
            player_2.getSubmarine().setY_end((int)end.charAt(1) - '0');
            //kiem tra toa do dat tau
            while (player_2.getSubmarine().check_Ship_position(player_2.getMatrix(), len, 3) == 0) {
                System.out.println("Vui long nhap lai toa do!");
                begin = sc.next();
                end = sc.next();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                //cap nhap toa do cua tau ngam
                //diem dau
                player_2.getSubmarine().setX_begin((int)begin.charAt(0) - 'A' + 1);
                player_2.getSubmarine().setY_begin((int)begin.charAt(1) - '0');
                //diem cuoi
                player_2.getSubmarine().setX_end((int)end.charAt(0) - 'A' + 1);
                player_2.getSubmarine().setY_end((int)end.charAt(1) - '0');
            }
            //thiet lap tau
            player_2.getSubmarine().set_up_ship(player_2.getMatrix());
            player_2.show_board_of_player();
            //thiet giap ham
            System.out.println("Nhap toa do cua thiet giap ham (do dai 5 o): ");
            begin = sc.next();
            end = sc.next();
            if (begin.compareTo(end) > 0) {
                String temp = begin;
                begin = end;
                end = temp;
            }
            //cap nhap toa do cua tau
            //diem dau
            player_2.getBattle_ship().setX_begin((int)begin.charAt(0) - 'A' + 1);
            player_2.getBattle_ship().setY_begin((int)begin.charAt(1) - '0');
            //diem cuoi
            player_2.getBattle_ship().setX_end((int)end.charAt(0) - 'A' + 1);
            player_2.getBattle_ship().setY_end((int)end.charAt(1) - '0');
            //kiem tra toa do dat tau
            while (player_2.getBattle_ship().check_Ship_position(player_2.getMatrix(), len, 5) == 0) {
                System.out.println("Vui long nhap lai toa do!");
                begin = sc.next();
                end = sc.next();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                //cap nhap toa do cua thiet giap ham
                //diem dau
                player_2.getBattle_ship().setX_begin((int)begin.charAt(0) - 'A' + 1);
                player_2.getBattle_ship().setY_begin((int)begin.charAt(1) - '0');
                //diem cuoi
                player_2.getBattle_ship().setX_end((int)end.charAt(0) - 'A' + 1);
                player_2.getBattle_ship().setY_end((int)end.charAt(1) - '0');
            }
            //thiet lap tau
            player_2.getBattle_ship().set_up_ship(player_2.getMatrix());
            player_2.show_board_of_player();
        }
    }
    //muc 2
    public int luot_2 = 1;
    void Level_2() {
        Scanner sc = new Scanner(System.in);
        if (luot_2 == 1) {
            player_1.setPoint(0);
            player_1.setNumber_of_ships_live(5);
            player_1.setNumber_of_ships_destroy(0);
            //sc.nextLine();
            String begin, end;
            //tau tuan tra
            for (int i = 0; i < 2; i ++) {
                begin = random();
                end = random();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                if (i == 0) {
                    //cap nhap toa do cua tau tuan tra 1
                    //diem dau
                    player_1.getPatrol_boat_1().setX_begin((int)begin.charAt(0) - 'A' + 1);
                    player_1.getPatrol_boat_1().setY_begin((int)begin.charAt(1) - '0');
                    //diem cuoi
                    player_1.getPatrol_boat_1().setX_end((int)end.charAt(0) - 'A' + 1);
                    player_1.getPatrol_boat_1().setY_end((int)end.charAt(1) - '0');
                    //kiem tra toa do dat tau
                    while (player_1.getPatrol_boat_1().check_Ship_position(player_1.getMatrix(), len, 2) == 0) {
                        begin = random();
                        end = random();
                        if (begin.compareTo(end) > 0) {
                            String temp = begin;
                            begin = end;
                            end = temp;
                        }
                        //cap nhap toa do cua tau tuan tra 1
                        //diem dau
                        player_1.getPatrol_boat_1().setX_begin((int)begin.charAt(0) - 'A' + 1);
                        player_1.getPatrol_boat_1().setY_begin((int)begin.charAt(1) - '0');
                        //diem cuoi
                        player_1.getPatrol_boat_1().setX_end((int)end.charAt(0) - 'A' + 1);
                        player_1.getPatrol_boat_1().setY_end((int)end.charAt(1) - '0');
                    }
                    //thiet lap tau
                    player_1.getPatrol_boat_1().set_up_ship(player_1.getMatrix());
                }
                else {
                    //cap nhap toa do cua tau tuan tra 2
                    //diem dau
                    player_1.getPatrol_boat_2().setX_begin((int)begin.charAt(0) - 'A' + 1);
                    player_1.getPatrol_boat_2().setY_begin((int)begin.charAt(1) - '0');
                    //diem cuoi
                    player_1.getPatrol_boat_2().setX_end((int)end.charAt(0) - 'A' + 1);
                    player_1.getPatrol_boat_2().setY_end((int)end.charAt(1) - '0');
                    //kiem tra toa do dat tau
                    while (player_1.getPatrol_boat_2().check_Ship_position(player_1.getMatrix(), len, 2) == 0) {
                        begin = random();
                        end = random();
                        if (begin.compareTo(end) > 0) {
                            String temp = begin;
                            begin = end;
                            end = temp;
                        }
                        //cap nhap toa do cua tau tuan tra 2
                        //diem dau
                        player_1.getPatrol_boat_2().setX_begin((int)begin.charAt(0) - 'A' + 1);
                        player_1.getPatrol_boat_2().setY_begin((int)begin.charAt(1) - '0');
                        //diem cuoi
                        player_1.getPatrol_boat_2().setX_end((int)end.charAt(0) - 'A' + 1);
                        player_1.getPatrol_boat_2().setY_end((int)end.charAt(1) - '0');
                    }
                    //thiet lap tau
                    player_1.getPatrol_boat_2().set_up_ship(player_1.getMatrix());
                }
            }
            //tau khu truc
            begin = random();
            end = random();
            if (begin.compareTo(end) > 0) {
                String temp = begin;
                begin = end;
                end = temp;
            }
            //cap nhap toa do cua tau
            //diem dau
            player_1.getDestroyer_boat().setX_begin((int)begin.charAt(0) - 'A' + 1);
            player_1.getDestroyer_boat().setY_begin((int)begin.charAt(1) - '0');
            //diem cuoi
            player_1.getDestroyer_boat().setX_end((int)end.charAt(0) - 'A' + 1);
            player_1.getDestroyer_boat().setY_end((int)end.charAt(1) - '0');
            //kiem tra toa do dat tau
            while (player_1.getDestroyer_boat().check_Ship_position(player_1.getMatrix(), len, 4) == 0) {
                begin = random();
                end = random();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                //cap nhap toa do cua tau khu truc
                //diem dau
                player_1.getDestroyer_boat().setX_begin((int)begin.charAt(0) - 'A' + 1);
                player_1.getDestroyer_boat().setY_begin((int)begin.charAt(1) - '0');
                //diem cuoi
                player_1.getDestroyer_boat().setX_end((int)end.charAt(0) - 'A' + 1);
                player_1.getDestroyer_boat().setY_end((int)end.charAt(1) - '0');
            }
            //thiet lap tau
            player_1.getDestroyer_boat().set_up_ship(player_1.getMatrix());
            //tau ngam
            begin = random();
            end = random();
            if (begin.compareTo(end) > 0) {
                String temp = begin;
                begin = end;
                end = temp;
            }
            //cap nhap toa do cua tau
            //diem dau
            player_1.getSubmarine().setX_begin((int)begin.charAt(0) - 'A' + 1);
            player_1.getSubmarine().setY_begin((int)begin.charAt(1) - '0');
            //diem cuoi
            player_1.getSubmarine().setX_end((int)end.charAt(0) - 'A' + 1);
            player_1.getSubmarine().setY_end((int)end.charAt(1) - '0');
            //kiem tra toa do dat tau
            while (player_1.getSubmarine().check_Ship_position(player_1.getMatrix(), len, 3) == 0) {
                begin = random();
                end = random();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                //cap nhap toa do cua tau ngam
                //diem dau
                player_1.getSubmarine().setX_begin((int)begin.charAt(0) - 'A' + 1);
                player_1.getSubmarine().setY_begin((int)begin.charAt(1) - '0');
                //diem cuoi
                player_1.getSubmarine().setX_end((int)end.charAt(0) - 'A' + 1);
                player_1.getSubmarine().setY_end((int)end.charAt(1) - '0');
            }
            //thiet lap tau
            player_1.getSubmarine().set_up_ship(player_1.getMatrix());
            //thiet giap ham
            begin = random();
            end = random();
            if (begin.compareTo(end) > 0) {
                String temp = begin;
                begin = end;
                end = temp;
            }
            //cap nhap toa do cua tau
            //diem dau
            player_1.getBattle_ship().setX_begin((int)begin.charAt(0) - 'A' + 1);
            player_1.getBattle_ship().setY_begin((int)begin.charAt(1) - '0');
            //diem cuoi
            player_1.getBattle_ship().setX_end((int)end.charAt(0) - 'A' + 1);
            player_1.getBattle_ship().setY_end((int)end.charAt(1) - '0');
            //kiem tra toa do dat tau
            while (player_1.getBattle_ship().check_Ship_position(player_1.getMatrix(), len, 5) == 0) {
                begin = random();
                end = random();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                //cap nhap toa do cua thiet giap ham
                //diem dau
                player_1.getBattle_ship().setX_begin((int)begin.charAt(0) - 'A' + 1);
                player_1.getBattle_ship().setY_begin((int)begin.charAt(1) - '0');
                //diem cuoi
                player_1.getBattle_ship().setX_end((int)end.charAt(0) - 'A' + 1);
                player_1.getBattle_ship().setY_end((int)end.charAt(1) - '0');
            }
            //thiet lap tau
            player_1.getBattle_ship().set_up_ship(player_1.getMatrix());
            luot_2 ++;
        }
        else {
            player_2.setPoint(0);
            player_2.setNumber_of_ships_live(5);
            player_2.setNumber_of_ships_destroy(0);
            String begin, end;
            //tau tuan tra
            for (int i = 0; i < 2; i ++) {
                begin = random();
                end = random();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                if (i == 0) {
                    //cap nhap toa do cua tau tuan tra 1
                    //diem dau
                    player_2.getPatrol_boat_1().setX_begin((int)begin.charAt(0) - 'A' + 1);
                    player_2.getPatrol_boat_1().setY_begin((int)begin.charAt(1) - '0');
                    //diem cuoi
                    player_2.getPatrol_boat_1().setX_end((int)end.charAt(0) - 'A' + 1);
                    player_2.getPatrol_boat_1().setY_end((int)end.charAt(1) - '0');
                    //kiem tra toa do dat tau
                    while (player_2.getPatrol_boat_1().check_Ship_position(player_2.getMatrix(), len, 2) == 0) {
                        begin = random();
                        end = random();
                        if (begin.compareTo(end) > 0) {
                            String temp = begin;
                            begin = end;
                            end = temp;
                        }
                        //cap nhap toa do cua tau tuan tra 1
                        //diem dau
                        player_2.getPatrol_boat_1().setX_begin((int)begin.charAt(0) - 'A' + 1);
                        player_2.getPatrol_boat_1().setY_begin((int)begin.charAt(1) - '0');
                        //diem cuoi
                        player_2.getPatrol_boat_1().setX_end((int)end.charAt(0) - 'A' + 1);
                        player_2.getPatrol_boat_1().setY_end((int)end.charAt(1) - '0');
                    }
                    //thiet lap tau
                    player_2.getPatrol_boat_1().set_up_ship(player_2.getMatrix());
                }
                else {
                    //cap nhap toa do cua tau tuan tra 2
                    //diem dau
                    player_2.getPatrol_boat_2().setX_begin((int)begin.charAt(0) - 'A' + 1);
                    player_2.getPatrol_boat_2().setY_begin((int)begin.charAt(1) - '0');
                    //diem cuoi
                    player_2.getPatrol_boat_2().setX_end((int)end.charAt(0) - 'A' + 1);
                    player_2.getPatrol_boat_2().setY_end((int)end.charAt(1) - '0');
                    //kiem tra toa do dat tau
                    while (player_2.getPatrol_boat_2().check_Ship_position(player_2.getMatrix(), len, 2) == 0) {
                        begin = random();
                        end = random();
                        if (begin.compareTo(end) > 0) {
                            String temp = begin;
                            begin = end;
                            end = temp;
                        }
                        //cap nhap toa do cua tau tuan tra 2
                        //diem dau
                        player_2.getPatrol_boat_2().setX_begin((int)begin.charAt(0) - 'A' + 1);
                        player_2.getPatrol_boat_2().setY_begin((int)begin.charAt(1) - '0');
                        //diem cuoi
                        player_2.getPatrol_boat_2().setX_end((int)end.charAt(0) - 'A' + 1);
                        player_2.getPatrol_boat_2().setY_end((int)end.charAt(1) - '0');
                    }
                    //thiet lap tau
                    player_2.getPatrol_boat_2().set_up_ship(player_2.getMatrix());
                }
            }
            //tau khu truc
            begin = random();
            end = random();
            if (begin.compareTo(end) > 0) {
                String temp = begin;
                begin = end;
                end = temp;
            }
            //cap nhap toa do cua tau
            //diem dau
            player_2.getDestroyer_boat().setX_begin((int)begin.charAt(0) - 'A' + 1);
            player_2.getDestroyer_boat().setY_begin((int)begin.charAt(1) - '0');
            //diem cuoi
            player_2.getDestroyer_boat().setX_end((int)end.charAt(0) - 'A' + 1);
            player_2.getDestroyer_boat().setY_end((int)end.charAt(1) - '0');
            //kiem tra toa do dat tau
            while (player_2.getDestroyer_boat().check_Ship_position(player_2.getMatrix(), len, 4) == 0) {
                begin = random();
                end = random();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                //cap nhap toa do cua tau khu truc
                //diem dau
                player_2.getDestroyer_boat().setX_begin((int)begin.charAt(0) - 'A' + 1);
                player_2.getDestroyer_boat().setY_begin((int)begin.charAt(1) - '0');
                //diem cuoi
                player_2.getDestroyer_boat().setX_end((int)end.charAt(0) - 'A' + 1);
                player_2.getDestroyer_boat().setY_end((int)end.charAt(1) - '0');
            }
            //thiet lap tau
            player_2.getDestroyer_boat().set_up_ship(player_2.getMatrix());
            //tau ngam
            begin = random();
            end = random();
            if (begin.compareTo(end) > 0) {
                String temp = begin;
                begin = end;
                end = temp;
            }
            //cap nhap toa do cua tau
            //diem dau
            player_2.getSubmarine().setX_begin((int)begin.charAt(0) - 'A' + 1);
            player_2.getSubmarine().setY_begin((int)begin.charAt(1) - '0');
            //diem cuoi
            player_2.getSubmarine().setX_end((int)end.charAt(0) - 'A' + 1);
            player_2.getSubmarine().setY_end((int)end.charAt(1) - '0');
            //kiem tra toa do dat tau
            while (player_2.getSubmarine().check_Ship_position(player_2.getMatrix(), len, 3) == 0) {
                begin = random();
                end = random();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                //cap nhap toa do cua tau ngam
                //diem dau
                player_2.getSubmarine().setX_begin((int)begin.charAt(0) - 'A' + 1);
                player_2.getSubmarine().setY_begin((int)begin.charAt(1) - '0');
                //diem cuoi
                player_2.getSubmarine().setX_end((int)end.charAt(0) - 'A' + 1);
                player_2.getSubmarine().setY_end((int)end.charAt(1) - '0');
            }
            //thiet lap tau
            player_2.getSubmarine().set_up_ship(player_2.getMatrix());
            //thiet giap ham
            begin = random();
            end = random();
            if (begin.compareTo(end) > 0) {
                String temp = begin;
                begin = end;
                end = temp;
            }
            //cap nhap toa do cua tau
            //diem dau
            player_2.getBattle_ship().setX_begin((int)begin.charAt(0) - 'A' + 1);
            player_2.getBattle_ship().setY_begin((int)begin.charAt(1) - '0');
            //diem cuoi
            player_2.getBattle_ship().setX_end((int)end.charAt(0) - 'A' + 1);
            player_2.getBattle_ship().setY_end((int)end.charAt(1) - '0');
            //kiem tra toa do dat tau
            while (player_2.getBattle_ship().check_Ship_position(player_2.getMatrix(), len, 5) == 0) {
                begin = random();
                end = random();
                if (begin.compareTo(end) > 0) {
                    String temp = begin;
                    begin = end;
                    end = temp;
                }
                //cap nhap toa do cua thiet giap ham
                //diem dau
                player_2.getBattle_ship().setX_begin((int)begin.charAt(0) - 'A' + 1);
                player_2.getBattle_ship().setY_begin((int)begin.charAt(1) - '0');
                //diem cuoi
                player_2.getBattle_ship().setX_end((int)end.charAt(0) - 'A' + 1);
                player_2.getBattle_ship().setY_end((int)end.charAt(1) - '0');
            }
            //thiet lap tau
            player_2.getBattle_ship().set_up_ship(player_2.getMatrix());
        }
    }

    void Start_game(){
        Scanner sc  = new Scanner(System.in);
        int luot = 1, lan_dau_1 = 0, lan_dau_2 = 0, win_player = 0;
        //cap nhap kich thuong mat tran cua 2 nguoi choi
        player_1.getBoard().setChieu_cao(len * 2 + 1);
        player_1.getBoard().setChieu_rong(len * 4 + 1);
        player_2.getBoard().setChieu_cao(len * 2 + 1);
        player_2.getBoard().setChieu_rong(len * 4 + 1);
        player_1.set_up();
        player_2.set_up();
        while (true){
            if (luot == 1){
                clrscr();
                if (level == 1 || (level == 2 && (lan_dau_1 != 0  || lan_dau_1 == 0) ))  System.out.println("---------------------Luot choi cua nguoi so 1: " + player_1.getName() + "---------------------");
                //Vong chuan bi cua nguoi choi 1
                if (lan_dau_1 == 0) {
                    if (level == 1) {
                        System.out.println("Nhap ten cua ban (khong nhap ten co chua khoang trang):");
                        String name = sc.next();
                        player_1.setName(name);
                        Level_1();
                    }
                    else {
                        System.out.println("Nhap ten cua ban (khong nhap ten co chua khoang trang):");
                        String name = sc.next();
                        player_1.setName(name);
                        Level_2();
                    }
                    lan_dau_1 ++;
                }
                //Vong chien dau
                else {
                    player_1.setNumber_of_ships_live(5 - player_2.getNumber_of_ships_destroy());
                    player_1.setNumber_of_ships_destroy(5 - player_2.check_Number_Ship());
                    System.out.println("So o da ban o mat tran dich: " + player_1.getPoint());
                    System.out.println("So tau ban da pha cua dich: " + player_1.getNumber_of_ships_destroy());
                    System.out.println("So tau con lai cua ban: " + player_1.getNumber_of_ships_live());
                    player_1.show_board_of_player();
                    while (true) {
                        System.out.print("Moi lua chon chuc nang:\n" + "1. Attack\n" + "2. Xem mat tran dich dang suong mu\n");
                        int cn = sc.nextInt();
                        if (cn == 1) {
                            System.out.print("Nhap toa do X Y can ban:\n");
                            char c = sc.next().charAt(0);
                            int x = c - 'A' + 1, y = sc.nextInt();
                            //Nhap lai toa do vi bi sai
                            while (Check_position(x, y) == 0 || player_2.getMatrix()[x][y] == 'o' || player_2.getMatrix()[x][y] == 'x') {
                                System.out.println("Vui long nhap lai!");
                                x = sc.nextInt();
                                y = sc.nextInt();
                            }
                            int kt = 0;
                            //kien tra xem co danh trung bo phan tau khong
                            if (player_2.getMatrix()[x][y] == '#') kt = 1;
                            //danh dau vi tri vua danh la o neu la trung tau, la x neu khong trung tau
                            if (kt == 1) {
                                System.out.println("Ban da ban trung!");
                                player_2.getMatrix()[x][y] = 'o';
                                //kiem tra xem co win chua
                                if (Check_win(player_2.getMatrix()) == 1) {
                                    win_player = 1;
                                    break;
                                }
                            }
                            else {
                                player_2.getMatrix()[x][y] = 'x';
                                break;
                            }
                            //tang so o da ban o ben mat tran dich
                            player_1.setPoint(player_1.getPoint() + 1);
                            player_1.setNumber_of_ships_live(5 - player_2.getNumber_of_ships_destroy());
                            player_1.setNumber_of_ships_destroy(5 - player_2.check_Number_Ship());
                        }
                        else {
                            player_2.show_board_lite();
                            System.out.println("An 1 neu khong muon xem nua!");
                            int k = sc.nextInt();
                            while (k != 1) {
                                System.out.println("Vui long nhap dung so 1!");
                                k = sc.nextInt();
                            }
                            if (k == 1) break;
                        }
                    }
                    if (win_player == 1) {
                        break;
                    }
                }
                luot = 0;
            }
            else{
                clrscr();
                if (level == 1 || (level == 2 && (lan_dau_2 != 0 || lan_dau_2 == 0))) System.out.println("---------------------Luot choi cua nguoi so 2: "  + player_2.getName() + "---------------------");
                //Vong chuan bi cua nguoi choi 2
                if (lan_dau_2 == 0) {
                    if (level == 1) {
                        System.out.println("Nhap ten cua ban (khong nhap ten co chua khoang trang):");
                        String name = sc.next();
                        player_2.setName(name);
                        Level_1();
                    }
                    else {
                        System.out.println("Nhap ten cua ban (khong nhap ten co chua khoang trang):");
                        String name = sc.next();
                        player_2.setName(name);
                        Level_2();
                    }
                    lan_dau_2++;
                }
                //Vong chien dau
                else {
                    player_2.setNumber_of_ships_live(5 - player_1.getNumber_of_ships_destroy());
                    player_2.setNumber_of_ships_destroy(5 - player_1.check_Number_Ship());
                    System.out.println("So o da ban o mat tran dich: " + player_2.getPoint());
                    System.out.println("So tau ban da pha cua dich: " + player_2.getNumber_of_ships_destroy());
                    System.out.println("So tau con lai cua ban: " + player_2.getNumber_of_ships_live());
                    player_2.show_board_of_player();
                    while (true) {
                        System.out.print("Moi lua chon chuc nang:\n" + "1. Attack\n" + "2. Xem mat tran dich dang suong mu\n");
                        int cn = sc.nextInt();
                        if (cn == 1) {
                            System.out.print("Nhap toa do X Y can ban:\n");
                            char c = sc.next().charAt(0);
                            int x = c - 'A' + 1, y = sc.nextInt();
                            //Nhap lai toa do vi bi sai
                            while (Check_position(x, y) == 0 || player_1.getMatrix()[x][y] == 'o' || player_1.getMatrix()[x][y] == 'x') {
                                System.out.println("Vui long nhap lai!");
                                x = sc.nextInt();
                                y = sc.nextInt();
                            }
                            int kt = 0;
                            //kien tra xem co danh trung bo phan tau khong
                            if (player_1.getMatrix()[x][y] == '#') kt = 1;
                            //danh dau vi tri vua danh la o neu la trung tau, la x neu khong trung tau
                            if (kt == 1) {
                                System.out.println("Ban da ban trung!");
                                player_1.getMatrix()[x][y] = 'o';
                                //kiem tra xem co win chua
                                if (Check_win(player_1.getMatrix()) == 1) {
                                    win_player = 2;
                                    break;
                                }

                            }
                            else {
                                player_1.getMatrix()[x][y] = 'x';
                                break;
                            }
                            //tang so o da ban o ben mat tran dich
                            player_2.setPoint(player_2.getPoint() + 1);
                            player_2.setNumber_of_ships_live(5 - player_1.getNumber_of_ships_destroy());
                            player_2.setNumber_of_ships_destroy(5 - player_1.check_Number_Ship());
                        }
                        else {
                            player_1.show_board_lite();
                            System.out.println("An 1 neu khong muon xem nua!");
                            int k = sc.nextInt();
                            while (k != 1) {
                                System.out.println("Vui long nhap dung so 1!");
                                k = sc.nextInt();
                            }
                            if (k == 1) break;
                        }
                    }
                    if (win_player == 2) {
                        break;
                    }
                }
                luot = 1;
            }
        }
        if (win_player == 1) {
            System.out.println("Nguoi choi 1 da chien thang!");
            ranking.add(new Ranking(player_1.getName(), player_1.getPoint(), player_1.getNumber_of_ships_live()));
        }
        else if (win_player == 2) {
            System.out.println("Nguoi choi 2 da chien thang!");
            ranking.add(new Ranking(player_2.getName(), player_2.getPoint(), player_2.getNumber_of_ships_live()));
        }
        Collections.sort(ranking, Comparator.comparing(Ranking::getAttackCount).thenComparing(Ranking::getShip_live).thenComparing(Ranking::getPlayerName));
    }

    void Huong_dan(){
        System.out.println("Se co 2 muc do choi: \n" + "+Level 1: Nguoi choi duoc dat thuyen/tau\n" + "+Level 2: Thuyen se duoc dat ngau nhien");
        System.out.println("Se co 2 loai bang: \n" + "+ 10 x 10\n" + "+ 20 x 20");
        System.out.println("Nguoi choi vao phan Setting de chon cac chuc nang/che do tren");

        System.out.println("Moi nguoi choi so huu 5 con thuyen nhu sau:\n" +
                "\n" +
                "+ 2 Thuyen Tuan Tra (Patrol Boat) 1x2\n" +
                "+ 1 Tau Khu Truc (Destroyer Boat) 1x4\n" +
                "+ 1 Tau Ngam (Submarine) 1x3\n" +
                " + 1 Thiet Giap Ham (Battle Ship) 1x5\n" + "Nguoi choi se co the nhap vao 2 toa do (X,Y) voi tung mau thuyen de dat thuyen, man hinh se hien thi thuyen len bang. Sau khi dat xong het, se sang luot dat cua nguoi kia.\n" +
                "Trong Game\n" +
                "-Trong tro choi, luot cua hai nguoi choi se luan phien nhau. Trong luot cua 1 nguoi, co the chon cac lua chon sau:\n" +
                "-Khi menu lua chon hien thi , hien thi luon tinh hinh hien tai bao gom : so o da ban o mat tran dich, so tau da pha, so tau con lai cua ban than.\n" +
                "-Xem bang, cach dat thuyen cua ban than (Neu hai nguoi cung choi tren 1 may tinh, luc nay  se tam quay may tinh di de xem cuc dien hien tai)\n" +
                "-Dat lenh khai hoa,  nhap 1 toa do X,Y (vi du 1 4) de tien hanh khai hoa vao mat tran doi phuong, neu trung bat ki "
                + "\ndiem nao cua thuyen thi se co thong bao va duoc ban tiep, con khong trung thi thoi. Nguoi choi co the xem duoc mat tran cua doi "
                + "\nphuong o dang suong mu (tuc la hien thi nhung diem nao da bi ban vao roi, bao gom diem nao da ban vao nhung khong co gi, diem nao da ban "
                + "\nvao nhung co mot phan tau o do, ...) cac o khac thi khong hien thi thong tin gi\n" +
                "-Ket thuc luot\n" + " Luat tro choi\n" +
                "-1 tau se bi pha huy chi sau khi toan bo diem bi pha het. vi du tau 1x5 thi it nhat 5 phat ban trung dich toan bo de pha\n" +
                "-Khi ben nao bi pha het tau trưoc lap tuc thua cuoc va - hien thi man hinh ket qua, bang cua ca 2 ben\n");
        System.out.println("*Note:\n" + "'o' la ban trung tau cua dich\n" + "'x' là chua ban trung tau cua dich\n" + "'#' la tau");
        return;
    }

    void Display(){
        System.out.println("-----Sea Battle-----");
        System.out.println("|   1.Star game    |");
        System.out.println("|   2.Setting game |");
        System.out.println("|   3.Rank         |");
        System.out.println("|   4.Huong dan    |");
        System.out.println("|   5.Thoat game   |");
        System.out.println("--------------------");
    }
}
