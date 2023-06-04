public interface StackInt<T>{
    T push(T obj);
    T peek();
    T pop();
    boolean isEmpty();
}
