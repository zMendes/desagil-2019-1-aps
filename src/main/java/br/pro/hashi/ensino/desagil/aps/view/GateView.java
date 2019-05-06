// FONTE DAS IMAGENS: https://en.wikipedia.org/wiki/Logic_gate (domínio público)

package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

public class GateView extends FixedPanel implements ItemListener {
    private static final int BORDER = 10;
    private static final int SWITCH_SIZE = 18;
    private static final int GATE_WIDTH = 90;
    private static final int GATE_HEIGHT = 60;

    private final Switch[] switches;
    private final Gate gate;
    private final JCheckBox[] inputBoxes;
    private final JCheckBox[] outputBoxes;
    private final Image image;

    public GateView(Gate gate) {
        super(BORDER + SWITCH_SIZE + GATE_WIDTH + SWITCH_SIZE + BORDER, GATE_HEIGHT);

        this.gate = gate;

        int inputSize = gate.getInputSize();
        int outputSize = gate.getOutputSize();

        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];
        outputBoxes = new JCheckBox[outputSize];

        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();

            gate.connect(i, switches[i]);
        }
        for (int i=0; i<outputSize; i++) {
            outputBoxes[i]= new JCheckBox();
        }
        int x, y, step;

        x = BORDER;
        y = -(SWITCH_SIZE / 2);
        step = (GATE_HEIGHT / (inputSize + 1));
        for (JCheckBox inputBox : inputBoxes) {
            y += step;
            add(inputBox, x, y, SWITCH_SIZE, SWITCH_SIZE);
        }

        int yOut = (GATE_HEIGHT - SWITCH_SIZE)/2;
        for (JCheckBox outputBox : outputBoxes) {
            if (outputSize >1){
                add(outputBox, BORDER + SWITCH_SIZE + GATE_WIDTH, yOut+10, SWITCH_SIZE, SWITCH_SIZE);
                yOut -= 20;
            }else {
                add(outputBox, BORDER + SWITCH_SIZE + GATE_WIDTH, yOut, SWITCH_SIZE, SWITCH_SIZE);
            }}
        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addItemListener(this);
        }
        for (JCheckBox outputBox: outputBoxes) {
            outputBox.setEnabled(false);
        }
        update();
    }

    private void update() {
        boolean result;
        boolean result2;
        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputBoxes[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }

        //Eu sei que é hardcode mas ¯\_(ツ)_/¯
        if (gate.getOutputSize() >1)
        {result = gate.read(0);
        result2 = gate.read(1);

        outputBoxes[0].setSelected(result2);
        outputBoxes[1].setSelected(result);


        }
        else{result = gate.read();
        for (JCheckBox outputBox : outputBoxes){
            outputBox.setSelected(result);}}
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, BORDER + SWITCH_SIZE, 0, GATE_WIDTH, GATE_HEIGHT, this);

        getToolkit().sync();
    }
}
