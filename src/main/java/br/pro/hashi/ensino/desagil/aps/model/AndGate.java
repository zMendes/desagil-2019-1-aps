package br.pro.hashi.ensino.desagil.aps.model;

public class AndGate extends Gate {
    private final NandGate nand;
    private final NandGate nand2;


    public AndGate() {
        super(2);

        nand = new NandGate();
        nand2 = new NandGate();
    }


    @Override
    public boolean read() {
        return nand2.read();
    }


    @Override
    public void connect(int inputPin, SignalEmitter emitter) {
        if (inputPin < 0 | inputPin >1) {
            throw new IndexOutOfBoundsException(inputPin);
        }
        nand.connect(inputPin, emitter);
        nand.connect(inputPin, emitter);

        nand2.connect(1, nand);
        nand2.connect(0, nand);
    }
}
