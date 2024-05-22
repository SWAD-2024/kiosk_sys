package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class selectMenu extends JFrame implements ActionListener {

    private String selectedOption;
    private JPanel menuPanel;
    private DefaultListModel<String> cartModel;
    private JLabel timerLabel;
    private Timer timer;
    private int timeRemaining = 200;

    public selectMenu(String selectedOption) {
        this.selectedOption = selectedOption;

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
        bottomPanel.setLayout(new GridLayout(1, 2));

        // 장바구니 패널 생성
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout());
        cartModel = new DefaultListModel<>();
        JList<String> cartList = new JList<>(cartModel);
        cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        // 결제 및 타이머 패널 생성
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new BorderLayout());

        // 타이머 라벨 추가
        timerLabel = new JLabel("Time remaining: " + timeRemaining + " seconds", JLabel.CENTER);
        paymentPanel.add(timerLabel, BorderLayout.NORTH);

        // 결제 버튼 추가
        JButton paymentButton = new JButton("결제");
        paymentPanel.add(paymentButton, BorderLayout.SOUTH);

        // 하단 패널에 장바구니와 결제 패널 추가
        bottomPanel.add(cartPanel);
        bottomPanel.add(paymentPanel);

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
                cartModel.addElement(command);
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
                    //JOptionPane.showMessageDialog(null, "Time is up!");
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

    public static void main(String[] args) {
        // 예시로 실행해보기 위한 코드 (실제 사용 시 startMenuSelect에서 호출됨)
        new selectMenu("포장");
    }
}
