package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.SignalEmitter;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GateView extends JPanel implements ActionListener {
    private final Gate gate;

    private final JCheckBox entrada1Box;
    private final JCheckBox entrada2Box;
    private final JCheckBox saidaBox;

    private final Switch switch1 = new Switch();
    private final Switch switch2 = new Switch();

    public GateView(Gate gate) {
        this.gate = gate;

        entrada1Box = new JCheckBox();
        entrada2Box = new JCheckBox();
        saidaBox = new JCheckBox();

        JLabel entradaLabel = new JLabel("Entrada");
        JLabel saidaLabel = new JLabel("Saída");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        saidaBox.setEnabled(false);
        entrada1Box.addActionListener(this);
        entrada2Box.addActionListener(this);


        if (gate.getInputSize() != 1) {
            add(entradaLabel);
            add(entrada1Box);
            add(entrada2Box);
            add(saidaLabel);
            add(saidaBox);

            this.gate.connect(0, switch1);
            this.gate.connect(1, switch2);
        }   
          else {
            add(entradaLabel);
            add(entrada1Box);
            add(saidaLabel);
            add(saidaBox);
            this.gate.connect(0, switch1);
        }

        update();
    }

    private void update() {
        if (entrada1Box.isSelected()) {
            switch1.turnOn();
        } 
        else
            {switch1.turnOff();
        }
        
        if (entrada2Box.isSelected()) {
            switch2.turnOn(); 
        }
        else
            {switch2.turnOff();
        }
        
        boolean saida = this.gate.read();
        saidaBox.setSelected(saida);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    update();
    }
}
