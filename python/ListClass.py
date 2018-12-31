# Class Node for List

class Node:
    def __init__(self,data):
        self._data=data
        self._next=None
        self._prev=None

    def getData(self):
        return self._data

    def setData(self,newData):
        self._data=newData

    def getNext(self):
        return self._next

    def setNext(self,newNext):
        self._next=newNext

    def getPrev(self):
        return self._prev

    def setPrev(self,newPrev):
        self._prev=newPrev

# Class List

class List:
    #Initialization
    def __init__(self):
        self._items=[]
        self._head=None
        self._tail=None
        self._count=0

    def getHeadData(self):
        if(self.isEmpty()):
            return None
        return self._head._data

    def isEmpty(self):
        return self._head==None

    def getHead(self):
        if(self.isEmpty()):
            return None
        return self._head

    def getTail(self):
        if(self.isEmpty()):
            return None
        return self._tail

    def getNextNode(self,currNode):
        if(currNode==None):
            return None
        else:
            return currNode.getNext()

    #Add data at the beggining of the list
    def addToHead(self,data):
        temp=Node(data)
        temp.setNext(self._head)
        if(self._head==None):
            self._tail=temp
        else:
            self._head.setPrev(temp)

        self._head=temp
        self._count+=1

    #Add data at the end of the list
    def addToTail(self,data):
        temp=Node(data)
        if (self._tail == None):
            self._head=temp
        else:
            temp.setPrev(self._tail)
            self._tail.setNext(temp)
        self._tail=temp
        self._count+=1

    def listSize(self):
        return self._count

    def printList(self):
        if(self.isEmpty()):
            return

        curr = self._head
        while curr!=None:
            print curr._data
            curr=curr.getNext()

    def searchNode(self,data):
        foundNode=self._search(data)
        if(foundNode==None):
            return False
        else:
            return True

    def _search(self,x):

        curr = self._head
        while curr!=None:
            if (x==curr._data):
                return curr
            curr=curr.getNext()
        return None

    #Remove data from list
    def remove(self,data):
        curr = self._head

        while (curr!=None):
            if(curr._data==data):
                self._count-=1
                if(self._head==self._tail):
                    self._head=self._tail=None
                elif(curr==self._head):
                    self._head=curr._next
                    curr.getNext().setPrev(None)
                elif(curr==self._tail):
                    self._tail = curr._prev
                    curr.getPrev().setNext(None)
                else:
                    curr.getPrev().setNext(curr._next)
                    curr.getNext().setPrev(curr._prev)
                return

            curr=curr.getNext()

    #Add new data after given data
    def addAfter(self,data,newData):

        if(data==self._tail._data):
            self.addToTail(newData)
            return True
        currNode=self._search(data)
        if(currNode==None):
            return False

        else:
            newNode=Node(newData)
            newNode.setPrev(currNode)
            newNode.setNext(currNode.getNext())
            currNode.setNext(newNode)
            currNode.getNext().setPrev(newNode)
        self._count+=1
        return True







