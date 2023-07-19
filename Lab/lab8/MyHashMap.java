import com.sun.nio.sctp.IllegalReceiveException;

import java.beans.PropertyEditorManager;
import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K,V> implements Map61B<K,V>{

    private static final int INITIAL_CAPACITY=16;
    private static final double LOAD_FACTOR=0.75;

    private  int size;
    private int threshold;
    private BucketEntity<K,V>[] buckets;

    private class BucketEntity<K,V>
    {
        private K key;
        private V value;
        private BucketEntity<K,V>next;
        private int hashcode;

        private BucketEntity(int hashcode,K key,V value,BucketEntity<K,V> next)
        {
            this.hashcode=hashcode;
            this.key=key;
            this.value=value;
            this.next=next;
        }

        public int getHashcode()
        {
            return hashcode;
        }

        public void setHashcode(int hashcode)
        {
            this.hashcode=hashcode;
        }

        public K getKey()
        {
            return key;
        }

        public  void setKey()
        {
            this.key=key;
        }

        public V getValue()
        {
            return value;
        }

        public void setValue(V value)
        {
            this.value=value;
        }

        public BucketEntity<K,V> getNext()
        {
            return next;
        }

        public void setNext(BucketEntity<K,V> next)
        {
            this.next=next;
        }
    }

    public MyHashMap()
    {
        buckets=new BucketEntity[INITIAL_CAPACITY];
        threshold=(int)(INITIAL_CAPACITY*LOAD_FACTOR);
        size=0;
    }

    public MyHashMap(int initialSize)
    {
        buckets=new BucketEntity[initialSize];
        threshold=(int)(initialSize* LOAD_FACTOR);
        size=0;
    }

    public MyHashMap(int initialSize,double loadFactor)
    {
        buckets=new BucketEntity[initialSize];
        threshold=(int)(initialSize*loadFactor);
        size=0;
    }

    @Override
    protected void finalize() throws Throwable {

    }

    @Override
    public void clear() {
        buckets=new BucketEntity[buckets.length];
        size=0;
    }

    @Override
    public boolean containsKey(K key) {
        if(key==null)
        {
            throw new IllegalArgumentException();
        }
        return get(key)!=null;
    }

    private int hash(K key,int length)
    {
        if(key==null)
        {
            throw new IllegalArgumentException();
        }
        return (key.hashCode()&0x7fffffff)%length;
    }

    @Override
    public V get(K key) {
        if(key==null)
        {
            throw new IllegalArgumentException();
        }
        int hashcode=hash(key, buckets.length);
        BucketEntity<K,V> entity=get(hashcode,key);
        return entity==null?null:entity.getValue();
    }

    private BucketEntity<K,V> get(int hashCode,K key)
    {
        BucketEntity<K,V> entity=buckets[hashCode];
        while(entity!=null)
        {
            if(entity.getHashcode()==hashCode && entity.getKey().equals(key))
            {
                return entity;
            }
            entity=entity.getNext();
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        int hashcode=hash(key, buckets.length);
        BucketEntity<K,V> entity=buckets[hashcode];
        while(entity!=null)
        {
            if(entity.getHashcode()==hashcode && entity.getKey().equals(key))
            {
                entity.setValue(value);
                return;
            }
            entity=entity.getNext();
        }
        put(hashcode,key,value);
    }

    private void put(int hashcode,K key,V value)
    {
        BucketEntity<K,V> entity=new BucketEntity<>(hashcode,key,value,buckets[hashcode]);
        buckets[hashcode]=entity;
        size+=1;
        if(size>threshold)
        {
            resize(buckets.length*2);
        }
    }

    public  void resize(int capacity)
    {
        BucketEntity<K,V>[] newBuckets=new BucketEntity[capacity];
        for(int i=0;i<buckets.length;i++)
        {
            BucketEntity<K,V> entity=buckets[i];
            while(entity!=null)
            {
                BucketEntity<K,V> oldNext=entity.getNext();
                int newHashCode=hash(entity.getKey(),newBuckets.length);
                entity.setNext(newBuckets[newHashCode]);
                entity.setHashcode(newHashCode);
                newBuckets[newHashCode]=entity;
                entity=oldNext;
            }
        }
        buckets=newBuckets;
        threshold=(int)(buckets.length*LOAD_FACTOR);
    }

    @Override
    public Set keySet() {
        Set<K> allKeys=new HashSet<>();
        for(int i=0;i<buckets.length;i++)
        {
            BucketEntity<K,V> entity=buckets[i];
            while(entity!=null)
            {
                allKeys.add(entity.getKey());
                entity=entity.getNext();
            }
        }
        return allKeys;
    }

    @Override
    public V remove(K key) {
        if(key==null)
        {
            throw new IllegalArgumentException();
        }
        int hashCode=hash(key,buckets.length);
        return remove(hashCode,key);
    }

    private V remove(int hashCode,K key)
    {
        BucketEntity<K,V> entity=buckets[hashCode];
        BucketEntity<K,V> nextEntity=entity.getNext();
        if(entity.getKey().equals(key))
        {
            V toRemove=entity.getValue();
            buckets[hashCode]=nextEntity;
            size-=1;
            return toRemove;
        }
        else
        {
            while(!nextEntity.getKey().equals(key))
            {
                entity=entity.getNext();
                nextEntity=nextEntity.getNext();
            }
            V toRemove=nextEntity.getValue();
            entity.setNext(nextEntity.getNext());
            size-=1;
            return  toRemove;
        }
    }

    @Override
    public V remove(K key, V value) {
        if(key==null)
        {
            throw new IllegalArgumentException();
        }
        int hashCode=hash(key,buckets.length);
        return remove(hashCode,key,value);
    }

    private V remove(int hashCode,K key,V value)
    {
        BucketEntity<K,V> entity=buckets[hashCode];
        BucketEntity<K,V> nextEntity=entity.getNext();
        if(entity.getKey().equals(key) && entity.getValue().equals(value))
        {
            V toRemove=entity.getValue();
            buckets[hashCode]=nextEntity;
            size-=1;
            return toRemove;
        }
        else
        {
            while(!nextEntity.getKey().equals(key))
            {
                entity=entity.getNext();
                nextEntity=nextEntity.getNext();
            }
            if(nextEntity.getValue().equals(value))
            {
                V toRemove=nextEntity.getValue();
                entity.setNext(nextEntity.getNext());
                size-=1;
                return toRemove;
            }
        }
        return null;
    }

    @Override
    public Iterator iterator() {
        return keySet().iterator();
    }
}
