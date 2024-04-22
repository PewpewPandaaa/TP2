package simulator.model;

public interface Observable <T>{
	void addObserver(T o);
	void remomveObserber(T o);
}
