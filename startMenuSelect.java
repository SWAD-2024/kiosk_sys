import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class startMenuSelect extends JFrame implements ActionListener {

    private JButton takeoutButton;
    private JButton dineInButton;
    private String selectedOption;

    public startMenuSelect() {
        // 창의 제목 설정
        setTitle("키오스크 시작 화면");

        // 창의 크기 설정
        setSize(400, 800);

        // 창 닫기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 배경 패널 생성 및 설정
        BackgroundPanel background = new BackgroundPanel(new ImageIcon("hamburger.png").getImage());

        // 레이아웃 설정
        background.setLayout(new BorderLayout());

        // 상단 라벨 추가
        JLabel titleLabel = new JLabel("단맹쏘의 햄버거 가게", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);  // 텍스트 색상 변경
        background.add(titleLabel, BorderLayout.NORTH);

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setOpaque(false);  // 버튼 패널의 배경을 투명하게 설정

        // 포장 버튼 생성 및 패널에 추가
        takeoutButton = new JButton("포장");
        takeoutButton.addActionListener(this);
        buttonPanel.add(takeoutButton);

        // 매장 버튼 생성 및 패널에 추가
        dineInButton = new JButton("매장");
        dineInButton.addActionListener(this);
        buttonPanel.add(dineInButton);

        // 버튼 패널을 중앙에 추가
        background.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

        // 배경 패널을 프레임에 추가
        add(background);

        // 창을 화면에 표시
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == takeoutButton) {
            selectedOption = "포장";
            //JOptionPane.showMessageDialog(this, "포장을 선택하셨습니다.");
            openSelectMenu();
        } else if (e.getSource() == dineInButton) {
            selectedOption = "매장";
            //JOptionPane.showMessageDialog(this, "매장을 선택하셨습니다.");
            openSelectMenu();
        }
    }

    private void openSelectMenu() {
        // 선택된 옵션을 저장하는 코드 추가 가능 (파일, 데이터베이스 등)
        System.out.println("선택된 옵션: " + selectedOption);

        // selectMenu.java 실행
        new selectMenu(selectedOption);

        // 현재 창 닫기
        dispose();
    }

//    public static void main(String[] args) {
//        // 프로그램 실행
//        new startMenuSelect();
//    }

    // 배경 이미지를 그리기 위한 커스텀 패널 클래스
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
