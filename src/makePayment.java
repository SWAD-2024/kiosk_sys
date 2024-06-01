package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;

public class makePayment extends JFrame {

    private int totalPrice;
    private static AtomicInteger waitingNumber = new AtomicInteger(1);

    public makePayment(int totalPrice) {
        this.totalPrice = totalPrice;
        setTitle("결제 화면");
        setSize(300, 200);
        setLayout(new BorderLayout());

        // 총 금액 표시
        JLabel priceLabel = new JLabel("총 결제 금액: " + totalPrice + "원", JLabel.CENTER);
        priceLabel.setFont(new Font("Serif", Font.BOLD, 16));
        add(priceLabel, BorderLayout.NORTH);

        // 카드 결제 버튼
        JButton cardPaymentButton = new JButton("카드 결제");
        cardPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });
        add(cardPaymentButton, BorderLayout.CENTER);

        setVisible(true);
    }

    private void processPayment() {
        // 결제 처리 로직
        int currentWaitingNumber = generateWaitingNumber();
        JOptionPane.showMessageDialog(this, "결제가 완료되었습니다.\n대기번호: " + currentWaitingNumber);
        dispose();
    }

    private int generateWaitingNumber() {
        return waitingNumber.getAndIncrement();
    }
}
