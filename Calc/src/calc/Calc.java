/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import javax.swing.*;

public class Calc extends JFrame {

    private JPanel mainPanel;
    private JPanel line1Panel;
    private JPanel line2Panel;
    private JPanel line3Panel;
    private JPanel line4Panel;
    private JPanel line5Panel;
    private JPanel line6Panel;

    private JLabel screenLabel;

    private JButton buttonEq;
    private JButton buttonCe;
    private JButton buttonZero;
    private JButton buttonDot;
    private JButton buttonDiv;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton buttonMult;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton buttonMinus;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton buttonPlus;

    private StringBuilder screenData = new StringBuilder();

    private double currNum = 0;
    private String currOperation = "";

    private Clipboard clipboard = Toolkit
            .getDefaultToolkit()
            .getSystemClipboard();

    private int currScreenHeight;
    private Dimension screenSizeActual;

    public Calc() throws HeadlessException {
        setTitle("Calculator");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        screenSizeActual = Toolkit.getDefaultToolkit().getScreenSize();

        Dimension screenSizeMin = new Dimension(
                screenSizeActual.width / 7,
                screenSizeActual.height / 3);

        Dimension screenSizeMax = new Dimension(
                screenSizeActual.width / 3,
                screenSizeActual.height / 2);

        setMinimumSize(screenSizeMin);
        setMaximumSize(screenSizeMax);

        setSize(screenSizeMin);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(1, 1));

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));

        /* Line 1. */
        line1Panel = new JPanel();
        line1Panel.setLayout(new BorderLayout());
        line1Panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        screenLabel = new JLabel("0");
        line1Panel.add(screenLabel, BorderLayout.LINE_END);

        /* Line 2. */
        line2Panel = new JPanel();
        line2Panel.setLayout(new GridLayout(1, 4));
        line2Panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        button7 = new JButton("7");
        button8 = new JButton("8");
        button9 = new JButton("9");
        buttonPlus = new JButton("+");
        button7.addActionListener(this::onNumButtonPressed);
        button8.addActionListener(this::onNumButtonPressed);
        button9.addActionListener(this::onNumButtonPressed);
        buttonPlus.addActionListener(this::onOperationButtonPressed);
        line2Panel.add(button7);
        line2Panel.add(button8);
        line2Panel.add(button9);
        line2Panel.add(buttonPlus);

        /* Line 3. */
        line3Panel = new JPanel();
        line3Panel.setLayout(new GridLayout(1, 4));
        line3Panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        button4 = new JButton("4");
        button5 = new JButton("5");
        button6 = new JButton("6");
        buttonMinus = new JButton("-");
        button4.addActionListener(this::onNumButtonPressed);
        button5.addActionListener(this::onNumButtonPressed);
        button6.addActionListener(this::onNumButtonPressed);
        buttonMinus.addActionListener(this::onOperationButtonPressed);
        line3Panel.add(button4);
        line3Panel.add(button5);
        line3Panel.add(button6);
        line3Panel.add(buttonMinus);

        /* Line 4. */
        line4Panel = new JPanel();
        line4Panel.setLayout(new GridLayout(1, 4));
        line4Panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        button1 = new JButton("1");
        button2 = new JButton("2");
        button3 = new JButton("3");
        buttonMult = new JButton("*");
        button1.addActionListener(this::onNumButtonPressed);
        button2.addActionListener(this::onNumButtonPressed);
        button3.addActionListener(this::onNumButtonPressed);
        buttonMult.addActionListener(this::onOperationButtonPressed);
        line4Panel.add(button1);
        line4Panel.add(button2);
        line4Panel.add(button3);
        line4Panel.add(buttonMult);

        /* Line 5. */
        line5Panel = new JPanel();
        line5Panel.setLayout(new GridLayout(1, 4));
        line5Panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        buttonCe = new JButton("CE");
        buttonZero = new JButton("0");
        buttonDot = new JButton(".");
        buttonDiv = new JButton("/");
        buttonCe.addActionListener(this::onCEButtonPressed);
        buttonZero.addActionListener(this::onNumButtonPressed);
        buttonDot.addActionListener(this::onDotButtonPressed);
        buttonDiv.addActionListener(this::onOperationButtonPressed);
        line5Panel.add(buttonCe);
        line5Panel.add(buttonZero);
        line5Panel.add(buttonDot);
        line5Panel.add(buttonDiv);

        /* Line 6. */
        line6Panel = new JPanel();
        line6Panel.setLayout(new BorderLayout());
        line6Panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        buttonEq = new JButton("=");
        buttonEq.addActionListener(this::onEqButtonPressed);
        line6Panel.add(buttonEq);

        add(mainPanel);
        mainPanel.add(line1Panel);
        mainPanel.add(line2Panel);
        mainPanel.add(line3Panel);
        mainPanel.add(line4Panel);
        mainPanel.add(line5Panel);
        mainPanel.add(line6Panel);

        /* ComponentListener for getting current resized frame size. */
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                currScreenHeight = getSize().height;
                adaptFontsSize(currScreenHeight);
            }
        });
    }

    private void adaptFontsSize(int scrHeight) {
        JButton[] buttons = new JButton[]{buttonEq, buttonCe, buttonZero, buttonDot, buttonDiv, button1,
            button2, button3, buttonMult, button4, button5, button6,
            buttonMinus, button7, button8, button9, buttonPlus};
        if (scrHeight >= (screenSizeActual.height / 2.3)) {
            screenLabel.setFont(new Font("SansSerif", Font.BOLD,
                    screenSizeActual.height / 31));
        } else {
            screenLabel.setFont(new Font("SansSerif", Font.BOLD,
                    screenSizeActual.height / 54));
        }

        if (scrHeight >= (screenSizeActual.height / 2)) {
            for (JButton button : buttons) {
                button.setFont(new Font("SansSerif", Font.BOLD,
                        screenSizeActual.height / 54));
            }
        } else {
            for (JButton button : buttons) {
                button.setFont(new Font("SansSerif", Font.BOLD,
                        screenSizeActual.height / 90));
            }
        }
    }

    public static String doubleToString(Double d) {
        if (d == null) {
            return null;
        }
        if (d.isNaN() || d.isInfinite()) {
            return d.toString();
        }

        if (d.doubleValue() == 0) {
            return "0";
        }
        return new BigDecimal(d.toString()).stripTrailingZeros().toPlainString();
    }

    private void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, selection);
    }

    private void updateScreenLabel() {
        screenLabel.setText(screenData.toString());
    }

    private void onNumButtonPressed(ActionEvent evt) {
        int numberEntered = Integer.valueOf(evt.getActionCommand());
        screenData.append(String.valueOf(numberEntered));
        updateScreenLabel();
    }

    private void onCEButtonPressed(ActionEvent evt) {
        screenData.delete(0, screenData.length());
        screenLabel.setText("0");

        StringSelection selection = new StringSelection("");
        clipboard.setContents(selection, null);

        currNum = 0;
        currOperation = "";
    }

    private void onDotButtonPressed(ActionEvent evt) {
        if (screenData.indexOf(".") == -1) {
            if (screenData.length() == 0) {
                screenData.append("0.");
                updateScreenLabel();
            } else {
                screenData.append(".");
                updateScreenLabel();
            }
        }
    }

    private void performCalculation() {
        if (!"".equals(screenData.toString())) {
            if (currNum == 0 && "".equals(currOperation)) {
                currNum = Double.valueOf(screenData.toString());
            } else {
                switch (currOperation) {
                    case "+":
                        currNum = currNum
                                + Double.valueOf(screenData.toString());
                        break;
                    case "-":
                        currNum = currNum
                                - Double.valueOf(screenData.toString());
                        break;
                    case "*":
                        currNum = currNum
                                * Double.valueOf(screenData.toString());
                        break;
                    case "/":
                        if (Double.valueOf(screenData.toString()) != 0) {
                            currNum = currNum
                                    / Double.valueOf(screenData.toString());
                        } else {
                            currNum = 0;
                        }
                        break;
                    case "=":
                        currNum = Double.valueOf(screenData.toString());
                        break;
                }
                copyToClipboard(doubleToString(currNum));
                screenLabel.setText(doubleToString(currNum));
                currOperation = "";
            }
        }
        screenData.delete(0, screenData.length());
    }

    private void onEqButtonPressed(ActionEvent evt) {
        performCalculation();
        currOperation = "=";
    }

    private void onOperationButtonPressed(ActionEvent evt) {
        performCalculation();
        currOperation = evt.getActionCommand();
    }
}
