package es.datastructur.synthesizer;
import java.util.ArrayList;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        first=0;
        last=0;
        fillCount=0;
        rb=(T[]) new Object[capacity];
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if(isFull())
        {
            throw new RuntimeException("Ring buffer overflow");
        }
        else
        {
            rb[last]=x;
            last=(last+1)%(capacity());
            fillCount++;
        }
        return;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if(isEmpty())
        {
            throw new RuntimeException("Ring buffer underflow");
        }
        else
        {
            T dequeuedItem=rb[first];
            first=(first+1)%capacity();
            fillCount--;
            return dequeuedItem;
        }
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        else
        {
            int index = (first + 1) % capacity();
            return rb[index];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T>
    {
        private int currPos;
        private int count;

        public ArrayRingBufferIterator()
        {
            currPos=first;
            count=0;
        }

        @Override
        public boolean hasNext() {
            return count<fillCount;
        }

        @Override
        public T next() {
            T currItem=rb[currPos];
            currPos=(currPos+1)%capacity();
            count++;
            return currItem;
        }
    }

    @Override
    public boolean equals(Object other)
    {
        //compare to itself
        if(other==this)
        {
            return true;
        }
        if(other==null)
        {
            return false;
        }
        if(other.getClass()!=this.getClass())
        {
            return false;
        }
        ArrayRingBuffer<T> o=(ArrayRingBuffer<T>) other;
        if(o.fillCount()!=this.fillCount())
        {
            return  false;
        }
        Iterator<T> thisIterator=this.iterator();
        Iterator<T> otherIterator=o.iterator();
        while(thisIterator.hasNext() && otherIterator.hasNext())
        {
            if(thisIterator.next()!=otherIterator.next())
            {
                return false;
            }
        }
        return true;
    }
    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.
