package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class GateView extends FixedPanel implements ItemListener, MouseListener {
    private final Switch[] switches;
    private final Gate gate;
    private final Light light;

    private final JCheckBox[] inputBoxes;

    private final Image image;
    private Color color;
    int radius = 10;
    int x0 = 210+radius;
    int y0 = 85+radius;

    public GateView(Gate gate) {
        super(245, 200);

        this.gate = gate;

        int inputSize = gate.getInputSize();

        light = new Light();
        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];

        light.connect(0, gate);
        light.setR(255);
        //é necessário setar apenas o R, pois o default é sempre 0, logo, não precisamos mudar G e B

        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();

            gate.connect(i, switches[i]);
        }

        //pegando imagens salvas em resources
        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);



        //quando temos 2 entradas, será necessário ajustar a distância entre as duas CheckBox
        int pos = 50;
        for (JCheckBox inputBox : inputBoxes) {
            if (inputSize ==1){
            add(inputBox,10,77,30,30);}
            else {
                add(inputBox, 10,pos,30,30);
                pos +=55;
            }
        }



        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addItemListener(this);
        }

        addMouseListener(this);
        update();
    }

    private void update() {




        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputBoxes[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }
        //transformando de lights para color
        int r = light.getR();
        int g = light.getG();
        int b = light.getB();
        color = new Color(r,g,b);
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 10, 30, 221, 121, this);


        g.setColor(color);
        //criando o circulo e posicionando no out da porta
        g.fillOval(x0-radius, y0-radius, 2*radius, 2*radius);


        getToolkit().sync();
    }
    @Override
    public void itemStateChanged(ItemEvent event) {

        update();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Color temp;
        int point = (x - x0)*(x-x0) + (y - y0)* (y-y0);
        if (gate.read() ) {
            if (point <radius*radius) {
                //pegando a cor nova
                temp= JColorChooser.showDialog(this, null, color);
                try {
                    light.setR(temp.getRed());
                    light.setG(temp.getGreen());
                    light.setB(temp.getBlue());
                    repaint();
                    update();
                } catch (Exception exception){}



            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
