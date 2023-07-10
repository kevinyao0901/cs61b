public class ArrayDeque<T> implements Deque<T> {

    public int size;
    public int nextFirst;
    public int nextLast;
    public T[] items;

    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<>();
    }

    private boolean isFull()
    {
        return size== items.length;
    }

    private boolean isSparse() {
        return items.length >= 16 && size < (items.length / 4);
    }

    private int plusOne(int index)
    {
        return (index+1)%items.length;
    }

    private int minusOne(int index)
    {
        return (index-1+ items.length)% items.length;
    }

    private void resize(int capacity)
    {
        T[] newDdeque=(T[]) new Object[capacity];
        int oldIndex=plusOne(nextFirst);
        for(int newIndex=0;newIndex<size;newIndex++)
        {
            newDdeque[newIndex]=items[oldIndex];
            oldIndex=plusOne(oldIndex);
        }
        items=newDdeque;
        nextFirst=capacity-1;
        nextLast=size;
    }

    private void upSize()
    {
        resize(size*2);
    }

    private void downSize()
    {
        resize(items.length/2);
    }

    @Override
    public void addFirst(T item) {
        if(isFull())
        {
            upSize();
        }
        items[nextFirst]=item;
        nextFirst=minusOne(nextFirst);
        size+=1;
    }

    @Override
    public void addLast(T item) {
        if(isFull())
        {
            upSize();
        }
        items[nextLast]=item;
        nextLast=plusOne(nextLast);
        size+=1;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for(int i=plusOne(nextFirst);i<nextLast;i=plusOne(i))
        {
            System.out.print(items[i]+" ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if(isSparse())
        {
            downSize();
        }
        nextFirst=plusOne(nextFirst);
        T toRemove=items[nextFirst];
        items[nextFirst]=null;
        if(!isEmpty())
        {
            size-=1;
        }
        return toRemove;
    }

    @Override
    public T removeLast() {
        if(isSparse())
        {
            downSize();
        }
        nextLast=minusOne(nextLast);
        T toRemove=items[nextLast];
        items[nextLast]=null;
        if(!isEmpty())
        {
            size-=1;
        }
        return toRemove;
    }

    @Override
    public T get(int index) {
        if(index>size)
        {
            return null;
        }
        int start=plusOne(nextFirst);
        return items[(start+index)% items.length];
    }


    public ArrayDeque() {
        items=(T[])new Object[8];
        size=0;
        nextFirst=0;
        nextLast=1;
    }

    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[other.size];
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        size = other.size;

        System.arraycopy(other.items, 0, items, 0, other.size);
    }
}