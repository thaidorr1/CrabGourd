package crabgourd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BauCuaGUI extends JFrame {
    private player player;
    private CrabGourd crab;
    private JLabel balanceLabel;
    private JTextArea resultArea;
    private JButton[] animalButtons;
    private ArrayList<String> selected ;

    public BauCuaGUI(player player) {
        this.player = player;
        this.crab = new CrabGourd();

        setTitle("Bầu Cua Đón Tết");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        selected = new ArrayList <> () ;
        createUI();
        
        setVisible(true);
    }
    private void selectedAnimals (String animal) {
        if (selected.contains(animal)) {
            selected.remove(animal) ;
        }
        else if (selected.size() < 3) {
            selected.add(animal) ;
        }
        else {
            JOptionPane.showMessageDialog(this, "chỉ được chọn tối đa 3 con vật.");
        }
    }
    
    private void createUI() {
        // tiêu đề và số dư
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Bầu Cua", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        balanceLabel = new JLabel("Số dư: " + player.getBalance());
        northPanel.add(titleLabel, BorderLayout.CENTER);
        northPanel.add(balanceLabel, BorderLayout.EAST);
        add(northPanel, BorderLayout.NORTH);

        // kết quả
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // các nút đặt cược
        JPanel southPanel = new JPanel(new GridLayout(2, 3));
        String[] animals = {"Nai", "Bầu", "Cua", "Cá", "Gà", "Tôm"};
        animalButtons = new JButton[6];

        for (int i = 0; i < animals.length; i++) {
            String animal = animals[i];
            animalButtons[i] = new JButton(animal);
            animalButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    placeBet(animal);
                }
            });
            southPanel.add(animalButtons[i]);
        }
        add(southPanel, BorderLayout.SOUTH);
    }

    private void placeBet(String animal) {
        String betAmountStr = JOptionPane.showInputDialog(this, "nhập số tiền cược cho " + animal + ":", "0");
        try {
            // kiểm tra số tiền đặt cược có đúng hay kh
            double betAmount = Double.parseDouble(betAmountStr);
            if (betAmount <= 0 || !player.deductBalance(betAmount)) {
                JOptionPane.showMessageDialog(this, "số tiền cược không hợp lệ hoặc không đủ số dư.");
                return;
            }
            // lắc kết quả
            crab.rollDice();
            String[] results = crab.getResults();
            // tính tiền thắng
            int winnings = crab.calculateWinnings(animal, betAmount);

            StringBuilder resultText = new StringBuilder("kết quả: ");
            for (String result : results) {
                resultText.append(result).append(" ");
            }
            if (winnings > 0) {
                double totalWinnings = betAmount * winnings;
                player.addbalance(totalWinnings);
                resultText.append("\nbạn thắng! tổng cộng: ").append(totalWinnings);
            } else {
                resultText.append("\nbạn thua");
            }

            resultArea.setText(resultText.toString());
            balanceLabel.setText("số dư: " + player.getBalance());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "vui lòng nhập số.");
        }
    }
    
    public static void main(String[] args) {
        String playerName = JOptionPane.showInputDialog("Nhập tên người chơi:");
        if (playerName == null || playerName.isEmpty()) {
            playerName = "người chơi mặc định";
        }
        try {
            double initialBalance = Double.parseDouble(JOptionPane.showInputDialog("Nhập số tiền ban đầu:"));
            if (initialBalance < 0) {
                JOptionPane.showMessageDialog(null, "Số tiền không hợp lệ. Vui lòng nhập số dương.");
                return;
            }
            player player = new player(playerName, initialBalance);
            SwingUtilities.invokeLater(() -> new BauCuaGUI(player));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Định dạng số không hợp lệ. Vui lòng nhập số.");
        }
    }
}