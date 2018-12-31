import numpy as np
import ListClass as ls

class HashNode:
    def __init__(self, key,value):
        self._value=value
        self._key = key

    def getValue(self):
        return self._value

    def setValue(self,newValue):
        self._value=newValue
    def getKey(self):
        return self._key
    def setKey(self,newKey):
        self._key=newKey
    def printHashNode(self):
        print self._key,self._value
#HashTable Class
class HashTable:
    #Initialization- claculate the size of the hashtable
    def __init__(self, size):
        self._size=size
        if(not self._isPrime(size)):
            #Find the next prime number, in order to decrease the number of collisions
            self._size=self._findNextPrime(size)

        self._table=[ls.List() for _ in range(self._size)]

    #Check if the size is prime
    def _isPrime(self,number):
        if (number < 2): return False
        if (number == 2): return True;
        if (number % 2 == 0): return False;
        x=np.sqrt(number)
        x=np.uint8(x+1)
        for i in range(3,x,2):
           if (number % i == 0 ):
                return False
        return True

    #Find larger next prime number if the size is not prime
    def _findNextPrime(self,number):
        prime=number
        if (number%2==0):
            prime+=1
        found=False
        while(not found):
            found=self._isPrime(prime)
            if (not found):
                prime+=2
        return prime

    def _hashFunction(self,key):
        return key%self._size

    def insert(self,key,value):
        #insert to hashTable only if the key is int
        if (isinstance(key,int)):

            self._table[self._hashFunction(key)].addToTail(HashNode(key,value))
        else:
            print "Insert to HashTable failed, illegal key"

    #Finds the value when key is given
    def getValue(self,key):

        #calculate the index in hashtable according to key
        pos=self._hashFunction(key)
        currNode = self._table[pos].getHead()
        #loop over the list for this index
        while(currNode!=None):
            if(currNode.getData().getKey()==key):
                return currNode.getData().getValue()
            else:
                currNode = self._table[pos].getNextNode(currNode)
        return None

    #Finds the given key and deletes the key+value
    def deleteKey(self,key):
        #calculate the index in hashtable according to key
        pos=self._hashFunction(key)
        currNode = self._table[pos].getHead()
        while(currNode!=None):
            if(currNode.getData().getKey()==key):
                self._table[pos].remove(currNode.getData())
                return True
            else:
                currNode = self._table[pos].getNextNode(currNode)
        return False

    def printHashTable(self):
        #loop over all the indexes
        for i in range(self._size):
            x = self._table[i].getHead()
            #loop over the list for each index
            for j in range (self._table[i].listSize()):

                print  i,
                x.getData().printHashNode()
                x=self._table[i].getNextNode(x)
