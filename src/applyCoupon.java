package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class applyCoupon extends JFrame {

    private selectMenu parent;
    private JTextField couponField;
    private JLabel resultLabel;

    public applyCoupon(selectMenu parent) {
        this.parent = parent;
        setTitle("쿠폰 적용");
        setSize(300, 150);
        setLayout(new BorderLayout());

        // 쿠폰 입력 패널 생성
        JPanel couponPanel = new JPanel();
        couponPanel.setLayout(new FlowLayout());

        JLabel couponLabel = new JLabel("쿠폰 코드: ");
        couponPanel.add(couponLabel);

        couponField = new JTextField(10);
        couponPanel.add(couponField);

        JButton applyButton = new JButton("적용");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyCouponCode();
            }
        });
        couponPanel.add(applyButton);

        add(couponPanel, BorderLayout.CENTER);

        resultLabel = new JLabel("", JLabel.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void applyCouponCode() {
        String couponCode = couponField.getText().trim();

        // 예시: 쿠폰 코드에 따라 할인을 적용
        if (couponCode.equals("DISCOUNT10")) {
            parent.applyDiscount(10);
            resultLabel.setText("쿠폰이 적용되었습니다: 10% 할인");
        } else if (couponCode.equals("DISCOUNT20")) {
            parent.applyDiscount(20);
            resultLabel.setText("쿠폰이 적용되었습니다: 20% 할인");
        } else {
            resultLabel.setText("유효하지 않은 쿠폰 코드입니다.");
        }
    }
}
