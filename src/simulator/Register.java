package simulator;

import java.util.ArrayList;
import java.util.Iterator;

public class Register implements Iterable<Object>{
    private ArrayList<Object> register;
    
    public Register() {
        this.register = new ArrayList<Object>();
    }

    public Object getRegister(Integer regNo) {
        return this.register.get(regNo);
    }

    public void setRegister(Integer regNo, Object value) {
        if(regNo == 0) return;
        this.register.set(regNo, value);
    }

	@Override
	public Iterator iterator() {
		return this.register.iterator();
	}
}
