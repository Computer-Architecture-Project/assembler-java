package simulator;

import java.util.ArrayList;
import java.util.Iterator;

import binary.Binary2C;

public class Register implements Iterable<Binary2C>{
    private ArrayList<Binary2C> register;
    
    public Register() {
        this.register = new ArrayList<Binary2C>();
        for (Integer i = 0; i < 8; i++) {
            this.register.add(new Binary2C(0));
        }
    }

    public Binary2C getRegister(Integer regNo) {
        return this.register.get(regNo);
    }

    public void setRegister(Integer regNo, Integer value) {
        if(regNo == 0) return;
        this.register.set(regNo, new Binary2C(value));
    }

	@Override
	public Iterator<Binary2C> iterator() {
		return this.register.iterator();
	}
}
