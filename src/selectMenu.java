package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class selectMenu extends JFrame implements ActionListener {

    private String selectedOption;
    private JPanel menuPanel;
    private JPanel cartPanel;
    private JPanel cartItemsPanel;
    private JLabel timerLabel;
    private Timer timer;
    private int timeRemaining = 200;
    private Map<String, Integer> menuPrices;
    private JLabel totalPriceLabel;
    private int totalPrice = 0;

    public selectMenu(String selectedOption) {
        this.selectedOption = selectedOption;

        // 메뉴와 가격 데이터 초기화
        menuPrices = new HashMap<>();
        menuPrices.put("불고기버거세트", 7000);
        menuPrices.put("치즈버거세트", 6500);
        menuPrices.put("새우버거세트", 6800);
        menuPrices.put("치킨버거세트", 7000);
        menuPrices.put("불고기버거", 4000);
        menuPrices.put("치즈버거", 3500);
        menuPrices.put("새우버거", 3800);
        menuPrices.put("치킨버거", 4000);
        menuPrices.put("콜라", 1500);
        menuPrices.put("사이다", 1500);
        menuPrices.put("밀크쉐이크", 2500);
        menuPrices.put("치킨너겟", 3000);
        menuPrices.put("치즈스틱", 2500);
        menuPrices.put("양념감자", 2000);

        // 창의 제목 설정
        setTitle("메뉴 선택 화면");

        // 창의 크기 설정
        setSize(400, 800);

        // 창 닫기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 레이아웃 설정
        setLayout(new BorderLayout());

        // 상단 카테고리 패널 생성
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridLayout(1, 5));

        // 카테고리 버튼 생성
        String[] categories = {"인기메뉴", "세트", "단품", "음료", "사이드"};
        for (String category : categories) {
            JButton button = new JButton(category);
            button.addActionListener(this);
            categoryPanel.add(button);
        }

        // 카테고리 패널을 상단에 추가
        add(categoryPanel, BorderLayout.NORTH);

        // 메뉴 패널 생성 및 중앙에 추가
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1));
        add(menuPanel, BorderLayout.CENTER);

        // 초기 메뉴를 인기메뉴로 설정
        showPopularMenu();

        // 하단 패널 생성
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        // 장바구니 패널 생성
        cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout());
        cartPanel.setPreferredSize(new Dimension(600, 300));

        cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        JScrollPane cartScrollPane = new JScrollPane(cartItemsPanel);
        cartScrollPane.setPreferredSize(new Dimension(600, 300));
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        // 결제 및 타이머 패널 생성
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new BorderLayout());

        // 총 금액 라벨 추가
        totalPriceLabel = new JLabel("총 금액: 0원", JLabel.CENTER);
        paymentPanel.add(totalPriceLabel, BorderLayout.NORTH);

        // 타이머 라벨 추가
        timerLabel = new JLabel("남은 시간: " + timeRemaining + " 초", JLabel.CENTER);
        paymentPanel.add(timerLabel, BorderLayout.CENTER);

        // 결제 버튼 추가
        JButton paymentButton = new JButton("결제");
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCartSummary();
            }
        });
        paymentPanel.add(paymentButton, BorderLayout.SOUTH);

        // 하단 패널에 장바구니와 결제 패널 추가
        bottomPanel.add(cartPanel, BorderLayout.CENTER);
        bottomPanel.add(paymentPanel, BorderLayout.SOUTH);

        // 하단 패널을 창에 추가
        add(bottomPanel, BorderLayout.SOUTH);

        // 타이머 시작
        startTimer();

        // 창을 화면에 표시
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String command = source.getText();

        switch (command) {
            case "인기메뉴":
                showPopularMenu();
                break;
            case "세트":
                showSetMenu();
                break;
            case "단품":
                showSingleMenu();
                break;
            case "음료":
                showDrinkMenu();
                break;
            case "사이드":
                showSideMenu();
                break;
            default:
                if (menuPrices.containsKey(command)) {
                    if (command.contains("세트")) {
                        showDetailOptions(command, true, true);
                    } else if (command.contains("버거")) {
                        showDetailOptions(command, true, false);
                    } else {
                        addItemToCart(command, menuPrices.get(command));
                    }
                }
                break;
        }
    }

    private void showPopularMenu() {
        menuPanel.removeAll();
        String[] popularMenus = {"불고기버거세트", "치즈버거세트", "새우버거세트"};
        addMenuButtons(popularMenus);
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private void showSetMenu() {
        menuPanel.removeAll();
        String[] setMenus = {"불고기버거세트", "치즈버거세트", "새우버거세트", "치킨버거세트"};
        addMenuButtons(setMenus);
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private void showSingleMenu() {
        menuPanel.removeAll();
        String[] singleMenus = {"불고기버거", "치즈버거", "새우버거", "치킨버거"};
        addMenuButtons(singleMenus);
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private void showDrinkMenu() {
        menuPanel.removeAll();
        String[] drinkMenus = {"콜라", "사이다", "밀크쉐이크"};
        addMenuButtons(drinkMenus);
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private void showSideMenu() {
        menuPanel.removeAll();
        String[] sideMenus = {"치킨너겟", "치즈스틱", "양념감자"};
        addMenuButtons(sideMenus);
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private void addMenuButtons(String[] menuItems) {
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.addActionListener(this);
            menuPanel.add(button);
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("남은 시간 : " + timeRemaining + " 초");
                if (timeRemaining <= 0) {
                    timer.stop();
                    restartApplication();
                }
            }
        });
        timer.start();
    }

    private void restartApplication() {
        // 현재 창 닫기
        this.dispose();

        // src.startMenuSelect 창 열기
        new startMenuSelect();
    }

    private void showDetailOptions(String selectedItem, boolean showToppings, boolean showDrinks) {
        JFrame detailFrame = new JFrame(selectedItem + " 옵션 선택");
        detailFrame.setSize(400, 600);
        detailFrame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel toppingPanel = new JPanel();
        toppingPanel.setLayout(new GridLayout(5, 1));
        if (showToppings) {
            JLabel toppingLabel = new JLabel("버거 토핑 추가");
            toppingPanel.add(toppingLabel);

            JCheckBox extraCheese = new JCheckBox("치즈 추가 (+400원)");
            JCheckBox extraTomato = new JCheckBox("토마토 추가 (+400원)");
            JCheckBox extraBacon = new JCheckBox("베이컨 추가 (+400원)");
            JCheckBox extraPatty = new JCheckBox("패티 추가 (+2000원)");

            toppingPanel.add(extraCheese);
            toppingPanel.add(extraTomato);
            toppingPanel.add(extraBacon);
            toppingPanel.add(extraPatty);
        }

        JPanel drinkPanel = new JPanel();
        drinkPanel.setLayout(new BorderLayout());
        if (showDrinks) {
            JLabel drinkLabel = new JLabel("세트 음료 변경 (무료)");
            drinkPanel.add(drinkLabel, BorderLayout.NORTH);

            JPanel drinkOptionsPanel = new JPanel();
            drinkOptionsPanel.setLayout(new GridLayout(1, 5));

            JRadioButton cola = new JRadioButton("콜라", true);
            JRadioButton sprite = new JRadioButton("사이다");
            JRadioButton zeroCola = new JRadioButton("제로콜라");
            JRadioButton zeroSprite = new JRadioButton("제로사이다");
            JRadioButton fanta = new JRadioButton("환타");

            ButtonGroup drinkGroup = new ButtonGroup();
            drinkGroup.add(cola);
            drinkGroup.add(sprite);
            drinkGroup.add(zeroCola);
            drinkGroup.add(zeroSprite);
            drinkGroup.add(fanta);

            drinkOptionsPanel.add(cola);
            drinkOptionsPanel.add(sprite);
            drinkOptionsPanel.add(zeroCola);
            drinkOptionsPanel.add(zeroSprite);
            drinkOptionsPanel.add(fanta);

            drinkPanel.add(drinkOptionsPanel, BorderLayout.CENTER);
        }

        mainPanel.add(toppingPanel, BorderLayout.NORTH);
        mainPanel.add(drinkPanel, BorderLayout.SOUTH);

        detailFrame.add(mainPanel, BorderLayout.CENTER);

        JButton addButton = new JButton("장바구니에 추가");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int price = menuPrices.get(selectedItem);
                StringBuilder itemDescription = new StringBuilder(selectedItem);

                for (Component comp : toppingPanel.getComponents()) {
                    if (comp instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) comp;
                        if (checkBox.isSelected()) {
                            String text = checkBox.getText();
                            itemDescription.append(" + ").append(text.split(" ")[0]);
                            price += Integer.parseInt(text.replaceAll("[^0-9]", ""));
                        }
                    }
                }

                if (showDrinks) {
                    for (Component comp : drinkPanel.getComponents()) {
                        if (comp instanceof JPanel) {
                            JPanel drinkOptionsPanel = (JPanel) comp;
                            for (Component drinkComp : drinkOptionsPanel.getComponents()) {
                                if (drinkComp instanceof JRadioButton) {
                                    JRadioButton radioButton = (JRadioButton) drinkComp;
                                    if (radioButton.isSelected()) {
                                        itemDescription.append(" + ").append(radioButton.getText());
                                    }
                                }
                            }
                        }
                    }
                }

                addItemToCart(itemDescription.toString(), price);
                detailFrame.dispose();
            }
        });

        detailFrame.add(addButton, BorderLayout.SOUTH);

        detailFrame.setVisible(true);
    }

    private void addItemToCart(String itemDescription, int price) {
        boolean found = false;
        for (int i = 0; i < cartItemsPanel.getComponentCount(); i++) {
            CartItemPanel cartItemPanel = (CartItemPanel) cartItemsPanel.getComponent(i);
            if (cartItemPanel.getItemDescription().equals(itemDescription)) {
                cartItemPanel.incrementQuantity();
                found = true;
                break;
            }
        }

        if (!found) {
            cartItemsPanel.add(new CartItemPanel(itemDescription, price));
        }

        updateTotalPrice();
    }

    private void updateTotalPrice() {
        totalPrice = 0;
        for (Component comp : cartItemsPanel.getComponents()) {
            CartItemPanel cartItemPanel = (CartItemPanel) comp;
            totalPrice += cartItemPanel.getTotalPrice();
        }
        updateTotalPriceLabel();
    }

    public void applyDiscount(int percentage) {
        int discountAmount = totalPrice * percentage / 100;
        totalPrice -= discountAmount;
        updateTotalPriceLabel();
    }

    private void updateTotalPriceLabel() {
        totalPriceLabel.setText("총 금액: " + totalPrice + "원");
    }

    private void showCartSummary() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        for (Component comp : cartItemsPanel.getComponents()) {
            CartItemPanel cartItemPanel = (CartItemPanel) comp;
            summaryPanel.add(new JLabel(cartItemPanel.getItemDescription() + " - " + cartItemPanel.getTotalPrice() + "원"));
        }

        add(new JScrollPane(summaryPanel), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        JLabel totalSummaryPriceLabel = new JLabel(totalPriceLabel.getText(), JLabel.CENTER);
        buttonPanel.add(totalSummaryPriceLabel);

        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                setLayout(new BorderLayout());

                // 카테고리 패널 다시 생성
                JPanel categoryPanel = new JPanel();
                categoryPanel.setLayout(new GridLayout(1, 5));
                String[] categories = {"인기메뉴", "세트", "단품", "음료", "사이드"};
                for (String category : categories) {
                    JButton button = new JButton(category);
                    button.addActionListener(selectMenu.this);
                    categoryPanel.add(button);
                }
                add(categoryPanel, BorderLayout.NORTH);

                // 메뉴 패널 다시 생성
                add(menuPanel, BorderLayout.CENTER);

                // 하단 패널 다시 생성
                JPanel bottomPanel = new JPanel();
                bottomPanel.setLayout(new BorderLayout());

                JPanel cartPanel = new JPanel();
                cartPanel.setLayout(new BorderLayout());
                cartPanel.add(new JScrollPane(cartItemsPanel), BorderLayout.CENTER);

                JPanel paymentPanel = new JPanel();
                paymentPanel.setLayout(new BorderLayout());

                paymentPanel.add(totalPriceLabel, BorderLayout.NORTH);
                paymentPanel.add(timerLabel, BorderLayout.CENTER);

                JButton paymentButton = new JButton("결제");
                paymentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showCartSummary();
                    }
                });
                paymentPanel.add(paymentButton, BorderLayout.SOUTH);

                bottomPanel.add(cartPanel, BorderLayout.CENTER);
                bottomPanel.add(paymentPanel, BorderLayout.SOUTH);

                add(bottomPanel, BorderLayout.SOUTH);

                revalidate();
                repaint();
            }
        });

        JButton applyCouponButton = new JButton("쿠폰 적용");
        applyCouponButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new applyCoupon(selectMenu.this);
            }
        });

        JButton confirmPaymentButton = new JButton("결제");
        confirmPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new makePayment(totalPrice);
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(applyCouponButton);
        buttonPanel.add(confirmPaymentButton);

        add(buttonPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
//새로 추가한 패널 (그,, +,- 수량용)
    private class CartItemPanel extends JPanel {
        private JLabel itemLabel;
        private JButton plusButton;
        private JButton minusButton;
        private JButton removeButton;
        private int quantity;
        private int price;
        private String itemDescription;

        public CartItemPanel(String itemDescription, int price) {
            this.itemDescription = itemDescription;
            this.price = price;
            this.quantity = 1;

            setLayout(new BorderLayout());

            itemLabel = new JLabel(getItemDescription());
            plusButton = new JButton("+");
            minusButton = new JButton("-");
            removeButton = new JButton("삭제");

            plusButton.addActionListener(e -> {
                incrementQuantity();
                updateCart();
            });

            minusButton.addActionListener(e -> {
                decrementQuantity();
                updateCart();
            });

            removeButton.addActionListener(e -> {
                cartItemsPanel.remove(this);
                updateCart();
            });

            JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
            buttonPanel.add(minusButton);
            buttonPanel.add(plusButton);
            buttonPanel.add(removeButton);

            add(itemLabel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.EAST);
        }

        public String getItemDescription() {
            return itemDescription + " x" + quantity;
        }

        public int getTotalPrice() {
            return quantity * price;
        }

        public void incrementQuantity() {
            quantity++;
            itemLabel.setText(getItemDescription());
        }

        public void decrementQuantity() {
            if (quantity > 1) {
                quantity--;
                itemLabel.setText(getItemDescription());
            }
        }

        private void updateCart() {
            itemLabel.setText(getItemDescription());
            updateTotalPrice();
            cartItemsPanel.revalidate();
            cartItemsPanel.repaint();
        }
    }

    public static void main(String[] args) {
        new selectMenu("포장");
    }
}
