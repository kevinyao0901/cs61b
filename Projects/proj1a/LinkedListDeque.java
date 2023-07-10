import javax.swing.*;

public class LinkedListDeque<T> {
    public class Node
    {
        public T item;
        public Node pre;
        public Node next;

        public Node(T x,Node ppre,Node nnext)
        {
            item=x;
            pre=ppre;
            next=nnext;
        }

        public Node(Node ppre,Node nnext)
        {
            pre=ppre;
            next=nnext;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque()
    {
        sentinel=new Node(null,null);
        sentinel.next=sentinel;
        sentinel.pre=sentinel;
        size=0;
    }
    public boolean isEmpty()
    {
        return size==0;
    }

    public int size()
    {
        return size;
    }

    public void addFirst(T item)
    {
        Node NewNode=new Node(item,sentinel,sentinel.next);
        sentinel.next.pre=NewNode;
        sentinel.next=NewNode;
        size++;
    }

    public void addLast(T item)
    {
        Node NewNode=new Node(item,sentinel.pre,sentinel);
        sentinel.pre.next=NewNode;
        sentinel.pre=NewNode;
        size++;
    }

    public T removeFirst()
    {
        if(size==0)
        {
            return null;
        }
        else
        {
            T ret=sentinel.next.item;
            sentinel.next.next.pre=sentinel;
            sentinel.next=sentinel.next.next;
            size--;
            return ret;
        }
    }

    public T removeLast()
    {
        if(size==0)
        {
            return null;
        }
        else
        {
            T ret=sentinel.pre.item;
            sentinel.pre.pre.next=sentinel;
            sentinel.pre=sentinel.pre.pre;
            size--;
            return ret;
        }
    }

    public T get(int index)
    {
        if(index>=size)
        {
            return null;
        }
        else
        {
            Node flag=sentinel;
            for(int i=0;i<=index;i++)
            {
                flag=flag.next;
            }
            return  flag.item;
        }
    }

    private T getRecursiveHelp(Node start, int index)
    {
        if(index==0)
        {
            return  start.item;
        }
        else
        {
            return getRecursiveHelp(start.next,index-1);
        }
    }

    public T getRecursive(int index)
    {
        if(index>=size)
        {
            return null;
        }
        else
        {
            return getRecursiveHelp(sentinel.next,index);
        }
    }

    public void printDeque() {
        Node ptr = sentinel.next;
        while (ptr != sentinel) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
    }
}
